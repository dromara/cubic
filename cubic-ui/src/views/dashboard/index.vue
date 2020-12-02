<template>
  <div class="app-container case-list-container">
    <el-card class="box-card">
      <div class="case-info">
        <el-tag>在线应用：{{ caseInfo.services }}</el-tag>
        <el-tag type="success">在线实例数：{{ caseInfo.instances }}</el-tag>
        <el-input
          v-model="search"
          size="mini"
          style="width: 220px"
          placeholder="应用名称:输入关键字搜索"
        />
        <span style="float: right;padding: 4px 10px">
          <el-date-picker
            v-model="searchForm.date"
            :picker-options="pickerOptions"
            type="datetime"
            size="mini"
            placeholder="选择启动时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            format="yyyy-MM-dd HH:mm:ss"
            align="right"
          />
          <el-button class="filter-item" size="mini" type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </span>
      </div>
      <!--表格渲染-->
      <el-table
        v-loading="listLoading"
        :data="tableData.filter(data => !search || data.instanceName.toLowerCase().includes(search.toLowerCase()))"
        border
        size="small"
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column prop="instanceName" label="应用唯一标识" header-align="center">
          <template slot-scope="{row}">
            <el-tooltip :content="row.appId" placement="top" effect="dark">
              <el-button type="text" style="font-size: 12px;" @click.stop.prevent="goCmd(row)">{{ row.appId }}</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="instanceName" label="实例名称" />
        <el-table-column :show-overflow-tooltip="true" prop="host" label="主机名称" />
        <el-table-column :show-overflow-tooltip="true" prop="ip" label="IP" />
        <el-table-column :show-overflow-tooltip="true" prop="version" label="Agent版本" />
        <el-table-column :show-overflow-tooltip="true" prop="startDate" label="启动时间" />
        <el-table-column :show-overflow-tooltip="true" prop="onLine" label="在线时长" />
        <el-table-column :show-overflow-tooltip="true" prop="lastHeartbeat" label="最后心跳" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { appList, fetchPv, createArticle, updateArticle } from '@/api/list'

// arr to obj, such as { CN : "China", US : "USA" }
export default {
  name: 'Dashboard',
  data() {
    return {
      caseInfo: {
        services: 0,
        instances: 0
      },
      search: '',
      tableData: [],
      listLoading: true,
      searchForm: {
        date: null
        // curPage: 1,
        // pageSize: 30
      },
      // totalCount: 1,
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
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      appList({ date: this.searchForm.date }).then(response => {
        this.tableData = response.data.informations
        this.total = response.data.total
        this.caseInfo.services = response.data.services
        this.caseInfo.instances = response.data.instances
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
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
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          this.temp.author = 'vue-element-admin'
          createArticle(this.temp).then(() => {
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateArticle(tempData).then(() => {
            const index = this.list.findIndex(v => v.id === this.temp.id)
            this.list.splice(index, 1, this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row, index) {
      this.$notify({
        title: 'Success',
        message: 'Delete Successfully',
        type: 'success',
        duration: 2000
      })
      this.list.splice(index, 1)
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    goCmd(row) {
      console.log(row)
      this.$router.push({ name: 'app', query: { id: row.appId }})
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
