package com.krest.rpc.common;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RpcRequestWrapper {
    private final RpcRequest rpcRequest;
    private final Channel channel;

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
