<template>
    <div class="error401">
        <div class="error401-body-con">
            <div class="error401-body-con-title">4<span class="error401-0-span">0</span><span class="error401-key-span">3</span></div>
            <p class="error401-body-con-message">You don't have permission</p>
            <div class="error401-btn-con">
                <a class="error401-home-button" href="/">返回首页</a>
                <div class="error401-block-button" @click="backPage">返回上一页</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Error401",
    methods: {
        backPage() {
            this.$router.go(-1);
        }
    }
};
</script>

<style type="text/scss" lang="scss">
@keyframes error401animation {
    0% {
        transform: rotateZ(0deg);
    }
    40% {
        transform: rotateZ(-20deg);
    }
    45% {
        transform: rotateZ(-15deg);
    }
    50% {
        transform: rotateZ(-20deg);
    }
    55% {
        transform: rotateZ(-15deg);
    }
    60% {
        transform: rotateZ(-20deg);
    }
    100% {
        transform: rotateZ(0deg);
    }
}
.error401 {
    &-body-con {
        width: 700px;
        height: 500px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        &-title {
            text-align: center;
            font-size: 240px;
            font-weight: 700;
            color: #2d8cf0;
            height: 260px;
            line-height: 260px;
            margin-top: 40px;
            .error401-0-span {
                display: inline-block;
                position: relative;
                width: 170px;
                height: 170px;
                border-radius: 50%;
                border: 20px solid #ed3f14;
                color: #ed3f14;
                margin-right: 10px;
                i {
                    display: inline-block;
                    font-size: 120px;
                    position: absolute;
                    left: 50%;
                    top: 50%;
                    transform: translate(-50%, -50%);
                }
            }
            .error401-key-span {
                display: inline-block;
                position: relative;
                width: 100px;
                height: 190px;
                border-radius: 50%;
                margin-right: 10px;
                i {
                    display: inline-block;
                    font-size: 190px;
                    position: absolute;
                    left: 20px;
                    transform: translate(-50%, -60%);
                    transform-origin: center bottom;
                    animation: error401animation 2.8s ease 0s infinite;
                }
            }
        }
        &-message {
            display: block;
            text-align: center;
            font-size: 30px;
            font-weight: 500;
            letter-spacing: 4px;
            color: #dddde2;
        }
    }
    &-btn-con {
        text-align: center;
        padding: 20px 0;
        margin-bottom: 40px;
    }
    &-home-button {
        width: 200px;
        border-color: transparent;
        padding: 6px 15px 7px;
        font-size: 14px;
        border-radius: 4px;
        display: inline-block;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        vertical-align: middle;
        -ms-touch-action: manipulation;
        touch-action: manipulation;
        cursor: pointer;
        background: transparent none;
        white-space: nowrap;
        line-height: 1.5;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        transition: color 0.2s linear, background-color 0.2s linear;
        color: #495060;
    }
    &-home-button:hover {
        text-decoration: none;
        color: #57a3f3;
    }
    &-block-button {
        width: 200px;
        margin-left: 40px;
        color: #fff;
        border-color: #2d8cf0;
        padding: 6px 15px 7px;
        font-size: 14px;
        border-radius: 4px;
        display: inline-block;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        vertical-align: middle;
        -ms-touch-action: manipulation;
        touch-action: manipulation;
        cursor: pointer;
        background: #2d8cf0 none;
        border-bottom-color: transparent;
        white-space: nowrap;
        line-height: 1.5;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        transition: color 0.2s linear, background-color 0.2s linear;
    }
    &-block-button:hover {
        color: #fff;
        background-color: #57a3f3;
        border-color: #57a3f3;
    }
}
</style>
