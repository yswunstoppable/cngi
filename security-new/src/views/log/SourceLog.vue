<template>
  <div>
    <a-card :bordered="false">
      <SourceLogSearchForm @search="(data)=>this.search(data)" />

      <!--      <a-alert show-icon type="info" class="mt-10">-->
      <!--        <template slot="message">-->
      <!--          已选择-->
      <!--          <span class="select-count">{{ selectCount }}</span> 项-->
      <!--          <a class="select-clear" @click="clearSelected">清空</a>-->
      <!--        </template>-->
      <!--      </a-alert>-->

      <!--      :rowSelection="rowSelection"-->
      <s-table
        ref="table"
        class="mt-10"
        row-key="id"
        :columns="table.columns"
        :data="table.loadData"
        :alert="false"
        show-pagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
        <template slot="attackType" slot-scope="text">
          {{ getAttackType(text) }}
        </template>
      </s-table>
    </a-card>
  </div>
</template>

<script>
import { Ellipsis, STable } from '@/components'
import SourceLogSearchForm from './components/SourceLogSearchForm'
import { listAttackSourceLog } from '@/api/log'

const attackType = {
  11: '深信服-高危高级',
  12: '深信服-残余攻击',
  13: '深信服-暴力破解',
  20: '绿盟'
}
export default {
  name: 'SourceLog',
  components: {
    SourceLogSearchForm,
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
          title: '#',
          align: 'center',
          scopedSlots: { customRender: 'serial' }
        }, {
          dataIndex: 'attackType',
          title: '攻击类型',
          scopedSlots: { customRender: 'attackType' }
        }, {
          dataIndex: 'attackIp',
          title: '攻击IP'
        }, {
          dataIndex: 'attackAddress',
          title: '攻击地点'
        }, {
          dataIndex: 'attackNumber',
          title: '攻击次数'
        }, {
          dataIndex: 'createdTime',
          title: '抓取时间'
        }],
        loadData: parameter => {
          const pageData = {
            pageSize: parameter.pageSize,
            pageNumber: parameter.pageNo
          }
          const requestParameters = Object.assign({}, pageData, this.searchForm.form)
          return listAttackSourceLog(requestParameters).then(res => {
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
    },
    getAttackType (type) {
      return attackType[type]
    }
  }
}
</script>
