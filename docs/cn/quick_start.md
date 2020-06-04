# 快速开始

Cubic 主要有两部分，目前UI 和proxy 是写在一起的
目前的部署方式：
- proxy 独立部署
- agent 需要和应用部署在同一台机器上。

### 构建
#### 获取部署包
 - 在项目[发行版本](https://gitee.com/sanjiankethree/cubic/releases)可直接下载
 - 也可以下载源码后自己打包。先clone 项目到本地，然后进入cubic 目录执行 scripts/build.sh.运行完成后会出现agent-dist 、agent-proxy-dist两个目录，第一个存放的是agent 加载需要的，第二个就是proxy 部署包。
 
 #### 准备
 - 目前支持mac linux 系统
 - 本机已经安装了jdk1.8+
 - 本机 6080 11900 端口未被占用（此端口都在proxy 配置）
 
 #### 启动
 - 将agent-dist整体拷贝到你要部署的机器，在启动应用时加入 -javaagent:/path/agent-dist/cubic-agent.jar -Dcubic.agent.service_name=cubic-proxy
 - 启动 cubic-proxy java -jar cubic-proxy.jar 的方式启动
 
 
 
#### 访问
- 通过 http://ip:6080 来对 web ui进行访问 ，默认地址 [http://localhost:6080](http://localhost:6080)

- 启动后看到如下界面

1.  agent 加载如下,将agent-dist整体拷贝到一个目录进行加载，里面的结构请不要进行变动，不要单独拷贝一个jar
    java -jar -javaagent:/xxx/agent-dist/cubic-agent.jar  yyy.jar
    
2.  启动代理服务 ，用于连接agent 和web
    java -jar cubic-proxy.jar 
    
3.  访问web ui localhost:6080
    进入默认页面，输入ip 和 agentId(id 第一次随意输入)，点击connect 进入命令交互模式
    ![启动](https://images.gitee.com/uploads/images/2020/0604/104511_31604c60_1168339.png "屏幕截图.png")
    命令分为两部分，输入1 基础命令， 3位arthas 命令
    
    输入1 然后输入help 可查看帮助
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0604/104847_6e728803_1168339.png "屏幕截图.png")
    

    使用search 命令来查询我们项目配置的agent, 比如在agent config 我们配置了参数 agent.service_name = cubic,则进行查询并获取到应用的agentId(如果使用quick_start启动的，因为增加了-Dcubic.agent.service_name=cubic-proxy ，所以我们可以使用cubic-proxy查询ID )
   ![输入图片说明](https://images.gitee.com/uploads/images/2020/0604/105104_fe67fe22_1168339.png "屏幕截图.png") 

    然后将agentId 填入上面的输入框 点击connect 按钮重新加载，则进入当前应用的命令模式
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0604/105209_d6e1da3a_1168339.png "屏幕截图.png")

    
    输入3，切换到arthas 命令，就可以使用了
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0604/105303_a3090417_1168339.png "屏幕截图.png")
    
    欢迎大家各种star，fork，提issue，pull request，感觉还可以就点个star吧！
    
