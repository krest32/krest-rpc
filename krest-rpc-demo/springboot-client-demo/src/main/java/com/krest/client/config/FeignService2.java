package com.krest.client.config;


import com.krest.rpc.annotation.RpcClientAnno;

@RpcClientAnno(host = "localhost", port = 3722)
public interface FeignService2 {

    String testMethod1();

    String testMethod2();

}
