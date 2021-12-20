<template>
  <div>
    <a-form-model-item label="菜单权限">
      <a-tree
        ref="editFormMenuTree"
        show-checkbox
        check-directly
        :data="menu.data"
        :load-data="loadMenuTreeData"
        :render="renderMenuTreeContent"
      />
    </a-form-model-item>
  </div>
</template>

<script>
import { listMenuTree } from '@/api/menu.js'
// import iviewUtil from '@/utils/iview-util.js'

export default {
  name: 'ChoosePermission',
  data () {
    return {
      menu: {
        isFirstLoadData: true, // 是否第一次进行懒加载请求
        callback: null, // 懒加载函数
        loading: false,
        maxHeight: '500px',
        data: [],
        chosenNode: []
      },
      app: {
        data: [],
        chosenNode: []
      }
    }
  },
  mounted () {
    // 获取权限菜单信息
    this.listMenu()
    this.listAppMenu()
  },
  methods: {
    listMenu () {
      this.menu.loading = true
      this.menu.chosenNode = []
      listMenuTree(0, '').then(response => {
        const { data } = response
        for (const v of data) {
          v['expand'] = false
          v['checked'] = false
          if (v['isParent'] === 1) {
            v['loading'] = false
            v['children'] = []
          }
        }
        this.menu.data = data
        this.menu.loading = false
      })
    },
    loadData (item, callback) {
      listMenuTree(item.id, '').then(response => {
        const { data } = response
        for (const v of data) {
          v['checked'] = false
          if (v['isParent'] === 1) {
            v['loading'] = false
            v['children'] = []
          }
        }
        callback(data)
      })
    },
    loadMenuTreeData (item, callback) {
      if (this.menu.isFirstLoadData) {
        this.menu.callback = callback
        this.menu.isFirstLoadData = false
      }
      this.loadData(item, callback)
    },
    renderMenuTreeContent (h, { root, node, data }) {
      let icon = ''
      if (data.type === 1) {
        // 页面菜单
        icon = data.isParent === 1 ? 'md-list-box' : 'md-list'
      } else {
        icon = 'md-radio-button-on'
      }
      return h(
        'span',
        {
          style: {
            display: 'inline-block',
            cursor: 'pointer'
          }
        },
        [
          h('span', [
            h('Icon', {
              props: {
                type: icon,
                size: '16'
              },
              style: {
                'margin-right': '8px',
                'margin-bottom': '3px'
              }
            }),
            h(
              'span',
              {
                class: {
                  'ivu-tree-title': true
                }
              },
              data.title
            )
          ])
        ]
      )
    },
    // 重置菜单选择
    resetChosen () {
      // iviewUtil.convertTreeCheck(this.menu.data, false)
      // iviewUtil.convertTreeCheck(this.app.data, false)
    },
    // 获取选择的节点
    getChosenNodes () {
      // return this.$refs.editFormMenuTree.getCheckedNodes().concat(this.$refs.editFormAppTree.getCheckedNodes())
    },
    setChosenNodes (menuIdList) {
      // 渲染树节点
      // iviewUtil.checkTreeByIdList(this.menu.data, menuIdList)
    }
  }
}
</script>

<style lang="less" scoped>

</style>
