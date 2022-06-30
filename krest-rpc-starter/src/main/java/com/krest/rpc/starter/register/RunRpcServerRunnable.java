package com.krest.rpc.starter.register;

import com.krest.rpc.server.RpcServer;
import com.krest.rpc.server.RpcServerBuilder;

public class RunRpcServerRunnable implements Runnable {

    Object bean;
    int port;
    int threads;

    public RunRpcServerRunnable(Object bean, int port, int threads) {
        this.bean = bean;
        this.port = port;
        this.threads = threads;
    }

    @Override
    public void run() {
        RpcServer rpcServer = RpcServerBuilder.create()
                .serviceInterface(this.bean.getClass())
                .serviceProvider(this.bean)
                .threads(this.threads)
                .bind(this.port)
                .build();
        rpcServer.start();
    }
}
