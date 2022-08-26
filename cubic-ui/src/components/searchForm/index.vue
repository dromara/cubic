<template>
  <div class="app-search-form">
    <app-widget
      :title="title"
      :show-header-border="showHeaderBorder"
      :show-fold="showFold"
      :default-fold="defaultFold"
      :isShowHeader='isShowHeader'
      @widget:fold="handleFold"
    >
      <template slot="custom">
        <slot></slot>
      </template>
      <!-- <div class="app-section"> -->
      <div>
        <el-form
          :class="!isShowHeader ? 'app-margin--top' : ''"
          :label-position="labelPosition"
          :label-width="labelWidth"
          @submit.native.prevent="submit">
          <el-row :gutter="gutter">
            <slot name="fields">
            </slot>
          </el-row>
          <div :class="buttonClass">
            <slot name="buttons">
            </slot>
          </div>
        </el-form>
      </div>
    </app-widget>
    <div class="app-search-form__query app-border app-border--bottom app-padding--container"
      v-if="floatQuery && queryable && querys.length > 0"
      ref="query">
      <el-tag type="primary" v-for="query in querys"
        closable
        @close="handleClose(query)"
        :key="query.field">{{$t(query.label)}}：{{query.value}}</el-tag>
    </div>
  </div>
</template>

<script>
import { addEvent, removeEvent } from '@/utils'

const GENERAL_FIELD = ['page', 'size', 'order', 'orderBy']

export default {
  name: 'app-search-form',
  props: {
    labelPosition: {
      type: String,
      default: 'right'
    },
    labelWidth: {
      type: String,
      default: '80px'
    },
    gutter: {
      type: Number,
      default: 15
    },
    buttonAlign: {
      type: String,
      default: 'left'
    },
    form: {
      type: Object,
      default() {
        return {}
      }
    },
    showFold: {
      type: Boolean,
      default: true
    },
    fold: {
      type: Boolean,
      default: true
    },
    isShowHeader: {
      type: Boolean,
      default: true
    },

    title: {
      type: String,
      default: '查询条件'
    },

    showHeaderBorder: {
      type: Boolean,
      default: true
    }

    // floatQuery: {
    //   type: Boolean,
    //   default: false
    // }
  },
  data() {
    const params = Object.keys(this.$route.query)
    let defaultFold = this.fold

    // 根据查询参数设置默认折叠状态
    if (params.length > GENERAL_FIELD.length || _.difference(params, GENERAL_FIELD).length > 0) {
      defaultFold = false
      this.handleFold(false)
    }

    return {
      defaultFold: defaultFold,
      queryable: false,
      querys: [],
      floatQuery: false
    }
  },
  computed: {
    buttonClass() {
      return this.buttonAlign === 'right' ? 'app-search-form__button--right' : null
    }
  },
  watch: {
    queryable(value) {
      if (value) {
        this.$nextTick(() => {
          const firstChild = this.floatWindow.children[0]

          this.$refs.query && this.floatWindow.insertBefore(this.$refs.query, firstChild)
        })
        this.querys = this.formatQuery()
      } else {
        this.querys = []
      }
    }
  },
  methods: {
    submit(e) {
      this.$emit('submit', e)
    },

    /**
     * 注册item
     */
    registryItem({label, prop, type, codeType, typeOption}) {
      this.itemMap[prop] = {
        label,
        type,
        codeType,
        typeOption
      }
    },

    /**
     * 格式化查询条件
     */
    formatQuery() {
      const query = this.$route.query
      const keys = Object.keys(query)
      const querys = []

      // 合并查询字段
      keys.forEach(key => {
        const ret = key.match(/(.*)(Begin|End)$/)

        if (ret) {
          querys.indexOf(ret[1]) === -1 && querys.push(ret[1])
        } else if (this.itemMap[key]) {
          querys.push(key)
        }
      })

      return querys.map(key => {
        const { label, type, codeType, typeOption } = this.itemMap[key]
        let value
        if (typeOption && typeOption.length > 0) {
          value = _.find(typeOption, {code: +query[key]}).name
        } else if (codeType) {

          // 从字典查找
          // value = _.find(this.dictionaryOptions[codeType], {code: +query[key]}).name
        } else if (type === 'range') {

          // 时间字段
          value = query[`${key}Begin`] + ' - ' + query[`${key}End`]
        } else {

          // 文本字段
          value = query[key]
        }

        return {
          field: key,
          label: label,
          value
        }
      })
    },
    handleFold(fold) {
      this.$emit('searchForm:fold', fold)
    },
    handleScroll() {
      const offsetHeight = this.$el.offsetHeight
      const scrollTop = this.container.scrollTop

      this.queryable = scrollTop > 0 && offsetHeight < scrollTop
    },
    handleClose(query) {
      this.querys.splice(this.querys.indexOf(query), 1);
      this.$emit('searchForm:remove', query)
    }
  },
  created() {
    this.itemMap = {}
  },
  mounted() {
    if (this.floatQuery) {
      this.container = document.querySelector('.app-container__main__content') // 页面容器
      this.floatWindow = document.querySelector('.app-float-window') // 浮动窗口
      this._handleScroll = _.debounce(this.handleScroll, 20)
      addEvent(this.container, 'scroll', this._handleScroll)
    }
  },
  beforeDestroy() {
    if (this.floatQuery) {
      this.$refs.query && this.floatWindow.removeChild(this.$refs.query)
      removeEvent(this.container, 'scroll', this._handleScroll)
    }
  }
}
</script>

<style lang="scss">
  .app-search-form {
    overflow: hidden;
    &__simple {
      padding: 2px 5px;
      input {
        border: none;
      }
    }
    &__button {
      &--right {
        text-align: right;
      }
    }
    &__query {
      .el-tag {
        margin-top: 15px;
        &:not(:last-of-type) {
          margin-right: 15px;
        }
      }
    }
    .el-select, .el-range-editor {
      width: 100%;
    }
    .el-range-editor {
      vertical-align: bottom
    }
    .el-col {
      border-radius: 4px;
    }
    .bg-purple-dark {
      background: #99a9bf;
    }
    .bg-purple {
      background: #d3dce6;
    }
    .bg-purple-light {
      background: #e5e9f2;
    }
    .grid-content {
      border-radius: 4px;
      min-height: 36px;
    }
    .app-widget {
      border-top: none;
    }
  }

</style>

