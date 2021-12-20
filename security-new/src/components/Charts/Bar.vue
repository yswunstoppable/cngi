<template>
  <div :style="{ padding: '0 0 32px 32px' }">
    <h4 :style="{ marginBottom: '20px' }">{{ title }}</h4>
    <v-chart
      height="254"
      :data="data"
      :force-fit="true"
      :padding="['auto', 'auto', '40', '50']"
    >
      <v-tooltip />
      <v-axis />
      <v-bar position="x*攻击数" />
    </v-chart>
  </div>
</template>

<script>
export default {
  name: 'Bar',
  props: {
    title: {
      type: String,
      default: ''
    },
    data: {
      type: Array,
      default: () => {
        return []
      }
    },
    scale: {
      type: Array,
      default: () => {
        return [{
          dataKey: 'x'
        }, {
          dataKey: '攻击数',
          title: '攻击数',
          min: 0
        }]
      }
    },
    tooltip: {
      type: Array,
      default: () => {
        return [
          'x*攻击数',
          (x, 攻击数) => ({
            name: x,
            value: 攻击数
          })
        ]
      }
    }
  },
  data () {
    return {
    }
  }
}
</script>
