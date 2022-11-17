package io.dummymaker.error;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public final class ClassConstructorException extends GenException {

    public ClassConstructorException(String message) {
        super(message);
    }

    public ClassConstructorException(Throwable throwable) {
        super(throwable);
    }
}
