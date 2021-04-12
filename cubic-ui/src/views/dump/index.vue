<template>
  <div v-loading="loading" class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span style="font-size: 14px;">应用： </span>
        <el-select
          v-model="instanceName"
          size="mini"
          style="width: 280px"
          filterable
          placeholder="请选择应用"
          @change="appChange">
          <el-option v-for="item in appOption" :key="item" :label="item" :value="item"/>
        </el-select>

        <span style="font-size: 14px;">实例： </span>
        <el-select
          v-model="instanceUid"
          size="mini"
          style="width: 280px"
          placeholder="请选择应用实例"
          @change="instanceUidChange"
        >
          <el-option v-for="item in instanceUidOption" :key="item" :label="item" :value="item"/>
        </el-select>
      </div>
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="线程栈" name="first">
          <el-card style="margin-top: 10px">
            <div slot="header" class="clearfix">
              <el-date-picker
                v-model="secondDateTime"
                type="datetime"
                size="mini"
                placeholder="选择日期时间"
                value-format="yyyy-MM-dd HH:mm"
                @change="handleSecondDateTime"
              />
              <el-button size="mini" type="primary" @click="querySum">查询</el-button>
              <el-button type="text" style="float: right" @click="downLoadTxtFile">下载</el-button>
            </div>
            <pre v-if="threadDetail">
                    {{ threadDetail }}
            </pre>
            <div v-else>
              无数据
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="实时线程栈" name="second">
          <el-card style="margin-top: 10px">
            <div slot="header" class="clearfix">
              <el-button size="mini" type="primary" @click="querySum">查询</el-button>
              <el-button type="text" style="float: right" @click="downLoadTxtFileReal">下载</el-button>
            </div>
            <pre v-if="threadRealDetail">
                    {{ threadRealDetail }}
            </pre>
            <div v-else>
              无数据
            </div>
          </el-card>
        </el-tab-pane>
      </el-tabs>

    </el-card>
  </div>
</template>
<script>
import {getThreadsDetailsByUid, getThreadsRealDetailsByUid} from '@/utils/threadMonitorApi'
import {getAppNames, getInstanceInfo, getInstanceNames} from '@/api/list'
import moment from 'moment'

export default {
  name: 'Dump',
  data() {
    return {
      secondDateTime: moment().subtract(1, 'm').format('YYYY-MM-DD HH:mm'),
      instanceUid: '',
      instanceName: '',
      tableOther: {},
      search: '',
      checkList: ['RUNNABLE'],
      activeName: 'first',
      loading: false,
      timeStamp: true,
      threadName: '',
      dayTime: '',
      tableData: [],
      indexActive: 0,
      count: 8000,
      rangeDate: [],
      threadDetail: '',
      threadRealDetail: '',
      scatterChartData: {},
      instanceUidOption: [],
      appOption: []
    }
  },
  computed: {},
  watch: {
    checkList(val) {
      this.getThreadsDetailsByMin()
    }
  },
  created() {
    this.instanceUid = this.$cookies.get('appId')
    this.instanceName = this.$cookies.get('instanceName')
    this.getInstanceList({name: this.instanceName})
    this.getAppList()
  },
  methods: {
    getInstanceList(params) {
      const _this = this
      getInstanceNames(params).then(res => {
        _this.instanceUidOption = res.data
        _this.instanceUid = res.data[0]
      })
    },
    getAppList() {
      const _this = this
      getAppNames().then(res => {
        _this.appOption = res.data
        if (_this.instanceName !== '') {
          //TODO 第一个赋值默认的
        } else {
          _this.instanceName = res.data[0]
        }
      })
    },
    instanceUidChange(val) {
      this.$cookies.set("instanceUid", val)
      this.getInstanceDetail({appId: val})
    },
    appChange(val) {
      this.instanceName = val;
      this.instanceUid = '';
      this.$cookies.set("instanceName", val)
      this.$cookies.set("instanceUid", '')
      this.getInstanceList({name: this.instanceName});
    },
    handleTabClick(obj, e) {
      if (obj.name === 'first') {
        this.getThreadTimeDetail()
      }

      if (obj.name === 'second') {
        this.getThreadRealTimeDetail()
      }
    },
    handleSecondDateTime(val) {
      this.getThreadTimeDetail()
      console.log(val, 'x.....')
    },
    getThreadTimeDetail() {
      this.loading = true
      const params = {
        appId: this.instanceUid,
        dayTime: this.secondDateTime // 依赖日期
      }
      getThreadsDetailsByUid(params).then(res => {
        this.loading = false
        console.log(res)
        if (res.code === 200) {
          this.threadDetail = res.data
        } else {
          this.$message.error(res.msg)
        }
      }).catch(() => {
        this.loading = false
      })
    },
    querySum() {
      if (this.activeName === 'first') {
        this.getThreadTimeDetail()
      }

      if (this.activeName === 'second') {
        this.getThreadRealTimeDetail()
      }
    },
    getThreadRealTimeDetail() {
      this.loading = true
      const params = {
        instanceUuid: this.instanceUid,
        type: 2,
        command: 'dump'
      }
      getThreadsRealDetailsByUid(params).then(res => {
        this.loading = false
        console.log(res)
        if (res.code === 200) {
          this.threadRealDetail = res.data
        } else {
          this.$message.error(res.msg)
        }
      }).catch(() => {
        this.loading = false
      })
    },
    downLoadTxtFileReal() {
      if (!this.threadRealDetail) {
        this.$message.warning('数据不能为空')
      }
      // let str = this.threadDetail.join("")
      // const name = this.instanceUidOption.length > 0
      //   ? this.instanceUidOption.find(item => item.uid === this.searchChart.uuid).name
      //   : this.serviceName
      // let dataUrl = btoa(this.threadDetail);
      // console.log(dataUrl)
      const a = document.createElement('a')
      a.href = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(this.threadRealDetail)
      a.download = name + '(' + this.dayTime + ').dump'
      a.click()
    },

    downLoadTxtFile() {
      if (!this.threadDetail) {
        this.$message.warning('数据不能为空')
      }
      // let str = this.threadDetail.join("")
      const name = this.instanceUidOption.length > 0
        ? this.instanceUidOption.find(item => item.uid === this.searchChart.uuid).name
        : this.serviceName
      // let dataUrl = btoa(this.threadDetail);
      // console.log(dataUrl)
      const a = document.createElement('a')
      a.href = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(this.threadDetail)
      a.download = name + '(' + this.dayTime + ').dump'
      a.click()
    }
  }
}
</script>

<style lang="scss" scoped>
.el-loading-mask {
  position: fixed;
}

.legend-color-runnable {
  color: #52c41a;
}

.legend-color-blocked {
  color: #722ed1;
}

.legend-color-waiting {
  color: #faad14;
}

.legend-color-unknown {
  color: #d9d9d9;
}

.legend-color-timed-waiting {
  color: #fadb14;
}

.search {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;

  .time {

  }

  .filterType {

  }
}

.showDemo {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.log-panel {
  display: flex;
}

.infinite-list {
  height: 352px;
  width: 320px;
  padding: 6px;
  margin: 0px;;
  list-style: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  transition: all .3s;
  border-radius: 3px;
}

.infinite-list .infinite-list-item {
  display: flex;
  height: 26px;
  line-height: 26px;
  border-bottom: 1px solid #e8f3fe;
  font-size: 14px;
  position: relative;
  cursor: pointer;
  z-index: 1;
}

.infinite-list-item-active {
  display: flex;
  height: 26px;
  line-height: 26px;
  color: #409EFF;
  border-bottom: 1px solid #e8f3fe;
  border-radius: 6px;
  position: relative;
  cursor: pointer;
  z-index: 1;
}

.infinite-list-item-name {
  flex: 1;
  margin-right: 32px;
  display: block;
  width: 138px;
  overflow: hidden;
  white-space: nowrap;
  z-index: 99;
}

.infinite-list-item-bgc {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  z-index: 0;
  background-color: #afd5f2;
}

.infinite-list-item-val {
  z-index: 99;
}

.app-container .el-card__header {
  padding: 8px 20px;
}

.chart-wrapper {
  margin-left: 10px;
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  border-radius: 3px;
}

.legend {
  display: inline-block;
  margin-right: 10px;

  .legend-color {
    position: relative;
    display: inline-block;
    width: 32px;
    height: 10px;
    vertical-align: middle;
    border-radius: 6px;
  }

  .legend-color-new {
    background-color: #1890ff;
  }

  .legend-color-runnable {
    background-color: #52c41a;
  }

  .legend-color-blocked {
    background-color: #722ed1;
  }

  .legend-color-waiting {
    background-color: #faad14;
  }

  .legend-color-unknown {
    background-color: #d9d9d9;
  }

  .legend-color-timed-waiting {
    background-color: #fadb14;
  }

  .legend-color-terminated {
    background-color: #f5222d;
  }

  .legend-text {
    font-size: 12px;
  }
}
</style>
