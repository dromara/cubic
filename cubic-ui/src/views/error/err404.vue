<template>
    <div class="error404">
        <div class="error404-body-con">
            <div class="error404-body-con-title">4<span>0</span>4</div>
            <p class="error404-body-con-message">YOU&nbsp;&nbsp;LOOK&nbsp;&nbsp;LOST</p>
            <div class="error404-btn-con">
                <a href="/" class="error404-home-button">返回首页</a>
                <div class="error404-block-button" @click="backPage">返回上一页</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Error404",
    methods: {
        backPage() {
            this.$router.go(-1);
        }
    }
};
</script>

<style type="text/scss" lang="scss">
@keyframes error404animation {
    0% {
        transform: rotateZ(0deg);
    }
    20% {
        transform: rotateZ(-60deg);
    }
    40% {
        transform: rotateZ(-10deg);
    }
    60% {
        transform: rotateZ(50deg);
    }
    80% {
        transform: rotateZ(-20deg);
    }
    100% {
        transform: rotateZ(0deg);
    }
}
.error404 {
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
            span {
                display: inline-block;
                color: #19be6b;
                font-size: 230px;
                animation: error404animation 3s ease 0s infinite alternate;
            }
        }
        &-message {
            display: block;
            text-align: center;
            font-size: 30px;
            font-weight: 500;
            letter-spacing: 12px;
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
