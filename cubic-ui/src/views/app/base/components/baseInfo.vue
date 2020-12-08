<template>
  <div>
    <el-row :gutter="20">
      <el-col :xs="12" :sm="12" :md="12" :lg="8" :xl="8">
        <el-card>
          <el-table
            :data="serverInfoTable"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="服务器信息"
              width="120px"
            >
            </el-table-column>
            <el-table-column
              prop="value"
            >
            </el-table-column>
          </el-table>
        </el-card>

      </el-col>
      <el-col :xs="12" :sm="12" :md="12" :lg="8" :xl="8">
        <el-card>
          <el-table
            :data="jvmBaseTable"
            style="width: 100%">
            <el-table-column
              prop="name"
              label="JVM 信息"
              width="120px"
            >
            </el-table-column>
            <el-table-column
              prop="value"
            >
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :xs="12" :sm="12" :md="12" :lg="8" :xl="8">
        <el-card>
          <el-table
            :data="jvmParamsTable"
            style="width: 100%">
            <el-table-column
              prop="value"
              label="启动参数"
            >
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<style lang="scss">
.title {
  background-color: #ddd;
}

.list-item {
  &::before {
    display: table;
    content: '';
    clear: both;
  }

  .list-item-lable {
    width: 90px;
    float: left;
    font-size: 14px;
    color: royalblue;
    line-height: 40px;
    padding: 0 12px 0 0;
    box-sizing: inherit;
    -webkit-box-sizing: border-box;
    vertical-align: middle;
  }

  .list-item-content {
    position: relative;
    font-size: 14px;
    line-height: 40px;

    &::after {
      display: table;
      content: '';
    }
  }
}
</style>
<style>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
export default {
  props: {
    serverInfoTable: {
      type: Array,
      default: []
    },
    jvmBaseTable: {
      type: Array,
      default: []
    },
    jvmParamsTable: {
      type: Array,
      default: []
    },
    libsTable: {
      type: Array,
      default: []
    },
  },
  data() {
    return {
      tableData: [{
        id: '12987122',
        name: '好滋好味鸡蛋仔',
        category: '江浙小吃、小吃零食',
        desc: '荷兰优质淡奶，奶香浓而不腻',
        address: '上海市普陀区真北路',
        shop: '王小虎夫妻店',
        shopId: '10333'
      }, {
        id: '12987123',
        name: '好滋好味鸡蛋仔',
        category: '江浙小吃、小吃零食',
        desc: '荷兰优质淡奶，奶香浓而不腻',
        address: '上海市普陀区真北路',
        shop: '王小虎夫妻店',
        shopId: '10333'
      }, {
        id: '12987125',
        name: '好滋好味鸡蛋仔',
        category: '江浙小吃、小吃零食',
        desc: '荷兰优质淡奶，奶香浓而不腻',
        address: '上海市普陀区真北路',
        shop: '王小虎夫妻店',
        shopId: '10333'
      }, {
        id: '12987126',
        name: '好滋好味鸡蛋仔',
        category: '江浙小吃、小吃零食',
        desc: '荷兰优质淡奶，奶香浓而不腻',
        address: '上海市普陀区真北路',
        shop: '王小虎夫妻店',
        shopId: '10333'
      }]
    }
  }
}
</script>
