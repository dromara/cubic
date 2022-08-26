<template>
  <div class="app-container">
    <!-- 列表 -->
    <searchTable>
      <template slot="tools">
        <!-- 添加表格工具，如tab功能等 -->
        <el-form :inline="true" :model="form">
          <el-form-item label="用户名">
            <el-input v-model="form.username">
            </el-input>
          </el-form-item>
          <el-form-item label="是否管理员">
            <el-select clearable v-model="form.isAdmin" placeholder="请选择" filterable>
              <el-option :value='1' :label="'是'"></el-option>
              <el-option :value='0' :label="'否'"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select clearable v-model="form.status" placeholder="请选择" filterable>
              <el-option :value='1' :label="'启用'"></el-option>
              <el-option :value='0' :label="'禁用'"></el-option>
            </el-select>
          </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search">查询</el-button>
        </el-form-item>
      </el-form>
      </template>
      <template slot="buttons">
        <el-button type="primary" @click="create">{{ '新建' }}</el-button>
      </template>
      <app-table
        :pagination="true"
        :is-pagination-float="false"
        :is-show-param="false"
        :border="false"
        :query-params="queryParams"
        url="/user/list"
        ref="userTable">
        <el-table-column prop="username" show-overflow-tooltip label="用户名"></el-table-column>
        <el-table-column prop="secret" show-overflow-tooltip label="密码"></el-table-column>
        <el-table-column prop="isAdmin" show-overflow-tooltip label="是否管理员">
          <template slot-scope="scope">
            <span v-if="scope.row.isAdmin">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" show-overflow-tooltip label="状态">
          <template slot-scope="scope">
            <el-button v-if="scope.row.status" size="mini" type="success" @click="handleStatus(scope.row)">启用</el-button>
            <el-button  v-else  size="mini" type="danger" @click="handleStatus(scope.row)">禁用</el-button>

          </template>
        </el-table-column>
        <el-table-column prop="createTime" show-overflow-tooltip label="创建时间"></el-table-column>
        <el-table-column width="110" default label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button type="text" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </app-table>
    </searchTable>
    <el-dialog
      v-if="userDialogVisible"
      :title="title"
      :visible.sync="userDialogVisible"
      width="60%"
      :close-on-click-modal="false"
      :before-close="cancelDicDialog">
      <el-form ref="form" :model="formData" :rules="rules" label-suffix=":" label-width="130px">
        <el-row>
          <el-form-item label="用户名" prop="username">
            <el-input placeholder="请输入" v-model="formData.username" :maxlength="50"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="密码" prop="secret">
            <el-input placeholder="请输入" v-model="formData.secret" :maxlength="50"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="是否管理员" prop="isAdmin">
            <el-select clearable v-model="formData.isAdmin" placeholder="请选择" filterable>
              <el-option :value='1' :label="'是'"></el-option>
              <el-option :value='0' :label="'否'"></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="状态" prop="status">
            <el-select clearable v-model="formData.status" placeholder="请选择" filterable>
              <el-option :value='1' :label="'启用'"></el-option>
              <el-option :value='0' :label="'禁用'"></el-option>
            </el-select>
          </el-form-item>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer center">
        <el-button @click="cancelDicDialog" class="cancel-btn">取 消</el-button>
        <el-button type="primary" @click="submit">{{title == '新建' ? '新 建' : '保 存'}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import searchTable from '@/components/searchTable'
import table from '@/components/table'
import { userUpdateStatus, userUpdate, userDelete, userCreate, userView } from '@/api/userManage'

export default {
  name: "userManage",
  components:{
    searchTable,
    'app-table': table
  },
  data() {
    return {
      dicData: [],
      form: {
        username: '',
        isAdmin: '',
        status: ''
      },
      formData: {
        username: '',
        isAdmin: '',
        status: '',
        secret: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        isAdmin: [
          { required: true, message: '请选择是否管理员', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ],
        secret: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      userDialogVisible: false,
      title: '新建'
    };
  },
  created() {
  },
  methods: {
    queryParams() {
      return this.form
    },
    search() {
      this.$refs.userTable.reload()
    },
    handleStatus(row) {
      this.$confirm('是否修改状态？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userUpdateStatus({ id: row.id, status: row.status ? 0 : 1 }).then(() => {
          this.search();
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {})
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    create() { // 新建业务关系
      this.title = '新建'
      this.userDialogVisible = true
    },
    cancelDicDialog() {
      this.formData = {
        username: '',
        isAdmin: '',
        status: '',
        secret: ''
      }
      this.userDialogVisible = false
    },
    handleUpdate(row) {
      userView({ id: row.id }).then((res) => {
        this.formData = res.data
        this.title = '修改'
        this.userDialogVisible = true
      })

    },
    submit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.title == '新建') {
            userCreate({ ...this.formData }).then(() => {
              this.$message({
                message: '新建成功',
                type: 'success'
              });
              this.cancelDicDialog()
              this.search()
            })
          } else {
            userUpdate({ ...this.formData }).then(() => {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.cancelDicDialog()
              this.search()
            })
          }
        }
      })
    },
    handleDelete(row) {
      this.$confirm('是否删除数据？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userDelete({ id: row.id }).then(() => {
          this.search();
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {})
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }
};
</script>
<style scoped>
.margin-left {
  margin-left: 20px;
}
.padding-bottom {
  padding-bottom: 20px;
}
.margin-right {
  margin-right: 5px;
}
.center {
  text-align: center;
}
.content {
  max-height: 115px;
  text-overflow: ellipsis
}
.text {
  text-overflow: ellipsis
}
.content-solt {
  width: 500px;
}
</style>
