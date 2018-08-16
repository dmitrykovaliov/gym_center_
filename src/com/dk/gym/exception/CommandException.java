package com.dk.gym.exception;

/**
 * The Class CommandException. Corresponds to command layer.
 */
public class CommandException extends Exception {

    /**
     * Instantiates a new command exception.
     */
    public CommandException() {
        super();
    }

    /**
     * Instantiates a new command exception.
     *
     * @param s the s
     */
    public CommandException(String s) {
        super(s);
    }

    /**
     * Instantiates a new command exception.
     *
     * @param s the s
     * @param throwable the throwable
     */
    public CommandException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Instantiates a new command exception.
     *
     * @param throwable the throwable
     */
    public CommandException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new command exception.
     *
     * @param s the s
     * @param throwable the throwable
     * @param b the b
     * @param b1 the b 1
     */
    protected CommandException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
