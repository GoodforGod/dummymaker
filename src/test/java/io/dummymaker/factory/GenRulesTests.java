package io.dummymaker.factory;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.number.ByteGenerator;
import io.dummymaker.model.DummyEmbedded;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.10.2019
 */
class GenRulesTests extends Assertions {

    @Test
    void genLambdaGeneratorRuleForNamedField() {
        final Generator<String> generator = () -> "BILL";

        final GenRule rule = GenRule.manual(DummyEmbedded.class)
                .named(() -> generator, "name");

        final GenFactory factory = GenFactory.builder().rule(rule).build();
        final DummyEmbedded dummy = factory.build(DummyEmbedded::new);

        assertNotNull(dummy);
        assertNotNull(dummy.getName());
        assertEquals("BILL", dummy.getName());
    }

    @Test
    void genLambdaGeneratorRuleForType() {
        final Generator<String> generator = () -> "BILL";

        final GenRule rule = GenRule.manual(DummyEmbedded.class)
                .typed(() -> generator, String.class);

        final GenFactory factory = GenFactory.builder().rule(rule).build();
        final DummyEmbedded dummy = factory.build(DummyEmbedded::new);

        assertNotNull(dummy);
        assertNotNull(dummy.getName());
        assertEquals("BILL", dummy.getName());
        assertEquals("BILL", dummy.getId());
    }

    @Test
    void genFieldRuleNotEqualsForTypeTargets() {
        final GenRule rule1 = GenRule.manual(DummyEmbedded.class)
                .typed(ByteGenerator::new, int.class);

        final GenRule rule2 = GenRule.manual(DummyEmbedded.class)
                .typed(ByteGenerator::new, byte.class);

        assertNotEquals(rule1, rule2);
    }

    @Test
    void genFieldRuleNotEqualsForNamedTargets() {
        final GenRule rule1 = GenRule.manual(DummyEmbedded.class)
                .named(ByteGenerator::new, "a");

        final GenRule rule2 = GenRule.manual(DummyEmbedded.class)
                .named(ByteGenerator::new, "a1");

        assertNotEquals(rule1, rule2);
    }

    @Test
    void genFieldRuleAreEqualsForTypeTargets() {
        final GenRule rule1 = GenRule.manual(DummyEmbedded.class)
                .typed(ByteGenerator::new, int.class);

        final GenRule rule2 = GenRule.manual(DummyEmbedded.class)
                .typed(ByteGenerator::new, int.class);

        assertEquals(rule1, rule2);
    }

    @Test
    void genFieldRuleAreEqualsForNamedTargets() {
        final GenRule rule1 = GenRule.manual(DummyEmbedded.class)
                .named(ByteGenerator::new, "a");

        final GenRule rule2 = GenRule.manual(DummyEmbedded.class)
                .named(ByteGenerator::new, "a");

        assertEquals(rule1, rule2);
    }

    @Test
    void rulesMergeValid() {
        final GenRule rule1 = GenRule.auto(DummyEmbedded.class, 2)
                .named(ByteGenerator::new, "a");

        final GenRule rule2 = GenRule.auto(DummyEmbedded.class, 2)
                .named(ByteGenerator::new, "aa");

        final GenRules rules = GenRules.of(Arrays.asList(rule1, rule2));
        assertNotNull(rules);

        final Optional<GenRule> merged = rules.find(DummyEmbedded.class);
        assertTrue(merged.isPresent());

        final Set<GenRuleField> mergedFieldRules = merged.get().getFieldRules();
        assertNotNull(mergedFieldRules);
        assertEquals(2, mergedFieldRules.size());
        assertTrue(mergedFieldRules.stream().anyMatch(r -> r.getNames().contains("a")));
        assertTrue(mergedFieldRules.stream().anyMatch(r -> r.getNames().contains("aa")));
    }
}
