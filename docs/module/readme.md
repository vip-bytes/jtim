##### 模块

* 概述

```
1.模块的设计基于寄宿主,寄宿者两个名词构建，例如 NettyTcpServer 是一个容器，这个容器内部需要使用的一些模块（连接管理模块，编解码处理模块等等），而被使用的这些模块称之为寄宿者，对寄宿者而言这个容器就是寄宿主

2.从设计上来说了没有绝对的寄宿者跟寄宿主，一个寄宿者也同时可以作为另一个模块的寄宿主

NettyTcpServer
    // 当前这个容器server 添加了一个寄宿者在自己容器内部供自己需要的时候使用
	.boarder(
		new ConnectionModule()
			//连接模块也添加了一个寄宿者在自己的容器内部供自己需要的时候使用
			.boarder(
				new RetryModule()
			)
	);

```

---

* 自定义模块

  * `Module` 模块接口
  * `AbstractSimpleModule` 模块的实现基类

  ```java
  //自定义处理接口，这个接口继承了Module的操作方法，那么他就是一个标记的模块
  public interface RetryModule extends Module, Cloneable{
      
  }
  
  //实现自定义的接口,AbstractSimpleModule 实现了Module定义的操作方法
  public class SimpleRetryModule extends AbstractSimpleModule implements RetryModule {
      
  }
  
  //重试以后的操作逻辑
  public interface RetryAfterModule extends Module {
      
  }
  
  public class SimpleRetryAfterModule extends AbstractSimpleModule implements RetryAfterModule {
      
  }
  
  //绑定相关的模块
  new SimpleRetryModule().boarder(new SimpleRetryAfterModule());
  
  ```

* 模块中如何获取寄宿者(V1.0 后续将会淘汰这种方式)

  * 首先一个模块会有一个自定义的标识

    ```java
    public interface Module extends Map<ModuleMapping, Module> {
        /**
         * 模块映射名称
         * @return
         */
        ModuleMapping mapping();
        
        <T extends Module> T getBoarder(ModuleMapping mapping);
    }
    
    //在RetryModule实现接口类中获取方式
    //eg
    RetryAfterModule retryAfterModule = getBoarder(ModuleMapping.RETRY_AFTER_MODULE);
    
    ```

    

    

