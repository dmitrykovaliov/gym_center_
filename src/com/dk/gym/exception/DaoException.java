package com.dk.gym.exception;

/**
 * The Class DaoException. Corresponds to dao layer.
 */
public class DaoException extends Exception {

    /**
     * Instantiates a new dao exception.
     */
    public DaoException() {
        super();
    }

    /**
     * Instantiates a new dao exception.
     *
     * @param s the s
     */
    public DaoException(String s) {
        super(s);
    }

    /**
     * Instantiates a new dao exception.
     *
     * @param s the s
     * @param throwable the throwable
     */
    public DaoException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Instantiates a new dao exception.
     *
     * @param throwable the throwable
     */
    public DaoException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new dao exception.
     *
     * @param s the s
     * @param throwable the throwable
     * @param b the b
     * @param b1 the b 1
     */
    protected DaoException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
