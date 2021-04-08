<template>
  <div v-loading="loading" class="app-container">
    <el-backtop target=".app-view"/>
    <el-card>
      <div slot="header" class="clearfix">
        <span style="font-weight: 800">{{ "实例名称：" }}</span>
        <el-select v-model="searchChart.uuid" style="width: 400px" size="mini" placeholder="请选择" @change="instanceUidChange">
          <el-option
            v-for="item in instanceUidOption"
            :key="item.uid"
            :label="item.name"
            :value="item.uid"/>
        </el-select>
        <div class="block" style="float: right; padding: 3px 0" >
          <el-date-picker
            v-model="rangeDate"
            :default-time="['12:00:00', '12:00:00']"
            :picker-options="pickerOptions"
            size="mini"
            type="datetimerange"
            align="right"

            value-format="yyyy-MM-dd HH:mm:ss"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="rangeDateChange"/>
        </div>
      </div>
      <line-chart :chart-data="lineChartData" />
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="线程详情" name="first">
          <el-card>
            <div slot="header" class="clearfix">
              <div class="search">
                <div class="time">

                  <el-date-picker
                    v-model="firstDateTime"
                    type="datetime"
                    size="mini"
                    placeholder="选择日期时间"
                    value-format="yyyyMMddHHmm"
                    @change="handleFirstDateTime"
                  />
                  <el-button size="mini" type="primary" @click="querySum">查询</el-button>
                </div>
                <div class="filterType">
                  <el-checkbox-group v-model="checkList">
                    <el-checkbox label="BLOCKED"/>
                    <el-checkbox label="RUNNABLE"/>
                    <el-checkbox label="WAITING"/>
                    <el-checkbox label="TIMED_WAITING"/>
                  </el-checkbox-group>
                </div>
              </div>
              <div class="showDemo">
                <div>
                  <el-input
                    v-model="search"
                    :style="{width: '250px'}"
                    size="mini"
                    placeholder="输入threadName搜索"/>
                </div>
                <div>
                  <span class="legend">
                    <span class="legend-color legend-color-blocked"/>
                    <span class="legend-text">BLOCKED({{ tableOther.blocked }})</span>
                  </span>
                  <span class="legend">
                    <span class="legend-color legend-color-runnable"/>
                    <span class="legend-text">RUNNABLE({{ tableOther.runable }})</span>
                  </span>
                  <span class="legend">
                    <span class="legend-color legend-color-timed-waiting"/>
                    <span class="legend-text">WAITING/TIMED_WAITING({{ tableOther.wait }})</span>
                  </span>

                  <span class="legend">
                    <span class="legend-color legend-color-unknown"/>
                    <span class="legend-text">OTHER({{ tableOther.other }})</span>
                  </span>
                  <span class="legend">
                    <span class="legend-color legend-color-unknown"/>
                    <span class="legend-text">TOTAL({{ tableOther.total }})</span>
                  </span>
                </div>
              </div>
            </div>
            <el-table
              :data="tableData.filter(data => !search || data.threadName.toLowerCase().includes(search.toLowerCase()))"
              class="table-calss"
              style="width: 100%">
              <el-table-column
                prop="id"
                width="230"
                label="id"
              />
              <el-table-column
                prop="stack"
                label="StackTrace"
                align="center"
                width="100">
                <template slot-scope="scope">
                  <el-popover trigger="hover" placement="top">
                    <pre v-html="scope.row.stack"/>
                    <div slot="reference">
                      <i class="el-icon-view"/>
                    </div>
                  </el-popover>
                </template>
              </el-table-column>
              <el-table-column
                show-overflow-tooltip

                prop="threadName"
                label="threadName"
              />
              <el-table-column
                sortable
                prop="cpuTime"
                label="cpuTime"
                width="180"/>
              <el-table-column
                show-overflow-tooltip
                prop="threadState"

                label="State"
                width="180">
                <template slot-scope="scope">
                  <div :class="getClassNameBy(scope.row.threadState)">
                    {{ scope.row.threadState }}
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="线程栈" name="second">
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
        <el-tab-pane label="实时线程栈" name="third">
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
import { getThreadCharts, getThreadDetails, getThreadsDetailsByUid, getThreadsDetailsByMin, getThreadsRealDetailsByUid } from '@/utils/threadMonitorApi'
import { getInstanceNames } from '@/api/logs/logsMonitorApi'
import LineChart from './components/LineChart'
import ScatterChart from './components/ScatterChart'
import Badge from './components/badge'
import moment from 'moment'
import Axios from 'axios'
export default {
  components: { LineChart, ScatterChart, Badge },
  data() {
    return {

      tableOther: {},
      search: '',
      checkList: ['RUNNABLE'],
      secondDateTime: moment().subtract(1, 'm').format('YYYY-MM-DD HH:mm'),
      firstDateTime: moment().subtract(1, 'm').format('YYYYMMDDHHmm'),
      activeName: 'first',
      loading: false,
      serviceName: JSON.parse(localStorage.getItem('projectInfo')).name,
      timeStamp: true,
      searchChart: {
        uuid: '',
        serviceName: JSON.parse(localStorage.getItem('projectInfo')).name,
        timePeriod: '30',
        endTime: ''
      },
      threadName: '',
      dayTime: '',
      instanceUidOption: [],
      tableData: [],
      indexActive: 0,
      count: 8000,
      rangeDate: [],
      lineChartData: {
        timeData: [],
        totalStartedCount: [],
        threadCount: [],
        daemonCount: [],
        peakCount: []
      },
      threadDetail: '',
      threadRealDetail: '',
      scatterChartData: {},
      pickerOptions: {
        shortcuts: [
          {
            text: '最近30分钟',
            onClick: (picker) => {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 500)
              this.searchChart.timePeriod = '30'
              this.timeStamp = false
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近1个小时',
            onClick: (picker) => {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000)
              this.searchChart.timePeriod = '60'
              this.timeStamp = false
              picker.$emit('pick', [start, end])
            }
          }
        ]
      }
    }
  },
  computed: {
    getUUid() {
      return this.searchChart.uuid
    }
  },
  watch: {
    checkList(val) {
      console.log(val, '...checkList')
      this.getThreadsDetailsByMin()
    }
  },
  created() {
    this.getInstanceList()
  },
  methods: {
    getClassNameBy(type) {
      switch (type) {
        case 'RUNNABLE':
          return 'legend-color-runnable'
        case 'BLOCKED':
          return 'legend-color-blocked'
        case 'WAITING':
          return 'legend-color-waiting'
        case 'TIMED_WAITING':
          return 'legend-color-timed-waiting'
        default:
          return 'legend-color-unknown'
      }
    },
    handleTypeClick(e) {
      console.log(e, 'x.....')
    },
    handleTabClick(obj, e) {
      if (obj.name === 'first') {
        this.getThreadsDetailsByMin()
      }

      if (obj.name === 'second') {
        this.getThreadTimeDetail()
      }

      if (obj.name === 'third') {
        this.getThreadRealTimeDetail()
      }
    },
    handleFirstDateTime(val) {
    //   console.log(val, 'x.....')
    },
    handleSecondDateTime(val) {
    //   this.getThreadTimeDetail()
      console.log(val, 'x.....')
    },
    querySumThird() {

    },
    querySum() {
      if (this.activeName === 'first') {
        this.getThreadsDetailsByMin()
      }

      if (this.activeName === 'second') {
        this.getThreadTimeDetail()
      }

      if (this.activeName === 'third') {
        this.getThreadRealTimeDetail()
      }
    },
    getThreadsDetailsByMin(val) {
      this.loading = true

      const params = {
        uid: this.getUUid,
        serviceName: this.serviceName,
        dayTime: this.firstDateTime,
        threadState: this.checkList.join(',')
      }

      getThreadsDetailsByMin(params)
        .then((res) => {
          this.loading = false
          console.log(res, 'xxx')
          if (res.code === 0) {
            this.tableData = res.data.threads
            this.tableOther = res.data
          } else {
            this.$message.error('错误' || res.msg)
          }
        }).catch((e) => {
          this.loading = false
          console.log(e, 'x....')
        })
    },
    // 获取实例列表
    getInstanceList() {
      this.loading = true

      const params = {
        serviceName: this.serviceName,
        endTime: this.searchChart.endTime,
        timePeriod: this.searchChart.timePeriod
      }

      // 获得实例 获得uuid
      getInstanceNames(params).then(res => {
        this.instanceUidOption = res.data
        this.loading = false
        if (res.code === 0) {
          // this.instanceUid = res.data[0].uid
          this.searchChart.uuid = res.data[0].uid
        } else {
          this.$message.error(res.msg)
        }
      }).then(() => {
        this.getThreadChartData()
        this.getThreadsDetailsByMin()
      })
    },
    // 获取折线图 依赖uuid
    getThreadChartData() {
      this.loading = true
      getThreadCharts(this.searchChart).then(res => {
        console.log(res)
        if (res.code === 0) {
          if (res.data.length > 0) {
            this.loading = false
            this.lineChartData.timeData = res.data[0].xaxis
            res.data.forEach(item => {
              this.lineChartData[item.name] = item.yaxis
            })
            return true
          } else {
            this.$message.error('监控折线图数据为空')
            this.tableData = []
            this.threadName = ''
            this.dayTime = ''
            this.threadDetail = ''
            return false
          }
        } else {
          this.$message.error(res.msg)
          return false
        }
      })
    },
    // 获取thread表格详情
    getThreadTableDetails(params) {
      this.loading = true
      getThreadDetails(params).then(res => {
        console.log(res)
        this.loading = false

        if (res.code === 0) {
          if (res.data.length > 0) {
            this.tableData = res.data.slice(0, 20)
            // this.tableData = res.data
            return true
          } else {
            this.$message.error('表格数据为空')
            this.tableData = []
            this.threadName = ''
            this.dayTime = ''
            this.threadDetail = ''
            return false
          }
        } else {
          this.$message.error(res.msg)
        }
      }).then(val => {
        if (!val) return

        const params = {
          uid: this.searchChart.uuid,
          serviceName: this.serviceName,
          dayTime: this.tableData[0].chats[0].date // 依赖日期
        }

        this.threadName = this.tableData[0].name
        this.dayTime = this.tableData[0].chats[0].date
        this.getThreadTimeDetail(params)
      })
    },
    getThreadRealTimeDetail() {
      this.loading = true
      const params = {
        uid: this.searchChart.uuid,
        serviceName: this.serviceName
        // dayTime: this.secondDateTime // 依赖日期
      }
      getThreadsRealDetailsByUid(params).then(res => {
        this.loading = false
        console.log(res)
        if (res.code === 0) {
          this.threadRealDetail = res.data
        } else {
          this.$message.error(res.msg)
        }
      }).catch(() => {
        this.loading = false
      })
    },
    // 获取线程时间点详情
    getThreadTimeDetail() {
      this.loading = true
      const params = {
        uid: this.searchChart.uuid,
        serviceName: this.serviceName,
        dayTime: this.secondDateTime // 依赖日期
      }
      getThreadsDetailsByUid(params).then(res => {
        this.loading = false
        console.log(res)
        if (res.code === 0) {
          this.threadDetail = res.data
        } else {
          this.$message.error(res.msg)
        }
      }).catch(() => {
        this.loading = false
      })
    },
    rangeDateChange(val) {
      if (this.timeStamp) {
        const stamp = (moment(val[1]) - moment(val[0])) / 60000
        this.searchChart.timePeriod = stamp > 1 ? Math.round(stamp) : 1
        this.searchChart.endTime = val[1]
      } else {
        this.searchChart.endTime = val[1]
      }
      this.timeStamp = true
      this.getInstanceList()
    },
    instanceUidChange(val) {
      console.log(val)
      this.getThreadChartData()
      if (this.activeName === 'first') {
        this.getThreadsDetailsByMin()
      }

      if (this.activeName === 'second') {
        this.getThreadTimeDetail()
      }
    },
    stateClick(state, name) {
      console.log(state)
      this.threadName = name
      this.dayTime = state.date
      const params = {
        uid: this.searchChart.uuid,
        serviceName: this.serviceName,
        dayTime: state.date
      }
      this.getThreadTimeDetail(params)
    },

    downLoadTxtFileReal() {
      if (!this.threadRealDetail) {
        this.$message.warning('数据不能为空')
      }
      // let str = this.threadDetail.join("")
      const name = this.instanceUidOption.length > 0
        ? this.instanceUidOption.find(item => item.uid === this.searchChart.uuid).name
        : this.serviceName
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
  .legend-color-runnable{
            color: #52c41a;
        }
        .legend-color-blocked{
            color: #722ed1;
        }
        .legend-color-waiting{
            color: #faad14;
        }
        .legend-color-unknown{
            color: #d9d9d9;
        }

        .legend-color-timed-waiting{
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

    .log-panel{
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
    .infinite-list-item-active{
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
    .infinite-list-item-name{
        flex: 1;
        margin-right: 32px;
        display: block;
        width: 138px;
        overflow:hidden;
        white-space: nowrap;
        z-index: 99;
    }
    .infinite-list-item-bgc{
        position: absolute;
        top: 0;
        left: 0;
        height: 100%;
        z-index: 0;
        background-color: #afd5f2;
    }
    .infinite-list-item-val{
        z-index: 99;
    }
    .app-container .el-card__header{
        padding: 8px 20px;
    }
    .chart-wrapper{
        margin-left: 10px;
        width: 100%;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        border-radius: 3px;
    }
    .legend{
        display: inline-block;
        margin-right: 10px;
        .legend-color{
           position: relative;
            display: inline-block;
            width: 32px;
            height: 10px;
            vertical-align: middle;
            border-radius: 6px;
        }
        .legend-color-new{
            background-color: #1890ff;
        }
        .legend-color-runnable{
            background-color: #52c41a;
        }
        .legend-color-blocked{
            background-color: #722ed1;
        }
        .legend-color-waiting{
            background-color: #faad14;
        }
        .legend-color-unknown{
            background-color: #d9d9d9;
        }
        .legend-color-timed-waiting{
            background-color: #fadb14;
        }
        .legend-color-terminated{
            background-color: #f5222d;
        }

        .legend-text{
            font-size: 12px;
        }
    }
</style>
