<template>
  <div>
    <a-card :bordered="false">
      <SearchForm @search="(data)=>this.search(data)" />

      <div class="mt-10">
        <a-button type="primary" icon="plus" @click="showEditContext(true)">新增</a-button>
        <a-button v-if="table.selectedRowKeys.length > 0" type="danger" icon="delete" class="ml-5" @click="batchDel">
          批量删除
        </a-button>
      </div>

      <a-alert show-icon type="info" class="mt-10">
        <template slot="message">
          已选择
          <span class="select-count">{{ selectCount }}</span> 项
          <a class="select-clear" @click="clearSelected">清空</a>
        </template>
      </a-alert>

      <s-table
        ref="table"
        class="mt-10"
        row-key="id"
        :columns="table.columns"
        :data="table.loadData"
        :alert="false"
        :row-selection="rowSelection"
        show-pagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>

        <template slot="role" slot-scope="text,record">
          <a-tag v-if="record['role']===1" color="#52c41a">主机管理员</a-tag>
          <a-tag v-if="record['role']===2" color="#1890ff">流量监管员</a-tag>
          <a-tag v-if="record['role']===3" color="#faad14">日志管理员</a-tag>
          <a-tag v-else color="#108ee9">超级管理员</a-tag>
        </template>

        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="handleEdit(record)">编辑</a>
            <a-divider type="vertical" />
            <a @click="resetPass(record.id)">重置密码</a>
          </template>
        </span>
      </s-table>
    </a-card>

    <a-modal
      :visible="editForm.show"
      :title="editForm.title"
      :confirm-loading="editForm.loading"
      @ok="handleSubmit"
      @cancel="editForm.show=false"
    >
      <a-form-model
        ref="editForm"
        :model="editForm.form"
        :rules="editForm.rules"
        :label-width="100"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 20 }"
      >
        <a-form-model-item label="登录账号" prop="account">
          <a-input v-model="editForm.form.account" placeholder="请输入登录账号" />
        </a-form-model-item>
        <a-form-model-item label="用户姓名" prop="username">
          <a-input v-model="editForm.form.username" placeholder="请输入用户姓名" />
        </a-form-model-item>
        <a-form-model-item label="登录密码" prop="password">
          <a-input v-model="editForm.form.password" placeholder="请输入登录密码" />
        </a-form-model-item>
        <a-form-model-item label="角色" prop="role">
          <a-radio-group v-model="editForm.form.role" :default-value="0" button-style="solid">
            <a-radio-button :value="1">
              主机管理员
            </a-radio-button>
            <a-radio-button :value="2">
              流量监管员
            </a-radio-button>
            <a-radio-button :value="3">
              日志管理员
            </a-radio-button>
            <a-radio-button :value="0">
              超级管理员
            </a-radio-button>
          </a-radio-group>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import { STable } from '@/components'
import SearchForm from './components/SearchForm'
import { delUser, editUser, judgeUserField, listUser, resetPass } from '@/api/user'
import cloneDeep from 'lodash.clonedeep'
import sha1 from 'sha1'
import { parseFormData } from '@/utils'

export default {
  name: 'User',
  components: {
    SearchForm,
    STable
  },
  data () {
    const validateAccount = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户登录账号'))
        return
      }
      judgeUserField(this.editForm.form.id, 'account', value).then((response) => {
        const { data } = response
        if (data === 'fail') {
          callback(new Error('登录账号已存在'))
        }
        callback()
      })
    }

    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名称'))
        return
      }
      judgeUserField(this.editForm.form.id, 'username', value).then((response) => {
        const { data } = response
        if (data === 'fail') {
          callback(new Error('用户名称已存在'))
        }
        callback()
      })
    }

    return {
      searchForm: {
        form: {}
      },
      table: {
        columns: [{
          title: '#',
          align: 'center',
          scopedSlots: { customRender: 'serial' }
        }, {
          align: 'center',
          dataIndex: 'account',
          title: '登录账号'
        }, {
          align: 'center',
          title: '用户名称',
          dataIndex: 'username'
        },
        {
          align: 'center',
          dataIndex: 'role',
          title: '角色',
          scopedSlots: { customRender: 'role' }
        }, {
          align: 'center',
          width: '150px',
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }],
        loadData: parameter => {
          const pageData = {
            pageSize: parameter.pageSize,
            pageNumber: parameter.pageNo
          }
          const requestParameters = Object.assign({}, pageData, this.searchForm.form)
          return listUser(requestParameters).then(res => {
            res['pageNo'] = parameter['pageNo']
            return res
          })
        },
        selectedRowKeys: [],
        selectedRows: [],
        filter: {}
      },
      editForm: {
        show: false,
        loading: false,
        title: '新增',
        form: {
          id: 0,
          account: '',
          username: '',
          password: '',
          role: 0,
          isSuper: false
        },
        rules: {
          account: { required: true, trigger: 'blur', validator: validateAccount },
          username: { required: true, trigger: 'blur', validator: validateName }
        }
      }

    }
  },
  computed: {
    rowSelection () {
      return {
        selectedRowKeys: this.table.selectedRowKeys,
        onChange: this.onTableSelectChange
      }
    },
    selectCount () {
      return this.table.selectedRowKeys.length
    }
  },
  created () {

  },
  methods: {
    search (data) {
      this.searchForm.form = data
      this.$refs.table.refresh()
    },
    onTableSelectChange (selectedRowKeys, selectedRows) {
      this.table.selectedRowKeys = selectedRowKeys
      this.table.selectedRows = selectedRows
    },
    // 显示信息编辑form
    showEditContext (isAdd) {
      this.editForm.show = true
      this.editForm.loading = false
      // 初始化ID，方式编辑后残留数据值
      this.editForm.form.id = 0
      if (isAdd) {
        this.$nextTick(() => {
          this.$refs.editForm.resetFields()
        })
      }
    },
    handleEdit (record) {
      this.editForm.title = '编辑'
      this.showEditContext(false)
      parseFormData(this.editForm.form, record)
      this.editForm.form.password = ''
      this.editForm.form.isSuper = this.editForm.form.isSuper === 1
    },
    // 处理表单提交事件
    handleSubmit () {
      this.editForm.loading = true
      const requestData = cloneDeep(this.editForm.form)
      requestData['password'] = sha1(requestData['password'])
      requestData['isSuper'] = requestData['isSuper'] ? 1 : 0
      editUser(requestData).then(() => {
        this.$refs.table.refresh(true)
        this.editForm.show = false
      }).finally(() => {
        this.editForm.loading = false
      })
    },
    clearSelected () {
      this.table.selectedRows = []
      this.table.selectedRowKeys = []
    },
    batchDel () {
      const _this = this
      this.$confirm({
        title: '系统提示',
        content: '是否确认删除',
        onOk () {
          return new Promise((resolve) => {
            delUser(_this.table.selectedRowKeys.join(',')).then(() => {
              _this.table.selectedRowKeys = []
              _this.table.selectedRows = []
              _this.$message.success('删除成功')
              _this.$refs.table.refresh(true)
              resolve()
            })
          }).catch(() => console.log('Oops errors!'))
        },
        onCancel () {
        }
      })
    },
    resetPass (id) {
      const _this = this
      this.$confirm({
        title: '系统提示',
        content: '是否确认重置密码',
        onOk () {
          return new Promise((resolve) => {
            resetPass(id).then(() => {
              _this.$message.success('重置成功')
              resolve()
            })
          }).catch(() => console.log('Oops errors!'))
        },
        onCancel () {
        }
      })
    }
  }
}
</script>
