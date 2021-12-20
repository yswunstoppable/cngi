<template>
  <div>
    <a-card :bordered="false">
      <div class="mt-10">
        <a-button type="primary" icon="plus" @click="addNew()">新增</a-button>
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
        <template slot="perceptionPlatform" slot-scope="text">
          <div>{{ text===1?'深信服':'绿盟' }}</div>
        </template>
        <template slot="protocolType" slot-scope="text">
          <div>{{ text===1?'http':'https' }}</div>
        </template>
        <template slot="isValidated" slot-scope="text,record">
          <a-badge v-if="record['isValidated']===1" status="success" text="是" />
          <a-badge v-else status="error" text="否" />
        </template>

        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="handleEdit(record)">编辑</a>
          </template>
        </span>
      </s-table>
    </a-card>
  </div>
</template>

<script>
import { Ellipsis, STable } from '@/components'
import { delWhiteList } from '@/api/white-list'
import { listPerceptionSource } from '@/api/source'

export default {
  name: 'SourceList',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      table: {
        columns: [{
          align: 'center',
          dataIndex: 'perceptionPlatform',
          title: '感知平台',
          scopedSlots: { customRender: 'perceptionPlatform' }
        }, {
          align: 'center',
          dataIndex: 'protocolType',
          title: '网络协议',
          scopedSlots: { customRender: 'protocolType' }
        }, {
          align: 'center',
          title: '主机',
          dataIndex: 'host'
        }, {
          align: 'center',
          title: '登录账户',
          dataIndex: 'account'
        }, {
          align: 'center',
          title: '登录密码',
          dataIndex: 'password'
        }, {
          title: '是否通过测试',
          align: 'center',
          dataIndex: 'isValidated',
          scopedSlots: { customRender: 'isValidated' }
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
          return listPerceptionSource(pageData).then(res => {
            res['pageNo'] = parameter['pageNo']
            return res
          })
        },
        selectedRowKeys: [],
        selectedRows: [],
        filter: {}
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
    addNew () {
      this.$router.push({
        path: '/source/add'
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
    handleEdit (record) {
      this.$router.push({
        name: 'SourceAdd',
        params: {
          id: record.id
        }
      })
    },
    batchDel () {
      const _this = this
      this.$confirm({
        title: '系统提示',
        content: '是否确认删除',
        onOk () {
          return new Promise((resolve) => {
            delWhiteList(_this.table.selectedRowKeys.join(',')).then(() => {
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
