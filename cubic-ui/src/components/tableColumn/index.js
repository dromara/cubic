
import {getClosestVueParent} from '@/utils'

export default {
  inheritAttrs: false,
  props: {
    default: Boolean // 必须传默认显示列
  },
  render() { return null },
  mounted() {
    const parent = getClosestVueParent(this.$parent, 'app-table')

    parent.$emit('column.add', {
      props: {
        ...this.$attrs,
        ...this.$props,
        scopedSlots: this.$scopedSlots.default
      }
    })
  }
}
