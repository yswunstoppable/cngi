<template>
  <div>
    <a-card :bordered="false">
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
        <template slot="boundary" slot-scope="text,record">
          <div style="white-space: pre-wrap">{{ getBoundary(record) }}</div>
        </template>

        <template slot="command" slot-scope="text,record">
          <div style="white-space: pre-wrap">{{ getCommand(record) }}</div>
        </template>

        <template slot="status" slot-scope="text,record">
          <a-switch checked-children="开" un-checked-children="关" :checked="text===1" @click="(checked,event)=>changeStatus(record,checked,event)" />
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
        class="add-form"
        :model="editForm.form"
        :rules="editForm.rules"
        :label-col="editForm.labelCol"
        :wrapper-col="editForm.wrapperCol"
      >
        <a-form-model-item label="对应感知源" prop="sourceId">
          <a-select v-model="editForm.form.sourceId" placeholder="请选择感知平台">
            <a-select-option v-for="item in data.sourceList" :key="item.id" :value="item.id">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="对应边界" prop="boundaryList">
          <a-select v-model="editForm.form.boundaryList" placeholder="请选择对应边界" mode="multiple">
            <a-select-option v-for="item in data.boundaryList" :key="item.id" :value="item.id">{{ item.ipAddress }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="对应命令" prop="commandList">
          <a-select v-model="editForm.form.commandList" placeholder="请选择对应命令" mode="multiple">
            <a-select-option v-for="item in data.commandList" :key="item.id" :value="item.id">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="定时表达式" prop="cronExpress">
          <a-input v-model="editForm.form.cronExpress" placeholder="请输入定时表达式" />
        </a-form-model-item>
        <a-form-model-item label="状态" prop="status">
          <a-switch v-model="editForm.form.status" checked-children="开" un-checked-children="关" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import { Ellipsis, STable } from '@/components'
import { listValidatedSource } from '@/api/source'
import { delBoundary, listUsedBoundary, listUsedCommand } from '@/api/center'
import { parseFormData } from '@/utils'
import { changeStatus, editTimer, listTimer } from '@/api/timer'
import cloneDeep from 'lodash.clonedeep'

export default {
  name: 'TimerList',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      data: {
        commandList: [],
        boundaryList: [],
        sourceList: []
      },
      table: {
        columns: [{
          dataIndex: 'sourceName',
          title: '感知平台'
        }, {
          title: '所选边界',
          scopedSlots: { customRender: 'boundary' }
        }, {
          title: '所选命令',
          scopedSlots: { customRender: 'command' }
        }, {
          dataIndex: 'cronExpress',
          title: '定时任务表达式'
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
          return listTimer(pageData).then(res => {
            res['pageNo'] = parameter['pageNo']
            return res
          })
        },
        selectedRowKeys: [],
        selectedRows: [],
        filter: {}
      },
      editForm: {
        labelCol: { lg: { span: 5 }, sm: { span: 5 }},
        wrapperCol: { lg: { span: 19 }, sm: { span: 19 }},
        show: false,
        title: '新增',
        loading: false,
        form: {
          id: 0,
          sourceId: undefined,
          cronExpress: '',
          commandList: [],
          boundaryList: [],
          status: true
        },
        rules: {
          sourceId: { required: true, trigger: 'change', message: '请选择对应感知源' },
          cronExpress: { required: true, trigger: 'blur', message: '请输入定时任务表达式' },
          commandList: { required: true, type: 'array', message: '请选择命令' },
          boundaryList: { required: true, type: 'array', message: '请选择边界' }
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
    this.getData()
  },
  methods: {
    getData () {
      listUsedBoundary().then(response => {
        this.data.boundaryList = response.data
      })
      listValidatedSource().then(response => {
        this.data.sourceList = response.data
      })
      listUsedCommand().then(response => {
        this.data.commandList = response.data
      })
    },
    onTableSelectChange (selectedRowKeys, selectedRows) {
      this.table.selectedRowKeys = selectedRowKeys
      this.table.selectedRows = selectedRows
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
            delBoundary(_this.table.selectedRowKeys.join(',')).then(() => {
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
      this.editForm.form.boundaryList = record['boundaries'].map(item => item.id)
      this.editForm.form.commandList = record['commands'].map(item => item.id)
      this.editForm.form.status = this.editForm.form.status === 1
    },
    handleSubmit () {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.editForm.loading = true
          const requestData = cloneDeep(this.editForm.form)
          delete requestData['boundaryList']
          delete requestData['commandList']
          requestData['boundaries'] = this.editForm.form.boundaryList.join(',')
          requestData['commands'] = this.editForm.form.commandList.join(',')
          requestData['status'] = requestData['status'] ? 1 : 2
          editTimer(requestData).then(() => {
            this.$refs.table.refresh(true)
            this.editForm.show = false
          }).finally(() => {
            this.editForm.loading = false
          })
        }
      })
    },
    changeStatus (record, checked, event) {
      const status = checked ? 1 : 2
      changeStatus(record.id, status).then(() => {
        record['status'] = status
        this.$message.success('状态修改成功')
      })
    },
    getBoundary (record) {
      return record['boundaries'].map(item => item.ipAddress).join('\n')
    },
    getCommand (record) {
      return record['commands'].map(item => item.name).join('\n')
    }
  }
}
</script>
