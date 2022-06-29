package com.krest.rpc.demo.day1.client;

/**
 * 代理钩子函授，可以执行代理前后的方法
 */
public interface RpcInvokeHook {

    void beforeInvoke(String methodName, Object[] args);

    void afterInvoke(String methodName, Object[] args);

}
