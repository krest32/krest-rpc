package com.krest.server.rpc.impl;

import com.krest.rpc.annotation.RpcServerAnno;
import com.krest.server.rpc.RpcService1;
import org.springframework.stereotype.Service;

@Service
@RpcServerAnno(port = 3721)
public class RpcService1Impl implements RpcService1 {

    @Override
    public String testMethod1(String name, int id) throws InterruptedException {
//        Thread.sleep(3000);
        String result = "hello RpcService1 testMethod1";
        System.out.println(name + " " + id);
        return result;
    }

    @Override
    public String testMethod2(String name, int id) throws InterruptedException {
//        Thread.sleep(3000);
        String result = "hello RpcService1 testMethod2";
        System.out.println(name + " " + id);
        return result;
    }
}
