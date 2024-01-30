package com.krest.rpc.server;

import com.krest.rpc.common.RpcInvokeHook;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcServerBuilder {

    private Class<?> interfaceClass;
    private Object serviceProvider;

    private int port;
    private int threads;
    private RpcInvokeHook rpcInvokeHook;

    public static RpcServerBuilder create() {
        return new RpcServerBuilder();
    }

    /**
     * 设置代理的对象的类型，最好是接口类型
     */
    public RpcServerBuilder serviceInterface(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
        return this;
    }

    /**
     * 设置最终的服务的提供方
     */
    public RpcServerBuilder serviceProvider(Object serviceProvider) {
        this.serviceProvider = serviceProvider;
        return this;
    }

    public RpcServerBuilder bind(int port) {
        this.port = port;
        return this;
    }

    public RpcServerBuilder threads(int threadCount) {
        this.threads = threadCount;
        return this;
    }

    public RpcServerBuilder hook(RpcInvokeHook rpcInvokeHook) {
        this.rpcInvokeHook = rpcInvokeHook;
        return this;
    }

    public RpcServer build() {
        if (threads <= 0) {
            threads = Runtime.getRuntime().availableProcessors();
        }
        RpcServer rpcServer = new RpcServer(interfaceClass, serviceProvider, port, threads, rpcInvokeHook);
        return rpcServer;
    }
}
