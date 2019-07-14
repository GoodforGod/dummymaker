package io.dummymaker.data;

import io.dummymaker.annotation.special.GenExportIgnore;
import io.dummymaker.annotation.special.GenExportName;

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
