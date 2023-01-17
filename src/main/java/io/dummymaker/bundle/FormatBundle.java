package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with programming file formats liek JSON, YAML, etc
 *
 * @author Anton Kurako (GoodforGod) Anton Kurako
 * @since 07.06.2017
 */
public final class FormatBundle extends AbstractBundle {

    private static final List<String> BUNDLE = Arrays.asList(
            "json",
            "avro",
            "text",
            "xml",
            "protobuf",
            "binary",
            "yaml",
            "csv",
            "html",
            "css");

    public FormatBundle() {
        super(BUNDLE);
    }
}