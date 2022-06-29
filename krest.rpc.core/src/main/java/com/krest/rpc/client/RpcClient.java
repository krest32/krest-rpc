package com.krest.rpc.client;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * rpc 客户端
 * 1. 建立链接
 * 2. 创建代理对象，执行 invoke 方法
 * 3. 获取调用的结果
 */
public class RpcClient implements InvocationHandler {


    private String host;
    private int port;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
