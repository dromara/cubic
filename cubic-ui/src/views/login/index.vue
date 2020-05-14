<template>
    <div class="login-container">
        <el-form class="card-box login-form" autoComplete="on" :model="ruleForm" :rules="rules" ref="ruleForm"
                 label-position="left">
            <h3 class="title">系统登录</h3>
            <el-form-item prop="username" class="item">
                <el-input
                    placeholder="邮箱"
                    name="userName"
                    autoComplete="on"
                    v-model="ruleForm.userName">
                    <i slot="prefix" class="el-input__icon"><icon-svg icon-class="user"/></i>
                </el-input>
            </el-form-item>
            <el-form-item prop="password" class="item">
                <!--<span class="svg-container">-->
                <!--<icon-svg icon-class="pwd"/>-->
                <!--</span>-->
                <el-input
                    placeholder="密码"
                    name="pwd" :type="isShowPwd ? 'text' : 'password'"
                    @keyup.enter.native="handleLogin"
                    v-model="ruleForm.pwd"
                    autoComplete="on">
                    <i slot="prefix" class="el-input__icon"><icon-svg icon-class="pwd"/></i>
                    <i slot="suffix" class="el-input__icon" @click='isShowPwd = !isShowPwd'><icon-svg icon-class="eye"/></i>
                </el-input>
            </el-form-item>
            <div>
                <el-button type="primary" style="width:100%;margin-bottom:30px;" :loading="loading"
                           @click.native="handleLogin()">登录
                </el-button>
            </div>
            <div>
                <el-button type="primary" style="width:100%;margin-bottom:30px;"
                           @click='showDialog = true'>
                    第三方登录
                </el-button>
            </div>
        </el-form>

        <el-dialog title="第三方验证" :visible.sync="showDialog">
            邮箱登录成功,请选择第三方验证<br/>
        </el-dialog>

    </div>
</template>

<script>
export default {
    data() {
        let validatePwd = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请输入密码"));
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                userName: "admin",
                pwd: "admin",
                checked: true
            },
            rules: {
                userName: [
                    { required: true, message: "请输入登录名", trigger: "blur" }
                ],
                pwd: [{ validator: validatePwd, trigger: "blur" }]
            },
            isShowPwd: false, // 是否显示密码
            loading: false, // 登录loading
            showDialog: false, // 显示dialog
            redirect: null // 回调地址
        };
    },
    methods: {
        handleLogin() {
            this.$refs["ruleForm"].validate(valid => {
                if (valid) {
                    this.loading = true;
                    this.$store
                        .dispatch("loginName", this.ruleForm)
                        .then(response => {
                            this.loading = false;
                            if (response.code) {
                                this.$message.error(response.message);
                                return;
                            }
                            let path = "/";
                            if (this.redirect) {
                                path = this.redirect;
                            }
                            this.$router.push({
                                path: path
                            });
                            // window.location.replace(path);
                            // this.showDialog = true
                        })
                        .catch(() => {
                            this.loading = false;
                        });
                } else {
                    return false;
                }
            });
        }
    },
    created() {
        // 将参数拷贝进查询对象
        let query = this.$route.query;
        if (query.redirect) {
            // URL Encode
            this.redirect = decodeURIComponent(query.redirect);
        }
    }
};
</script>

<style type="text/scss" lang="scss">
@import "../../styles/mixin";

$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.login-container {
    @include relative;
    height: 100%;
    background-color: $bg;
    input:-webkit-autofill {
        -webkit-box-shadow: 0 0 0 1000px #293444 inset !important;
        -webkit-text-fill-color: #fff !important;
    }
    .item {
        .el-form-item__content {
            display: flex;
            flex-flow: row;
        }
    }
    input {
        background: transparent;
        border: 0;
        -webkit-appearance: none;
        border-radius: 0;
        padding: 0.46rem 0.0666rem 0.16rem 0.2rem;
        color: $light_gray;
        height: 100%;
    }
    .el-input {
        display: inline-block;
    }
    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 0.13333rem;
    }
    .svg-container {
        padding: 0.08rem 0.0666rem 0.08rem 0.2rem;
        color: $dark_gray;
        vertical-align: middle;
        display: inline-block;
        &_login {
            font-size: 20px;
        }
    }
    .title {
        font-size: 26px;
        color: $light_gray;
        margin: 0 auto 0.5333rem auto;
        text-align: center;
        font-weight: bold;
    }
    .login-form {
        @include fxied-center;
        top: 40%;
        width: 22em;
        padding: 0.4666rem 0.4666rem 0.2rem 0.4666rem;
    }
    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgba(0, 0, 0, 0.1);
        border-radius: 0.0666rem;
        color: #454545;
    }
    .show-pwd {
        position: absolute;
        right: 0.1333rem;
        top: 0.09333rem;
        font-size: 16px;
        color: $dark_gray;
        cursor: pointer;
    }
    .thirdparty-button {
        /*position: absolute;*/
        /*right: .4666rem;*/
        /*bottom: .37333rem;*/
    }
}
</style>
