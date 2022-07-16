package com.krest.client.controller;

import com.krest.client.config.FeignService1;
import com.krest.client.config.FeignService2;
import com.krest.rpc.client.RpcClientAsyncProxy;
import com.krest.rpc.client.RpcClientProxyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("client")
@RestController
public class ClientController {

    @GetMapping("testMethod1")
    public String testMethod1() {


        RpcClientAsyncProxy client1 = RpcClientProxyBuilder.create(FeignService1.class)
                .timeout(0)
                .threads(4)
                .connect("127.0.0.1", 3721)
                .buildAsyncProxy();


        RpcClientAsyncProxy client2
                = RpcClientProxyBuilder.create(FeignService2.class)
                .timeout(0)
                .threads(4)
                .connect("127.0.0.1", 3722)
                .buildAsyncProxy();

        System.out.println(client1.call("testMethod1", "aaa", 16));
        System.out.println(client1.call("testMethod2","bbb", 16));

        System.out.println(client2.call("testMethod1","ccc", 16));
        System.out.println(client2.call("testMethod2","ddd", 16));

        return "ok";

    }
}
