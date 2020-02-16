#### 系统设计

#### 消息发送流程

#### 服务

* tcp-server
* websocket-server
* http-server

#### 数据传输

* protobuf

---

#### 功能

* 认证

  ![image-20200216003333824](docs\images\oauth-001.png)

* 单聊

  * 发送消息逻辑

    ![image-20200216003543540](docs\images\o2o-oo1.png)

    ![image-20200216003647629](E:\bytes-im\jtim\docs\images\o2o-002.png)

* 群聊

---
#### 使用示例

* 内部tcp 通信

  ```java
  服务端
      public static void main(String[] args) {
          Configuration configuration = new Configuration();
          configuration.setHost("127.0.0.1");
          configuration.setPort(1999);
          NettyTcpServer nettyTcpServer = new NettyTcpServer(configuration);
          nettyTcpServer.boarder(
                  new SimpleChannelHandlerProtoBufModule().addLast(new ProtobufServerHandler()),
                  new SimpleConnectionModule()
          );
  
          nettyTcpServer.open();
      }
  客户端
      public static void main(String[] args) {
          Configuration configuration = new Configuration();
          configuration.setHost("127.0.0.1");
          configuration.setPort(1999);
  
          NettyTcpClient nettyTcpClient = new NettyTcpClient(configuration);
          nettyTcpClient.boarder(
                  new SimpleChannelHandlerProtoBufModule()
                          .addLast(new ProtobufClientHandler()),
                  new SimpleConnectionModule(),
                  SimpleRetryModule.builder().build()
          );
          nettyTcpClient.open();
      }
  ```

  

#### 模块

#### 配置

TODO