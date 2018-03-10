package io.dummymaker.export;

/**
 * All available export types for exporters
 *
 * @see IExporter
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public enum Format {
    CSV(".csv"),
    JSON(".json"),
    XML(".xml"),
    SQL(".sql");

    Format(final String extension) {
        this.extension = extension;
    }

    private final String extension;

    public String getExtension() {
        return extension;
    }
}
