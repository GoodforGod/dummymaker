package io.dummymaker.export;

import io.dummymaker.cases.Cases;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 21.04.2018
 */
public class CaseParameterizedTests extends Assertions {

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "MyTomEx", "MyTomEx", Cases.DEFAULT },
                { "MyTomEx", "mytomex", Cases.LOWER_CASE },
                { "MyTomEx", "MYTOMEX", Cases.UPPER_CASE },
                { "MyTomEx", "my-tom-ex", Cases.KEBAB_LOWER_CASE },
                { "MyTomEx", "MY-TOM-EX", Cases.KEBAB_UPPER_CASE },
                { "MyTomEx", "my_tom_ex", Cases.SNAKE_LOWER_CASE },
                { "MyTomEx", "MY_TOM_EX", Cases.SNAKE_UPPER_CASE },
                { "MyTomEx", "myTomEx", Cases.CAMEL_CASE },
                { "myTomEx", "MyTomEx", Cases.PASCAL_CASE },
        });
    }

    @MethodSource("data")
    @ParameterizedTest
    void checkCase(String initial, String expected, Cases caseUsed) {
        final String result = caseUsed.value().apply(initial);
        assertEquals(expected, result);
    }
}
