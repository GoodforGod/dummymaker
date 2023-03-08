package io.dummymaker;

import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.testdata.Dummy;
import io.dummymaker.testdata.DummyNoZeroConstructor;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
class GenFactoryTests {

    @Test
    void emptyWhenBuildLessThanZero() {
        final GenFactory factory = GenFactory.build();
        final List<Dummy> dummies = factory.build(Dummy.class, -2);
        assertTrue(dummies.isEmpty());
    }

    @Test
    void emptyWhenBuildNonZeroConstructor() {
        final GenFactory factory = GenFactory.build();
        final List<DummyNoZeroConstructor> dummies = factory.build(DummyNoZeroConstructor.class, 2);
        assertEquals(2, dummies.size());
    }

    @Test
    void nullWhenBuildNonZeroConstructor() {
        final GenFactory factory = GenFactory.build();
        final DummyNoZeroConstructor dummy = factory.build(DummyNoZeroConstructor.class);
        assertNotNull(dummy);
    }
}
