#### 模块

* 模块管理器 

  * ModuleManager 
    * DefaultModuleManager

* 编解码处理器模块

  * DefineHandlerManager (必选)
    * DefaultDefineHandlerManager

* 连接管理模块 (必选)

  * DefineConnectionManager 
    * DefaultDefineConnectionManager

* 重试模块 (可选)

  * DefineRetryManager 
    * DefaultDefineRetryManager

* 注册(中心)模块(可选)

  * DefineRegisterManager
    * DefaultDefineRegisterManager
  * 选址方式
  * 如果注册模块不开启使用，对外提供的服务列表中将不会存在该地址，通过地址直连

* 路由模块(可选)

  * DefineRouterManager
    * DefaultDefineRouterManager
  * 不注册路由模块，那么在消息转发的时候只在本机查找对应目标

* 模块的嵌套

  * 实际场景中，内部通信的TCP服务端与客户端连接断开以后需要重新连接，在注册中心失败了也有可能需要重新注册等等，也就是说每个模块可能都存在自己对应的不一样的重试方式

  * 针对上述场景，使用模块嵌套的方式处理,模块中的子模块信息需要模块自己提供存储,获取等等（并不是在ModuleManager中保存）

    ```java
            Configuration configuration = new Configuration();
            configuration.setHost("127.0.0.1");
            configuration.setPort(1999);
    
            NettyTcpServer nettyTcpServer = new NettyTcpServer(configuration,
                    DefaultModuleManager.builder()
                            .build()
                            .module(new DefaultDefineHandlerManager(),
                                    new DefaultDefineConnectionManager(),
                                    //register中添加了自己的子模块,当前register模块需要自己处理对应的模块信息
                                    new DefaultDefineRegisterManager()
                                            .module(DefaultDefineRetryManager.builder()
                                                    .retryMax(new AtomicInteger(Integer.MAX_VALUE))
                                                    .build())
                            )
            );
            nettyTcpServer.open();
    ```

#### 