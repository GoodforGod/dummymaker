package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.annotation.export.GenExportIgnore;
import io.goodforgod.dummymaker.annotation.export.GenExportName;

/**
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
