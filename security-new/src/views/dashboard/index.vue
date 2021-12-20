<template>
  <div class="page-header-index-wide">
    <a-card style="margin-bottom: 10px">
      <iframe id="iframeId" :src="url" frameborder="0" class="pc iframe" scrolling="auto" />
    </a-card>
    <a-card :loading="loading" :bordered="false" :body-style="{ padding: '0' }">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{ marginBottom: '24px', paddingLeft: '16px' }">
          <a-tab-pane key="1" loading="true" tab="最近24小时TOP5">
            <a-row>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="深信服-高危攻击" :list="highRiskList" />
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="深信服-残余攻击" :list="remnantList" />
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="绿盟" :list="lmList" />
              </a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>

    <div class="antd-pro-pages-dashboard-analysis-twoColLayout" :class="'desktop'">
      <a-row :gutter="24" type="flex" :style="{ marginTop: '24px' }">
        <a-col :xl="12" :lg="24" :md="24" :sm="24" :xs="24">
          <a-card :loading="loading" :bordered="false" title="攻击源统计" :style="{ height: '100%' }">
            <!--            <a-row :gutter="68">-->
            <!--              <a-col :xs="24" :sm="12" :style="{ marginBottom: ' 24px'}">-->
            <!--                <number-info :total="12321" :sub-total="17.1">-->
            <!--                  <span slot="subtitle">-->
            <!--                    <span>攻击时间峰值</span>-->
            <!--                    <a-tooltip title="指标说明" slot="action">-->
            <!--                      <a-icon type="info-circle-o" :style="{ marginLeft: '8px' }" />-->
            <!--                    </a-tooltip>-->
            <!--                  </span>-->
            <!--                </number-info>-->
            <!--                &lt;!&ndash; miniChart &ndash;&gt;-->
            <!--                <div>-->
            <!--                  <mini-smooth-area :style="{ height: '45px' }" :dataSource="searchUserData" :scale="searchUserScale" />-->
            <!--                </div>-->
            <!--              </a-col>-->
            <!--              <a-col :xs="24" :sm="12" :style="{ marginBottom: ' 24px'}">-->
            <!--                <number-info :total="2.7" :sub-total="26.2" status="down">-->
            <!--                  <span slot="subtitle">-->
            <!--                    <span>日均攻击次数</span>-->
            <!--                    <a-tooltip title="指标说明" slot="action">-->
            <!--                      <a-icon type="info-circle-o" :style="{ marginLeft: '8px' }" />-->
            <!--                    </a-tooltip>-->
            <!--                  </span>-->
            <!--                </number-info>-->
            <!--                &lt;!&ndash; miniChart &ndash;&gt;-->
            <!--                <div>-->
            <!--                  <mini-smooth-area :style="{ height: '45px' }" :dataSource="searchUserData" :scale="searchUserScale" />-->
            <!--                </div>-->
            <!--              </a-col>-->
            <!--            </a-row>-->
            <div class="ant-table-wrapper">
              <a-table
                row-key="index"
                size="small"
                :columns="searchTableColumns"
                :data-source="searchData"
                :pagination="{ pageSize: 10 }"
              />
            </div>
          </a-card>
        </a-col>
        <a-col :xl="12" :lg="24" :md="24" :sm="24" :xs="24">
          <a-card
            class="antd-pro-pages-dashboard-analysis-salesCard"
            :loading="loading"
            :bordered="false"
            title="攻击源地域占比"
            :style="{ height: '100%' }"
          >
            <h4>攻击源地域</h4>
            <div>
              <!-- style="width: calc(100% - 240px);" -->
              <div>
                <v-chart :force-fit="true" :height="405" :data="pieData" :scale="pieScale">
                  <v-tooltip :show-title="false" data-key="item*percent" />
                  <v-axis />
                  <!-- position="right" :offsetX="-140" -->
                  <v-legend data-key="item" />
                  <v-pie position="percent" color="item" :v-style="pieStyle" />
                  <v-coord type="theta" :radius="0.75" :inner-radius="0.6" />
                </v-chart>
              </div>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>

  </div>
</template>

<script>
import { RankList } from '@/components'
import { getLastAttack, listAttackByAddress, listAttackStatics, staticsAttackByMonth } from '@/api/log'
import config from '@/config/config'

const searchTableColumns = [
  {
    dataIndex: 'index',
    title: '排名',
    width: 90
  },
  {
    dataIndex: 'attackIp',
    title: '攻击源IP'
  },
  {
    dataIndex: 'attackNumber',
    title: '总攻击数'
  },
  {
    dataIndex: 'attackAddress',
    title: '攻击源地点'
  }
]

const DataSet = require('@antv/data-set')

export default {
  name: 'Dashboard',
  components: {
    RankList
  },
  data() {
    return {
      loading: true,
      rankList: [],
      highRiskList: [],
      remnantList: [],
      violenceList: [],
      lmList: [],

      // 搜索用户数
      searchTableColumns,
      searchData: [],

      barData: [],

      pieScale: [],
      pieData: [],
      sourceData: [],
      pieStyle: {
        stroke: '#fff',
        lineWidth: 1
      },
      url: config.monitorHost + '/dash/main'
    }
  },
  created() {
    setTimeout(() => {
      this.loading = !this.loading
    }, 1000)
    this.getRankList()
    this.getAddress()
    this.getStatics()
  },
  methods: {
    getRankList() {
      getLastAttack().then(response => {
        this.rankList = response.data
        this.highRiskList = this.rankList.filter(item => item['attackType'] === 11)
        this.remnantList = this.rankList.filter(item => item['attackType'] === 12)
        this.violenceList = this.rankList.filter(item => item['attackType'] === 13)
        this.lmList = this.rankList.filter(item => item['attackType'] === 20)
      })
    },
    getAddress() {
      listAttackByAddress().then(response => {
        this.sourceData = response.data
        this.pieScale = [
          {
            dataKey: 'percent',
            min: 0,
            formatter: '.0%'
          }
        ]
        const dv = new DataSet.View().source(this.sourceData)
        dv.transform({
          type: 'percent',
          field: 'count',
          dimension: 'item',
          as: 'percent'
        })
        this.pieData = dv.rows
      })
    },
    getStatics() {
      listAttackStatics().then(response => {
        this.searchData = response.data
      })
    },
    getStaticsByMonth() {
      staticsAttackByMonth().then(response => {
        const { data } = response
        this.barData = []
        for (const item of data) {
          this.barData.push({
            x: `${item.month}月`,
            攻击数: item.number
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.extra-wrapper {
  line-height: 55px;
  padding-right: 24px;

  .extra-item {
    display: inline-block;
    margin-right: 24px;

    a {
      margin-left: 24px;
    }
  }
}

.antd-pro-pages-dashboard-analysis-twoColLayout {
  position: relative;
  display: flex;
  display: block;
  flex-flow: row wrap;
}

.antd-pro-pages-dashboard-analysis-salesCard {
  height: calc(100% - 24px);
  /deep/ .ant-card-head {
    position: relative;
  }
}

.dashboard-analysis-iconGroup {
  i {
    margin-left: 16px;
    color: rgba(0, 0, 0, 0.45);
    cursor: pointer;
    transition: color 0.32s;
    color: black;
  }
}
.analysis-salesTypeRadio {
  position: absolute;
  right: 54px;
  bottom: 12px;
}
.iframe {
  width: 100%;
  height: 900px;
  background: #fff;
  overflow-y: hidden;
}
</style>
