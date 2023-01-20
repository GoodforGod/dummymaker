package io.dummymaker.factory;

import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoFillFields;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
class DummySupplierTests {

    @Test
    void populateListOfTwo() {
        final GenFactory factory = GenFactory.build();
        final List<Dummy> filled = factory.build(Dummy::new, 2);

        assertNotNull(filled);
        assertFalse(filled.isEmpty());

        final Dummy dummy1 = filled.get(0);
        assertNotNull(filled);
        assertNotNull(dummy1.getCity());
        assertNotNull(dummy1.getName());
        assertNotNull(dummy1.getNum());
        assertNotNull(dummy1.getGroup());

        final Dummy dummy2 = filled.get(1);
        assertNotNull(dummy2);
        assertNotNull(dummy2.getCity());
        assertNotNull(dummy2.getName());
        assertNotNull(dummy2.getNum());
        assertNotNull(dummy2.getGroup());
    }

    @Test
    void populateSingleDummy() {
        final GenFactory factory = GenFactory.build();

        final String group = "300";
        final Dummy dummy = new Dummy();
        dummy.setGroup(group);

        final Dummy filled = factory.build(() -> dummy);

        assertNotNull(filled);
        assertNotNull(filled.getCity());
        assertNotNull(filled.getName());
        assertNotNull(filled.getNum());
        assertNotNull(filled.getGroup());

        assertTrue(filled.getCity().matches("[a-zA-Z0-9\\-]+"));
        assertTrue(filled.getName().matches("[a-zA-Z]+"));
        assertEquals(0, (int) filled.getNum());
        assertTrue(filled.getGroup().matches("[0-9]+"));
    }

    @Test
    void populateWithNoPopulateFields() {
        final GenFactory factory = GenFactory.builder().autoByDefault(false).build();

        final String group = "300";
        final DummyNoFillFields dummy = new DummyNoFillFields();
        dummy.setGroup(group);

        final DummyNoFillFields filled = factory.build(() -> dummy);

        assertNotNull(filled);
        assertNull(filled.getCity());
        assertNull(filled.getName());
        assertNull(filled.getNum());
        assertEquals(group, filled.getGroup());
    }
}
