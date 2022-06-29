package com.krest.rpc.demo.day1.demo;

import com.krest.rpc.demo.day1.client.*;

public class Demo {
    public static void main(String[] args) {
        sync();
        System.out.println("-----------------分割线--------------------");
        async();
    }

    public static void sync() {

        RpcInvokeHook hook = new RpcInvokeHook() {
            @Override
            public void beforeInvoke(String method, Object[] args) {
                System.out.println("before invoke " + method);
            }

            @Override
            public void afterInvoke(String method, Object[] args) {
                System.out.println("after invoke " + method);
            }
        };


        TestInterface testInterface
                = RpcClientProxyBuilder.create(TestInterface.class)
                .timeout(0)
                .hook(hook)
                .connect("127.0.0.1", 3721)
                .build();

        System.out.println("invoke result = " + testInterface.testMethod01("qwerty"));
    }

    public static void async() {
        // 设置钩子函授
        RpcInvokeHook hook = new RpcInvokeHook() {
            @Override
            public void beforeInvoke(String method, Object[] args) {
                System.out.println("before invoke " + method);
            }

            @Override
            public void afterInvoke(String method, Object[] args) {
                System.out.println("after invoke " + method);
            }
        };

        // 异步代理对象
        RpcClientAsyncProxy rpcClientAsyncProxy
                = RpcClientProxyBuilder.create(TestInterface.class)
                .timeout(0)
                .hook(hook)
                .connect("127.0.0.1", 3721)
                .buildAsyncProxy();

        // 发送异步请求
        RpcFuture rpcFuture = rpcClientAsyncProxy.call("testMethod01", "qwerty");
        // 设置监听器
        rpcFuture.setRpcFutureListener(new RpcFutureListener() {
            @Override
            public void onResult(Object result) {
                System.out.println("RpcFutureListener result = " + result.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("RpcFutureListener onException");
            }
        });

        System.out.println("RpcFuture isDone = " + rpcFuture.isDone());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("RpcFuture isDone = " + rpcFuture.isDone());
        try {
            System.out.println("result = " + rpcFuture.get());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
