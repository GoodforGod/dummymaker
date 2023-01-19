package io.dummymaker.error;

/**
 * Exception occurred during export
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public final class ExportException extends GenException {

    public ExportException(String message) {
        super(message);
    }

    public ExportException(Throwable throwable) {
        super(throwable);
    }
}
