<template>
  <a-form-model ref="form" :model="form" :rules="loginRules" class="form">
    <a-form-model-item prop="username">
      <a-input
        ref="username"
        v-model="form.username"
        :size="formSize"
        clearable
        autocomplete="false"
        tabindex="1"
        placeholder="请输入用户名"
      >
        <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }" />
      </a-input>
    </a-form-model-item>
    <a-form-model-item prop="password">
      <a-input
        v-model="form.password"
        :size="formSize"
        clearable
        type="password"
        autocomplete="false"
        tabindex="2"
        password
        placeholder="请输入密码"
        @keyup.native="checkCapslock"
        @blur="capsTooltip = false"
        @keyup.enter.native="handleLogin"
      >
        <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }" />
      </a-input>
    </a-form-model-item>
  </a-form-model>
</template>

<script>
export default {
  name: 'AccountForm',
  props: {
    size: {
      type: String,
      default: 'default'
    }
  },
  data () {
    const validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    return {
      formSize: this.size,
      form: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      }
    }
  },
  methods: {
    checkCapslock ({ shiftKey, key } = {}) {
      if (key && key.length === 1) {
        if (shiftKey && (key >= 'a' && key <= 'z') || !shiftKey && (key >= 'A' && key <= 'Z')) {
          this.capsTooltip = true
        } else {
          this.capsTooltip = false
        }
      }
      if (key === 'CapsLock' && this.capsTooltip === true) {
        this.capsTooltip = false
      }
    },
    handleLogin () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('handleLogin', this.form)
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .form {
    margin-top: 1vh;
  }
</style>
