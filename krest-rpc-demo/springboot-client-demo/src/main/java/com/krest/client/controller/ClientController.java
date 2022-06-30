package com.krest.client.controller;

import com.krest.client.config.FeignService1;
import com.krest.client.config.FeignService2;
import com.krest.rpc.client.ProxyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("client")
@RestController
public class ClientController {

    @GetMapping("testMethod1")
    public String testMethod1() {
        ProxyBuilder proxyBuilder1 = new ProxyBuilder(FeignService1.class, null);
        ProxyBuilder proxyBuilder2 = new ProxyBuilder(FeignService2.class, null);
        try {

            FeignService1 client1 = (FeignService1) proxyBuilder1.build();
            System.out.println(client1.testMethod1());
            System.out.println(client1.testMethod2());

            FeignService2 client2 = (FeignService2) proxyBuilder2.build();
            System.out.println(client2.testMethod1());
            System.out.println(client2.testMethod2());
            return "ok";

        } finally {
            proxyBuilder1.stop();
            proxyBuilder2.stop();

        }

    }
}
