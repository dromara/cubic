<template>
  <div class="app-container case-list-container">
    <el-card class="box-card">
      <div class="case-info">
        <el-tag class="mr">线程池总数：{{  this.total }}</el-tag>
        <el-input
          v-model="search"
          size="mini"
          style="width: 220px"
          placeholder="应用名称:输入关键字搜索"
        />
        <span style="float: right;padding: 4px 10px">
           <el-date-picker
             v-model="secondDateTime"
             type="datetime"
             size="mini"
             placeholder="选择日期时间"
             value-format="yyyy-MM-dd HH:mm"
           />
          <el-button
            class="filter-item ml"
            size="mini"
            type="primary"
            icon="el-icon-search"
            @click="getList"
          >搜索</el-button>
        </span>
      </div>
      <div slot="header" class="clearfix">
        <span style="font-size: 14px;">应用： </span>
        <el-select
          class="mr"
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
      <!--表格渲染-->
      <el-table
        v-loading="listLoading"
        :data="tableData.filter(data => !search || data.threadPoolKey.toLowerCase().includes(search.toLowerCase()))"
        border
        size="small"
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column :show-overflow-tooltip="true" prop="threadPoolKey" label="线程池唯一标识" />
        <el-table-column :show-overflow-tooltip="true" prop="coreSize" label="核心线程" />
        <el-table-column :show-overflow-tooltip="true" prop="maximumPoolSize" label="最大线程" />
        <el-table-column :show-overflow-tooltip="true" prop="poolSize" label="队列长度" />
        <el-table-column :show-overflow-tooltip="true" prop="activeCount" label="活跃线程" />
        <el-table-column :show-overflow-tooltip="true" prop="keepAliveTime" label="存活时间" />
        <el-table-column :show-overflow-tooltip="true" prop="taskCount" label="任务数" />
        <el-table-column :show-overflow-tooltip="true" prop="completedTaskCount" label="完成任务数" />
        <el-table-column :show-overflow-tooltip="true" prop="largestPoolSize" label="历史最大线程数" />
        <el-table-column :show-overflow-tooltip="true" prop="createTime" label="创建时间" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import {appList, getAppNames, getInstanceNames, threadPoolList} from '@/api/list'
import moment from "moment";

// arr to obj, such as { CN : "China", US : "USA" }
export default {
  name: 'List',
  data() {
    return {
      total: '',
      instanceUid: '',
      instanceName: '',
      instanceUidOption: [],
      appOption: [],
      search: '',
      tableData: [],
      listLoading: true,
      secondDateTime: moment().subtract(1, 'm').format('YYYY-MM-DD HH:mm'),
      dialogVisible: false,
      drawerVisible: false,
      sort: 'id_desc',
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      }
    }
  },
  created() {
    this.instanceUid = this.$cookies.get('appId')
    this.instanceName = this.$cookies.get('instanceName')
    this.getInstanceList({name: this.instanceName})
    this.getAppList()
    this.getList()
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
    },
    appChange(val) {
      this.instanceName = val;
      this.instanceUid = '';
      this.$cookies.set("instanceName", val)
      this.$cookies.set("instanceUid", '')
      this.getInstanceList({name: this.instanceName});
    },
    getList() {
      this.listLoading = true
      let _this;
      _this = this;
      threadPoolList({ instanceUid:  _this.instanceUid, dayTime: _this.secondDateTime }).then(response => {
        _this.tableData = response.data.records
        _this.total = response.data.total
      }).finally(() => {
        this.listLoading = false
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    }

  }
}
</script>

<style lang="scss" scoped>
  .mr {
    margin-right: 20px;
  }
  .ml {
    margin-left: 10px;
  }
</style>
