package com.krest.rpc.client;

import com.krest.rpc.annotation.RpcClientAnno;
import com.krest.rpc.common.RpcInvokeHook;
import lombok.Data;

import java.lang.reflect.Proxy;


@Data
public class ProxyBuilder<T> {
    private Class<T> clazz;
    private RpcClient rpcClient;
    private long timeoutMills = 0;
    private RpcInvokeHook rpcInvokeHook = null;
    private String host;
    private int port;
    private int threads;

    public ProxyBuilder(Class clazz, RpcInvokeHook rpcInvokeHook) {
        if (clazz.isAnnotationPresent(RpcClientAnno.class)) {
            RpcClientAnno rpcClientAnno = (RpcClientAnno) clazz.getAnnotation(RpcClientAnno.class);
            this.clazz = clazz;
            this.timeoutMills = timeoutMills;
            this.rpcInvokeHook = rpcInvokeHook;
            this.host = rpcClientAnno.host();
            this.port = rpcClientAnno.port();
            this.threads = rpcClientAnno.threads();

        } else {
            throw new RuntimeException(clazz.getName() + " not found is not annotation " +
                    "RpcClientAnno.class present");

        }
    }

    public T build() {
        if (threads <= 0) {
            threads = Runtime.getRuntime().availableProcessors();
        }
        rpcClient = new RpcClient(timeoutMills, rpcInvokeHook, host, port, threads, false);
        rpcClient.connect();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, rpcClient);
    }

    public void stop() {
        if (rpcClient != null) {
            rpcClient.stop();
        }
    }

    /**
     * build the asynchronous proxy.In asynchronous way, a RpcFuture will be
     * return immediately.
     */
    public RpcClientAsyncProxy buildAsyncProxy() {
        if (threads <= 0) {
            threads = Runtime.getRuntime().availableProcessors();
        }

        rpcClient = new RpcClient(timeoutMills, rpcInvokeHook, host, port, threads, false);
        rpcClient.connect();

        return new RpcClientAsyncProxy(rpcClient, clazz);
    }
}
