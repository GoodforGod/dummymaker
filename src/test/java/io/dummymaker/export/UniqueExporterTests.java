package io.dummymaker.export;

import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.model.DummyEmbedded.DummyEmbeddedIntoSimple;
import io.dummymaker.model.GenRule;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 19.4.2020
 */
public class UniqueExporterTests extends Assert {

    @Test
    public void checkThatLambdaGeneratorTypeWorks() {
        final IGenerator<Integer> generator = () -> ThreadLocalRandom.current().nextBoolean()
                ? 1
                : 2;

        final GenRule rule = GenRule.of(DummyEmbeddedIntoSimple.class)
                .add(generator, "number");

        final GenFactory factory = new GenFactory(rule);
        final DummyEmbeddedIntoSimple dummy = factory.build(DummyEmbeddedIntoSimple.class);

        final String json = new JsonExporter().convert(dummy);
        assertTrue(json.contains("\"number\":1") || json.contains("\"number\":2"));
    }
}
