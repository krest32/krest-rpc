package com.krest.rpc.demo.day2.client;

import com.krest.rpc.demo.day2.common.RpcInvokeHook;

import java.lang.reflect.Proxy;

/**
 * rpc client 代理生成器
 */
public class RpcClientProxyBuilder {

    /**
     * 生成代理对象
     * @param targetClass
     * @param <T>
     * @return 创建好的代理对象
     */
    public static <T> ProxyBuilder<T> create(Class<T> targetClass) {
        return new ProxyBuilder<T>(targetClass);
    }

    /**
     * 可以为代理对象设置一堆参数
     * @param <T>
     */
    public static class ProxyBuilder<T> {

        private Class<T> clazz;
        private RpcClient rpcClient;

        private long timeoutMills = 0;
        private RpcInvokeHook rpcInvokeHook = null;
        private String host;
        private int port;

        private ProxyBuilder(Class<T> clazz) {
            this.clazz = clazz;
        }
        /**
         * 设置超时
         *
         * @param timeoutMills
         * @return
         */
        public ProxyBuilder<T> timeout(long timeoutMills) {
            this.timeoutMills = timeoutMills;
            if (timeoutMills < 0) {
                throw new IllegalArgumentException("timeoutMills can not be minus!");
            }
            return this;
        }

        /**
         * 回调方法
         *
         * @param hook
         * @return
         */
        public ProxyBuilder<T> hook(RpcInvokeHook hook) {
            this.rpcInvokeHook = hook;
            return this;
        }


        /**
         * 远程链接 Server 的 IP 和 port
         *
         * @param host
         * @param port
         * @return
         */
        public ProxyBuilder<T> connect(String host, int port) {
            this.host = host;
            this.port = port;
            return this;
        }

        /**
         * 生成一个代理对象，
         */
        @SuppressWarnings("unchecked")
        public T build() {
            rpcClient = new RpcClient(timeoutMills, rpcInvokeHook, host, port);
            rpcClient.connect();
            return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, rpcClient);
        }

        /**
         * 生成一个异步代理对象分
         */
        public RpcClientAsyncProxy buildAsyncProxy() {
            rpcClient = new RpcClient(timeoutMills, rpcInvokeHook, host, port);
            rpcClient.connect();

            return new RpcClientAsyncProxy(rpcClient);
        }
    }


}