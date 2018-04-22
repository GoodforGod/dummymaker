package io.dummymaker.bundle;

import io.dummymaker.data.DummyTest;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
public class DummyRunTest extends Assert {

    @Test
    public void test() {
        IProduceFactory factory = new GenProduceFactory();
        DummyTest dummyTest = factory.produce(DummyTest.class);
        assertNotNull(dummyTest);
    }
}
