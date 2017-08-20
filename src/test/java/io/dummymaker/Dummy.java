package io.dummymaker;

import io.dummymaker.annotation.GenCity;
import io.dummymaker.annotation.GenName;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.annotation.special.GenRenameExport;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class Dummy {

    @GenRenameExport(name = "socialGroup")
    private String group = "100";

    @GenCity
    @GenIgnoreExport
    private String city;

    @GenEnumerate
    private Integer num;

    @GenName
    private String name;
}
