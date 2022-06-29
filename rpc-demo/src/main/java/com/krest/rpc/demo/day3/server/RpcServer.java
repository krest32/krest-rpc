package com.krest.rpc.demo.day3.server;

import com.krest.rpc.demo.day3.common.RpcInvokeHook;
import com.krest.rpc.demo.day3.common.RpcRequest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用来启动 rpc server
 */
public class RpcServer {

    private Class<?> interfaceClass;
    private Object serviceProvider;

    private int port;
    private int threads;
    private RpcInvokeHook rpcInvokeHook;

    private RpcServerRequestHandler rpcServerRequestHandler;

    protected RpcServer(Class<?> interfaceClass, Object serviceProvider, int port, int threads,
                        RpcInvokeHook rpcInvokeHook) {
        this.interfaceClass = interfaceClass;
        this.serviceProvider = serviceProvider;
        this.port = port;
        this.threads = threads;
        this.rpcInvokeHook = rpcInvokeHook;

        rpcServerRequestHandler = new RpcServerRequestHandler(interfaceClass,
                serviceProvider, threads, rpcInvokeHook);
        rpcServerRequestHandler.start();
    }

    public void start() {
        System.out.println("bind port:" + port + " success!");
        //模拟接收到 客户端请求
        AtomicInteger idGenerator = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            rpcServerRequestHandler.addRequest(
                    new RpcRequest(idGenerator.addAndGet(1),
                            "testMethod01",
                            new Object[]{"qwerty"}));
        }
    }

    public void stop() {
        //TODO add stop codes here
        System.out.println("server stop success!");
    }
}
