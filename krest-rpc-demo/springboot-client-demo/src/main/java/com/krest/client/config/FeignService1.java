package com.krest.client.config;


import com.krest.rpc.annotation.RpcClientAnno;

@RpcClientAnno(host = "localhost", port = 3721)
public interface FeignService1 {

    String testMethod1();

    String testMethod2();

}
