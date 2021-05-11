<template>
  <div class="app-container case-list-container">
    <el-card class="box-card">
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
        <el-button
            class="filter-item ml"
            size="mini"
            type="primary"
            icon="el-icon-search"
            @click="getList"
          >搜索</el-button>
      </div>
      <!-- 依赖列表 -->
      <div class="bottom-crad">
        <el-card class="left-card">
          <div slot="header" class="clearfix">
            <span>依赖列表</span>
          </div>
          <div v-for="(value, key, index) in list" :key="index" class="text item">
            <el-button @click="handleDetail(key)" type="text" size="small" :class="value.length > 1 ? 'red' : ''" >{{key + '(' + value.length + ')'}}</el-button>
          </div>
        </el-card>
        <el-card class="left-card">
          <div slot="header" class="clearfix">
            <span>版本详情</span>
          </div>
          <el-form label-width="80px">
            <el-form-item v-for="(item, index) in detail" :key="index" label="jar包名称:">
              {{ item }}
            </el-form-item>
          </el-form>
        </el-card>
      </div>
      
    </el-card>
  </div>
</template>

<script>
import {appList, getAppNames, getInstanceNames, getJarList} from '@/api/list'
import moment from "moment";

// arr to obj, such as { CN : "China", US : "USA" }
export default {
  name: 'Clash',
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
      list: {
        // "apiguardian": [
        //     "apiguardian-api-1.1.0.jar"
        // ],
        // "jsse.jar": [
        //     "jsse.jar"
        // ],
        // "tomcat": [
        //     "tomcat-embed-core-9.0.39.jar",
        //     "tomcat-embed-websocket-9.0.39.jar"
        // ],
        // "jakarta.activation": [
        //     "jakarta.activation-api-1.2.2.jar",
        //     "jakarta.activation-1.2.2.jar"
        // ]
      },
      detail: ''
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
      _this.detail = ''
      getJarList({ appId:  _this.instanceUid}).then(response => {
        _this.list = response.data
      }).finally(() => {
        this.listLoading = false
      })
    },
    handleDetail(row) {
      this.detail = this.list[row]
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
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .left-card {
    width: 500px;
  }

  .bottom-crad {
    display: flex;
    justify-content: space-around;
  }

  .red {
    color: red;
  }
</style>
