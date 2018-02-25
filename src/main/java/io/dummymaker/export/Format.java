package io.dummymaker.export;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public enum Format {
    CSV(".csv"),
    JSON(".json"),
    XML(".xml"),
    SQL(".sql");

    Format(final String extention) {
        this.extention = extention;
    }

    private final String extention;

    public String getExtention() {
        return extention;
    }
}
