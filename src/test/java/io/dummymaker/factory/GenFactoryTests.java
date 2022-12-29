package io.dummymaker.factory;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.dummymaker.factory.old.MainGenFactory;
import io.dummymaker.factory.refactored.GenFactory;
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
public class GenFactoryTests {

    @Test
    public void isEmptyForEmptyList() {
        final GenFactory factory = GenFactory.build();
        assertTrue(factory.build(Collections::emptyList).isEmpty());
    }

    @Test
    public void isEmptyForNullList() {
        final GenFactory factory = GenFactory.build();
        assertTrue(factory.build(() -> (List<Object>) null).isEmpty());
    }

    @Test
    public void isNullForNull() {
        final GenFactory factory = GenFactory.build();
        assertNull(factory.build(() -> (Dummy) null));
    }

    @Test
    public void emptyWhenBuildLessThanZero() {
        final GenFactory factory = GenFactory.build();
        final List<Dummy> dummies = factory.build(Dummy.class, -2);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void emptyWhenBuildNonZeroConstructor() {
        final GenFactory factory = GenFactory.build();
        final List<DummyNoZeroConstructor> dummies = factory.build(DummyNoZeroConstructor.class, 2);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void nullWhenBuildNonZeroConstructor() {
        final GenFactory factory = GenFactory.build();
        final DummyNoZeroConstructor dummy = factory.build(DummyNoZeroConstructor.class);
        assertNull(dummy);
    }
}
