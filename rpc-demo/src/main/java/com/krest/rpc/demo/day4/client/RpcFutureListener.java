package com.krest.rpc.demo.day4.client;

public interface RpcFutureListener {
    public void onResult(Object result);

    public void onException(Throwable throwable);
}
