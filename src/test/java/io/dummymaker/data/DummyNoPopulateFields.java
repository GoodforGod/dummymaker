package io.dummymaker.data;

import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.annotation.special.GenRenameExport;

/**
 * Dummy Object used as data to proceed in tests
 * This object HAS NO FIELDS TO POPULATE
 *
 * @author GoodforGod
 * @since 18.08.2017
 */
public class DummyNoPopulateFields {

    @GenRenameExport("socialGroup")
    @GenForceExport
    private String group = "100";

    @GenIgnoreExport
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
