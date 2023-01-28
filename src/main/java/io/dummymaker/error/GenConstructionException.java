package io.dummymaker.error;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
public final class GenConstructionException extends GenException {

    public GenConstructionException(String message) {
        super(message);
    }

    public GenConstructionException(Throwable throwable) {
        super(throwable);
    }
}
