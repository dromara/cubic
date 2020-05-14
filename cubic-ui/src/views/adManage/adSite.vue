<template>

    <div>
        <el-form :inline="true" :model="query" class="query-form" size="mini">
            <el-form-item class="query-form-item">
                <el-input v-model="query.siteId" placeholder="广告位id"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button-group>
                    <el-button type="primary" icon="el-icon-refresh" @click="onReset();"></el-button>
                    <el-button type="primary" icon="search" @click="onSubmit">查询</el-button>
                    <el-button type="primary" @click.native="handleForm(null,null)">新增</el-button>
                </el-button-group>
            </el-form-item>
        </el-form>
        <el-table
            v-loading="loading"
            :data="list"
            style="width: 100%;">
            <el-table-column
                label="ID"
                prop="siteId"
                fixed>
            </el-table-column>
            <el-table-column
                label="广告位名称"
                prop="siteName"
                fixed>
            </el-table-column>
            <el-table-column
                label="广告位描述"
                prop="describe"
                with="300"
                :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                label="最后更新时间"
                prop="updateTime">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span>{{ scope.row.modifiedTime | formatDateStr('yyyy-MM-dd hh:mm:ss') }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="操作"
                fixed="right">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click.native="handleForm(scope.$index, scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click.native="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
            :page-size="query.limit"
            @current-change="handleCurrentChange"
            layout="prev, pager, next"
            :total="total">
        </el-pagination>

        <!--表单-->
        <el-dialog
            :title="formMap[formName]"
            :visible.sync="formVisible"
            :before-close="hideForm"
            width="85%"
            top="5vh">
            <el-form :model="formData" :rules="formRules" ref="dataForm">
                <el-form-item label="广告位名称" prop="siteName">
                    <el-input v-model="formData.siteName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="包含广告（拖动更改排序）" prop="describe">
                    <span style="color: red;">注意：广告列表中红色背景为已禁用</span>
                    <div class="remove-list-box">
                        <div class="remove-list">

                            <draggable element="ul" v-model="formData.ads">
                                <li class="remove-list-item" style="cursor: move;" :style="{backgroundColor: item.status === 1 ? '#fff' : '#fef0f0', color: item.status === 1 ? '#606266' : '#f56c6c'}" v-for="(item, index) in formData.ads" :key="index">
                                    {{item.title}} - {{item.describe}}（ID：{{item.adId}}）
                                    <i class="el-icon-close remove-list-close" @click="handleAdClose(index)"></i>
                                </li>
                            </draggable>

                        </div>
                        添加广告（填写广告ID，批量添加以英文逗号隔开，按顺序排列）
                        <el-select
                            size="mini"
                            v-model="adId"
                            multiple
                            filterable
                            remote
                            reserve-keyword
                            placeholder="输入广告ID查找"
                            :no-data-text="adListNoDataText"
                            :remote-method="queryAdIdAsync"
                            @change="handleAdChange"
                            :loading="queryAdIdAsyncLoading">
                            <el-option
                                v-for="item in adList"
                                :key="item.adId"
                                :label="item.title"
                                :value="item.adId">
                            </el-option>
                        </el-select>
                        <el-button type="primary" size="mini" @click.native="handleAdSubmit()" style="margin-left: 5px;">插入</el-button>
                    </div>
                </el-form-item>
                <el-form-item label="描述" prop="describe">
                    <el-input v-model="formData.describe" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="hideForm">取消</el-button>
                <el-button type="primary" @click.native="formSubmit()" :loading="formLoading">提交</el-button>
            </div>
        </el-dialog>

    </div>

</template>

<script>
import {
    adSiteList,
    adSiteAdList,
    adSiteSave,
    adSiteDelete
} from "../../api/ad/adSite";
import draggable from "vuedraggable";
const formJson = {
    siteId: "",
    siteName: "",
    describe: "",
    ads: []
};
export default {
    data() {
        return {
            query: {
                siteId: "",
                page: 1,
                limit: 20
            },
            list: [],
            adId: [],
            adSelectList: [],
            adList: {},
            adListNoDataText: "无数据",
            queryAdIdAsyncLoading: false,
            total: 0,
            loading: true,
            index: null,
            formName: null,
            formMap: {
                add: "新增",
                edit: "编辑"
            },
            formLoading: false,
            formVisible: false,
            formData: formJson,
            formRules: {
                siteName: [
                    {
                        required: true,
                        message: "请输入广告位名称",
                        trigger: "blur"
                    }
                ]
            },
            deleteLoading: false
        };
    },
    components: {
        draggable
    },
    methods: {
        onReset() {
            this.$router.push({
                path: ""
            });
            this.query = {
                siteId: "",
                page: 1,
                limit: 20
            };
            this.getList();
        },
        onSubmit() {
            this.$router.push({
                path: "",
                query: this.query
            });
            this.getList();
        },
        handleSizeChange(val) {
            this.query.limit = val;
            this.getList();
        },
        handleCurrentChange(val) {
            this.query.page = val;
            this.getList();
        },
        getList() {
            this.loading = true;
            adSiteList(this.query)
                .then(response => {
                    this.loading = false;
                    this.list = response.data.list || [];
                    this.total = response.data.total || 0;
                })
                .catch(() => {
                    this.loading = false;
                    this.list = [];
                    this.total = 0;
                });
        },
        // 异步查找广告
        queryAdIdAsync(adIdStr) {
            this.adListNoDataText = "请确认广告状态是否正常";
            if (!adIdStr || adIdStr.charAt(adIdStr.length - 1) === ",") {
                this.adList = {};
                return false;
            }
            let adIds = adIdStr.split(",");
            this.formData.ads.forEach(item => {
                adIds.forEach((obj, index) => {
                    if (item.adId === parseInt(obj)) {
                        adIds.splice(index, 1);
                    }
                });
            });
            if (adIds.length === 0) {
                this.adListNoDataText = "当前所有广告已存在列表中";
                return false;
            }
            this.queryAdIdAsyncLoading = true;
            adSiteAdList(adIds)
                .then(response => {
                    this.queryAdIdAsyncLoading = false;
                    let list = response.data.list;
                    if (list) {
                        list.forEach(value => {
                            let has = false;
                            if (this.adList.length > 0) {
                                has = this.adList.some(item => {
                                    return item.adId === value.adId;
                                });
                            }
                            if (!has) {
                                if (!this.adList.hasOwnProperty(value.adId)) {
                                    this.adList[value.adId] = value;
                                }
                            }
                        });
                    } else {
                        this.adList = {};
                    }
                })
                .catch(() => {
                    this.adList = {};
                    this.queryAdIdAsyncLoading = false;
                });
        },
        handleAdSubmit() {
            if (this.adSelectList.length === 0) {
                return false;
            }
            let ads = [];
            this.adSelectList.map(item => {
                if (this.adList.hasOwnProperty(item)) {
                    ads.push(this.adList[item]);
                }
            });
            this.formData.ads = this.formData.ads.concat(ads);
            this.adList = {};
            this.adId = [];
        },
        handleAdClose(index) {
            this.formData.ads.splice(index, 1);
        },
        handleAdChange(item) {
            this.adSelectList = item;
        },
        // 刷新表单
        resetForm() {
            if (this.$refs["dataForm"]) {
                // 清空验证信息表单
                this.$refs["dataForm"].clearValidate();
                // 刷新表单
                this.$refs["dataForm"].resetFields();
            }
        },
        // 隐藏表单
        hideForm() {
            // 更改值
            this.formVisible = !this.formVisible;
            return true;
        },
        // 显示表单
        handleForm(index, row) {
            this.formVisible = true;
            this.formData = JSON.parse(JSON.stringify(formJson));
            if (row !== null) {
                this.formData = Object.assign({}, row);
            }
            this.formName = "add";
            if (index !== null) {
                this.index = index;
                this.formName = "edit";
            }
        },
        formSubmit() {
            this.$refs["dataForm"].validate(valid => {
                if (valid) {
                    this.formLoading = true;
                    let data = Object.assign({}, this.formData);
                    let adIds = [];
                    let ads = data.ads;
                    ads.forEach(function(value) {
                        adIds.push(value.adId);
                    });
                    data.ads = [];
                    data.adIds = adIds;
                    let addData = Object.assign({}, this.formData);
                    adSiteSave(data, this.formName)
                        .then(response => {
                            this.formLoading = false;
                            if (response.code) {
                                this.$message.error(response.message);
                                return false;
                            }
                            this.$message.success("操作成功");
                            this.formVisible = false;
                            if (this.formName === "add") {
                                // 向头部添加数据
                                addData.siteId = response.data.siteId;
                                this.list.unshift(addData);
                            } else {
                                this.list.splice(this.index, 1, addData);
                            }
                            // 刷新表单
                            this.resetForm();
                        })
                        .catch(() => {
                            this.formLoading = false;
                        });
                }
            });
        },
        // 删除
        handleDel(index, row) {
            if (row.siteId) {
                this.$confirm("确认删除该记录吗?", "提示", {
                    type: "warning"
                })
                    .then(() => {
                        let para = { siteId: row.siteId };
                        this.deleteLoading = false;
                        adSiteDelete(para)
                            .then(res => {
                                this.deleteLoading = false;
                                if (res.code) {
                                    this.$message.error(res.message);
                                    return false;
                                }
                                this.$message.success("删除成功");
                                // 刷新数据
                                this.list.splice(index, 1);
                            })
                            .catch(() => {
                                this.deleteLoading = false;
                            });
                    })
                    .catch(() => {
                        this.$message.info("取消删除");
                    });
            }
        }
    },
    filters: {},
    mounted() {
        document.body.ondrop = function(event) {
            event.preventDefault();
            event.stopPropagation();
        };
    },
    created() {
        // 将参数拷贝进查询对象
        let query = this.$route.query;
        this.query = Object.assign(this.query, query);
        this.query.limit = parseInt(this.query.limit);
        // 加载表格数据
        this.getList();
    }
};
</script>

<style type="text/scss" lang="scss">
</style>
