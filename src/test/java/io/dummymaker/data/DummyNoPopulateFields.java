package io.dummymaker.data;

import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.annotation.special.GenRenameExport;

public class DummyNoPopulateFields {

    @GenRenameExport(name = "socialGroup")
    @GenForceExport
    private String group = "100";

    @GenIgnoreExport
    private String city;

    private Integer num;

    private String name;
}
