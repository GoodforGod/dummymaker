package io.dummymaker.export;

/**
 * Available export types, used by writer
 *
 * @see io.dummymaker.writer.BufferedFileWriter
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
enum ExportFormat {
    CSV(".csv"),
    JSON(".json"),
    XML(".xml"),
    SQL(".sql");

    ExportFormat(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
