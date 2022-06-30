package com.krest.rpc.starter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KrestRpcService {

    private KrestRpcProperties rpcProperties;

    public KrestRpcService(KrestRpcProperties rpcProperties) {
        this.rpcProperties = rpcProperties;
    }
}
