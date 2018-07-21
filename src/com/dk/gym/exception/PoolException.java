package com.dk.gym.exception;


public class PoolException extends Exception {

    public PoolException() {
        super();
    }

    public PoolException(String s) {
        super(s);
    }

    public PoolException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PoolException(Throwable throwable) {
        super(throwable);
    }

    protected PoolException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
