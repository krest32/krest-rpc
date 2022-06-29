package com.krest.rpc.test;

import com.krest.rpc.demo.day5.server.RpcServer;
import com.krest.rpc.demo.day5.server.RpcServerBuilder;
import org.junit.Test;

public class JUnitServerTest {
    @Test
    public void testServerStart() {
        JUnitTestInterfaceImpl jUnitTestInterfaceImpl = new JUnitTestInterfaceImpl();
        RpcServer rpcServer = RpcServerBuilder.create()
                .serviceInterface(JUnitTestInterface.class)
                .serviceProvider(jUnitTestInterfaceImpl)
                .threads(4)
                .bind(3721)
                .build();
        rpcServer.start();
    }

    public static void main(String[] args) {
        new JUnitServerTest().testServerStart();
    }
}
