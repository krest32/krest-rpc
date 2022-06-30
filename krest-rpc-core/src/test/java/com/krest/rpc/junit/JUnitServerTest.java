package com.krest.rpc.junit;

import com.krest.rpc.server.RpcServer;
import com.krest.rpc.server.RpcServerBuilder;
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
