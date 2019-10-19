package io.dummymaker.factory;

import io.dummymaker.bundle.impl.NounBundle;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.generator.simple.number.ByteGenerator;
import io.dummymaker.generator.simple.string.NounGenerator;
import io.dummymaker.model.DummyEmbedded;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Global Gen rules tests
 *
 * @author GoodforGod
 * @since 15.10.2019
 */
public class DummyGlobalRulesTests extends Assert {

    private final GenFactory factory = new GenFactory(GenRules.of(
            GenRule.global(2)
                    .add(ByteGenerator.class, "number")
                    .add(NounGenerator.class, "name")
    ));

    @Test
    public void validFieldsGeneration() {
        final DummyEmbedded build = factory.build(DummyEmbedded.class);

        final Set<String> nouns = new HashSet<>(Arrays.asList(new NounBundle().getAll()));

        assertNotNull(build.getId());
        assertNotNull(build.getChild());
        assertNotNull(build.getSimpleChild());
        assertNotNull(build.getName());
        assertTrue(nouns.contains(build.getName()));

        assertTrue(build.getSimpleChild().getNumber() < Byte.MAX_VALUE);
        assertNotNull(build.getSimpleChild().getSimpleName());
        assertTrue(nouns.contains(build.getSimpleChild().getSimpleName()));

        assertNotNull(build.getChild().getName());
        assertTrue(nouns.contains(build.getChild().getName()));
    }
}
