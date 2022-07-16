package com.krest.rpc.client;

import com.krest.rpc.common.RpcFuture;
import com.krest.rpc.common.RpcResponse;

import java.util.concurrent.*;

public class RpcClientResponseHandler {

    private ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap = new ConcurrentHashMap<Integer, RpcFuture>();
    private BlockingQueue<RpcResponse> responseQueue = new LinkedBlockingQueue<RpcResponse>();
    private ExecutorService threadPool;

    /**
     * 同时开启多个线程去处理结果
     */
    public RpcClientResponseHandler(int threads) {
        threadPool = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            threadPool.execute(new RpcClientResponseHandleRunnable(invokeIdRpcFutureMap, responseQueue));
        }
    }

    // 将结果放入到 map 集合中
    public void register(int id, RpcFuture rpcFuture) {
        invokeIdRpcFutureMap.put(id, rpcFuture);
    }

    // 将最终的结果放入到队列当中
    public void addResponse(RpcResponse rpcResponse) {
        responseQueue.add(rpcResponse);
    }
}
