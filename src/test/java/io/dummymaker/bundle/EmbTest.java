package io.dummymaker.bundle;

import io.dummymaker.data.DummyEmbedded;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.AutoGeneratorsFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import io.dummymaker.generator.simple.IGenerator;
import org.junit.Test;

import java.util.List;
import java.util.Map;

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
        AutoGeneratorsFactory factory1 = new AutoGeneratorsFactory();

        DummyEmbedded emb = factory.produce(DummyEmbedded.class);
        Map<Class<?>, List<Class<? extends IGenerator>>> classListMap = factory1.availableGenerators();
        assertNotNull(emb);
    }
}
