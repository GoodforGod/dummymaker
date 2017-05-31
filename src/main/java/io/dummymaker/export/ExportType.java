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
    XML("xml"),
    SQL(".sql");

    ExportType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
