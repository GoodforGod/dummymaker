package io.dummymaker.factory;

import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoPopulateFields;
import io.dummymaker.data.DummyNoZeroConstructor;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.07.2017
 */
public class GenProduceFactoryTest {

    @Test
    public void produceLessThanZeroAmount() {
        IProduceFactory dummyGenPopulateFactory = new GenProduceFactory();
        List<Dummy> dummies = dummyGenPopulateFactory.produce(Dummy.class, -20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorErrorList() {
        IProduceFactory dummyGenPopulateFactory = new GenProduceFactory();
        List<DummyNoZeroConstructor> dummies = dummyGenPopulateFactory.produce(DummyNoZeroConstructor.class, 20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorError() {
        IProduceFactory dummyGenPopulateFactory = new GenProduceFactory();
        DummyNoZeroConstructor dummy = dummyGenPopulateFactory.produce(DummyNoZeroConstructor.class);
        assertNull(dummy);
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
        assertNotNull(dummy.getNum());
        assertNotNull(dummy.getGroup());

        assertTrue(dummy.getCity().matches("[a-zA-Z0-9]+"));
        assertTrue(dummy.getName().matches("[a-zA-Z]+"));
        assertEquals((int) dummy.getNum(), 0);
        assertTrue(dummy.getGroup().matches("[0-9]+"));
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
