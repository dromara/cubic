# cubic

#### 介绍
分布式应用监控工具，提供agent  ui,可快速进行arthas 嵌入
![输入图片说明](https://images.gitee.com/uploads/images/2020/0514/142021_d5e1ce4b_1168339.png "屏幕截图.png")

#### 软件架构
目前整体项目分为如下几个部署模块
cubic-agent 探针数据采集，以及动态命令执行
cubic-proxy 代理，用于维护通道和命令下发，目前包含简单的web-ui



#### 安装教程

1.  git clone https://gitee.com/sanjiankethree/cubic.git
2.  执行打包脚本 ./script/build.sh
3.  打包完成的agent 在agent-dist目录下
4.  打包完成的proxy 在agent-proxy-dist目录下

#### 使用说明

1.  agent 加载如下
    java -jar -javaagent:/xxx/agent-dist/cubic-agent.jar  yyy.jar
    
2.  启动代理服务 
    java -jar cubic-proxy.jar 
    
3.  访问web ui localhost:6080
    进入默认页面，输入ip 和 agentId(id 第一次随意输入)，点击connect 进入命令交互模式
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0519/184040_83550767_1168339.png "屏幕截图.png")

    命令分为两部分，输入1 基础命令， 3位arthas 命令
    
    输入1 然后输入help 可查看帮助
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0519/184155_075ae651_1168339.png "屏幕截图.png")

    使用search 命令来查询我们项目配置的agent, 比如在agent config 我们配置了参数 agent.service_name = cubic,则进行查询并获取到应用的agentId
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0519/184353_ea961ae8_1168339.png "屏幕截图.png")
    

    然后将agentId 填入上面的输入框 点击connect 按钮重新加载，则进入当前应用的命令模式
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0519/184650_c54d80bf_1168339.png "屏幕截图.png")

    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0519/184759_67e0f845_1168339.png "屏幕截图.png")

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
