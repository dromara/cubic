<template>
    <div style="padding: 10px;">
        <el-button-group>
            <el-button type="primary" size="small" icon="el-icon-refresh" @click="refresh"></el-button>
            <el-button type="primary" size="small" @click="isIcon = !isIcon">{{ isIcon ? '列表' : '图标' }}</el-button>
            <span></span>
            <el-button size="small" type="primary" icon="el-icon-plus" @click="addTag">新建分组</el-button>
            <el-popover
                placement="top"
                title="上传"
                width="100%"
                v-model="uploadVisible"
                trigger="click">
                <el-upload
                    style="max-width: 300px;overflow-x: hidden;"
                    class="upload-demo"
                    ref="upload"
                    :action="uploadUrl"
                    :on-success="handleSuccess"
                    :on-error="handleError"
                    :data="uploadData"
                    :name="uploadData.uploadName"
                    :accept="uploadData.exts"
                    :on-change="beforeChange"
                    :auto-upload="false">
                    <el-button slot="trigger" size="small" type="primary" icon="el-icon-upload2">选取文件</el-button>
                    <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器
                    </el-button>
                    <div slot="tip" class="el-upload__tip">只能上传{{ uploadData.exts }}文件，且不超过{{ uploadData.size | renderSize }}</div>
                </el-upload>
                <el-button slot="reference" size="small" type="primary" icon="el-icon-upload">上传文件</el-button>
            </el-popover>
        </el-button-group>
        <ul class="breadcrumb-list">
            <li>
                <a v-if="query.tagId === ''" href="javascript:;" class="none-path" title="全部分组">全部分组</a>
                <a v-else="" href="javascript:;" title="全部分组" @click="tagClick('')">全部分组</a>

                <template v-for="item in tagLists">
                    <span :key="item.id">
                        <span class="shufenge">|</span>
                        <a v-if="query.tagId !== item.id" href="javascript:;"
                           @click="tagClick(item)">{{ item.tag }}</a>
                        <a v-else="" href="javascript:;" class="none-path" :title="item.tag">{{ item.tag }}</a>
                    </span>
                </template>
            </li>
        </ul>

        <div v-if="isIcon" class="icon-lists">
            <template v-for="(item, index) in lists">
                <a href="#" :key="item.id" class="item" :class="item.select ? 'select' : ''" :style="item.edit ? 'width: 150px;' : ''" @click="selectFile(item, index)">
                    <div class="app-icon fileicon-small-pic">
                        <img :src="item.url"/>
                    </div>
                    <span class="title">
                        <span>
                            {{ item.filename }}
                        </span>
                    </span>
                    <span v-if="isAll" class="circle-check">
                        <span class="el-icon-circle-check"></span>
                    </span>
                    <!--大图-->
                    <span class="big-img">
                        <img :src="item.url"/>
                    </span>
                </a>
            </template>
            <template  v-if="lists.length <= 0">
                <div class="icon-list-empty">
                    暂无数据
                </div>
            </template>
        </div>


        <el-table
            v-else=""
            ref="uploadTable"
            highlight-current-row
            :data="lists"
            style="width: 100%; height: 100%;">
            <el-table-column
                v-if="isAll"
                width="55">
                <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.select" @click="selectFile(scope.row, scope.$index)"></el-checkbox>
                </template>
            </el-table-column>
            <el-table-column
                prop="filename"
                label="文件名">
                <template slot-scope="scope">
                    <a class="file-type-icon fileicon-small-pic"
                       :href="scope.row.url"
                       title="点击查看"
                       target="_blank"></a>
                    <span class="filename"
                          :title="scope.row.filename"
                          @click="selectFile(scope.row, scope.$index)">{{ scope.row.filename }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="大小"
                width="130">
                <template slot-scope="scope">
                    <span v-if="!scope.row.size || scope.row.size == 0">-</span>
                    <span v-else="">{{ scope.row.size | renderSize}}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="修改日期"
                width="180">
                <template slot-scope="scope">
                    <span v-if="scope.row.create_time">{{ scope.row.create_time | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
                    <span v-else="">-</span>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
            :page-size="query.size"
            @current-change="handleCurrentChange"
            layout="prev, pager, next"
            :total="total">
        </el-pagination>

        <div style="margin-top: 20px">
            <el-button @click="allOK()" type="primary">确认选择</el-button>
        </div>

    </div>
</template>

<!--上传资源-->
<script>
import { fileResourceList, fileResourceAdd } from "../../api/file/fileResource";
import {
    fileResourceTagList,
    fileResourceTagAdd
} from "../../api/file/fileResourceTag";
import { renderSize } from "../../filtres/index";
export default {
    name: "file-resource",
    data() {
        return {
            uploadVisible: false,
            isIcon: true, // 是否以图标形式展示（列表 or 图标）
            total: 0,
            lists: [],
            tagLists: [],
            uploadData: {
                type: 0, // 0 表示图片类型
                tagId: "",
                uploadName: "file",
                token: "",
                exts: "jpg,png,gif",
                size: 0
            },
            query: {
                type: 0, // 0 表示图片类型
                tagId: "",
                size: 15,
                page: 0
            },
            selectList: []
        };
    },
    props: {
        uploadUrl: String, // 上传的地址
        uploadName: String, // 上传的文件key名称
        token: String, // 上传的授权码
        isAll: false, // 是否可多选
        size: Number, // 允许上传的文件大小
        fileExt: String, // 允许上传的文件后缀
        limit: Number // 每页显示多少
    },
    mounted() {},
    methods: {
        // 刷新
        refresh() {
            this.getList();
            this.getTagList();
        },
        // 新建分组
        addTag() {
            this.$prompt("请输入分组名称", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            })
                .then(({ value }) => {
                    let data = {
                        tag: value
                    };
                    fileResourceTagAdd(data)
                        .then(response => {
                            if (response.code) {
                                this.$message({
                                    message: response.message,
                                    type: "error"
                                });
                                return;
                            }
                            this.tagLists.push(response.data);
                        })
                        .catch(() => {});
                })
                .catch(() => {
                    // 取消
                });
        },
        // 点击某个分组
        tagClick(item) {
            let tagId = "";
            if (item && item.id) {
                tagId = item.id;
            }
            this.query.tagId = tagId;
            this.uploadData.tagId = tagId;
            this.getList();
        },
        /**
         * 选择某个文件
         * @param row
         */
        selectFile(row, index) {
            if (row.edit) {
                return false;
            }
            // 选择单个文件，并且文件后缀
            let exts = this.uploadData.exts.split(",");
            if (exts.indexOf(row.ext) < 0) {
                // 不在允许后缀中
                this.$message.error(
                    "文件只能为 " + this.uploadData.exts + "格式!"
                );
                return;
            }
            row.select = !row.select;
            // 如果不为全选模式
            if (!this.isAll) {
                this.selectList = [row];
                this.resourceSelect();
                return;
            }
            this.lists.splice(index, 1, row);
        },
        allOK() {
            this.selectList = [];
            if (this.lists.length > 0) {
                let lists = this.lists;
                let exts = this.uploadData.exts.split(",");
                for (let i in lists) {
                    let item = lists[i];
                    if (item.select && item.select === true) {
                        // 如果在扩展列表中
                        if (
                            item.ext &&
                            exts.length > 0 &&
                            exts.indexOf(item.ext) !== -1
                        ) {
                            this.selectList.push(item);
                        }
                    }
                }
            }
            this.resourceSelect();
        },
        // 获取文件列表
        getList() {
            fileResourceList(this.query)
                .then(response => {
                    this.lists = response.data || [];
                    this.total = response.total || 0;
                })
                .catch(() => {});
        },
        // 获取分组列表
        getTagList() {
            fileResourceTagList(this.query)
                .then(response => {
                    this.tagLists = response || [];
                })
                .catch(() => {});
        },
        // 对外的选择文件后的方法
        resourceSelect() {
            this.$emit("resourceSelect", this.selectList);
        },
        submitUpload() {
            this.$refs.upload.submit();
        },
        /**
         * 分页的点击
         * @param val
         */
        handleCurrentChange(val) {
            this.query.page = val;
            this.getList();
        },
        handleSuccess(response, file, fileList) {
            if (response.code) {
                this.$message({
                    message: response.message,
                    type: "error"
                });
                return;
            }
            for (let i = 0; i < fileList.length; i++) {
                let tempFile = fileList[i];
                if (file.uid === tempFile.uid) {
                    fileList.splice(i, 1);
                }
            }
            if (fileList.length <= 0) {
                // 上传完成
                this.uploadVisible = false;
            }
            let data = response;
            data.tag_id = this.uploadData.tagId;
            data.type = this.uploadData.type;
            data.size = this.uploadData.size;
            let fileName = file.name;
            data.filename = fileName;
            data.size = file.size;
            let extIndex = fileName.lastIndexOf(".") + 1;
            data.ext = fileName.substr(extIndex, fileName.length);
            fileResourceAdd(data)
                .then(response => {
                    if (response.code) {
                        this.$message({
                            message: response.message,
                            type: "error"
                        });
                        return;
                    }
                    this.lists.unshift(response);
                })
                .catch(() => {});
        },
        handleError(err, file, fileList) {
            this.$message({
                message: err.message,
                type: "error"
            });
            for (let i = 0; i < fileList.length; i++) {
                let tempFile = fileList[i];
                if (file.uid === tempFile.uid) {
                    fileList.splice(i, 1);
                }
            }
        },
        // 选择文件时
        beforeChange(file, fileList) {
            let name = file.name ? file.name : "";
            let ext = name
                ? name.substr(name.lastIndexOf(".") + 1, name.length)
                : true;
            let isExt = this.uploadData.exts.indexOf(ext) < 0;
            if (isExt) {
                this.$message.error(
                    "文件只能为 " + this.uploadData.exts + " 格式!"
                );
            }
            let isSize =
                this.uploadData.size > 0 && file.size > this.uploadData.size;
            let sizeStr = this.uploadData.size;
            if (isSize) {
                this.$message.error(
                    "上传文件不能超过 " + renderSize(sizeStr) + "!"
                );
            }
            if (isExt || isSize) {
                for (let i = 0; i < fileList.length; i++) {
                    let tempFile = fileList[i];
                    if (file.uid === tempFile.uid) {
                        fileList.splice(i, 1);
                    }
                }
            }
            return false;
        }
    },
    created() {
        // 获取列表
        this.getList();
        // 获取分组列表
        this.getTagList();
        if (this.size) {
            this.uploadData.size = this.size;
        }
        if (this.limit) {
            this.query.size = this.limit;
        }
        if (
            this.fileExt &&
            this.fileExt !== "" &&
            this.fileExt !== "undefined"
        ) {
            this.uploadData.exts = this.fileExt;
        }
        if (
            this.uploadName &&
            this.uploadName !== "" &&
            this.uploadName !== "undefined"
        ) {
            this.uploadData.uploadName = this.uploadName;
        }
        if (this.token && this.token !== "" && this.token !== "undefined") {
            this.uploadData.token = this.token;
        }
    },
    computed: {}
};
</script>

<style type="text/scss" lang="scss">
.edit-input {
    margin-left: 10px;
    width: 200px;
}
.new-dir-bth {
    position: absolute;
    right: 15px;
    top: 10px;
}
.breadcrumb-list {
    padding-left: 5px;
    background: #fff;
    height: 16px;
    line-height: 16px;
    overflow: hidden;
    white-space: nowrap;
    margin-top: 10px;
    li {
        float: left;
        list-style: none;
        margin: 0;
        padding: 0;
    }
    a {
        color: #4287ed;
        text-decoration: none;
        outline: 0;
        display: inline-block;
        max-width: 105px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        float: left;
        &:hover {
            text-decoration: underline;
        }
    }
    .none-path {
        color: #666666;
        display: inline-block;
        max-width: 200px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .shufenge {
        float: left;
        padding: 0 3px;
        color: #c5d8f3;
    }
}
.filename {
    cursor: pointer;
    float: left;
    margin-left: 10px;
    max-width: 80%;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    line-height: 26px;
    color: #666666;
    &:hover {
        text-decoration: none;
        color: #3b8cff;
    }
    &:active {
        text-decoration: none;
        color: #3b8cff;
    }
}
.file-type-icon {
    float: left;
    height: 26px;
    width: 26px;
    background-image: url("../../assets/image/file_type_icon.png");
    &.fileicon-small-pic {
        background-position: -596px -306px;
    }
}

/*icon图片列表展示*/
@import "../../styles/mixin";
.icon-lists {
    @include size(100%, 100%);
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    & .icon-list-empty {
        text-align: center;
        width: 100%;
        height: 60px;
        line-height: 60px;
        color: #909399;
        border: 1px solid #cccccc;
        border-left: none;
        border-right: none;
        margin-bottom: 10px;
        margin-top: 10px;
        opacity: 0.5;
    }
    & .item {
        @include f_left;
        background-color: #fff;
        text-align: center;
        width: 100px;
        height: 120px;
        float: left;
        margin: 4px 0 0 6px;
        border: 1px solid #fff;
        position: relative;
        border-radius: 4px;
        &:hover {
            background-color: #f1f5fa;
        }
        & .app-icon {
            border-radius: 0.154rem;
            position: relative;
            margin: auto 0;
            width: 100%;
            height: 100%;
            line-height: 120px;
            overflow: hidden;
            background-color: #f5f5f5;
            &.fileicon-small-pic {
                img {
                    /*如果是图片则展示图片*/
                    max-width: 100%;
                    max-height: 100%;
                    background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAAAAAByaaZbAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JQAAgIMAAPn/AACA6QAAdTAAAOpgAAA6mAAAF2+SX8VGAAAAAmJLR0QA/4ePzL8AAAAJcEhZcwAACxMAAAsTAQCanBgAAAAHdElNRQfiBR0RGx0qH34fAAAAP0lEQVRIx2P8zwABD6C0AgE+EwOJYFQDMYDlAZShAKUJ8QehH4aDBsbR/DAoNIzmh8GhYTQ/DA4No/lhcGgAANgjFQHywHtPAAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDE4LTA1LTI5VDE2OjU5OjE4KzA4OjAwfq61WQAAACV0RVh0ZGF0ZTptb2RpZnkAMjAxOC0wNS0yOVQxNzoyNzoyOSswODowMM1v/QcAAAAASUVORK5CYII=);
                    background-size: 24px;
                    transition: all 0.2s;
                    vertical-align: middle;
                }
            }
        }
        & .title {
            position: absolute;
            bottom: 2px;
            left: 0;
            display: block;
            /*截断多出来的字*/
            @include text-overflow;
            font-size: 12px;
            color: #4a4a4a;
            text-align: center;
            margin: auto 0;
            width: 100%;
            & .edit-input {
                padding: 0 0 0 5px;
                height: 24px;
                vertical-align: middle;
                border: 1px solid rgba(58, 140, 255, 0.3);
                background: #fff;
                border-radius: 2px;
                width: 78px;
                margin: 0;
            }
            & .edit-btn {
                width: 20px;
                height: 20px;
                display: inline-block;
                vertical-align: middle;
                cursor: pointer;
                position: relative;
                margin: 0 0 0 4px;
                border: 1px solid rgba(58, 140, 255, 0.3);
                border-radius: 2px;
                line-height: 20px;
                color: #3b8cff;
                font-size: 16px;
                font-weight: 600;
            }
        }
        & .circle-check {
            display: none;
            position: absolute;
            top: 5px;
            left: 5px;
            height: 21px;
            width: 21px;
            cursor: pointer;
            font-size: 18px;
            color: #b5d3ff;
        }
        &:hover .circle-check {
            display: block;
        }
        &.select {
            background-color: #f1f5fa;
        }
        &.select .circle-check {
            color: #3b8cff;
            display: block;
        }
        &:hover .big-img {
            display: block;
        }
        & .big-img {
            animation: bounce-in 0.5s;
            position: absolute;
            right: -40px;
            top: 100px;
            width: 200px;
            height: 200px;
            display: none;
            z-index: 99;
            & img {
                max-width: 100%;
                max-height: 100%;
            }
        }
    }
}
@keyframes bounce-in {
    0% {
        opacity: 0;
    }
    20% {
        opacity: 0;
    }
    100% {
        opacity: 1;
    }
}
</style>
