<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="登录账号">
              <a-input v-model="queryParam.account" placeholder="请输入登录账号" />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="用户名称">
              <a-input v-model="queryParam.username" placeholder="请输入用户名称" />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
            <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="showEditContext(true)">新增</a-button>
      <a-button v-if="selectedRowKeys.length > 0" type="danger" icon="delete" @click="batchDel">批量删除</a-button>
    </div>

    <s-table
      ref="table"
      size="default"
      row-key="id"
      :columns="columns"
      :data="loadData"
      :alert="options.alert"
      :row-selection="options.rowSelection"
      show-pagination="auto"
    >
      <span slot="serial" slot-scope="text, record, index">
        {{ index + 1 }}
      </span>

      <template slot="isSuper" slot-scope="text,record">
        <a-tag v-if="record['is_super']===1" color="#108ee9">是</a-tag>
        <a-tag v-else>否</a-tag>
      </template>

      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="resetPass(record.id)">重置密码</a>
        </template>
      </span>

    </s-table>

    <edit-form-dialog
      :show.sync="editForm.show"
      :title="editForm.title"
      :loading="editForm.loading"
      @submit="handleSubmit"
    >
      <a-form ref="editForm" :form="form">
        <a-form-item
          label="登录账号"
          :label-col="editForm.formItemLayout.labelCol"
          :wrapper-col="editForm.formItemLayout.wrapperCol"
        >
          <a-input
            v-decorator="[
              'account',
              {
                rules: [{ required: true, message: '请输入登录账号' }],
                initialValue:editForm.form.account
              },
            ]"
            placeholder="请输入登录账号"
          />
        </a-form-item>
        <a-form-item
          label="用户姓名"
          :label-col="editForm.formItemLayout.labelCol"
          :wrapper-col="editForm.formItemLayout.wrapperCol"
        >
          <a-input
            v-decorator="[
              'username',
              {
                rules: [{ required: true, message: '请输入用户姓名' }],
                initialValue:editForm.form.username
              },
            ]"
            placeholder="请输入用户姓名"
          />
        </a-form-item>
        <a-form-item :label-col="editForm.formTailLayout.labelCol" :wrapper-col="editForm.formTailLayout.wrapperCol">
          <a-checkbox
            v-decorator="[
              'is_super',
              {
                valuePropName: 'checked',
                initialValue: editForm.form.is_super===1,
              },
            ]"
            @change="handleChange"
          >
            超级管理员
          </a-checkbox>
        </a-form-item>
      </a-form>
    </edit-form-dialog>
  </a-card>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import EditFormDialog from '@/components/EditFormDialog'
import { getUserList } from '@/api/user.js'
import { delUser, editUser, resetPass } from '@/api/user'
import { parseFormData } from '@/utils/util.js'

const formItemLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 7 }
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 13 }
  }
}
const formTailLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 7 }
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 13, offset: 7 }
  }
}

export default {
  name: 'User',
  components: {
    STable,
    Ellipsis,
    EditFormDialog
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '#',
          scopedSlots: { customRender: 'serial' }
        },
        {
          title: '登录账号',
          dataIndex: 'account'
        },
        {
          title: '用户名称',
          dataIndex: 'username'
        },
        {
          title: '超级管理员',
          dataIndex: 'is_super',
          scopedSlots: { customRender: 'isSuper' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        console.log('loadData.parameter', parameter)
        return getUserList(Object.assign(parameter, this.queryParam))
          .then(res => {
            return res.data
          })
      },
      selectedRowKeys: [],
      selectedRows: [],

      // custom table alert & rowSelection
      options: {
        alert: {
          show: true,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },

      editForm: {
        show: false,
        title: '',
        loading: false,
        formItemLayout,
        formTailLayout,
        form: {
          id: 0,
          account: '',
          username: '',
          is_super: 0
        }
      }
    }
  },
  created () {
    this.form = this.$form.createForm(this)
  },
  methods: {
    handleOk () {
      this.$refs.table.refresh()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      console.log(selectedRowKeys)
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
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
    },
    // 显示信息编辑form
    showEditContext (isAdd) {
      this.editForm.show = true
      this.editForm.loading = false
      // 初始化ID，防止编辑后残留数据值
      this.editForm.form.id = 0
      this.$nextTick(() => {
        this.form.resetFields()
      })
      if (isAdd) {
        this.editForm.title = '新增用户'
      }
    },
    handleChange (e) {
      this.editForm.form.is_super = e.target.checked ? 1 : 0
    },
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (error !== null) {
          // 说明表单验证未通过
          return
        }
        parseFormData(this.editForm.form, values)
        editUser(this.editForm.form).then(() => {
          this.$message.success('操作成功')
          this.$refs.table.refresh(true)
          this.editForm.loading = false
          this.editForm.show = false
        })
      })
    },
    handleEdit (record) {
      this.editForm.title = '编辑'
      this.showEditContext(false)
      parseFormData(this.editForm.form, record)
      console.log('this.editForm.form', this.editForm.form)
    },
    batchDel () {
      const _this = this
      this.$confirm({
        title: '系统提示',
        content: '是否确认删除',
        onOk () {
          return new Promise((resolve) => {
            delUser(_this.selectedRowKeys.join(',')).then(() => {
              _this.selectedRowKeys = []
              _this.$refs.table.refresh(true)
              _this.$message.success('删除成功')
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
