<template>
  <el-form-item v-bind="$attrs">
    <slot></slot>
  </el-form-item>
</template>

<script>
import { getClosestVueParent } from '@/utils'

export default {
  name: 'app-search-form-item',
  inheritAttrs: false,
  props: {
    codeType: null,
    typeOption: null,
    type: {
      type: String,
      default: 'text'
    }
  },
  methods: {
    getItem() {
      const { label, prop } = this.$attrs

      return {
        label,
        prop,
        type: this.type,
        codeType: this.codeType,
        typeOption: this.typeOption
      }
    }
  },
  mounted() {
    const parent = getClosestVueParent(this.$parent, 'app-search-form')

    if (!parent) {
      throw Error('app-search-form-item must be included app-search-form')
    }

    parent.registryItem(this.getItem())
  }
}
</script>
