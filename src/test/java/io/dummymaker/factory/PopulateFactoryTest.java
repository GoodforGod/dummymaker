package io.dummymaker.factory;

import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoPopulateFields;
import io.dummymaker.factory.impl.GenPopulateFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
public class PopulateFactoryTest {

    @Test
    public void genPopulateTestEmptyList() {
        final IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        assertTrue(dummyGenPopulateFactory.populate(Collections.emptyList()).isEmpty());
    }

    @Test
    public void genPopulateTestNullList() {
        final IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        assertTrue(dummyGenPopulateFactory.populate(null).isEmpty());
    }

    @Test
    public void genPopulateTestNull() {
        final IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        assertNull(dummyGenPopulateFactory.populate((Dummy) null));
    }

    @Test
    public void populateListOfTwo() {
        final String group1 = "300";
        final String group2 = "400";

        List<Dummy> dummies = new ArrayList<>();
        dummies.add(new Dummy());
        dummies.add(new Dummy());

        dummies.get(0).setGroup(group1);
        dummies.get(1).setGroup(group2);

        final IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        dummies = dummyGenPopulateFactory.populate(dummies);

        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());

        final Dummy dummy1 = dummies.get(0);
        assertNotNull(dummy1);
        assertNotNull(dummy1.getCity());
        assertNotNull(dummy1.getName());
        assertNotNull(dummy1.getNum());
        assertEquals(group1, dummy1.getGroup());

        final Dummy dummy2 = dummies.get(1);
        assertNotNull(dummy2);
        assertNotNull(dummy2.getCity());
        assertNotNull(dummy2.getName());
        assertNotNull(dummy2.getNum());
        assertEquals(group2, dummy2.getGroup());
    }

    @Test
    public void populateSingleDummy() {
        final String group = "300";

        Dummy dummy = new Dummy();
        dummy.setGroup(group);

        final IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        dummy = dummyGenPopulateFactory.populate(dummy);

        assertNotNull(dummy);
        assertNotNull(dummy.getCity());
        assertNotNull(dummy.getName());
        assertNotNull(dummy.getNum());
        assertNotNull(dummy.getGroup());

        assertTrue(dummy.getCity().matches("[a-zA-Z0-9\\-]+"));
        assertTrue(dummy.getName().matches("[a-zA-Z]+"));
        assertEquals((int) dummy.getNum(), 0);
        assertTrue(dummy.getGroup().matches("[0-9]+"));
    }

    @Test
    public void populateWithNoPopulateFields() {
        final String group = "300";

        DummyNoPopulateFields dummy = new DummyNoPopulateFields();
        dummy.setGroup(group);

        final IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        dummy = dummyGenPopulateFactory.populate(dummy);

        assertNotNull(dummy);
        assertNull(dummy.getCity());
        assertNull(dummy.getName());
        assertNull(dummy.getNum());
        assertEquals(group, dummy.getGroup());
    }
}
