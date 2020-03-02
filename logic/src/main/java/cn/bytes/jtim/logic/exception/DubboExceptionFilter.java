package cn.bytes.jtim.logic.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;

/**
 * @version 1.0
 * @date 2020/2/24 21:47
 */
@Activate(
        group = {"provider"}
)
@Slf4j
public class DubboExceptionFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = result.getException();
                    if (!(exception instanceof RuntimeException) && exception instanceof Exception) {
                        return result;
                    } else {
                        try {
                            Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                            Class<?>[] exceptionClassses = method.getExceptionTypes();
                            Class[] arrClasses = exceptionClassses;
                            int length = exceptionClassses.length;

                            for (int i = 0; i < length; ++i) {
                                Class<?> exceptionClass = arrClasses[i];
                                if (exception.getClass().equals(exceptionClass)) {
                                    return result;
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            return result;
                        }
                        // 自定义的异常类型,直接返回
                        // TODO: 2020/2/24
                        if (exception instanceof RuntimeException) {
                            return result;
                        }
                        log.error("Got unchecked and undeclared exception which called by " + com.alibaba.dubbo.rpc.RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);
                        log.error("Exception error:", exception);
                        String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                        String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                        if (serviceFile != null && exceptionFile != null && !serviceFile.equals(exceptionFile)) {
                            String className = exception.getClass().getName();
                            return !className.startsWith("java.") && !className.startsWith("javax.") ? new AppResponse(new RuntimeException(StringUtils.toString(exception))) : result;
                        } else {
                            return result;
                        }
                    }
                } catch (Throwable throwable) {
                    log.warn("Fail to ExceptionFilter when called by " + com.alibaba.dubbo.rpc.RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + throwable.getClass().getName() + ": " + throwable.getMessage(), throwable);
                    return result;
                }
            } else {
                return result;
            }
        } catch (RuntimeException e) {
            log.error("Got unchecked and undeclared exception which called by " + com.alibaba.dubbo.rpc.RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e;
        }
    }
}
