package com.krest.client.controller;

import com.alibaba.fastjson.JSON;
import com.krest.client.config.FeignService1;
import com.krest.client.config.FeignService2;
import com.krest.rpc.client.RpcClientAsyncProxy;
import com.krest.rpc.client.RpcClientProxyBuilder;
import com.krest.rpc.common.RpcFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("client")
@RestController
public class ClientController {

    static RpcClientAsyncProxy client1
            = RpcClientProxyBuilder.create(FeignService1.class)
            .timeout(0)
            .threads(4)
            .connect("127.0.0.1", 3721)
            .buildAsyncProxy();

    static RpcClientAsyncProxy client2
            = RpcClientProxyBuilder.create(FeignService2.class)
            .timeout(0)
            .threads(4)
            .connect("127.0.0.1", 3722)
            .buildAsyncProxy();

    @GetMapping("testMethod1")
    public String testMethod1() throws Throwable {


        RpcFuture future1 = client1.call("testMethod1", "aaa", 16);
        System.out.println(future1);
        RpcFuture future2 = client1.call("testMethod2", "bbb", 16);
        System.out.println(future2);
        RpcFuture future3 = client2.call("testMethod1", "ccc", 16);
        System.out.println(future3);
        RpcFuture future4 = client2.call("testMethod2", "ddd", 16);
        System.out.println(future4);
        System.out.println(JSON.toJSONString(future1.get()));
        System.out.println(JSON.toJSONString(future2.get()));
        System.out.println(JSON.toJSONString(future3.get()));
        System.out.println(JSON.toJSONString(future4.get()));
        return "ok";

    }
}
