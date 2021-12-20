<template>
  <a-card :bordered="false">
    <a-steps class="steps" :current="currentTab">
      <a-step title="填写感知源信息" />
      <a-step title="数据连接测试" />
      <a-step title="完成" />
    </a-steps>
    <div class="content">
      <AddForm v-show="currentTab === 0" ref="addForm" @nextStep="(form)=>validateCode(form)" />
      <ValidateForm v-if="currentTab===1" :data="this.info" @nextStep="complete" @prevStep="prevStep" />
      <div v-if="currentTab===2">
        <a-result
          status="success"
          title="感知源添加成功"
          sub-title="您可以通过感知源获取安全相关信息"
        >
          <template #extra>
            <a-button type="primary" @click="continueAdd">
              继续添加
            </a-button>
            <a-button @click="viewList">
              查看列表
            </a-button>
          </template>
        </a-result>
      </div>
    </div>
  </a-card>
</template>

<script>
import AddForm from './components/AddForm'
import ValidateForm from '@/views/source/components/ValidateForm'
import { getPerceptionSource } from '@/api/source'

export default {
  name: 'SourceAdd',
  components: {
    ValidateForm,
    AddForm
  },
  data () {
    return {
      currentTab: 0,
      info: {}
    }
  },
  mounted () {
    if (this.$route.params.id) {
      getPerceptionSource(this.$route.params.id).then(response => {
        this.$refs.addForm.setForm(response.data)
      })
    }
  },
  methods: {
    validateCode (form) {
      this.currentTab = 1
      this.info = form
    },
    prevStep () {
      this.currentTab = 0
    },
    complete () {
      this.currentTab = 2
    },
    continueAdd () {
      this.currentTab = 0
      this.$refs.addForm.reset()
    },
    viewList () {
      this.$router.push({
        path: '/source/list'
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .steps {
    max-width: 750px;
    margin: 16px auto;
  }
</style>
