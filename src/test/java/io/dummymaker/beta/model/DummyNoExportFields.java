package io.dummymaker.beta.model;

import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
public class DummyNoExportFields {

    @GenExportName("socialGroup")
    private String group = "100";

    @GenExportIgnore
    private String city;

    private Integer num;

    private String name;
}
