package io.dummymaker.testdata;

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
    @GenExportIgnore
    private String group = "100";

    @GenExportIgnore
    private String city;

    @GenExportIgnore
    private Integer num;

    @GenExportIgnore
    private String name;
}
