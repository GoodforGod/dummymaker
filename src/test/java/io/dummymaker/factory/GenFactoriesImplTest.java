package io.dummymaker.factory;

import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoPopulateFields;
import io.dummymaker.factory.impl.GenPopulateFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.07.2017
 */
public class GenFactoriesImplTest {

    @Test
    public void genPopulateFactoryListTest() {
        String group1 = "300";
        String group2 = "400";

        List<Dummy> dummies = new ArrayList<Dummy>() {{
            add(new Dummy());
            add(new Dummy());
        }};

        dummies.get(0).setGroup(group1);
        dummies.get(1).setGroup(group2);

        IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        dummies = dummyGenPopulateFactory.populate(dummies);

        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());

        Dummy dummy1 = dummies.get(0);
        assertNotNull(dummy1);
        assertNotNull(dummy1.getCity());
        assertNotNull(dummy1.getName());
        assertNotNull(dummy1.getNum());
        assertEquals(group1, dummy1.getGroup());

        Dummy dummy2 = dummies.get(1);
        assertNotNull(dummy2);
        assertNotNull(dummy2.getCity());
        assertNotNull(dummy2.getName());
        assertNotNull(dummy2.getNum());
        assertEquals(group2, dummy2.getGroup());
    }

    @Test
    public void genPopulateFactorySingleDummyTest() {
        String group = "300";

        Dummy dummy = new Dummy();
        dummy.setGroup(group);

        IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        dummy = dummyGenPopulateFactory.populate(dummy);

        assertNotNull(dummy);
        assertNotNull(dummy.getCity());
        assertNotNull(dummy.getName());
        assertNull(dummy.getNum());
        assertEquals(group, dummy.getGroup());
    }

    @Test
    public void genProduceFactoryListTest() {
        IProduceFactory dummyGenPopulateFactory = new GenProduceFactory();
        List<Dummy> dummies = dummyGenPopulateFactory.produce(Dummy.class, 2);

        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());

        Dummy dummy1 = dummies.get(0);
        assertNotNull(dummy1);
        assertNotNull(dummy1.getCity());
        assertNotNull(dummy1.getName());
        assertNotNull(dummy1.getNum());
        assertNotNull(dummy1.getGroup());

        Dummy dummy2 = dummies.get(1);
        assertNotNull(dummy2);
        assertNotNull(dummy2.getCity());
        assertNotNull(dummy2.getName());
        assertNotNull(dummy2.getNum());
        assertNotNull(dummy2.getGroup());
    }

    @Test
    public void genProduceFactorySingleDummyTest() {
        IProduceFactory dummyGenPopulateFactory = new GenProduceFactory();
        Dummy dummy = dummyGenPopulateFactory.produce(Dummy.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getCity());
        assertNotNull(dummy.getName());
        assertNull(dummy.getNum());
        assertNotNull(dummy.getGroup());
    }

    @Test
    public void genPopulateWithNoPopulateFields() {
        String group = "300";

        DummyNoPopulateFields dummy = new DummyNoPopulateFields();
        dummy.setGroup(group);

        IPopulateFactory dummyGenPopulateFactory = new GenPopulateFactory();
        dummy = dummyGenPopulateFactory.populate(dummy);

        assertNotNull(dummy);
        assertNull(dummy.getCity());
        assertNull(dummy.getName());
        assertNull(dummy.getNum());
        assertEquals(group, dummy.getGroup());
    }

    @Test
    public void genProduceWithNoPopulateFields() {
        IProduceFactory dummyGenPopulateFactory = new GenProduceFactory();
        DummyNoPopulateFields dummy = dummyGenPopulateFactory.produce(DummyNoPopulateFields.class);

        assertNotNull(dummy);
        assertNull(dummy.getCity());
        assertNull(dummy.getName());
        assertNull(dummy.getNum());
        assertEquals("100", dummy.getGroup());
    }
}
