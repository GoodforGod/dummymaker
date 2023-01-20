package io.dummymaker.export;

import io.dummymaker.factory.GenFactory;
import io.dummymaker.factory.GenRule;
import io.dummymaker.generator.Generator;
import io.dummymaker.model.DummyEmbedded.DummyEmbeddedIntoSimple;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 19.4.2020
 */
class UniqueExporterTests extends Assertions {

    @Test
    void checkThatLambdaGeneratorTypeWorks() {
        final Generator<Integer> generator = () -> ThreadLocalRandom.current().nextBoolean()
                ? 1
                : 2;

        final GenRule rule = GenRule.manual(DummyEmbeddedIntoSimple.class)
                .named(() -> generator, "number");

        final GenFactory factory = GenFactory.builder().rule(rule).build();
        final DummyEmbeddedIntoSimple dummy = factory.build(DummyEmbeddedIntoSimple.class);

        final String json = JsonExporter.build().convert(dummy);
        assertTrue(json.contains("\"number\":1") || json.contains("\"number\":2"));
    }
}
