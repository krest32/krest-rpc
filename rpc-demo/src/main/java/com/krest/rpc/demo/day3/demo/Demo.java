package com.krest.rpc.demo.day3.demo;

import com.krest.rpc.demo.day3.common.RpcInvokeHook;
import com.krest.rpc.demo.day3.server.RpcServer;
import com.krest.rpc.demo.day3.server.RpcServerBuilder;

public class Demo {

    public static void main(String[] args) {
        request();
    }

    public static void request() {
        // 需要被调用的接口
        TestInterface testInterface = new TestInterface() {
            @Override
            public String testMethod01(String string) {
                return string.toUpperCase();
            }
        };

        // 接口的钩子函授
        RpcInvokeHook hook = new RpcInvokeHook() {
            @Override
            public void beforeInvoke(String methodName, Object[] args) {
                System.out.println("beforeInvoke " + methodName);
            }

            @Override
            public void afterInvoke(String methodName, Object[] args) {
                System.out.println("afterInvoke " + methodName);
            }
        };

        // 启动server
        RpcServer rpcServer = RpcServerBuilder.create()
                .serviceInterface(TestInterface.class)
                .serviceProvider(testInterface)
                .threads(4)
                .hook(hook)
                .bind(3721)
                .build();
        rpcServer.start();
    }
}
