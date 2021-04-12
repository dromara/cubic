<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span style="font-size: 14px;">应用： </span>
        <el-select
          v-model="instanceName"
          size="mini"
          style="width: 280px"
          filterable
          placeholder="请选择应用"
          @change="appChange">
          <el-option v-for="item in appOption" :key="item" :label="item" :value="item" />
        </el-select>

        <span style="font-size: 14px;">实例： </span>
        <el-select
          v-model="instanceUid"
          size="mini"
          style="width: 280px"
          placeholder="请选择应用实例"
          @change="instanceUidChange"
        >
          <el-option v-for="item in instanceUidOption" :key="item" :label="item" :value="item" />
        </el-select>
      </div>

      <el-tabs v-model="activeName">
        <el-tab-pane label="概要" name="1">
          <base-info
            :server-info-table="serverInfoTable"
            :jvm-base-table="jvmBaseTable"
            :jvm-params-table="jvmParamsTable"
            :libs-table="libsTable"
          />
        </el-tab-pane>
        <el-tab-pane label="Lib 列表" name="2">
          <ul class="instance-list">
            <li class="instance-list-item">
              <!-- <span class="instance-list-item-title" style="width: 140px">Lib 列表</span> -->
              <el-input v-model="searchParams" size="mini" style="width: 200px" placeholder="Lib列表:输入关键字搜索" />
            </li>
            <div >
              <vue-scroll>
                <li
                  v-for="(item, index) in libs.filter(data=> !searchParams || data.toLowerCase().includes(searchParams.toLowerCase()))"
                  :key="index"
                  class="instance-list-item"
                >
                  <span>{{ item.trim() }}</span>
                </li>
              </vue-scroll>
            </div>
          </ul>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>

</template>
<script>
import {getAppNames, getInstanceInfo, getInstanceNames} from '@/api/list'

import BaseInfo from './components/baseInfo'

export default {
  components: { BaseInfo },
  name: 'Base',
  data() {
    return {
      appOption: [],
      instanceUidOption: [],
      activeName: '1',
      instanceUid: '',
      instanceName: '',
      libs: [],
      instanceObj: {},
      searchParams: '',
      serverInfoTable: [],
      jvmBaseTable: [],
      jvmParamsTable: [],
      libsTable: []
    }
  },
  created() {
    this.instanceUid = this.$cookies.get('appId')
    this.instanceName = this.$cookies.get('instanceName')
    this.getInstanceList({ name: this.instanceName })
    this.getAppList()
  },
  methods: {
    getInstanceList(params) {
      const _this = this
      getInstanceNames(params).then(res => {
        _this.instanceUidOption = res.data
        if (_this.instanceUid !== '') {
          _this.getInstanceDetail({ appId: _this.instanceUid })
        } else {
          _this.getInstanceDetail({ appId: res.data[0] })
          _this.instanceUid = res.data[0]
        }
      })
    },
    getAppList() {
      const _this = this
      getAppNames().then(res => {
        console.log(res.data)
        _this.appOption = res.data
        if (_this.instanceName !== '') {
         //TODO 第一个赋值默认的
        } else {
          _this.instanceName = res.data[0]
        }
      })
    },
    getInstanceDetail(params) {
      const _this = this
      getInstanceInfo(params).then(res => {
        console.log(res)
        _this.libs = res.data.libs
        _this.instanceObj = res.data

        // ips: res.data.ips,
        //  osArch: res.data.osArch ,
        //  osVersion: res.data.osVersion,
        //  system: res.data.system,
        //  processorNum: res.data.processorNum
        _this.serverInfoTable = [
          {
            name: 'service',
            value: res.data.instanceName
          },
          {
            name: 'appId',
            value: res.data.appId
          },
          {
            name: 'hostname',
            value: res.data.hostname
          },
          {
            name: 'ips',
            value: res.data.ips
          },
          {
            name: 'osArch',
            value: res.data.osArch
          },
          {
            name: 'osVersion',
            value: res.data.osVersion
          },
          {
            name: 'os',
            value: res.data.os
          },
          {
            name: 'processorNum',
            value: res.data.processorNum
          }
        ],
        _this.jvmBaseTable = [{
          name: 'progress',
          value: res.data.progress
        },
        {
          name: 'jdkVersion',
          value: res.data.jdkVersion
        },
        {
          name: 'jdkDir',
          value: res.data.jdkDir
        },
        {
          name: 'userDir',
          value: res.data.userDir
        },
        {
          name: 'initMemory',
          value: res.data.initMemory
        },
        {
          name: 'maxMemory',
          value: res.data.maxMemory
        }],
        _this.jvmParamsTable = res.data.arguments.map(item => {
          return {
            value: item.trim()
          }
        })
        _this.libsTable = res.data.libs.map(item => {
          return {
            value: item.trim()
          }
        })
      })
    },
    instanceUidChange(val) {
      this.$cookies.set("instanceUid",val)
      this.getInstanceDetail({ appId: val })
    },
    appChange(val) {
      this.instanceName = val;
      this.instanceUid = '';
      this.$cookies.set("instanceName",val)
      this.$cookies.set("instanceUid",'')
      this.getInstanceList({ name: this.instanceName });
    }
  }
}
</script>
<style lang="scss" scoped>

.instance-list {
  list-style: none;
  margin: 0;
  padding: 0;

  .instance-list-item {
    line-height: 40px;
    border-bottom: 1px solid #dfe6ec;
    font-size: 16px;
    position: relative;
    width: 100%;

    .instance-list-item-lable {
      width: 120px;
      position: fixed;
      font-size: 14px;
      font-weight: 700;
    }

    .instance-list-item-value {
      width: 100%;
      margin-left: 100px;
      line-height: 40px;
      position: relative;
      font-size: 14px;
      font-weight: 400;
    }
  }
}

.instance-list-item:last-child {
  border-bottom: none;
}

.instance-list-item-value p {
  margin: 0;
  padding: 0;
}

.box-card {
  .el-form-item {
    margin-bottom: 0px;
    border-bottom: 1px solid #dfe6ec;
  }

  .el-form-item:last-child {
    // border-bottom: none;
  }
}
</style>
