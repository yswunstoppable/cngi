<template>
  <div class="container">
    <a-row type="flex" class="login-main">
      <a-col :span="13" class="login-carousel">
        <img src="@/assets/images/login/web.png">
      </a-col>
      <a-col :span="11" class="login-form-container">
        <div class="title">基于IPv6的网络服务质量监控及多权限消息预警系统</div>
        <AccountForm ref="accountForm" :size="size" @handleLogin="handleAccountLogin" />
        <a-row type="flex" justify="space-between">
          <a-checkbox v-model="form.keepLogin" :size="size">自动登录</a-checkbox>
        </a-row>
        <a-row>
          <a-button
            type="primary"
            size="large"
            class="login-btn"
            tabindex="5"
            block
            @click="handleAccountLogin"
          >
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
          </a-button>
        </a-row>
      </a-col>
    </a-row>

    <a-row class="foot">
      <a-row type="flex" justify="space-around" class="help">
        <a class="item" href="#" target="_blank">帮助</a>
        <a class="item" href="#" target="_blank">隐私</a>
        <a class="item" href="#" target="_blank">条款</a>
      </a-row>
      <!--      <a-row type="flex" justify="center">-->
      <!--        Copyright © 2019 - Present-->
      <!--        <a href="javascript:void(0);" target="_blank" style="margin:0 5px;">中南财经政法大学</a>-->
      <!--        版权所有-->
      <!--      </a-row>-->
    </a-row>
  </div>
</template>

<script>
import AccountForm from './components/AccountForm'

export default {
  name: 'Login',
  components: { AccountForm },
  data() {
    return {
      size: 'large',
      loading: false, // 是否正在加载请求
      form: {
        isAccount: false, // 是否账号登录
        keepLogin: false // 是否保存登录信息
      },
      capsTooltip: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {
    // if (this.loginForm.username === '') {
    //   this.$$refs.username.focus()
    // } else if (this.loginForm.password === '') {
    //   this.$refs.password.focus()
    // }
  },
  destroyed() {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
    handleAccountLogin() {
      this.$refs.accountForm.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          this.form['isAccount'] = true
          const data = Object.assign({}, this.form, this.$refs.accountForm.form)
          this.$store.dispatch('Login', data).then(() => {
            this.$router.push({ path: this.redirect || '/' })
          }).finally(() => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .container {
    height: 100vh;
    background: url("../../assets/background2.svg");

    .login-main {
      position: absolute;
      top: 50%;
      left: 50%;
      box-shadow: 0 8px 19px 2px rgba(0, 0, 0, 0.16);
      border-radius: 10px;
      background: #FFFFFF;
      width: 1000px;
      -webkit-transform: translate(-50%, -50%);
      transform: translate(-50%, -50%);

      .login-carousel {
        height: 600px;

        img {
          display: inline-block;
          height: 600px;
          width: 100%;
        }
      }

      .login-form-container {
        margin-top: 50px;
        padding: 0 60px;

        .title {
          padding: 50px 0;
          font-size: 28px;
          text-align: center;
          color: #333333;
        }

        .forget-pass,
        .other-way {
          font-size: 14px;
        }

        .login-btn,
        .other-login {
          margin-top: 3vh;
        }

        .icons {
          display: flex;
          align-items: center;
        }

        .other-icon {
          cursor: pointer;
          margin-left: 10px;
          display: flex;
          align-items: center;
          color: rgba(0, 0, 0, 0.2);

          :hover {
            color: #2d8cf0;
          }
        }
      }
    }

    .foot {
      position: absolute;
      left: 50%;
      bottom: 10px;
      color: rgba(0, 0, 0, 0.45);
      font-size: 14px;
      -webkit-transform: translate(-50%);
      transform: translate(-50%);

      .help {
        margin: 0 auto 2vh;
        width: 60%;

        .item {
          color: rgba(0, 0, 0, 0.45);
        }

        :hover {
          color: rgba(0, 0, 0, 0.65);
        }
      }

      a {
        color: #2d8cf0;
        background: 0 0;
        text-decoration: none;
        outline: 0;
        cursor: pointer;
        transition: color 0.2s ease;
        font-size: 14px;
      }
    }
  }
</style>
