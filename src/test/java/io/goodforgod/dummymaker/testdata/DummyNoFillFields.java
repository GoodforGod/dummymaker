package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.annotation.export.GenExportIgnore;
import io.goodforgod.dummymaker.annotation.export.GenExportName;

/**
 * @author GoodforGod
 * @since 18.08.2017
 */
public class DummyNoFillFields {

    @GenExportName("socialGroup")
    private String group = "100";

    @GenExportIgnore
    private String city;

    private Integer num;

    private String name;

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String getCity() {
        return city;
    }

    public Integer getNum() {
        return num;
    }

    public String getName() {
        return name;
    }
}
