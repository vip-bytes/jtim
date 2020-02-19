 #### 设计

 TODO

#### 功能

TODO

 #### [模块](docs\module\readme.md)

* `InitializeModule`  初始模块，构建服务端跟客户端的基础模块
* `ConnectionModule` 连接管理模块
* `ChannelHandlerModule`  编解码处理器模块
* `RetryModule` 重试模块
* `ClusterModule` 集群模块

#### 使用(v1.0)

* 如下所示，使用模块构建服务端与客户端

* 服务端

  ```java
   Configuration configuration = new Configuration();
          configuration.setHost("127.0.0.1");
          configuration.setPort(1999);
          NettyTcpServer nettyTcpServer = new NettyTcpServer(configuration);
          nettyTcpServer
                  .then(new SimpleChannelHandlerProtoBufModule()
                          .codec(new ProtobufServerHandler()))
                  .then(new SimpleConnectionModule());
          nettyTcpServer.open();
  ```

* 客户端

  ```java
  Configuration configuration = new Configuration();
          configuration.setHost("127.0.0.1");
          configuration.setPort(1999);
          NettyTcpClient nettyTcpClient = new NettyTcpClient(configuration);
          nettyTcpClient
                  .then(new SimpleChannelHandlerProtoBufModule().codec(new ProtobufClientHandler()))
                  .then(new SimpleConnectionModule());
          nettyTcpClient.open();
  ```

* spring boot 

  ```java
  //构建处理器
  @Bean
  @ConditionalOnMissingBean
  public ProtobufServerHandler protobufTcpServerHandler() {
      return new ProtobufServerHandler();
  }
  
  //构建处理器管理模块
  @Bean
  @ConditionalOnMissingBean
  public ChannelHandlerModule channelHandlerModule(ProtobufServerHandler                                           protobufServerHandler) {
      return new              	SimpleChannelHandlerProtoBufModule().codec(protobufServerHandler);
  }
  
  //创建服务端
  @Bean
  public NettyTcpServer nettyTcpServer(
      SimpleChannelHandlerTcpModule simpleChannelHandlerTcpModule,
      SimpleConnectionTcpServerModule simpleConnectionTcpServerModule) {
      NettyTcpServer nettyTcpServer = new NettyTcpServer(super.getTcpConfiguration());
      nettyTcpServer
          .then(simpleChannelHandlerTcpModule)
          .then(simpleConnectionTcpServerModule);
      nettyTcpServer.open();
      return nettyTcpServer;
  }
  ```
  
