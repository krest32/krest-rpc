package com.krest.rpc.server;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.krest.rpc.common.RpcInvokeHook;
import com.krest.rpc.common.RpcRequestWrapper;
import com.krest.rpc.common.RpcResponse;
import io.netty.channel.Channel;

import java.util.concurrent.BlockingQueue;


/**
 * 结果最终处理的线程
 */
public class RpcServerRequestHandleRunnable implements Runnable {
    private Class<?> interfaceClass;
    private Object serviceProvider;
    private RpcInvokeHook rpcInvokeHook;
    private BlockingQueue<RpcRequestWrapper> requestQueue;
    private RpcRequestWrapper rpcRequestWrapper;

    private MethodAccess methodAccess;
    private String lastMethodName = "";
    private int lastMethodIndex;

    public RpcServerRequestHandleRunnable(Class<?> interfaceClass,
                                          Object serviceProvider, RpcInvokeHook rpcInvokeHook,
                                          BlockingQueue<RpcRequestWrapper> requestQueue) {
        this.interfaceClass = interfaceClass;
        this.serviceProvider = serviceProvider;
        this.rpcInvokeHook = rpcInvokeHook;
        this.requestQueue = requestQueue;

        methodAccess = MethodAccess.get(interfaceClass);
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 从请求中获取请求的信息
                rpcRequestWrapper = requestQueue.take();
                String methodName = rpcRequestWrapper.getMethodName();
                Object[] args = rpcRequestWrapper.getArgs();

                // 执行调用前的方法
                if (rpcInvokeHook != null)
                    rpcInvokeHook.beforeInvoke(methodName, args);


                // 获取调用方法的索引
                Object result = null;
                if (!methodName.equals(lastMethodName)) {
                    lastMethodIndex = methodAccess.getIndex(methodName);
                    lastMethodName = methodName;
                }

                // 生成调用的请求结果
                result = methodAccess.invoke(serviceProvider, lastMethodIndex, args);

                // 设置动用成功后的结果
                Channel channel = rpcRequestWrapper.getChannel();
                RpcResponse rpcResponse = new RpcResponse();
                rpcResponse.setId(rpcRequestWrapper.getId());
                rpcResponse.setResult(result);
                rpcResponse.setInvokeSuccess(true);

                // 将处理后的结果返回到客户端
                channel.writeAndFlush(rpcResponse);
                if (rpcInvokeHook != null)
                    rpcInvokeHook.afterInvoke(methodName, args);

            } catch (Exception e) {
                Channel channel = rpcRequestWrapper.getChannel();
                RpcResponse rpcResponse = new RpcResponse();
                rpcResponse.setId(rpcRequestWrapper.getId());
                rpcResponse.setThrowable(e);
                rpcResponse.setInvokeSuccess(false);

                // 将错的的结果返回到客户端
                channel.writeAndFlush(rpcResponse);
            }
        }
    }
}
