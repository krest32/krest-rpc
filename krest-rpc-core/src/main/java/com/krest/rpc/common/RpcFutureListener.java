package com.krest.rpc.common;

public interface RpcFutureListener {

    void onResult(Object result);

    void onException(Throwable throwable);

}
