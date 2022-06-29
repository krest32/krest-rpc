package com.krest.rpc.demo.day5.demo;

import com.krest.rpc.demo.day5.client.RpcClientProxyBuilder;
import com.krest.rpc.demo.day5.common.RpcInvokeHook;

public class TestClientBuildAndCall {
    public static void main(String[] args) {
        RpcInvokeHook hook = new RpcInvokeHook() {
            @Override
            public void beforeInvoke(String method, Object[] args) {
                System.out.println("before invoke in client" + method);
            }

            @Override
            public void afterInvoke(String method, Object[] args) {
                System.out.println("after invoke in client" + method);
            }
        };

        final TestInterface testInterface
                = RpcClientProxyBuilder.create(TestInterface.class)
                .timeout(0)
                .threads(4)
                .hook(hook)
                .connect("127.0.0.1", 3721)
                .build();

        for (int i = 0; i < 10; i++) {
            System.out.println("invoke result = " + testInterface.testMethod01());
        }
    }
}
