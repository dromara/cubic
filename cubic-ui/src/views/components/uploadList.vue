<template>
    <div>
        <el-button type="text" @click="dialogVisible = true">点击打开 上传插件</el-button>
        <el-dialog
            title="上传插件"
            :visible.sync="dialogVisible"
            width="80%">
            <file-resource
                :uploadUrl="uploadUrl"
                :isAll="true"
                :fileExt="fileExt"
                :size="2000000"
                :limit="15"
                @resourceSelect="resourceSelect">
            </file-resource>
        </el-dialog>
    </div>

</template>


<script>
import FileResource from "../../components/common/FileResource.vue";
import { BASE_URL } from "../../config/app";
export default {
    data() {
        return {
            uploadUrl: BASE_URL + "/admin/file/resource/upload",
            fileExt: "jpg,png,gif",
            dialogVisible: false
        };
    },
    components: {
        FileResource
    },
    methods: {
        resourceSelect(item) {
            alert(JSON.stringify(item));
        },
        handleClose(done) {
            this.$confirm("确认关闭？")
                .then(() => {
                    done();
                })
                .catch(() => {});
        }
    },
    mounted() {}
};
</script>


<style type="text/scss" lang="scss">
</style>
