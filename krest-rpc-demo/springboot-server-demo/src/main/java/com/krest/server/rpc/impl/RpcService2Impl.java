package com.krest.server.rpc.impl;

import com.krest.rpc.annotation.RpcServerAnno;
import com.krest.server.rpc.RpcService2;
import org.springframework.stereotype.Service;

@Service
@RpcServerAnno(port = 3722)
public class RpcService2Impl implements RpcService2 {

    @Override
    public String testMethod1() {
        String result = "hello RpcService2 testMethod1";
        System.out.println(result);
        return result;
    }

    @Override
    public String testMethod2() {
        String result = "hello RpcService2 testMethod2";
        System.out.println(result);
        return result;
    }
}
