package io.goodforgod.dummymaker.error;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public final class GenConstructionException extends GenException {

    public GenConstructionException(String message) {
        super(message);
    }

    public GenConstructionException(String message, Throwable cause) {
        super(message, cause);
    }
}
