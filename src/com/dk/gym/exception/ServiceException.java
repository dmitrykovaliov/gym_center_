package com.dk.gym.exception;

/**
 * The Class ServiceException. Corresponds to service layer.
 */
public class ServiceException extends Exception {
    
    /**
     * Instantiates a new service exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new service exception.
     *
     * @param s the s
     */
    public ServiceException(String s) {
        super(s);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param s the s
     * @param throwable the throwable
     */
    public ServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param throwable the throwable
     */
    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param s the s
     * @param throwable the throwable
     * @param b the b
     * @param b1 the b 1
     */
    protected ServiceException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
