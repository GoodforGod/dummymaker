package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.ByteGenerator;
import io.goodforgod.dummymaker.testdata.DummyEmbedded;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.10.2019
 */
class GenRulesTests extends Assertions {

    @Test
    void ruleGeneratorIsInstantiatedOneTimePerRun() {
        class DummyOneTime {

            Short asShort;
            short asShortPrime;
            Integer asInt;
            int asIntPrime;
            Long asLong;
            long asLongPrime;
        }

        final GenRule rule = GenRule.ofClass(DummyOneTime.class, false)
                .generateForTypes(Long.class, long.class, Integer.class, int.class, Short.class, short.class, () -> {
                    final AtomicInteger atomic = new AtomicInteger();
                    return atomic::incrementAndGet;
                });

        final GenFactory factory = GenFactory.builder().addRule(rule).build();
        final DummyOneTime dummy = factory.build(DummyOneTime::new);

        assertNotNull(dummy);
        assertEquals(1, dummy.asShort.intValue());
        assertEquals(2, dummy.asShortPrime);
        assertEquals(3, dummy.asInt);
        assertEquals(4, dummy.asIntPrime);
        assertEquals(5, dummy.asLong);
        assertEquals(6, dummy.asLongPrime);
    }

    @Test
    void genLambdaGeneratorRuleForNamedField() {
        final Generator<String> generator = () -> "BILL";

        final GenRule rule = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForNames("name", () -> generator);

        final GenFactory factory = GenFactory.builder().addRule(rule).build();
        final DummyEmbedded dummy = factory.build(DummyEmbedded::new);

        assertNotNull(dummy);
        assertNotNull(dummy.getName());
        assertEquals("BILL", dummy.getName());
    }

    @Test
    void genLambdaGeneratorRuleForType() {
        final Generator<String> generator = () -> "BILL";

        final GenRule rule = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForTypes(String.class, () -> generator);

        final GenFactory factory = GenFactory.builder().addRule(rule).build();
        final DummyEmbedded dummy = factory.build(DummyEmbedded::new);

        assertNotNull(dummy);
        assertNotNull(dummy.getName());
        assertEquals("BILL", dummy.getName());
        assertEquals("BILL", dummy.getId());
    }

    @Test
    void genFieldRuleEqualsForTypeTargets() {
        final GenRule rule1 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForTypes(int.class, ByteGenerator::new);

        final GenRule rule2 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForTypes(byte.class, ByteGenerator::new);

        assertEquals(rule1, rule2);
    }

    @Test
    void genFieldRuleEqualsForNamedTargets() {
        final GenRule rule1 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForNames("a", ByteGenerator::new);

        final GenRule rule2 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForNames("a1", ByteGenerator::new);

        assertEquals(rule1, rule2);
    }

    @Test
    void genFieldRuleAreEqualsForTypeTargets() {
        final GenRule rule1 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForTypes(int.class, ByteGenerator::new);

        final GenRule rule2 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForTypes(int.class, ByteGenerator::new);

        assertEquals(rule1, rule2);
    }

    @Test
    void genFieldRuleAreEqualsForNamedTargets() {
        final GenRule rule1 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForNames("a", ByteGenerator::new);

        final GenRule rule2 = GenRule.ofClass(DummyEmbedded.class, false)
                .generateForNames("a", ByteGenerator::new);

        assertEquals(rule1, rule2);
    }

    @Test
    void rulesMergeValid() {
        final GenRule rule1 = GenRule.ofClass(DummyEmbedded.class, true, 2)
                .generateForNames("a", ByteGenerator::new);

        final GenRule rule2 = GenRule.ofClass(DummyEmbedded.class, true, 2)
                .generateForNames("aa", ByteGenerator::new);

        final GenRules rules = GenRules.of(Arrays.asList(rule1, rule2));
        assertNotNull(rules);

        final Optional<GenRuleContext> merged = rules.context().findClass(DummyEmbedded.class);
        assertTrue(merged.isPresent());

        final Set<GenRuleFieldContext> mergedFieldRules = merged.get().getSpecifiedFields();
        assertNotNull(mergedFieldRules);
        assertEquals(2, mergedFieldRules.size());
        assertTrue(mergedFieldRules.stream().anyMatch(r -> r.getName().contains("a")));
        assertTrue(mergedFieldRules.stream().anyMatch(r -> r.getName().contains("aa")));
    }
}
