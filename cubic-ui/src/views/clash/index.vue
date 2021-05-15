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
      <div class="check" >
        <el-button @click="check" type="primary" class="rigth">
          检测冲突
        </el-button>
      </div>
      <div class="bottom-crad">
        <el-card class="left-card">
          <div slot="header" class="clearfix-header">
            <span>依赖列表</span>
            <div class="header-input">
              <el-input v-model="search" @input="searchInput" placeholder="模糊搜索"></el-input>
            </div>
          </div>
          <div v-for="(value, key, index) in showList" :key="index" class="text item">
            {{ key }}
          </div>
        </el-card>
        <el-card class="rigth-card">
          <div slot="header" class="clearfix">
            <span>依赖冲突</span>
          </div>
          <el-form label-width="10px">
            <el-form-item v-for="(value, key, index) in detail" :key="index" label="">
              <span class="red">
                {{ key }}
              </span>
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
        // "junit-platform-commons-1.6.3.jar": 1,
        // "junit-vintage-engine-5.6.3.jar": 1,
        // "sunpkcs11.jar": 1,
        // "zipfs.jar": 1,
        // "mybatis-plus-core-3.4.2.jar": 1,
        // "spring-boot-devtools-2.3.5.RELEASE.jar": 1
      },
      detail: '',
      search: '',
      showList: {}
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
        _this.showList = response.data
      }).finally(() => {
        this.listLoading = false
      })
    },
    searchInput() {
      if (this.search == '') {
        this.showList = this.list
      } else {
        let search = this.search
        let obj = {}
        for (const key in this.list) {
          if (key.indexOf(search) != -1) {
            obj[key] = this.list[key]
          }
        }
        this.showList = obj
      }
    },
    check() {
      let list = {}
      for (const key in this.list) {
        if (this.list[key] > 1) {
          list[key] = this.list[key]
        }
      }
      this.detail = list
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

  .clearfix-header {
    display: flex;
    justify-content: space-between;
    span {
      line-height: 40px;
    }
  }
  .clearfix {
    span {
      line-height: 40px;
    }
  }

  .left-card {
    width: 100%;
    margin-right: 10px;
  }

  .rigth-card {
    width: 100%;
    margin-left: 10px;
  }

  .bottom-crad {
    display: flex;
    justify-content: space-between;
  }

  .red {
    color: red;
  }

  .check {
    position: relative;
    width: 100%;
    height: 50px;
    .rigth {
      position: absolute;
      right: 0;
      top: 0;
    }

  .header-input {
    width: 200px !important;
    display: inline-block;
  }
  }
</style>
