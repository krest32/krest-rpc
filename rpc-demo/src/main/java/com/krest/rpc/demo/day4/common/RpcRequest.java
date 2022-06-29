package com.krest.rpc.demo.day4.common;

/**
 * 请求样式
 */
public class RpcRequest {
    int id;
    String methodName;
    Object[] args;

    public RpcRequest(int id, String methodName, Object[] args) {
        this.id = id;
        this.methodName = methodName;
        this.args = args;
    }

    public int getId() {
        return id;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }
}
