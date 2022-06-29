package com.krest.rpc.demo.day5.common;

import io.netty.channel.Channel;

public class RpcRequestWrapper {
    private final RpcRequest rpcRequest;
    private final Channel channel;

    public RpcRequestWrapper(RpcRequest rpcRequest, Channel channel) {
        this.rpcRequest = rpcRequest;
        this.channel = channel;
    }

    public int getId() {
        return rpcRequest.getId();
    }

    public String getMethodName() {
        return rpcRequest.getMethodName();
    }

    public Object[] getArgs() {
        return rpcRequest.getArgs();
    }

    public Channel getChannel() {
        return channel;
    }
}
