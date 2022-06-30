package com.krest.server.rpc.impl;

import com.krest.rpc.annotation.RpcServerAnno;
import com.krest.server.rpc.RpcService1;
import org.springframework.stereotype.Service;

@Service
@RpcServerAnno(port = 3721)
public class RpcService1Impl implements RpcService1 {

    @Override
    public String testMethod1() {
        String result = "hello RpcService1 testMethod1";
        System.out.println(result);
        return result;
    }

    @Override
    public String testMethod2() {
        String result = "hello RpcService1 testMethod2";
        System.out.println(result);
        return result;
    }
}
