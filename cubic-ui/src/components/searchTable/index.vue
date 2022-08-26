<template>
  <div class="app-search-table app-normal-bg" :style="searchStyle">
    <div class="app-search-table__oper app-flex app-flex--between app-flex--center app-padding" ref="oper"
      :class="operFixClass"
      :style="operStyle"
      v-if="showOper">
      <div class="app-search-table__left">
        <slot name="tools"></slot>
      </div>
      <div class="app-search-table__buttons">
        <slot name="buttons"></slot>
      </div>
    </div>
    <div class="app-search-table__table" ref="table">
      <slot></slot>
    </div>
  </div>
</template>

<script>
import { addEvent, removeEvent, addResize, removeResise } from '@/utils'

export default {
  name: 'search-table',
  props: {

    // floatOper: {
    //   type: Boolean,
    //   default: false
    // }
  },
  data() {
    return {
      operFixed: false,
      floatOper: false,
      operStyle: {
        width: null
      },
      searchStyle: {
        'padding-top': null
      },
      operFixClass: [ 'app-search-table__oper--normal' ]
    }
  },
  computed: {
    showOper() {
      return this.$slots.tools || this.$slots.buttons
    }
  },
  watch: {
    operFixed(value) {
      if (value) {
        const { height } = this.$refs.oper.getBoundingClientRect()

        this.searchStyle['padding-top'] = height + 'px'
        this.floatWindow.appendChild(this.$refs.oper)
        this.operFixClass = [ 'app-search-table__oper--fixed' ]
      } else {
        this.searchStyle['padding-top'] = null
        this.$el.insertBefore(this.$refs.oper, this.$refs.table)
        this.operFixClass = [ 'app-search-table__oper--normal' ]
      }
    }
  },
  methods: {
    handleScroll() {
      const { y } = this.$el.getBoundingClientRect()
      const { height } = this.$refs.oper.getBoundingClientRect()

      this.operFixed = y <= 115 - height
    },
    calculateOperPos() {
      const { width } = this.$el.getBoundingClientRect()

      this.operStyle.width = width + 'px'
    }
  },
  mounted() {
    if (this.floatOper && this.showOper) {
      this.container = document.querySelector('.app-container__main__content') // 页面容器
      this.floatWindow = document.querySelector('.app-float-window') // 浮动窗口
      this._handleScroll = _.debounce(this.handleScroll, 20)
      addEvent(this.container, 'scroll', this._handleScroll)
      addResize(this.$el, this.calculateOperPos)
    }
  },
  beforeDestroy() {
    if (this.floatOper && this.showOper) {
      this.$refs.oper.parentNode === this.floatWindow && this.floatWindow.removeChild(this.$refs.oper)
      removeEvent(this.container, 'scroll', this._handleScroll)
      removeResise(this.$el, this.calculateOperPos)
    }
  }
}
</script>

<style lang="scss">
  .app-search-table {
    background-color: #fff;
    padding: 15px;
    .app-flex {
      display: flex;
      display: -webkit-flex; /* Safari */
      flex-direction: row;
      justify-content: space-between;
    }
    &__oper {
      margin-bottom: 15px;
      &--normal {
        border-bottom: none;
      }
      &--fixed {
        border-top: none;
        background: rgba(255, 255, 255, .8);
        position: absolute;
        left: 15px;
      }
    }
    &__left {
      .el-form--inline .el-form-item {
        margin-bottom: 0;
      }
    }
  }
</style>


