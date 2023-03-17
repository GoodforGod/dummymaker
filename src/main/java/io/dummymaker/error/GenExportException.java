package io.dummymaker.error;

/**
 * Exception occurred during export
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public final class GenExportException extends GenException {

    public GenExportException(String message) {
        super(message);
    }

    public GenExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
