package io.dummymaker.bundle.impl;


/**
 * Bundle with programming file formats liek JSON, YAML, etc
 *
 * @author GoodforGod Anton Kurako
 * @since 07.06.2017
 */
public class FormatBundle extends BasicBundle {

    public FormatBundle() {
        super(
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
    }
}
