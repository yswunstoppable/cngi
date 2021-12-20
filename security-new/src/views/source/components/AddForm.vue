<template>
  <div>
    <a-form-model
      ref="form"
      class="add-form"
      :model="form"
      :rules="rules"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
    >
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name" placeholder="请输入名称标志" />
      </a-form-model-item>
      <a-form-model-item label="感知平台" prop="perceptionPlatform">
        <a-select v-model="form.perceptionPlatform" placeholder="请选择感知平台">
          <a-select-option :value="1">深信服</a-select-option>
          <a-select-option :value="2">绿盟</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="服务协议" prop="protocolType">
        <a-select v-model="form.protocolType" placeholder="请选择服务协议">
          <a-select-option :value="1">HTTP</a-select-option>
          <a-select-option :value="2">HTTPS</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="主机地址" prop="host">
        <a-input v-model="form.host" placeholder="请输入主机地址" />
      </a-form-model-item>
      <a-form-model-item label="登录账户" prop="account">
        <a-input-password v-model="form.account" placeholder="请输入登录账户" />
      </a-form-model-item>
      <a-form-model-item label="登录密码" prop="password">
        <a-input-password v-model="form.password" placeholder="请输入登录密码" />
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ lg: { span: 19,offset:5 }, sm: { span: 19,offset:5 } }">
        <a-button type="primary" :loading="loading" @click="nextStep">下一步</a-button>
      </a-form-model-item>
    </a-form-model>
    <a-divider />
    <div class="step-form-style-desc">
      <h3>说明</h3>
      <h4>测试成功后才可完成</h4>
      <h4>可在列表对感知源进行管理</h4>
    </div>
  </div>
</template>

<script>

import { editPerceptionSource, judgeSourceName } from '@/api/source'
import { parseFormData } from '@/utils'

export default {
  name: 'AddForm',
  data () {
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入名称'))
        return
      }
      judgeSourceName(this.form.id, value).then((response) => {
        const { data } = response
        if (data === 'fail') {
          callback(new Error('名称已存在'))
        }
        callback()
      })
    }

    return {
      labelCol: { lg: { span: 5 }, sm: { span: 5 }},
      wrapperCol: { lg: { span: 19 }, sm: { span: 19 }},
      loading: false,
      form: {
        id: 0,
        name: '',
        perceptionPlatform: undefined,
        protocolType: undefined,
        host: '',
        account: '',
        password: ''
      },
      rules: {
        name: [{ required: true, trigger: 'blur', validator: validateName }],
        perceptionPlatform: [{ required: true, trigger: 'change', message: '请选择感知平台' }],
        protocolType: [{ required: true, trigger: 'change', message: '请选择服务协议' }],
        host: [{ required: true, trigger: 'blur', message: '请输入主机' }],
        account: [{ required: true, trigger: 'blur', message: '请输入登录账户' }],
        password: [{ required: true, trigger: 'blur', message: '请输入登录密码' }]
      }
    }
  },
  methods: {
    setForm (form) {
      parseFormData(this.form, form)
    },
    reset () {
      this.$refs.form.resetFields()
      this.form.id = 0
    },
    nextStep () {
      this.$refs.form.validate(valid => {
        if (valid) {
          editPerceptionSource(this.form).then(response => {
            this.form.id = response.data
            this.$emit('nextStep', this.form)
          }).finally(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .add-form{
    max-width: 500px;
    margin: 40px auto 0;
  }

  .step-form-style-desc {
    padding: 0 56px;
    color: rgba(0,0,0,.45);

    h3 {
      margin: 0 0 12px;
      color: rgba(0,0,0,.45);
      font-size: 16px;
      line-height: 32px;
    }

    h4 {
      margin: 0 0 4px;
      color: rgba(0,0,0,.45);
      font-size: 14px;
      line-height: 22px;
    }

    p {
      margin-top: 0;
      margin-bottom: 12px;
      line-height: 22px;
    }
  }
</style>
