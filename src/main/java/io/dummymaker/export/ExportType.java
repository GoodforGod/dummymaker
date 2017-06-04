package io.dummymaker.export;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public enum ExportType {
    CSV(".csv"),
    JSON(".json"),
    XML(".xml"),
    SQL(".sql");

    ExportType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
