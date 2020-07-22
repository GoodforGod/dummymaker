package io.dummymaker.error;

/**
 * Exception occurred during export
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public class ExportException extends RuntimeException {

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
