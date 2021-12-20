<template>
  <div>
    <a-card :bordered="false">
      <CommandSearchForm @search="(data)=>this.search(data)" />

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

        <template slot="content" slot-scope="text">
          <div style="white-space: pre-wrap;">{{ text }}</div>
        </template>

        <template slot="status" slot-scope="text,record">
          <a-badge v-if="record['status']===1" status="success" text="正常" />
          <a-badge v-else status="error" text="禁用" />
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
        <a-form-model-item label="命令名称" prop="name">
          <a-input v-model="editForm.form.name" placeholder="请输入命令名称" />
        </a-form-model-item>
        <a-form-model-item label="命令内容" prop="content" extra="请使用${ip}替代攻击源IP，多行命令直接换行填写">
          <a-input v-model="editForm.form.content" type="textarea" :auto-size="{ minRows: 3, maxRows: 5 }" placeholder="请输入命令内容" />
        </a-form-model-item>
        <a-form-model-item label="备注信息" prop="note">
          <a-input v-model="editForm.form.note" placeholder="请输入备注" />
        </a-form-model-item>
        <a-form-model-item label="是否启用" prop="status">
          <a-switch v-model="editForm.form.status" size="large" checked-children="是" un-checked-children="否" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import { Ellipsis, STable } from '@/components'
import CommandSearchForm from './components/CommandSearchForm'
import cloneDeep from 'lodash.clonedeep'
import { parseFormData } from '@/utils'
import { delCommand, editCommand, judgeCommandField, listCommand } from '@/api/center'

export default {
  name: 'Command',
  components: {
    CommandSearchForm,
    STable,
    Ellipsis
  },
  data () {
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入命令名称'))
        return
      }
      judgeCommandField(this.editForm.form.id, 'name', value).then((response) => {
        const { data } = response
        if (data === 'fail') {
          callback(new Error('命令名称已存在'))
        }
        callback()
      })
    }

    const validateContent = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入命令内容'))
        return
      }
      judgeCommandField(this.editForm.form.id, 'content', value).then((response) => {
        const { data } = response
        if (data === 'fail') {
          callback(new Error('命令内容已存在'))
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
          dataIndex: 'name',
          title: '命令名称'
        }, {
          dataIndex: 'content',
          title: '命令内容',
          scopedSlots: { customRender: 'content' }
        }, {
          title: '备注',
          dataIndex: 'note'
        }, {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        }, {
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
          return listCommand(requestParameters).then(res => {
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
          content: '',
          note: '',
          status: true
        },
        rules: {
          name: { required: true, trigger: 'blur', validator: validateName },
          content: { required: true, trigger: 'blur', validator: validateContent }
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
      this.editForm.form.status = this.editForm.form.status === 1
    },
    // 处理表单提交事件
    handleSubmit () {
      this.editForm.loading = true
      const requestData = cloneDeep(this.editForm.form)
      requestData['status'] = requestData['status'] ? 1 : 2
      editCommand(requestData).then(() => {
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
            delCommand(_this.table.selectedRowKeys.join(',')).then(() => {
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
