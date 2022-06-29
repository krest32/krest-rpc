package com.krest.rpc.demo.day3.client;

/**
 * 异步代理对象
 */
public class RpcClientAsyncProxy {

    private RpcClient rpcClient;

    public RpcClientAsyncProxy(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    /**
     * 异步调用方式
     * @param methodName
     * @param args
     * @return
     */
    public RpcFuture call(String methodName, Object... args) {
        return (RpcFuture) rpcClient.call(methodName, args);
    }

}
