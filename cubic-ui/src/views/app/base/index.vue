<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span style="font-size: 14px;">实例： </span>
        <el-select v-model="instanceUid" size="mini" style="width: 280px" placeholder="请选择应用实例"
                   @change="instanceUidChange">
          <el-option v-for="item in instanceUidOption" :key="item.uid" :label="item.name" :value="item.uid"></el-option>
        </el-select>
      </div>
      <el-tabs v-model="activeName">
        <el-tab-pane label="概要" name="1">
          <base-info
            :serverInfoTable="serverInfoTable"
            :jvmBaseTable="jvmBaseTable"
            :jvmParamsTable="jvmParamsTable"
            :libsTable="libsTable"
          ></base-info>
        </el-tab-pane>
        <el-tab-pane label="Lib 列表" name="2">
          <ul class="instance-list">
            <li class="instance-list-item">
              <!-- <span class="instance-list-item-title" style="width: 140px">Lib 列表</span> -->
              <el-input v-model="searchParams" size="mini" style="width: 200px" placeholder="Lib列表:输入关键字搜索"></el-input>
            </li>
            <div style="height: 520px">
              <vue-scroll>
                <li class="instance-list-item"
                    v-for="(item, index) in libs.filter(data=> !searchParams || data.toLowerCase().includes(searchParams.toLowerCase()))"
                    :key="index">
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
// import {getInstanceInfo, getInstanceNames} from '@/api/logs/logsMonitorApi.js'
import BaseInfo from './components/baseInfo'

export default {
  components: {BaseInfo},
  data() {
    return {
      activeName: '1',
      instanceUid: '',
      instanceUidOption: [],
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
    if (Object.keys(this.$route.query).length > 0) {
      this.instanceUid = this.$route.query.instanceUid
      this.getInstanceList({serviceName: this.$route.query.serviceName})
    } else {
      this.getInstanceList({serviceName: JSON.parse(localStorage.getItem('projectInfo')).name})
    }
  },
  methods: {
    // getInstanceList(params) {
    //   getInstanceNames(params).then(res => {
    //     this.instanceUidOption = res.data
    //     if (res.code === 0) {
    //       if (res.data.length > 0 && Object.keys(this.$route.query).length > 0) {
    //         this.getInstanceDetail({instanceUid: this.$route.query.instanceUid})
    //       } else {
    //         this.getInstanceDetail({instanceUid: res.data[0].uid})
    //         this.instanceUid = res.data[0].uid
    //       }
    //     } else {
    //       this.$message.error(res.msg)
    //     }
    //   })
    // },
    // getInstanceDetail(params) {
    //   getInstanceInfo(params).then(res => {
    //     console.log(res)
    //     this.libs = res.data.libs
    //     this.instanceObj = res.data
    //
    //     // ips: res.data.ips,
    //     //  osArch: res.data.osArch ,
    //     //  osVersion: res.data.osVersion,
    //     //  system: res.data.system,
    //     //  processorNum: res.data.processorNum
    //     this.serverInfoTable = [
    //       {
    //         name: 'hostname',
    //         value: res.data.hostname
    //       },
    //       {
    //         name: 'ips',
    //         value: res.data.ips
    //       },
    //       {
    //         name: 'osArch',
    //         value: res.data.osArch
    //       },
    //       {
    //         name: 'osVersion',
    //         value: res.data.osVersion
    //       },
    //       {
    //         name: 'system',
    //         value: res.data.system
    //       },
    //       {
    //         name: 'processorNum',
    //         value: res.data.processorNum
    //       }
    //     ],
    //       this.jvmBaseTable = [{
    //         name: 'progress',
    //         value: res.data.progress
    //       },
    //         {
    //           name: 'jdkVersion',
    //           value: res.data.jdkVersion
    //         },
    //         {
    //           name: 'jdkDir',
    //           value: res.data.jdkDir
    //         },
    //         {
    //           name: 'userDir',
    //           value: res.data.userDir
    //         },
    //         {
    //           name: 'initMemory',
    //           value: res.data.initMemory
    //         },
    //         {
    //           name: 'maxMemory',
    //           value: res.data.maxMemory
    //         }],
    //       this.jvmParamsTable = res.data.arguments.map(item => {
    //         return {
    //           value: item.trim()
    //         }
    //       })
    //     this.libsTable = res.data.libs.map(item => {
    //       return {
    //         value: item.trim()
    //       }
    //     })
    //   })
    // },
    // instanceUidChange(val) {
    //   this.getInstanceDetail({instanceUid: val})
    // }
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
