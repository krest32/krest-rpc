package com.krest.rpc.junit.entity;

public class JUnitTestCustomException extends RuntimeException {
    private static final long serialVersionUID = 591530421634999576L;

    public JUnitTestCustomException() {
        super("CustomException");
    }
}
