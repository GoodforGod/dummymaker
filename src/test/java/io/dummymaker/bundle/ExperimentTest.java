package io.dummymaker.bundle;

import io.dummymaker.data.DummyExperiment;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 05.03.2018
 */
public class ExperimentTest extends Assert {

    @Test
    public void test() {
        IProduceFactory factory = new GenProduceFactory();
        DummyExperiment experiment = factory.produce(DummyExperiment.class);
        assertNotNull(experiment);
        assertNotNull(experiment.getObjects());
    }
}
