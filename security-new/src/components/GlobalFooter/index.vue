<template>
  <global-footer class="footer custom-render">
    <template v-slot:links>
      <a class="item" href="#" target="_blank">帮助</a>
      <a class="item" href="#" target="_blank">隐私</a>
      <a class="item" href="#" target="_blank">条款</a>
    </template>
    <!--    <template v-slot:copyright>-->
    <!--      Copyright © 2019 - Present-->
    <!--      <a href="javascript:void(0);" target="_blank" style="margin:0 5px;">中南财经政法大学</a>-->
    <!--      版权所有-->
    <!--    </template>-->
  </global-footer>
</template>

<script>
import { GlobalFooter } from '@ant-design-vue/pro-layout'

export default {
  name: 'ProGlobalFooter',
  components: {
    GlobalFooter
  }
}
</script>
