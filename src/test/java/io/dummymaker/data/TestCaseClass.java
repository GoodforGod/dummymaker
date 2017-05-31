package io.dummymaker.data;

import io.dummymaker.annotation.GenDouble;
import io.dummymaker.annotation.GenLocalDateTime;
import io.dummymaker.annotation.GenString;

import java.time.LocalDateTime;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class TestCaseClass implements TestCaseInter {

    @GenString
    private String str;

    @GenDouble
    private Double aDouble;

    @GenLocalDateTime
    private LocalDateTime dateTime;

    @Override
    public String test() {
        return null;
    }
}
