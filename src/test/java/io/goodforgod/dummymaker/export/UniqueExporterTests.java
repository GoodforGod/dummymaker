package io.goodforgod.dummymaker.export;

import io.goodforgod.dummymaker.GenFactory;
import io.goodforgod.dummymaker.GenRule;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.testdata.DummyEmbedded.DummyEmbeddedIntoSimple;
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

        final GenRule rule = GenRule.ofClass(DummyEmbeddedIntoSimple.class, false)
                .generateForNames("number", () -> generator);

        final GenFactory factory = GenFactory.builder().addRule(rule).build();
        final DummyEmbeddedIntoSimple dummy = factory.build(DummyEmbeddedIntoSimple.class);

        final String json = JsonExporter.build().exportAsString(dummy);
        assertTrue(json.contains("\"number\":1") || json.contains("\"number\":2"));
    }
}
