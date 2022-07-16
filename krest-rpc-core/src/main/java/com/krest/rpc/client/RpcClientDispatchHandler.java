package com.krest.rpc.client;

import com.krest.rpc.common.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理整个异步调用过程最终返回的结果
 */
public class RpcClientDispatchHandler extends ChannelInboundHandlerAdapter {

    // 结果区里器
    private RpcClientResponseHandler rpcClientResponseHandler;
    private RpcClientChannelInactiveListener rpcClientChannelInactiveListener;

    public RpcClientDispatchHandler(
            RpcClientResponseHandler rpcClientResponseHandler,
            RpcClientChannelInactiveListener rpcClientChannelInactiveListener) {

        this.rpcClientResponseHandler = rpcClientResponseHandler;
        this.rpcClientChannelInactiveListener = rpcClientChannelInactiveListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        // 将最终调用的结果返回到队列当中等待被处理
        RpcResponse rpcResponse = (RpcResponse) msg;
        rpcClientResponseHandler.addResponse(rpcResponse);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
    }

    /**
     * 监听连接失败的事件发生
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (rpcClientChannelInactiveListener != null) {
            rpcClientChannelInactiveListener.onInactive();
        }
    }
}
