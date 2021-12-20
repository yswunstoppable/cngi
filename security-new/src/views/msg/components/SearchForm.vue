<template>
  <a-form-model
    ref="searchForm"
    :model="searchForm.form"
    :label-width="80"
    layout="inline"
    :label-col="{span:6}"
    :wrapper-col="{ span: 18 }"
    @submit.native.prevent
  >
    <a-form-model-item
      label="配置名称"
      :style="{width:searchForm.width}"
    >
      <a-input
        v-model="searchForm.form.account"
        type="text"
        placeholder="请输入配置名称"
        @keyup.enter.native="search"
      />
    </a-form-model-item>
    <a-form-model-item
      label="键"
      :style="{width:searchForm.width}"
    >
      <a-input
        v-model="searchForm.form.name"
        type="text"
        placeholder="请输入键"
        @keyup.enter.native="search"
      />
    </a-form-model-item>
    <a-form-model-item :label-width="0" :style="{width:searchForm.width}">
      <a-button type="primary" icon="search" @click="search">搜索</a-button>
      <a-button icon="redo" class="ml-5" @click="reset">重置</a-button>
    </a-form-model-item>
  </a-form-model>
</template>

<script>
export default {
  name: 'SearchForm',
  data () {
    return {
      searchForm: {
        width: '280px',
        form: {}
      }
    }
  },
  methods: {
    search () {
      this.$emit('search', this.searchForm.form)
    },
    reset () {
      this.searchForm.form = {}
      this.search()
    }
  }
}
</script>

<style scoped>

</style>
