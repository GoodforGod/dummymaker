package io.dummymaker.bundle;

import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 23.04.2018
 */
public class EmbTest {

    @Test
    public void test() {
        IProduceFactory factory = new GenProduceFactory();
        DummyEmb emb = factory.produce(DummyEmb.class);
        assertNotNull(emb);
    }
}
