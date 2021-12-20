<template>
  <div>
    <a-card :bordered="false">
      <SearchForm @search="(data)=>search(data)" />

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

        <template slot="isSuper" slot-scope="text,record">
          <a-tag v-if="record['isSuper']===1" color="#108ee9">是</a-tag>
          <a-tag v-else>否</a-tag>
        </template>

        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="handleEdit(record)">编辑</a>
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
        <a-form-model-item label="配置名称" prop="name">
          <a-input v-model="editForm.form.name" placeholder="请输入配置名称" />
        </a-form-model-item>
        <a-form-model-item label="键" prop="key">
          <a-input v-model="editForm.form.key" placeholder="请输入键" />
        </a-form-model-item>
        <a-form-model-item label="值" prop="value">
          <a-input v-model="editForm.form.value" placeholder="值" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import { STable } from '@/components'
import SearchForm from './components/SearchForm'
import { delShreshold, editShreshold, judgeShresholdField, listShreshold } from '@/api/shreshold'
import cloneDeep from 'lodash.clonedeep'
import { parseFormData } from '@/utils'

export default {
  name: 'Shreshold',
  components: {
    SearchForm,
    STable
  },
  data () {
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入配置名称'))
        return
      }
      judgeShresholdField(this.editForm.form.id, 'name', value).then((response) => {
        const { data } = response
        if (data === 'fail') {
          callback(new Error('配置名称已存在'))
        }
        callback()
      })
    }

    const validateKey = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入键'))
        return
      }
      judgeShresholdField(this.editForm.form.id, 'key', value).then((response) => {
        const { data } = response
        if (data === 'fail') {
          callback(new Error('键已存在'))
        }
        callback()
      })
    }
    const validateValue = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入值'))
        return
      }
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
          dataIndex: 'name',
          title: '配置名称'
        }, {
          align: 'center',
          title: '键',
          dataIndex: 'key'
        }, {
          align: 'center',
          dataIndex: 'value',
          title: '值'
        },
        {
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
          return listShreshold(requestParameters).then(res => {
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
          name: '',
          key: '',
          value: ''
        },
        rules: {
          name: { required: true, trigger: 'blur', validator: validateName },
          key: { required: true, trigger: 'blur', validator: validateKey },
          value: { required: true, trigger: 'blur', validator: validateValue }
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
    },
    // 处理表单提交事件
    handleSubmit () {
      this.editForm.loading = true
      const requestData = cloneDeep(this.editForm.form)
      editShreshold(requestData).then(() => {
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
            delShreshold(_this.table.selectedRowKeys.join(',')).then(() => {
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
    }

  }
}
</script>
