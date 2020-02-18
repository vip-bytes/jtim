##### 模块

* `InitializeModule` 系统初始模块

  * `SimpleServerInitializeModule`  服务端初始模块
    * NettyTcpServer
  * `SimpleClientInitializeModule`  客户端初始模块
    * NettyTcpClient
  
* `DefineChannelHandler` 处理器模块

  * `SimpleChannelHandlerModule ` 基本实现
    * `SimpleChannelHandlerProtoBufModule`  内部protobuf实现

* `ConnectionModule` 连接管理模块

  * `SimpleConnectionModule` 

* `RetryModule` 重试模块

  * `SimpleRetryModule` 

* ClusterModule 集群模块

  * TODO

* RouteModule 路由模块

  * TODO
  * 按这种设计`ConnectionModule` 中可以添加`RouteModule` 毕竟路由也是跟连接有关联，有了连接才有l连接路由可言

* 模块设计实现

  * 一个模块中分两个角色（寄宿主，寄宿者）如图:

    ![image-20200217172149277](E:\bytes-im\jtim\docs\images\module-001.png)

  * 寄宿者

    * 每个模块都可以添加自己的子模块，这些子模块称为寄宿者

  * 寄宿主

    * 子模块对应寄宿的模块，称为寄宿主

  * 根据这样设计

    ```
    InitializeModule(模块)
    	+ DefineChannelHandler(处理器模块)
    	+ ConnectionModule(连接管理模块)
    这样可以启动一个单机版的通信服务
    ```

    