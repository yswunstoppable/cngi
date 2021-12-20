<template>
  <div>
    <a-card :bordered="false">
      <ExuLogSearchForm @search="(data)=>search(data)" />

      <!--      <a-alert show-icon type="info" class="mt-10">-->
      <!--        <template slot="message">-->
      <!--          已选择-->
      <!--          <span class="select-count">{{ selectCount }}</span> 项-->
      <!--          <a class="select-clear" @click="clearSelected">清空</a>-->
      <!--        </template>-->
      <!--      </a-alert>-->

      <s-table
        ref="table"
        class="mt-10"
        row-key="id"
        :scroll="{ x: 1500 }"
        :columns="table.columns"
        :data="table.loadData"
        :alert="false"
        show-pagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>

        <template slot="isWhite" slot-scope="text">
          <a-badge v-if="text===1" status="success" text="是" />
          <a-badge v-else status="error" text="否" />
        </template>

        <template slot="commandContent" slot-scope="text">
          <ellipsis :length="50" tooltip html>{{ text }}</ellipsis>
        </template>

        <template slot="isSuccess" slot-scope="text">
          <a-tag v-if="text===1" color="#108ee9">
            成功
          </a-tag>
          <a-tag v-else-if="text===0" color="#f50">
            失败
          </a-tag>
          <a-tag v-else>
            白名单不执行
          </a-tag>
        </template>

        <template slot="commandResult" slot-scope="text">
          <ellipsis :length="50" tooltip html>{{ text }}</ellipsis>
        </template>

        <template slot="errMsg" slot-scope="text">
          <ellipsis :length="50" tooltip html>{{ text }}</ellipsis>
        </template>
      </s-table>
    </a-card>
  </div>
</template>

<script>
import { Ellipsis, STable } from '@/components'
import { listExuLog } from '@/api/log'
import ExuLogSearchForm from '@/views/log/components/ExuLogSearchForm'

export default {
  name: 'SourceLog',
  components: {
    ExuLogSearchForm,
    STable,
    Ellipsis
  },
  data () {
    return {
      searchForm: {
        form: {}
      },
      table: {
        columns: [{
          width: '80px',
          title: '#',
          align: 'center',
          scopedSlots: { customRender: 'serial' },
          fixed: 'left'
        }, {
          width: '160px',
          dataIndex: 'boundary',
          title: '边界地址',
          fixed: 'left'
        }, {
          width: '160px',
          dataIndex: 'ipAddress',
          title: '攻击源IP'
        }, {
          width: '110px',
          dataIndex: 'isWhite',
          title: '是否白名单',
          scopedSlots: { customRender: 'isWhite' }
        }, {
          width: '260px',
          dataIndex: 'commandContent',
          title: '命令内容',
          scopedSlots: { customRender: 'commandContent' }
        }, {
          width: '120px',
          dataIndex: 'isSuccess',
          title: '是否执行成功',
          scopedSlots: { customRender: 'isSuccess' }
        }, {
          width: '260px',
          dataIndex: 'commandResult',
          title: '命令结果',
          scopedSlots: { customRender: 'commandResult' }
        }, {
          width: '260px',
          dataIndex: 'errMsg',
          title: '错误信息',
          scopedSlots: { customRender: 'errMsg' }
        }, {
          width: '190px',
          dataIndex: 'createdTime',
          title: '执行时间',
          fixed: 'right'
        }],
        loadData: parameter => {
          const pageData = {
            pageSize: parameter.pageSize,
            pageNumber: parameter.pageNo
          }
          const requestParameters = Object.assign({}, pageData, this.searchForm.form)
          return listExuLog(requestParameters).then(res => {
            res['pageNo'] = parameter['pageNo']
            return res
          })
        },
        selectedRowKeys: [],
        selectedRows: []
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
    clearSelected () {
      this.table.selectedRows = []
      this.table.selectedRowKeys = []
    }
  }
}
</script>
