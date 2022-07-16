package com.krest.rpc.client;

import com.krest.rpc.common.RpcFuture;
import com.krest.rpc.common.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class RpcClientResponseHandleRunnable implements Runnable {

    private ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap;
    private BlockingQueue<RpcResponse> responseQueue;

    public RpcClientResponseHandleRunnable(
            ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap,
            BlockingQueue<RpcResponse> responseQueue) {
        this.invokeIdRpcFutureMap = invokeIdRpcFutureMap;
        this.responseQueue = responseQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 从队列中获取请求结果
                RpcResponse rpcResponse = responseQueue.take();

                // 得到结果
                int id = rpcResponse.getId();
                RpcFuture rpcFuture = invokeIdRpcFutureMap.remove(id);

                // 将调用的结果放入到 Future 调用的结果当中
                if (rpcResponse.isInvokeSuccess()) {
                    rpcFuture.setResult(rpcResponse.getResult());
                } else {
                    rpcFuture.setThrowable(rpcResponse.getThrowable());
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
