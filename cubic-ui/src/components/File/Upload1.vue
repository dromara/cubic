<template>
    <div class="upload-container">
        <el-button :style="{background:color,borderColor:color}"
                   icon="el-icon-upload"
                   size="mini"
                   type="primary"
                   @click=" dialogVisible=true">上传文件</el-button>


        <el-dialog
            width="400px"
            title="插入图片"
            :visible.sync="dialogVisible"
            append-to-body>

            <el-upload
                style="margin-bottom: 10px;"
                ref="upload"
                drag
                :show-file-list="false"
                :on-success="handleSuccess"
                :on-change="handleChange"
                :before-remove="beforeRemove"
                :before-upload="beforeUpload"
                :action="uploadUrl"
                :multiple="false"
                :auto-upload="false"
                :data="uploadData">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击选取</em></div>
                <div class="el-upload__tip" slot="tip">
                    只能上传{{ cheekConfig.ext }}文件，且大小不超过{{ cheekConfig.size | renderSize }}，且宽高 {{ cheekConfig.width ? cheekConfig.width + 'px' : "不限" }} * {{ cheekConfig.height ? cheekConfig.height + 'px' : "不限" }}
                </div>
                <div class="el-upload__tip" slot="tip">
                    <el-input
                        placeholder="请上传文件"
                        v-model="fileName"
                        :disabled="true">
                    </el-input>
                </div>
            </el-upload>

            <div style="text-align: right">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleSubmit">插入</el-button>
            </div>

        </el-dialog>

    </div>
</template>

<script>
import { qiuniuUpToken } from "../../api/file/upload";
import { renderSize } from "../../filtres/index";

export default {
    name: "Upload",
    props: {
        cheekConfig: {
            type: Object,
            default: function() {
                return {
                    size: 6000, // 文件大小
                    ext: "jpg,png,gif", // 文件后缀
                    width: "", // 限制宽度
                    height: "" // 限制高度
                };
            }
        },
        color: {
            type: String,
            default: "#1890ff"
        }
    },
    data() {
        return {
            uploadUrl: "",
            uploadData: {
                token: ""
            },
            fileName: "",
            filePath: "",
            filePathUrl: "",
            isSelect: false, // 是否选择过文件
            dialogVisible: false
        };
    },
    methods: {
        handleSubmit() {
            this.isSelect = true;
            qiuniuUpToken()
                .then(response => {
                    if (response.code > 0) {
                        this.$message.error("出现未知问题，刷新页面");
                        return;
                    }
                    this.uploadUrl = response.data.upload_url;
                    this.uploadData.token = response.data.up_token;
                    this.$refs.upload.submit();
                })
                .catch(() => {
                    this.isSelect = false;
                    this.$message.error("出现未知问题，刷新页面");
                });
        },
        handleSuccess(response, file) {
            this.isSelect = false;
            console.log("上传成功");
            if (response.key) {
                const filePath = response.key;
                let _URL = window.URL || window.webkitURL;
                const filePathUrl = _URL.createObjectURL(file);
                this.$emit("on-select", filePath, filePathUrl);
                this.dialogVisible = false;
                return;
            }
            this.$message.error("上传文件报错");
        },
        handleChange(file) {
            this.fileName = file.name ? file.name : "";
            return false;
        },
        beforeRemove() {
            return !this.isSelect;
        },
        beforeUpload(file) {
            const name = file.name ? file.name : "";
            const ext = name
                ? name.substr(name.lastIndexOf(".") + 1, name.length)
                : true;
            let isExt = false;
            // 如果有坚持文件后缀的配置
            if (this.cheekConfig.ext) {
                isExt = this.cheekConfig.ext.indexOf(ext) >= 0;
                console.log(isExt, 111);
                if (!isExt) {
                    this.$message.error(
                        "文件只能为 " + this.cheekConfig.ext + " 格式!"
                    );
                }
            }
            let isSize = false;
            if (this.cheekConfig.size) {
                let sizeStr = this.cheekConfig.size;
                isSize = sizeStr > 0 && file.size > sizeStr;
                if (!isSize) {
                    this.$message.error(
                        "上传文件不能超过 " + renderSize(sizeStr) + "!"
                    );
                }
            }
            const _this = this;
            let isWidthHeight = false;
            if (_this.cheekConfig.width || _this.cheekConfig.height) {
                isWidthHeight = new Promise(function(resolve, reject) {
                    let width = _this.cheekConfig.width;
                    let height = _this.cheekConfig.height;
                    let _URL = window.URL || window.webkitURL;
                    let img = new Image();
                    img.src = _URL.createObjectURL(file);
                    img.onload = function() {
                        let valid =
                            (!width || img.width === width) &&
                            (!height || img.height === height);
                        if (valid) {
                            resolve();
                        } else {
                            let messageStr = "";
                            if (width && !height) {
                                messageStr = width;
                            }
                            if (height && !width) {
                                messageStr = height;
                            }
                            if (height && width) {
                                messageStr = width + "*" + height;
                            }
                            _this.$message.error(
                                "上传尺寸必须是 " + messageStr + " px!"
                            );
                            reject();
                        }
                    };
                });
            }
            return isExt && isSize && isWidthHeight;
        }
    },
    computed: {},
    watch: {}
};
</script>

<style type="text/scss" lang="scss">
</style>
