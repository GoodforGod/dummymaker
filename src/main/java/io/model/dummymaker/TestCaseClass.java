package io.model.dummymaker;

import io.model.dummymaker.annotation.GenDouble;
import io.model.dummymaker.annotation.GenInteger;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class TestCaseClass {

    @GenInteger
    private String str;

    @GenDouble
    private Double aDouble;
}
