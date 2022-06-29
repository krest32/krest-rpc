package com.krest.rpc.demo.day5.server;

import com.krest.rpc.demo.day5.common.InfoPrinter;
import com.krest.rpc.demo.day5.common.RpcInvokeHook;
import com.krest.rpc.demo.day5.netty.NettyKryoDecoder;
import com.krest.rpc.demo.day5.netty.NettyKryoEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 用来启动 rpc server
 */
public class RpcServer {

    private Class<?> interfaceClass;
    private Object serviceProvider;

    private int port;
    private int threads;
    private RpcInvokeHook rpcInvokeHook;

    private RpcServerRequestHandler rpcServerRequestHandler;

    protected RpcServer(Class<?> interfaceClass, Object serviceProvider, int port, int threads,
                        RpcInvokeHook rpcInvokeHook) {
        this.interfaceClass = interfaceClass;
        this.serviceProvider = serviceProvider;
        this.port = port;
        this.threads = threads;
        this.rpcInvokeHook = rpcInvokeHook;

        rpcServerRequestHandler = new RpcServerRequestHandler(interfaceClass,
                serviceProvider, threads, rpcInvokeHook);
        rpcServerRequestHandler.start();
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyKryoDecoder(),
                                    new RpcServerDispatchHandler(rpcServerRequestHandler),
                                    new NettyKryoEncoder());
                        }
                    });
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(port);
            channelFuture.sync();
            Channel channel = channelFuture.channel();
            InfoPrinter.println("RpcServer started.");
            InfoPrinter.println(interfaceClass.getSimpleName() + " in service.");
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        InfoPrinter.println("RpcServer started.");
        InfoPrinter.println(interfaceClass.getSimpleName() + " in service.");
    }

    public void stop() {
        //TODO add stop codes here
        System.out.println("server stop success!");
    }
}
