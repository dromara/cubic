<p align="center">
  <img width="320" src="https://lmxdawn.github.io/images/lmxdawn.png">
</p>

<p align="center">
  <a href="https://github.com/vuejs/vue">
    <img src="https://img.shields.io/badge/vue-2.5.16-brightgreen.svg" alt="vue">
  </a>
  <a href="https://cli.vuejs.org">
    <img src="https://img.shields.io/badge/vue-cli3.0.0-brightgreen.svg" alt="vue">
  </a>
  <a href="https://github.com/ElemeFE/element">
    <img src="https://img.shields.io/badge/element--ui-2.4.3-brightgreen.svg" alt="element-ui">
  </a>
</p>

# 前言

**项目PHP前端地址：** <a href="https://github.com/lmxdawn/vue-admin-html" target="_blank">https://github.com/lmxdawn/vue-admin-html</a>

**项目JAVA前端地址：** <a href="https://github.com/lmxdawn/vue-admin-html-java" target="_blank">https://github.com/lmxdawn/vue-admin-html-java</a>

**项目JAVA后端地址：** <a href="https://github.com/lmxdawn/vue-admin-java" target="_blank">https://github.com/lmxdawn/vue-admin-java</a>

**项目PHP后端地址：** <a href="https://github.com/lmxdawn/vue-admin-php" target="_blank">https://github.com/lmxdawn/vue-admin-php</a>

# 欢迎 star

# 整体效果

![donate](https://lmxdawn.github.io/images/show-how1.jpg)

# 目前 v3.0.0 版本 <a href="https://github.com/lmxdawn/vue-admin-html/tree/v1.0.0" target="_blank">点击前往 v1.0.0 版本</a>
## 更新日志
- [x] 更新vue-cli为3.0版本 <a href="https://segmentfault.com/a/1190000015133974">这里有篇文章</a>
- [x] 增加广告管理
- [x] 优化路由控制
- [x] 优化一些配置文件
- [x] 登录用户信息存储改为 cookie

# 一键操作包 <a href="https://pan.baidu.com/s/1gBPdt5IdDKhATNka1l1xOg" target="_blank">点击下载</a>

1. 集成环境搭建: windows 上面建议用 phpstudy ,其它环境自行百度
2. 把两个文件放到网站根目录
3. 把MySQL的root密码改为 root, 再新建数据库 vue-admin ,再把vue-admin.sql 文件导入到MySQL
4. 打开浏览器 输入 http://localhost/vue-admin-html/dist/index.html


# v3.0.0 踩过的坑
1. 这次更新后 vuex 开启严格模式后，出现浅拷贝的问题，就是变量引用的问题 <a href="https://segmentfault.com/q/1010000010025289/a-1020000015605407" target="_blank">这里有篇文章</a>
2. 还有就是路由导入某个文件时要在文件头部引入具体的某个文件，不用 resolve => require(['xx.vue'], resolve) 或者  () => import('xx.vue') 这种方式


# vue-admin-html

> Vue-cli3.0 + Element UI + ThinkPHP5.1 + RBAC权限 + 响应式的后台管理系统


## 权限演示

![donate](https://lmxdawn.github.io/images/rule.gif)

## 路由规则图示

>   路径: vue-admin-html/src/router/index.js

![donate](https://lmxdawn.github.io/images/router.png)

## env 配置说明

>   路径: vue-admin-html/.env.development

![donate](https://lmxdawn.github.io/images/env.png)

## 手机版演示

![donate](https://lmxdawn.github.io/images/phone.gif)

## 上传插件演示

![donate](https://lmxdawn.github.io/images/upload.gif)

## 添加 阿里巴巴矢量图演示

>   路径: vue-admin-html/src/assets/icons 注意 vue-admin-html/src/assets/icons/iconfont.js 头部需要加 ```/* eslint-disable */``` 去掉 eslint 检查 

1.第一步 选好一个图标加入到购物车 -> 把购物车的添加都项目 -> 下载项目到本地

![donate](https://lmxdawn.github.io/images/icon1.gif)

2.第二步 解压下载好的文件 -> 复制到 src/assets/icons , 覆盖掉 -> 增加 iconfont.js 的 eslint 注释

![donate](https://lmxdawn.github.io/images/icon2.gif)




## 功能 ##
- [x] 管理员登录
- [x] 登录
- [x] 修改密码
- [x] 角色管理
- [x] 权限管理
- [x] 401/404错误页面
- [x] 动态面包屑
- [x] 动态侧边栏
- [x] 广告管理


## 安装步骤 ##

	git clone https://github.com/lmxdawn/vue-admin-html.git      // 把模板下载到本地
	cd vue-admin-html    // 进入模板目录
	npm install         // 安装项目依赖，等待安装完成之后
	
    构建时三种环境可选，解决不同环境来回切换配置的痛楚（serve：本地测试，stage：预上线，build：生产环境）
    
## 本地开发 ##

	// 开启服务器，浏览器访问 http://localhost:8080
	npm run serve

## 构建预上线 ##

	// 执行构建命令，生成的stage文件夹放在服务器下即可访问
	npm run stage
	
## 构建生产 ##

	// 执行构建命令，生成的dist文件夹放在服务器下即可访问
	npm run build
	
# 项目目录介绍
```markdown
├── LICENSE                                 // 版权许可文件
├── README.md                               // 文档
├── babel.config.js                         // babel 插件配置                
├── jest.config.js                          // jest 测试配置   
├── package-lock.json                       // 锁定当前安装的扩展包的版本
├── package.json                            // 声明引用了哪些扩展包
├── public                                  // 公共文件
│   ├── favicon.ico                         // 图标
│   └── index.html                          // 入口文件
├── src                                     // src 主要代码文件
│   ├── App.vue                             // Vue 入口文件
│   ├── api                                 // API 接口逻辑文件
│   │   ├── ad                              // 广告相关
│   │   │   ├── ad.js                       // 广告
│   │   │   └── adSite.js                   // 广告位
│   │   ├── auth                            // 权限相关
│   │   │   ├── authAdmin.js                // 权限用户
│   │   │   ├── authPermissionRule.js       // 权限
│   │   │   └── authRole.js                 // 角色
│   │   ├── fileResource.js                 // 文件资源
│   │   ├── fileResourceTag.js              // 文件资源的标签
│   │   ├── login.js                        // 登录相关
│   │   └── upload.js                       // 旧版本上传插件的接口
│   ├── assets                              // 资源文件
│   │   ├── icons                           // 图标(使用的是 阿里巴巴矢量图标库)
│   │   │   ├── demo.css                    // demo 样式
│   │   │   ├── demo_fontclass.html         // demo HTML
│   │   │   ├── demo_symbol.html            // demo
│   │   │   ├── demo_unicode.html           // demo
│   │   │   ├── iconfont.css                // css
│   │   │   ├── iconfont.eot                // 
│   │   │   ├── iconfont.js                 // js 文件
│   │   │   ├── iconfont.svg                // svg 文件
│   │   │   ├── iconfont.ttf                // 字体文件
│   │   │   └── iconfont.woff               // 字体文件
│   │   ├── image                           // 资源图片文件
│   │   │   └── file_type_icon.png          // 文件图标文件
│   │   └── logo.png                        // logo
│   ├── components                          // 组件目录
│   │   ├── HelloWorld.vue                  // 测试文件
│   │   └── common                          // 公共组件
│   │       ├── FileResource.vue            // 上传资源的组件
│   │       ├── IconSvg.vue                 // 图标组件
│   │       └── UploadFile.vue              // 旧版上传文件的组件
│   ├── config                              // 自定义的配置
│   │   └── app.js                          // 项目的配置
│   ├── constants                           // 项目的常量目录
│   ├── element.js                          // 引入 element-ui 的js文件 (这个也可直接写在 main.js 里面)
│   ├── filtres                             // 过滤器目录
│   │   └── index.js                        // 全局过滤器
│   ├── main.js                             // 主入口
│   ├── mock                                // 模拟数据
│   │   ├── authAdmin.js                    // 权限用户的数据
│   │   ├── authPermissionRule.js           // 权限的数据
│   │   ├── authRole.js                     // 角色数据
│   │   ├── fileResource.js                 // 上传资源的数据
│   │   ├── fileResourceTag.js              // 上传资源的分组数据
│   │   ├── index.js                        // 引入 mockjs 的文件
│   │   ├── login.js                        // 登录的数据
│   │   └── upload.js                       // 旧版上传文件的数据
│   ├── role.js                             // 动态上传 router 路由的主要文件, 并且初始化权限, 检测权限
│   ├── router                              // 路由相关目录
│   │   └── index.js                        // 路由主文件
│   ├── store                               // vuex 状态 目录
│   │   ├── actions.js                      // Action
│   │   ├── getters.js                      // Getter
│   │   ├── index.js                        // 入口
│   │   ├── modules                         // 模块
│   │   │   ├── admin.js                    // Admin 用户相关
│   │   │   └── app.js                      // APP 项目相关
│   │   └── mutation-types.js               // Mutation
│   ├── styles                              // 样式目录
│   │   ├── base.scss                       // 基础样式
│   │   └── mixin.scss                      // 基础方法的样式
│   ├── utils                               // 工具目录
│   │   ├── auth.js                         // 权限工具
│   │   ├── axios.js                        // request 请求工具
│   │   ├── haiZiToPinYin.js                // 汉字转拼音的工具
│   │   └── store.js                        // 存放信息的工具
│   └── views                               // 页面目录
│       ├── adManage                        // 广告管理
│       │   ├── ad.vue                      // 广告
│       │   └── adSite.vue                  // 广告位
│       ├── components                      // 应用演示
│       │   └── uploadList.vue              // 上传插件
│       ├── error                           // 错误页面目录
│       │   ├── err401.vue                  // 401
│       │   ├── err404.vue                  // 404页面
│       │   └── err500.vue                  // 500页面
│       ├── home                            // 首页目录
│       │   ├── SidebarItem.vue             // 左边栏
│       │   ├── TabsView.vue                // 顶部tabs
│       │   ├── index.vue                   // 入口
│       │   └── main.vue                    // 前言
│       ├── login                           // 登录相关
│       │   └── index.vue                   // 登录首页
│       ├── profile                         // 测试
│       │   └── index.vue                   
│       └── userManage                      // 用户相关
│           └── admin                       // 管理员相关
│               ├── authAdmin.vue           // 权限用户
│               ├── authPermissionRule.vue  // 权限
│               ├── authRole.vue            // 角色
│               └── router.vue              // 路由文件
├── tests                                   // 测试
│   └── unit                                
│       └── HelloWorld.spec.js              
└── vue.config.js                           // 构建项目的配置文件
```

# Online Demo
 （建议使用最新版Chrome浏览器）
[在线 Demo](https://lmxdawn.github.io/vue-admin)

# Donate
鼓励鼓励鼓励，重要的事情说三遍 
![donate](https://lmxdawn.github.io/images/pay.png)


# License

[MIT](https://github.com/lmxdawn/vue-admin-html/blob/master/LICENSE)

Copyright (c) 2018 lmxdawn

