<template>
    <div class="error500">
        <div class="error500-body-con">
            <div class="error500-body-con-title">
                5<span class="error500-0-span">0</span><span class="error500-0-span">0</span>
            </div>
            <p class="error500-body-con-message">Oops! the server is wrong</p>
            <div class="error500-btn-con">
                <a href="/" class="error500-home-button">返回首页</a>
                <div class="error500-block-button" @click="backPage">返回上一页</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Error500",
    methods: {
        backPage() {
            this.$router.go(-1);
        }
    }
};
</script>

<style type="text/scss" lang="scss">
@keyframes error500animation {
    0% {
        transform: rotateZ(0deg);
    }
    20% {
        transform: rotateZ(-10deg);
    }
    40% {
        transform: rotateZ(5deg);
    }
    60% {
        transform: rotateZ(-5deg);
    }
    80% {
        transform: rotateZ(10deg);
    }
    100% {
        transform: rotateZ(0deg);
    }
}
.error500 {
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
            .error500-0-span {
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
                    bottom: -10px;
                    left: 10px;
                    transform-origin: center bottom;
                    animation: error500animation 3s ease 0s infinite alternate;
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
