<template>
  <Monitor url="/dash/systemInfoList?disk=1" />
</template>

<script>
import Monitor from '@/components/Monitor'
export default {
  name: 'HostDisk',
  components: { Monitor },
  created() {},
  methods: {

  }
}
</script>

