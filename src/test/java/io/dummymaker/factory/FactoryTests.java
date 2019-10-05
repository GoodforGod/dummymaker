package io.dummymaker.factory;

import io.dummymaker.beta.model.Dummy;
import io.dummymaker.beta.model.DummyNoZeroConstructor;
import io.dummymaker.factory.impl.GenFactory;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
