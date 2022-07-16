package com.krest.rpc.server;

import com.krest.rpc.common.RpcRequest;
import com.krest.rpc.common.RpcRequestWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 得到Netty客户端的请求，然后进行处理
 */
public class RpcServerDispatchHandler extends ChannelInboundHandlerAdapter {

    // 请求最终处理处理工具类
    private RpcServerRequestHandler rpcServerRequestHandler;

    public RpcServerDispatchHandler(RpcServerRequestHandler rpcServerRequestHandler) {
        this.rpcServerRequestHandler = rpcServerRequestHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取对象
        RpcRequest rpcRequest = (RpcRequest) msg;
        // 生成新的Wrapper对象，并进行处理
        RpcRequestWrapper rpcRequestWrapper = new RpcRequestWrapper(rpcRequest, ctx.channel());
        //
        rpcServerRequestHandler.addRequest(rpcRequestWrapper);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

    }

}
