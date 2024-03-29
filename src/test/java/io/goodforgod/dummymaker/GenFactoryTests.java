package io.goodforgod.dummymaker;

import static org.junit.jupiter.api.Assertions.*;

import io.goodforgod.dummymaker.error.GenException;
import io.goodforgod.dummymaker.testdata.DummyAbstract;
import io.goodforgod.dummymaker.testdata.DummyFullArgConstructor;
import io.goodforgod.dummymaker.testdata.DummyZeroArgConstructor;
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

        assertNotEquals(dummies.get(0).getAmount(), dummies.get(1).getAmount());
        assertNotEquals(dummies.get(0).getDummy(), dummies.get(1).getDummy());
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
    void buildAbstractSingle() {
        final GenFactory factory = GenFactory.build();
        assertThrows(GenException.class, () -> factory.build(DummyAbstract.class));
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
