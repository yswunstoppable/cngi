<template>
  <a-modal
    v-model="value"
    :mask-closable="false"
    :title="title"
    :width="width"
    ok-text="确 定"
    cancel-text="取 消"
    :confirm-loading="loading"
    @ok="submit"
    @cancel="close"
  >
    <slot />
  </a-modal>
</template>

<script>
export default {
  name: 'EditFormDialog',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    width: {
      type: [String, Number],
      default: 520
    },
    title: {
      type: String,
      default: '标题'
    },
    loading: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      value: false
    }
  },
  watch: {
    show () {
      this.value = this.show
    }
  },
  methods: {
    submit () {
      this.$emit('submit')
    },
    close () {
      this.value = false
      this.$emit('update:show', false)
    }
  }
}
</script>
