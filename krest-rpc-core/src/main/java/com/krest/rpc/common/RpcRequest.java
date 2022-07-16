package com.krest.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 请求样式
 */
@Data
@AllArgsConstructor
public class RpcRequest {
    int id;
    String methodName;
    Object[] args;

}
