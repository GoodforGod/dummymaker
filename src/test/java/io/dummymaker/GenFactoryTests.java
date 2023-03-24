package io.dummymaker;

import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.testdata.DummyFullArgConstructor;
import io.dummymaker.testdata.DummyZeroArgConstructor;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
class GenFactoryTests {

    @Test
    void buildFullArgConstructorList() {
        final GenFactory factory = GenFactory.build();
        final List<DummyFullArgConstructor> dummies = factory.build(DummyFullArgConstructor.class, 2);
        assertEquals(2, dummies.size());
        for (DummyFullArgConstructor dummy : dummies) {
            assertNotNull(dummy.getAmount());
            assertNotNull(dummy.getDummy());
            assertNotNull(dummy.getMap());
            assertNotNull(dummy.getObjectsFix());
            assertNotNull(dummy.getStringsFix());
        }
    }

    @Test
    void buildFullArgConstructorSingle() {
        final GenFactory factory = GenFactory.build();
        final DummyFullArgConstructor dummy = factory.build(DummyFullArgConstructor.class);
        assertNotNull(dummy);
        assertNotNull(dummy.getAmount());
        assertNotNull(dummy.getDummy());
        assertNotNull(dummy.getMap());
        assertNotNull(dummy.getObjectsFix());
        assertNotNull(dummy.getStringsFix());
    }

    @Test
    void buildZeroArgConstructorList() {
        final GenFactory factory = GenFactory.build();
        final List<DummyZeroArgConstructor> dummies = factory.build(DummyZeroArgConstructor.class, 2);
        assertEquals(2, dummies.size());
        for (DummyZeroArgConstructor dummy : dummies) {
            assertNotNull(dummy.getAmount());
            assertNotNull(dummy.getDummy());
            assertNotNull(dummy.getMap());
            assertNotNull(dummy.getObjectsFix());
            assertNotNull(dummy.getStringsFix());
        }
    }

    @Test
    void buildZeroArgConstructorSingle() {
        final GenFactory factory = GenFactory.build();
        final DummyZeroArgConstructor dummy = factory.build(DummyZeroArgConstructor.class);
        assertNotNull(dummy);
        assertNotNull(dummy.getAmount());
        assertNotNull(dummy.getDummy());
        assertNotNull(dummy.getMap());
        assertNotNull(dummy.getObjectsFix());
        assertNotNull(dummy.getStringsFix());
    }
}
