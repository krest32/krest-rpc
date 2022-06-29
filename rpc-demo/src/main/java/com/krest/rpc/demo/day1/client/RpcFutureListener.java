package com.krest.rpc.demo.day1.client;

public interface RpcFutureListener {

     void onResult(Object result);

     void onException(Throwable throwable);

}
