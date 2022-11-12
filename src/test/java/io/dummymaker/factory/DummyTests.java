package io.dummymaker.factory;

import static org.junit.Assert.*;

import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoFillFields;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class DummyTests {

    private final MainGenFactory factory = new MainGenFactory();

    @Test
    public void populateListOfTwo() {
        final String group1 = "300";
        final String group2 = "400";

        final List<Dummy> dummies = new ArrayList<>();
        dummies.add(new Dummy());
        dummies.get(0).setGroup(group1);
        dummies.add(new Dummy());
        dummies.get(1).setGroup(group2);

        final List<Dummy> filled = factory.fill(dummies);

        assertNotNull(filled);
        assertFalse(filled.isEmpty());

        final Dummy dummy1 = filled.get(0);
        assertNotNull(filled);
        assertNotNull(dummy1.getCity());
        assertNotNull(dummy1.getName());
        assertNotNull(dummy1.getNum());
        assertEquals(group1, dummy1.getGroup());

        final Dummy dummy2 = filled.get(1);
        assertNotNull(dummy2);
        assertNotNull(dummy2.getCity());
        assertNotNull(dummy2.getName());
        assertNotNull(dummy2.getNum());
        assertEquals(group2, dummy2.getGroup());
    }

    @Test
    public void populateSingleDummy() {
        final String group = "300";
        final Dummy dummy = new Dummy();
        dummy.setGroup(group);

        final Dummy filled = factory.fill(dummy);

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
    public void populateWithNoPopulateFields() {
        final String group = "300";
        final DummyNoFillFields dummy = new DummyNoFillFields();
        dummy.setGroup(group);

        final DummyNoFillFields filled = factory.fill(dummy);

        assertNotNull(filled);
        assertNull(filled.getCity());
        assertNull(filled.getName());
        assertNull(filled.getNum());
        assertEquals(group, filled.getGroup());
    }
}
