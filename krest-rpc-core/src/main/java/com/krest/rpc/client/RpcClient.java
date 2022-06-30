package com.krest.rpc.client;

import com.krest.rpc.common.RpcFuture;
import com.krest.rpc.common.RpcInvokeHook;
import com.krest.rpc.common.RpcRequest;
import com.krest.rpc.netty.NettyKryoDecoder;
import com.krest.rpc.netty.NettyKryoEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户端，实现代理原有对象
 */
@Slf4j
public class RpcClient implements InvocationHandler {

    private long timeoutMills = 0;
    private RpcInvokeHook rpcInvokeHook = null;
    private String host;
    private int port;
    private boolean isReConn = false;


    private RpcClientResponseHandler rpcClientResponseHandler;
    private AtomicInteger invokeIdGenerator = new AtomicInteger(0);
    private Bootstrap bootstrap;
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    /**
     * channel that connected with the server
     */
    private Channel channel;
    private RpcClientChannelInactiveListener rpcClientChannelInactiveListener;


    protected RpcClient(long timeoutMills, RpcInvokeHook rpcInvokeHook,
                        String host, int port,
                        int threads, boolean isReConn) {
        this.timeoutMills = timeoutMills;
        this.rpcInvokeHook = rpcInvokeHook;
        this.host = host;
        this.port = port;
        this.isReConn = isReConn;

        rpcClientResponseHandler = new RpcClientResponseHandler(threads);
        if (isReConn) {
            rpcClientChannelInactiveListener = new RpcClientChannelInactiveListener() {
                @Override
                public void onInactive() {
                    log.info("connection with server is closed.");
                    log.info("try to reconnect to the server.");
                    channel = null;
                    do {
                        channel = tryConnect();
                    }
                    while (channel == null);
                }
            };
        }
    }

    public RpcFuture call(String methodName, Object... args) {
        if (rpcInvokeHook != null) {
            rpcInvokeHook.beforeInvoke(methodName, args);
        }

        RpcFuture rpcFuture = new RpcFuture();
        int id = invokeIdGenerator.addAndGet(1);
        rpcClientResponseHandler.register(id, rpcFuture);

        RpcRequest rpcRequest = new RpcRequest(id, methodName, args);
        if (channel != null) {
            channel.writeAndFlush(rpcRequest);
        } else {
            return null;
        }

        return rpcFuture;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        RpcFuture rpcFuture = call(method.getName(), args);
        if (rpcFuture == null) {
            log.info("RpcClient is unavailable when disconnect with the server.");
            return null;
        }

        Object result;
        if (timeoutMills == 0) {
            result = rpcFuture.get();
        } else {
            result = rpcFuture.get(timeoutMills);
        }

        if (rpcInvokeHook != null) {
            rpcInvokeHook.afterInvoke(method.getName(), args);
        }
        return result;
    }

    public void connect() {
        bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new NettyKryoDecoder(),
                                    new RpcClientDispatchHandler(rpcClientResponseHandler, rpcClientChannelInactiveListener),
                                    new NettyKryoEncoder());
                        }
                    });
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            do {
                channel = tryConnect();
            }
            while (channel == null);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Channel tryConnect() {
        try {
            log.info("Try to connect to [" + host + ":" + port + "].");
            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                log.info("Connect to [" + host + ":" + port + "] successed.");
                return future.channel();
            } else {
                log.info("Connect to [" + host + ":" + port + "] failed.");
                log.info("Try to reconnect in 10s.");
                Thread.sleep(10000);
                return null;
            }
        } catch (Exception exception) {
            log.info("Connect to [" + host + ":" + port + "] failed.");
            log.info("Try to reconnect in 10 seconds.");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            return null;
        }
    }

    public void stop() {
        if (eventLoopGroup != null) {
            this.eventLoopGroup.shutdownGracefully();
        }
    }
}