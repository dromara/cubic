## Arthas功能
可根据主机任意远程连接实现，远程主机命令操作，完整兼容arthas命令集

## 使用

1. agent 加载如下,将agent-dist整体拷贝到一个目录进行加载，里面的结构请不要进行变动，不要单独拷贝一个jar
    java -jar -javaagent:/xxx/agent-dist/cubic-agent.jar  yyy.jar
    
2. 启动代理服务 ，用于连接agent 和web
    java -jar cubic-proxy.jar 
    
3. 访问web ui localhost:6080

    正常启动后会有数据上报到服务端，可在首页进行查看，点击唯一标识跳转到命令模式
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0628/162203_3293cbe3_1168339.png "屏幕截图.png")
    
    点击connect 进入可进行重新连接
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/0628/162418_6e57127d_1168339.png "屏幕截图.png")

4. 命令分为两部分，输入1 基础命令， 3位arthas 命令，输入1 然后输入help 可查看帮助
    
5. 也可以直接进入命令模式，使用search 命令来查询我们项目配置的agent, 比如在agent config 我们配置了参数 agent.service_name = cubic,则进行查询并获取到应用的agentId(如果使用quick_start启动的，因为增加了-Dcubic.agent.service_name=cubic-proxy ，所以我们可以使用cubic-proxy查询ID )
   ![输入图片说明](https://images.gitee.com/uploads/images/2020/0605/190339_bde70250_1168339.png "屏幕截图.png")

    
6. 然后将agentId 填入上面的输入框 点击connect 按钮重新加载，则进入当前应用的命令模式，输入3，切换到arthas 命令，就可以使用了
![输入图片说明](https://images.gitee.com/uploads/images/2020/0605/190447_b3cd9e91_1168339.png "屏幕截图.png")    
    
