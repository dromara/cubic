<template>
    <div>
        <el-button type="text" @click="dialogVisible = true">点击打开 表单</el-button>
        <el-dialog
            title="表单"
            :visible.sync="dialogVisible"
            width="80%">
            <el-form :model="formData">
                <el-form-item label="站内内容" prop="content">
                    <tinymce style="display: inline-block;width: 100%;" :height="300" v-model="formData.content"/>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>

</template>


<script>
import Tinymce from "../../components/Tinymce/index.vue";
export default {
    data() {
        return {
            formData: {
                content: ""
            },
            dialogVisible: false
        };
    },
    components: {
        Tinymce
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
