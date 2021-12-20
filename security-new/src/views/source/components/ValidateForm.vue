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
      <a-form-model-item label="感知平台">
        <a-select v-model="info.perceptionPlatform" placeholder="请选择感知平台" disabled>
          <a-select-option :value="1">深信服</a-select-option>
          <a-select-option :value="2">绿盟</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="服务协议">
        <a-select v-model="info.protocolType" placeholder="请选择服务协议" disabled>
          <a-select-option :value="1">HTTP</a-select-option>
          <a-select-option :value="2">HTTPS</a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="主机地址">
        <a-input v-model="info.host" placeholder="请输入主机地址" disabled />
      </a-form-model-item>
      <a-form-model-item label="登录账户">
        <a-input v-model="info.account" placeholder="请输入登录账户" disabled />
      </a-form-model-item>
      <a-form-model-item label="登录密码">
        <a-input v-model="info.password" placeholder="请输入登录密码" disabled />
      </a-form-model-item>
      <a-divider />
      <a-form-model-item v-if="info.perceptionPlatform===1" label="验证码">
        <img :src="codeSrc" class="pointer" @click="changeCaptcha">
      </a-form-model-item>
      <a-form-model-item v-if="info.perceptionPlatform===1" label="输入验证码" prop="validateCode">
        <a-input v-model="form.validateCode" placeholder="请输入验证码" />
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ lg: { span: 19,offset:5 }, sm: { span: 19,offset:5 } }">
        <a-button :loading="loading" type="primary" @click="nextStep">连接测试</a-button>
        <a-button class="ml-5" @click="prevStep">上一步</a-button>
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

import config from '@/config/config'
import { connectSource } from '@/api/source'

export default {
  name: 'ValidateForm',
  props: {
    data: {
      type: Object,
      default: () => {}
    }
  },
  data () {
    return {
      info: this.data,
      codeSrc: '',
      labelCol: { lg: { span: 5 }, sm: { span: 5 }},
      wrapperCol: { lg: { span: 19 }, sm: { span: 19 }},
      loading: false,
      form: {
        validateCode: ''
      },
      rules: {
        validateCode: [{ required: true, trigger: 'blur', message: '请输入验证码' }]
      }
    }
  },
  watch: {
    data (newV, oldV) {
      this.info = newV
    }
  },
  mounted () {
    this.codeSrc = `${config.host}/source/getCaptcha?id=${this.info.id}&time=${new Date().getTime()}`
  },
  methods: {
    nextStep () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          connectSource(this.info.id, this.form.validateCode).then(response => {
            this.$emit('nextStep')
          }).finally(() => {
            this.loading = false
          })
        }
      })
    },
    prevStep () {
      this.$emit('prevStep')
    },
    changeCaptcha () {
      this.codeSrc = `${config.host}/source/getCaptcha?id=${this.info.id}&time=${new Date().getTime()}`
    }
  }
}
</script>

<style lang="less" scoped>
  .add-form {
    max-width: 500px;
    margin: 40px auto 0;
  }

  .step-form-style-desc {
    padding: 0 56px;
    color: rgba(0, 0, 0, .45);

    h3 {
      margin: 0 0 12px;
      color: rgba(0, 0, 0, .45);
      font-size: 16px;
      line-height: 32px;
    }

    h4 {
      margin: 0 0 4px;
      color: rgba(0, 0, 0, .45);
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
