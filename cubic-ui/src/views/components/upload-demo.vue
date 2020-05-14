<template>
    <div>
        <div class="page">
            <h3>上传图片：</h3>
            <el-card class="box-card">
                <el-card class="box-card w100" v-if="filePathUrl1">
                    <img :src="filePathUrl1" style="width: 100%;" alt="">
                </el-card>
                <upload ext="jpg" :width="100" @on-select="onSelect1"></upload>
            </el-card>
        </div>
        <div class="page">
            <h3>上传视频：</h3>
            <el-card class="box-card">
                <el-card class="box-card w100" v-if="filePathUrl2">
                    <video :src="filePathUrl2" controls="controls" style="width: 100%;"></video>
                </el-card>
                <upload ext="mp4" @on-select="onSelect2"></upload>
            </el-card>
        </div>
        <div class="page">
            <h3>上传音乐：</h3>
            <el-card class="box-card">
                <el-card class="box-card w100" v-if="filePathUrl3">
                    <audio :src="filePathUrl3" controls="controls" style="width: 100%;"></audio>
                </el-card>
                <upload ext="mp3" @on-select="onSelect3"></upload>
            </el-card>
        </div>
        <div class="page">
            <h3>上传限制大小（单位是 B：1024B = 1kb）：</h3>
            <el-card class="box-card">
                <el-card class="box-card" v-if="filePathUrl4">
                    文件地址：{{ filePathUrl4 }}
                </el-card>
                <upload :size="60000" @on-select="onSelect4"></upload>
            </el-card>
        </div>
    </div>

</template>


<script>
import Upload from "../../components/File/Upload.vue";
export default {
    data() {
        return {
            filePath1: "",
            filePathUrl1: "",
            filePath2: "",
            filePathUrl2: "",
            filePath3: "",
            filePathUrl3: "",
            filePath4: "",
            filePathUrl4: "",
            dialogVisible: false
        };
    },
    components: {
        Upload
    },
    methods: {
        onSelect1(filePath, filePathUrl) {
            this.filePath1 = filePath;
            this.filePathUrl1 = filePathUrl;
        },
        onSelect2(filePath, filePathUrl) {
            console.log(filePathUrl);
            this.filePath2 = filePath;
            this.filePathUrl2 = filePathUrl;
        },
        onSelect3(filePath, filePathUrl) {
            this.filePath3 = filePath;
            this.filePathUrl3 = filePathUrl;
        },
        onSelect4(filePath, filePathUrl) {
            this.filePath4 = filePath;
            this.filePathUrl4 = filePathUrl;
        }
    },
    mounted() {}
};
</script>


<style type="text/scss" lang="scss">
.page > h3 {
    margin: 20px 0 20px;
    color: #1f2f3d;
}
.w100 {
    width: 300px;
    margin-bottom: 15px;
}
</style>
