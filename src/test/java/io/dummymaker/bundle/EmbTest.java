package io.dummymaker.bundle;

import io.dummymaker.data.DummyAuto;
import io.dummymaker.data.DummyEmbedded;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

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

        DummyEmbedded embedded = factory.produce(DummyEmbedded.class);
        assertNotNull(embedded);
    }

    @Test
    public void auto() {
        IProduceFactory factory = new GenProduceFactory();

        List<DummyAuto> autos = factory.produce(DummyAuto.class, 100000);
        assertNotNull(autos);
    }
}
