package io.dummymaker.data;

import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.annotation.special.GenRenameExport;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
public class DummyNoExportFields {

    @GenRenameExport(name = "socialGroup")
    private String group = "100";

    @GenIgnoreExport
    private String city;

    @GenForceExport(value = false)
    private String noexport;

    @GenIgnoreExport(value = false)
    private String noignore;

    private Integer num;

    private String name;
}
