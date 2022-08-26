<template>
<div :class="tableCls">
	<el-table
    style="width: 100%"
    :class="{'table-nowrap': isNoWrap}"
		:data="showData"
    :height="height"
    :max-height="maxHeight"
    :stripe="stripe"
    :border="border"
    :fit="fit"
    :show-header="showHeader"
    :highlight-current-row="highlightCurrentRow"
    :current-row-key="currentRowKey"
    :row-class-name="rowClassName"
    :row-style="rowStyle"
    :row-key="rowKey"
    :empty-text="emptyText"
    :default-expand-all="defaultExpandAll"
    :expand-row-keys="expandRowKeys"
    :default-sort="defaultSort"
    :tooltip-effect="tooltipEffect"
    :show-summary="showSummary"
    :sum-text="sumText"
    :summary-method="summaryMethod"

    @select="select"
    @select-all="selectAll"
    @selection-change="selectionChange"
    @cell-mouse-enter="cellMouseEnter"
    @cell-mouse-leave="cellMouseLeave"
    @cell-click="cellClick"
    @cell-dblclick="cellDblclick"
    @row-click="rowClick"
    @row-contextmenu="rowContextmenu"
    @row-dblclick="rowDblclick"
    @header-click="headerClick"
    @sort-change="sortChange"
    @filter-change="filterChange"
    @current-change="currentChange"
    @header-dragend="headerDragend"
    @expand-change="expandChange"
    @data-load-complete="data-load-complete"
    >
    <slot></slot>
    <template v-for="_column in columns">
      <render-column
        v-if="showColumns(_column)"
        :key="_column.prop"

        :type="_column.type"
        :index="_column.index"
        :column-key="_column.columnKey"

        :label="_column.label"
        :prop="_column.prop"
        :width="_column.width"
        :min-width="_column.minWidth"

        :fixed="_column.fixed"
        :render-header="_column.renderHeader"
        :sortable="_column.sortable"
        :sort-method="_column.sortMethod"

        :sort-by="_column.sortBy"
        :resizable="_column.resizable"
        :formatter="_column.formatter"
        :show-overflow-tooltip="_column.showOverflowTooltip"

        :align="_column.align"
        :header-align="_column.headerAlign"
        :class-name="_column.className"
        :label-class-name="_column.labelClassName"

        :selectable="_column.selectable"
        :reserve-selection="_column.reserveSelection"
        :filters="_column.filters"
        :filter-placement="_column.filterPlacement"

        :filter-multiple="_column.filterMultiple"
        :filter-method="_column.filterMethod"
        :filter-value="_column.filterValue"
        :scoped-slots="_column.scopedSlots">
      </render-column>
    </template>
    <div slot="append" class="loadtip" v-if="isScrollLoad">
      <div v-if="!loading && isAllDataLoaded">{{ $t('没有更多') }}</div>
      <div v-if="loading">{{ $t('数据加载') }}</div>
    </div>
	</el-table>

	<div v-if="!isScrollLoad && pagination" class="app-table-pagination app-border" :class="paginationClass" :style="paganationStyle" ref="pagination">
    <el-pagination
      v-if="isLoadDataComplete && total > 0"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :small="pgSmall"
      :current-page.sync="currentPage"
      :page-size="pgSize"
      :page-sizes="pageSizes"
      :layout="layoutSlot"
      :total="total">
      <!-- <el-button style="float: left;" size="mini" @click="filterColumnHandler"><i class="iconfont icon-settings"></i></el-button> -->
    </el-pagination>
	</div>

  <el-dialog
    title="自定义显示列"
    :visible.sync="dialogVisible"
    width="40%">
    <el-transfer
    :titles="['可选择列', '已选择列']"
    v-model="selectColumn" :data="columnFeilds"></el-transfer>
    <div class="app-table--filtercolumn">
      <el-button @click="saveSelectColumn">确定</el-button>
      <el-button @click="dialogVisible=false">取消</el-button>
    </div>
  </el-dialog>

  <div v-if="!isScrollLoad && loading" class="table-mask" :style="{'background-color': loadingBackground}">
    <div class="el-loading-spinner">
      <svg viewBox="25 25 50 50" class="circular">
        <circle cx="50" cy="50" r="20" fill="none" class="path">
        </circle>
      </svg>
    </div>
  </div>
</div>
</template>

<script>
import moment from 'moment'
import _ from 'lodash'
import { addResize, removeResise, browserIs } from '@/utils'
import { interceptParams } from '@/utils/tools'
import request from '@/utils/request'

 function getTableData(url, query) {
  return request({
    url: url,
    method: 'get',
    params: query
  })
}

const renderColumn = {
  functional: true,
  render(h, context) {
    const { scopedSlots, ...props } = context.props

    if (typeof scopedSlots === 'function') {

      return h('el-table-column', {
        props: {
          ...props
        },
        scopedSlots: {
          default: scopedSlots
        }
      })
    }

    return h('el-table-column', {
      props: {
        ...props
      }
    })
  }
}

const browser = browserIs()

export default {

	props: {

    // 返回total
    url: [String],

    api: [Function],

    // 是否延迟加载收据
    lazy: {
      type: Boolean,
      default: false
    },

    // 请求参数
    queryParams: {
			default() {
        return () => {}
      },
			type: Function
		},

    // 请求的方法
    methods: {
      default: 'GET',
      type: String
    },

    pagination: {
      default: true,
      type: Boolean
    },

    // 过长的列头，是否换行
    isNoWrap: {
      default: !browser.safari,
      type: Boolean
    },

    ifUseLoading: {
      default: true,
      type: Boolean
    },

    // elment-table默认参数
		data: {
			default() {
        return []
      },
			type: Array
		},
    height: [String, Number],
    maxHeight: [String, Number],
    stripe: {
      type: Boolean,
      default: false
    },
    border: {
      type: Boolean,
      default: false
    },
    size: {
      type: String,
      default: 'medium'
    },
    fit: {
      type: Boolean,
      default: true
    },
    showHeader: {
      type: Boolean,
      default: true
    },
    highlightCurrentRow: {
      type: Boolean,
      default: false
    },
    currentRowKey: [String, Number],
    rowClassName: [Function, String],
    rowStyle: [Function, Object],
    rowKey: [Function, String],
    emptyText: [String],
    defaultExpandAll: {
      type: Boolean,
      default: false
    },
    expandRowKeys: [Array],
    defaultSort: {
      type: Object,
      default() {
        return {}
      }
    },
    tooltipEffect: [String],
    showSummary: {
      type: Boolean,
      default: false
    },
    sumText: [String],
    summaryMethod: [Function],

    // pagination的参数
    pgSmall: {
      type: Boolean,
      default: false
    },

    pageSize: {
      type: [Number, String],
      default: 10
    },

    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },

    pageSizes: {
      type: Array,
      default() {
        return this.pageSize == 10 ? [10, 20, 50, 100] : [this.pageSize, 10, 20, 50, 100].sort((a, b) => { return a - b })
      }
    },

    isPaginationFloat: {
      type: Boolean,
      default: false
    },

    // 是否将查询参数拼接到地址栏
    isShowParam: {
      type: Boolean,
      default: false
    },

    loadingBackground: {
      type: String,
      default: 'rgba(255, 255, 255, 0.9)'
    },

    // 是否滚动加载
    isScrollLoad: {
      type: Boolean,
      default: false
    },

    // 是否从route中获取查询参数，适用于弹出框列表， 建议弹出框列表都加上参数:is-get-route-query="false"
    isGetRouteQuery: {
      type: Boolean,
      default: true
    }
	},

	data() {

		return {
			currentPage: 1,
			total: 0,
      pgSize: Number.parseInt(this.pageSize),
      isLoadDataComplete: false, // 是否已经加载完数据，分页使用
			showData: [],
      orderParams: {
        orderBy: '',
        order: ''
      },
      multipleSelection: [],
      loading: false,
      currentData: [],

      dialogVisible: false,

      selectColumn: [],
      columns: [],
      columnFeilds: [],

      isFloat: '',
      paganationStyle: {
        width: null,
        left: null
      }
		}
	},

  computed: {

    // 判断前端还是后端，传url还是data
    isServer() {
      return !(this.url === undefined)
    },

    isSortable() {
      return Object.getOwnPropertyNames(this.defaultSort).includes('order')
    },

    isAllDataLoaded() {
      return this.currentPage * this.pageSize >= this.total;
    },

    // TODO... 还需要重新判断，当有slot时，table传了layout的时候
    layoutSlot() {
      if (this.columns.length > 0) {
        return 'slot, total, sizes, prev, pager, next, jumper'
      } else if (this.layout) {
        return this.layout
      }
    },

    paginationClass() {
      return this.isPaginationFloat ? 'app-table-pagination--float app-border--top' : null
    },

    tableCls() {
      const cls = ['app-table', 'app-normal-bg']

      if (this.size != 'medium') cls.push(`app-table--${this.size}`)
      if (this.height === '100%') cls.push('app-table--full')
      return cls
    }
  },

  watch: {

    // data是传进来的数据，不主动改变
    data(val) {
      this.currentData = _.cloneDeep(val)
      if (!this.pagination) {
        this.showData = this.currentData
      }
    },

    // cloneDeep传进来的data，排序使用
    currentData() {
      if (this.pagination) {
        this.handleCurrentChange(this.currentPage)
      }
    },

    // 修改loading遮罩的高度
    loading(val) {
      if (val) {

        // this.resizeHeightLoading()
      }
    }
  },

  components: {
    renderColumn
  },

	methods: {
    init() {

      // 是否排序
      if (this.isSortable) {
        this.orderParams.orderBy = this.defaultSort.prop
        this.orderParams.order = this.defaultSort.order
      }
    },

    formatParams(params) {
      for (let key in params) {
        if (params[key] instanceof Date) {
          params[key] = moment(params[key]).format('yyyy-MM-dd HH:mm:ss')
        } else {
          params[key] = params[key]
        }
      }
      return params
    },

    // 参数拼接
    joinParams(params) {
      let paramsStr = ''
      if (params) {
        paramsStr = '?'
        _.mapKeys(params, (value, key) => {
          paramsStr += key + '=' + value + '&'
        })
        paramsStr = paramsStr.substring(0, paramsStr.length - 1)
      }
      return paramsStr
    },

    /**
     * 请求数据
     */
    _getData(isPage) {

      // 刚开始加载页面时，page不是1，size不是10的时候，此时table默认page,size会覆盖前者
      // 当前页size或者page发生变化时，如果先取this.currentPage, 那么this.queryParams传过来的page会覆盖this.currentPage
      let param = Object.assign(
        {
          pageNum: this.currentPage,
          pageSize: this.pgSize
        },
        this.queryParams(),
        this.orderParams)

      // 将param显示在地址栏URL上
      // if (this.isShowParam) {
      //   this.$router.replace({path: this.$route.path, query: this.formatParams(interceptParams(param))})
      // }
      this.ifUseLoading ? this.loading = true : this.loading = false
      const method = this.methods.toLowerCase()
      getTableData(this.url, interceptParams(param)).then(res => {
        if (res.data) {
          if (Array.isArray(res.data)) {
            this.showData = res.data
            this.total = res.total != undefined ? res.total : res.count != undefined ? res.count : this.total
          } else {
            this.showData = []
            this.total = 0
            console.warn(res.msg)
          }
        } else {
          this.showData = []
          this.total = 0
          console.warn(res.msg)
        }

        // 发事件
        this.dataLoadComplete(res)
        this.loading = false
        if (isPage) {
          this.$emit('current-change-page', res.data && res.data.list)
        }

        this.isLoadDataComplete = true
      },
      (res) => {
        console.warn(res.msg)
        this.loading = false
      })
      // this.$ajax[method](this.url, interceptParams(param)).then(
      //   (res) => {
      //     if (res.data) {
      //       if (Array.isArray(res.data.records) || Array.isArray(res.data.list)) {
      //         this.showData = res.data
      //         this.total = res.data.total != undefined ? res.data.total : res.count != undefined ? res.count : this.total
      //       } else {
      //         this.showData = []
      //         this.total = 0
      //         console.warn(res.msg)
      //       }
      //     } else {
      //       this.showData = []
      //       this.total = 0
      //       console.warn(res.msg)
      //     }

      //     // 发事件
      //     this.dataLoadComplete(res)
      //     this.loading = false
      //     if (isPage) {
      //       this.$emit('current-change-page', res.data && res.data.list)
      //     }

      //     this.isLoadDataComplete = true
      //   },
      //   (res) => {
      //     console.warn(res.msg)
      //     this.loading = false
      //   })
    },

    /**
     * 加载数据
     */
    _loadMore() {
      if (Math.ceil(this.total / this.pgSize) > this.currentPage) {
        this.currentPage++
        this.loading = true
        let param = Object.assign(
          {
            page: this.currentPage,
            size: this.pgSize
          },
          this.queryParams(),
          this.orderParams)
        this.$ajax.get(this.url, interceptParams(param)).then(
          (res) => {
            if (Array.isArray(res.data.records) || Array.isArray(res.data.list)) {
              let records = res.data.records || res.data.list
              this.showData.push(...records)
              this.total = res.data.total != undefined ? res.data.total : res.count != undefined ? res.count : this.total
            } else {
              console.warn(res.errmsg)
            }
            this.dataLoadComplete(res)
            this.loading = false
            this.$emit('current-change-page', res.data && res.data.records || res.data && res.data.list)
            this.isLoadDataComplete = true
          },
          (res) => {
            console.warn(res.errmsg)
            this.loading = false
          }
        )
      }
    },

    /**
     * 监听滚动事件
     */
    handleScroll() {
      let self = this
      let p = 0, t = 0, down = true

      return function() {
        p = this.scrollTop
          down = (t < p)
          t = p
          const sign = 10
          const scrollDistance = this.scrollHeight - this.scrollTop - this.clientHeight
          if (scrollDistance <= sign && down && !self.loading) {
            if (!this.pagination) {
              self._loadMore()
            }
          }
      }
    },

    // 改变当前显示条数
		handleSizeChange(size) {
      this.currentPage = 1
			this.pgSize = size
			this.handleCurrentChange(1)
		},

		// 翻页
		handleCurrentChange(page) {
      if (!this.isServer) {
        let tmpArr = []

        // 传data
        for (let i = 0; i < this.pgSize; i++) {
          let tmpData = this.currentData[(page - 1) * this.pgSize + i]

          if (tmpData) {
            tmpArr.push(tmpData)
          }
        }

        this.total = this.currentData.length
        this.showData = tmpArr
        this.$emit('current-change-page', page)
      } else {
        this._getData(true)
      }
    },

    // 暴露出去的方法
    // toFirstPage是false的情况只会刷新当前页
    reload(toFirstPage = true) {
      if (this.isServer) {
        if (toFirstPage) {
          this.currentPage = 1
          this._getData()
        } else {
          this._getData()
        }
      }
    },

    resizeHeightLoading() {
      this.$nextTick(() => {
        let vm = this.$el,
            headerHeight = vm.querySelector('.el-table__header-wrapper').offsetHeight == null ? 0 : vm.querySelector('.el-table__header-wrapper').offsetHeight,
            loadingHeight = vm.offsetHeight == null ? 0 : vm.offsetHeight

        vm.querySelector('.table-mask').style.height = loadingHeight - headerHeight + 'px'
      })
    },

    getTableData() {
      return this.isServer ? this.showData : this.currentData
    },

    getSelection() {
      return _.cloneDeep(this.multipleSelection)
    },

    dataLoadComplete(data) {
      this.$emit('data-load-complete', data)
    },

    // table methods
    clearSelection(...args) {
      return this.$children[0].clearSelection(...args)
    },

    toggleRowSelection(...args) {
      return this.$children[0].toggleRowSelection(...args)
    },

    toggleRowExpansion(...args) {
      return this.$children[0].toggleRowExpansion(...args)
    },

    setCurrentRow(...args) {
      return this.$children[0].setCurrentRow(...args)
    },

    // 对表格进行重绘
    doLayout(...args) {
      return this.$children[0].doLayout(...args)
    },

    // table event
    select(...args) {
      this.multipleSelection = args[0]
      this.$emit('select', ...args)
    },

    selectAll(...args) {
      this.multipleSelection = args[0]
      this.$emit('select-all', ...args)
    },

    selectionChange(...args) {
      this.multipleSelection = args[0]
      this.$emit('selection-change', ...args)
    },

    cellMouseEnter(...args) {
      this.$emit('cell-mouse-enter', ...args)
    },

    cellMouseLeave(...args) {
      this.$emit('cell-mouse-leave', ...args)
    },

    cellClick(...args) {
      this.$emit('cell-click', ...args)
    },

    cellDblclick(...args) {
      this.$emit('cell-dblclick', ...args)
    },

    rowClick(...args) {
      this.$emit('row-click', ...args)
    },

    rowContextmenu(...args) {
      this.$emit('row-contextmenu', ...args)
    },

    rowDblclick(...args) {
      this.$emit('row-dblclick', ...args)
    },

    headerClick(...args) {
      this.$emit('header-click', ...args)
    },

    sortChange(...args) {
      if (this.pagination) {
        if (this.isServer) {
          if (args.length != 0) {
            this.orderParams.order = args[0].order
            this.orderParams.orderBy = args[0].prop
          }
          this._getData()
        } else {
          if (args.length != 0) {
            let tmp = _.cloneDeep(this.currentData)
            if (args[0].order != null) {
              tmp.sort((a, b) => {
                return args[0].order == 'descending' ? b[args[0].prop] - a[args[0].prop] : a[args[0].prop] - b[args[0].prop]
              })
            }
            this.currentData = tmp
          }
        }
      }

      this.$emit('sort-change', ...args)
    },

    filterChange(...args) {
      this.$emit('filter-change', ...args)
    },

    currentChange(...args) {
      this.$emit('current-change', ...args)
    },

    headerDragend(...args) {
      this.$emit('header-dragend', ...args)
    },

    expandChange(...args) {
      this.$emit('expand-change', ...args)
    },

    /**
     * 列的全集不变的情况下用方法获取，当列全集有变化时必须通过计算属性
     * 获取自定义列表中所有列的数据，并计算默认选中的列
     */
    getColumnFeilds() {
      let feilds = []
      this.columns.map(_column => {
        if (_column.prop) {
          if (_column.default) {
            this.selectColumn.push(_column.prop)
          }
          feilds.push({
            key: _column.prop,
            label: _column.label
          })
        }
      })
      this.columnFeilds = feilds
    },

    /**
     * 弹出自定义列选择框
     */
    filterColumnHandler() {
      this.dialogVisible = true
    },

    /**
     * 自定义列保存操作
     */
    saveSelectColumn() {
      let updateColumns = [], _column = {}
      if (this.selectColumn && this.selectColumn.length > 0) {
        this.columns.forEach((item, index) => {
          _column = item
          _column.default = false
          for (let i = 0; i < this.selectColumn.length; i++) {
            if (item.prop == this.selectColumn[i]) {
              _column.default = true
              break
            }
          }

          // 如果传了type，默认显示该行
          if (this.showColumns(_column)) {
            _column.default = true
          }
          updateColumns.push(_column)
        })
        this.columns = updateColumns
        this.dialogVisible = false
      } else {
        this.$message.error('表格显示的列不能为空！')
      }
    },

    /**
     * 判断是否展示该列
     */
    showColumns(_column) {
      if (_column.default || _column.type || _column.fixed) {
        return true
      }
      return false
    },

    /**
     * 固定分页位置和宽度计算
     */
    calculatePaginationiPos() {
      const bounding = this.container.getBoundingClientRect()
      this.paginationFixed = true
      this.paganationStyle.left = (bounding.x || bounding.left) + 'px' // 兼容性问题， x|left属性
      this.paganationStyle.width = bounding.width + 'px'
    },

    addPagination() {
      if (!this.isScrollLoad && this.pagination && this.isPaginationFloat) {
        document.body.appendChild(this.$refs.pagination);
      }
    },

    removePagination() {
      if (this.pagination && this.isPaginationFloat) {
        document.body.removeChild(this.$refs.pagination)
        removeResise(this.container, this.calculatePaginationiPos)
      }
    }
  },

  created() {
    this.$on('column.add', function(datas) {
      if (datas.props) {
        let _col = _.cloneDeep(datas.props)
        this.columns.push(_col)
      }
    })
  },

  activated() {
    console.log('table activated')
    this.addPagination()
  },

  deactivated() {
    console.log('table deactivated')
    this.removePagination()
  },

	mounted() {
    this.container = document.querySelector('.app-container__main__content') || document.querySelector('.app-container-home')
    this.init()

    if (!this.isScrollLoad && this.pagination && this.isPaginationFloat) {

      // 固定到body下，解决层叠上下文对显示的影响ß
      document.body.appendChild(this.$refs.pagination);
    }

    if (!this.pagination) {

      this.showData = this.currentData = _.cloneDeep(this.data)
    } else if (!this.isServer) {
      this.currentData = _.cloneDeep(this.data)
      this.isLoadDataComplete = true // 数据加载完毕

      // 传data
      this.handleCurrentChange(1)
    } else {

      // 从route中取page,size,order,orderBy
      // TODO 为弹出框的时候，获取route中的order，orderBy 是错误的，待解决
      if (this.isGetRouteQuery && this.isShowParam) {
        let query = this.$route.query
        this.currentPage = +query.page || this.currentPage
        this.pgSize = +query.size || this.pgSize
        this.orderParams.order = query.order || this.orderParams.order
        this.orderParams.orderBy = query.orderBy || this.orderParams.orderBy
      } else {
        this.currentPage = this.currentPage
        this.pgSize = this.pgSize
        this.orderParams.order = this.orderParams.order
        this.orderParams.orderBy = this.orderParams.orderBy
      }

      if (this.isScrollLoad) {

        // 滚动加载
        !this.lazy && this._getData()
        this.$el.querySelector('.el-table__body-wrapper').addEventListener('scroll', this.handleScroll())
      } else {

        // 默认直接加载数据，延迟加载情况下，由调用者确定执行时机
        !this.lazy && this._getData()
      }
    }

    this.getColumnFeilds()
    this.isPaginationFloat && addResize(this.container, this.calculatePaginationiPos)
  },

  beforeDestroy() {
    this.removePagination()
  }
}
</script>

<style lang="scss">
.app-table {
  position: relative;

  // .el-table th,
  .el-table td {
    .cell {
      line-height: 36px;
    }
  }
  &--small,
  &--mini {
    // .el-table th,
    .el-table td {
      padding: 1px 0 !important;
      .cell {
        line-height: 36px;
      }
    }
  }

  &--full {
    height: 100%;
  }

  .loadtip {
    text-align: center;
  }

  .table-nowrap {
    // .is-leaf > div {
    //   white-space: nowrap;
    // }
    .el-table__row td > div {
      white-space: nowrap;
    }
  }

  .table-mask {
    position: absolute;
    top: 1px;
    left: 1px;
    bottom: 1px;
    right: 1px;
    z-index: 10;
  }

  &--filtercolumn {
    text-align: center;
    margin-top: 15px;
  }

  .el-transfer {
    .el-transfer-panel {
      width: 40%;
    }
  }
}

.app-table-pagination {
  text-align: right;
  padding: 5px 0;
  padding-right: 5px;
  &:not(.app-table-pagination--float) {
    border-top: none;
  }
  &--float {
    background-color: #ffffff;
    border-left: none;
    padding-left: 15px;
    padding-right: 15px;
    position: fixed;
    bottom: 0;
    z-index: 99;
  }
}
</style>
