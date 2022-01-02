package io.dummymaker.error;

/**
 * Exception occurred during export
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public class ExportException extends RuntimeException {

    public ExportException(String message) {
        super(message);
    }

    public ExportException(Throwable t) {
        super(t.getMessage(), t);
    }

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
