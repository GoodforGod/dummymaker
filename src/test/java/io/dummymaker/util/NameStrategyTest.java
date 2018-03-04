package io.dummymaker.util;

import io.dummymaker.export.naming.Strategies;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 02.09.2017
 */
@RunWith(Parameterized.class)
public class NameStrategyTest {

    private final Strategies strategy;

    private final String originLook;
    private final String expectedLook;

    public NameStrategyTest(Strategies strategy, String originLook, String expectedLook) {
        this.strategy = strategy;
        this.originLook = originLook;
        this.expectedLook = expectedLook;
    }

    @Parameters(name = "Strategy - {0}, Expected - {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { Strategies.DEFAULT, "DummyList", "DummyList" },
                { Strategies.LOW_CASE, "DummyList", "dummylist" },
                { Strategies.INITIAL_LOW_CASE, "D", "d" },
                { Strategies.UPPER_CASE, "DummyList", "DUMMYLIST" },
                { Strategies.UNDERSCORED_LOW_CASE, "DummyList", "dummy_list" },
                { Strategies.UNDERSCORED_UPPER_CASE, "DummyList", "DUMMY_LIST" },
                { Strategies.INITIAL_LOW_CASE, "DummyList", "dummyList" }
        });
    }

    @Test
    public void checkNameStrategistConverters() {
        final String converted = strategy.getStrategy().toStrategy(originLook);
        assertEquals(expectedLook, converted);
    }
}
