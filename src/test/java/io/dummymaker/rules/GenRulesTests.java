package io.dummymaker.rules;

import io.dummymaker.generator.simple.number.ByteGenerator;
import io.dummymaker.model.DummyEmbedded;
import io.dummymaker.model.GenFieldRule;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author GoodforGod
 * @see io.dummymaker.model.GenRule
 * @see io.dummymaker.model.GenRules
 * @since 20.10.2019
 */
public class GenRulesTests extends Assert {

    @Test
    public void genFieldRuleNotEqualsForTypeTargets() {
        final GenFieldRule rule1 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, int.class)
                .getRules().get(0);

        final GenFieldRule rule2 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, byte.class)
                .getRules().get(0);

        assertNotEquals(rule1, rule2);
    }

    @Test
    public void genFieldRuleNotEqualsForNamedTargets() {
        final GenFieldRule rule1 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, "a")
                .getRules().get(0);

        final GenFieldRule rule2 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, "a1")
                .getRules().get(0);

        assertNotEquals(rule1, rule2);
    }

    @Test
    public void genFieldRuleAreEqualsForTypeTargets() {
        final GenFieldRule rule1 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, int.class)
                .getRules().get(0);

        final GenFieldRule rule2 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, int.class)
                .getRules().get(0);

        assertEquals(rule1, rule2);
    }

    @Test
    public void genFieldRuleAreEqualsForNamedTargets() {
        final GenFieldRule rule1 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, "a")
                .getRules().get(0);

        final GenFieldRule rule2 = GenRule.of(DummyEmbedded.class)
                .add(ByteGenerator.class, "a")
                .getRules().get(0);

        assertEquals(rule1, rule2);
    }

    @Test(expected = IllegalAccessException.class)
    public void genRuleOfTargetInvalidForNull() {
        GenRule.of(null)
                .add(ByteGenerator.class, "a")
                .getRules();
    }

    @Test(expected = IllegalAccessException.class)
    public void genRuleOfTargetInvalidForObject() {
        GenRule.of(Object.class)
                .add(ByteGenerator.class, "a")
                .getRules();
    }

    @Test
    public void genRuleAutoDepthValid() {
        final List<GenFieldRule> rules = GenRule.auto(DummyEmbedded.class, 2)
                .add(ByteGenerator.class, "a")
                .getRules();

        assertNotNull(rules);
        assertFalse(rules.isEmpty());
    }


    @Test(expected = IllegalAccessException.class)
    public void genRuleAutoDepthInvalidForNull() {
        GenRule.auto(null, 2)
                .add(ByteGenerator.class, "a")
                .getRules();
    }

    @Test(expected = IllegalAccessException.class)
    public void genRuleAutoDepthInvalidForObject() {
        GenRule.auto(Object.class, 2)
                .add(ByteGenerator.class, "a")
                .getRules();
    }

    @Test(expected = IllegalAccessException.class)
    public void genRuleAutoDepthInvalidDepthLessOne() {
        GenRule.auto(DummyEmbedded.class, 0)
                .add(ByteGenerator.class, "a")
                .getRules();
    }

    @Test
    public void rulesMergeValid() {
        final GenRule rule1 = GenRule.auto(DummyEmbedded.class, 2)
                .add(ByteGenerator.class, "a");

        final GenRule rule2 = GenRule.auto(DummyEmbedded.class, 2)
                .add(ByteGenerator.class, "aa");

        final GenRules rules = GenRules.of(rule1, rule2);
        assertNotNull(rules);

        final GenRule merged = rules.getRules().get(0);
        assertNotNull(merged);

        final List<GenFieldRule> mergedFieldRules = merged.getRules();
        assertNotNull(mergedFieldRules);
        assertEquals(1, mergedFieldRules.size());
        assertTrue(mergedFieldRules.get(0).getNames().contains("a"));
        assertTrue(mergedFieldRules.get(0).getNames().contains("aa"));
    }

}
