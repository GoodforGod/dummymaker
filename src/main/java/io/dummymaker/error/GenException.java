package io.dummymaker.error;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 17.08.2019
 */
public class GenException extends RuntimeException {

    public GenException(String message) {
        super(message);
    }

    public GenException(String message, Throwable cause) {
        super(message, cause);
    }
}
