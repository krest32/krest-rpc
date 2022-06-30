package com.krest.rpc.server;

public interface RpcFutureListener {

    void onResult(Object result);

    void onException(Throwable throwable);
}
