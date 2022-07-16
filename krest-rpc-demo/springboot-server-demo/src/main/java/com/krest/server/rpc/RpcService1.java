package com.krest.server.rpc;

public interface RpcService1 {

    String testMethod1(String name, int id) throws InterruptedException;

    String testMethod2(String name, int id) throws InterruptedException;
}
