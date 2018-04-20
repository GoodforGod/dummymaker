package io.dummymaker.export;

import io.dummymaker.export.naming.Cases;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
@RunWith(Parameterized.class)
public class CaseParameterizedTest extends Assert {

    private String initial;
    private String expected;

    private Cases caseUsed;

    public CaseParameterizedTest(String initial, String expected, Cases caseUsed) {
        this.initial = initial;
        this.expected = expected;
        this.caseUsed = caseUsed;
    }

    @Parameters(name = "Case used - {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "MyTomEx", "MyTomEx",     Cases.DEFAULT },
                { "MyTomEx", "mytomex",     Cases.LOW_CASE },
                { "MyTomEx", "MYTOMEX",     Cases.UPPER_CASE },
                { "MyTomEx", "my-tom-ex",   Cases.KEBAB_CASE },
                { "MyTomEx", "MY-TOM-EX",   Cases.UPPER_KEBAB_CASE },
                { "MyTomEx", "my_tom_ex",   Cases.SNAKE_CASE },
                { "MyTomEx", "MY_TOM_EX",   Cases.UPPER_SNAKE_CASE },
                { "MyTomEx", "myTomEx",     Cases.CAMEL_CASE },
                { "myTomEx", "MyTomEx",     Cases.PASCAL_CASE },
        });
    }

    @Test
    public void checkCase() {
        final String result = caseUsed.value().format(initial);
        assertEquals(expected, result);
    }
}
