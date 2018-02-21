package io.dummymaker.util;

import io.dummymaker.export.NamingStrategy;
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

    private final NamingStrategy strategy;

    private final String originLook;
    private final String expectedLook;

    public NameStrategyTest(NamingStrategy strategy, String originLook, String expectedLook) {
        this.strategy = strategy;
        this.originLook = originLook;
        this.expectedLook = expectedLook;
    }

    @Parameters(name = "Strategy - {0}, Expected - {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { NamingStrategy.DEFAULT, "DummyList", "DummyList" },
                { NamingStrategy.LOW_CASE, "DummyList", "dummylist" },
                { NamingStrategy.UPPER_CASE, "DummyList", "DUMMYLIST" },
                { NamingStrategy.UNDERSCORED_LOW_CASE, "DummyList", "dummy_list" },
                { NamingStrategy.UNDERSCORED_UPPER_CASE, "DummyList", "DUMMY_LIST" },
                { NamingStrategy.INITIAL_LOW_CASE, "DummyList", "dummyList" }
        });
    }

    @Test
    public void checkNameStrategistConverters() {
        final String converted = strategy.toStrategy(originLook);
        assertEquals(expectedLook, converted);
    }
}
