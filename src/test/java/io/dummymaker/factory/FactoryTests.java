package io.dummymaker.factory;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoZeroConstructor;
import java.util.Collections;
import java.util.List;
import org.junit.Test;


/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class FactoryTests {

    private final GenFactory factory = new GenFactory();

    @Test
    public void isEmptyForEmptyList() {
        assertTrue(factory.fill(Collections.emptyList()).isEmpty());
    }

    @Test
    public void isEmptyForNullList() {
        assertTrue(factory.fill((List<Object>) null).isEmpty());
    }

    @Test
    public void isNullForNull() {
        assertNull(factory.fill((Dummy) null));
    }

    @Test
    public void emptyWhenBuildLessThanZero() {
        final List<Dummy> dummies = factory.build(Dummy.class, -2);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void emptyWhenBuildNonZeroConstructor() {
        final List<DummyNoZeroConstructor> dummies = factory.build(DummyNoZeroConstructor.class, 2);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void nullWhenBuildNonZeroConstructor() {
        final DummyNoZeroConstructor dummy = factory.build(DummyNoZeroConstructor.class);
        assertNull(dummy);
    }
}
