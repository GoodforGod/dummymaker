package io.dummymaker.factory;

import io.dummymaker.generator.simple.number.ByteGenerator;
import io.dummymaker.generator.simple.string.NounGenerator;
import io.dummymaker.model.DummyEmbedded;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Global Gen rules tests
 *
 * @author GoodforGod
 * @since 15.10.2019
 */
class DummyGlobalRulesTests extends Assertions {

    @Test
    void validFieldsGeneration() {
        final GenFactory factory = GenFactory.builder()
                .rule(GenRule.global(2)
                        .typed(ByteGenerator::new, int.class)
                        .named(NounGenerator::new, "name"))
                .build();

        final DummyEmbedded dummy = factory.build(DummyEmbedded.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getId());
        assertNotNull(dummy.getChild());
        assertNotNull(dummy.getSimpleChild());
        assertNotNull(dummy.getName());

        assertTrue(dummy.getSimpleChild().getNumber() < Byte.MAX_VALUE);
        assertNotNull(dummy.getSimpleChild().getSimpleName());

        assertNotNull(dummy.getChild().getName());
    }
}
