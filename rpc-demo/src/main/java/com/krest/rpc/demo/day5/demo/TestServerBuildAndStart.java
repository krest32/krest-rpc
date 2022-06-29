package com.krest.rpc.demo.day5.demo;

import com.krest.rpc.demo.day5.common.RpcInvokeHook;
import com.krest.rpc.demo.day5.server.RpcServer;
import com.krest.rpc.demo.day5.server.RpcServerBuilder;

public class TestServerBuildAndStart {
    public static void main(String[] args) {
        TestInterface testInterface = new TestInterface() {
                     @Override
            public String testMethod01() {
                return "我来自 server ";
            }
        };

        RpcInvokeHook hook = new RpcInvokeHook() {
            @Override
            public void beforeInvoke(String methodName, Object[] args) {
                System.out.println("beforeInvoke in server" + methodName);
            }

            @Override
            public void afterInvoke(String methodName, Object[] args) {
                System.out.println("afterInvoke in server" + methodName);
            }
        };

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
