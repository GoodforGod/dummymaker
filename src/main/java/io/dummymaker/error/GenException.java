package io.dummymaker.error;

/**
 * Base runtime exception
 *
 * @author GoodforGod
 * @since 17.08.2019
 */
public class GenException extends RuntimeException {

    public GenException() {
        super("Unexpected exception occurred");
    }

    public GenException(Throwable throwable) {
        super(throwable.getMessage());
    }
}
