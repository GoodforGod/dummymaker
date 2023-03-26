package io.dummymaker;

import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.testdata.DummyRules;
import io.dummymaker.testdata.DummyRulesClone;
import org.junit.jupiter.api.Test;

class GenFactoryRulesTests {

    @Test
    void namedRulesAppliedOnlyForSpecificClass() {
        final String value = "CORRECT";
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofClass(DummyRules.class)
                        .registerFieldNames(() -> () -> value, "code", "name"))
                .build();

        final DummyRules dummyRules = factory.build(DummyRules.class);
        assertEquals(value, dummyRules.getCode());
        assertEquals(value, dummyRules.getName());

        final DummyRulesClone dummyRulesClone = factory.build(DummyRulesClone.class);
        assertNotEquals(value, dummyRulesClone.getCode());
        assertNotEquals(value, dummyRulesClone.getName());
    }

    @Test
    void classRuleOverrideGlobalRule() {
        final String value = "CORRECT";
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofGlobal()
                        .registerFieldNames(() -> () -> "WRONG", "code", "name"))
                .addRule(GenRule.ofClass(DummyRules.class)
                        .registerFieldNames(() -> () -> value, "code", "name"))
                .build();

        final DummyRules dummyRules = factory.build(DummyRules.class);
        assertEquals(value, dummyRules.getCode());
        assertEquals(value, dummyRules.getName());

        final DummyRulesClone dummyRulesClone = factory.build(DummyRulesClone.class);
        assertEquals("WRONG", dummyRulesClone.getCode());
        assertEquals("WRONG", dummyRulesClone.getName());
    }

    @Test
    void globalRuleIfClassNotFound() {
        final String value = "CORRECT";
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofGlobal()
                        .registerFieldNames(() -> () -> value, "code", "name"))
                .build();

        final DummyRules dummyRules = factory.build(DummyRules.class);
        assertEquals(value, dummyRules.getCode());
        assertEquals(value, dummyRules.getName());
    }

    @Test
    void globalRuleIfClassNotSatisfied() {
        final String value = "CORRECT";
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofGlobal()
                        .registerFieldNames(() -> () -> value, "code", "name"))
                .addRule(GenRule.ofClass(DummyRules.class)
                        .registerFieldNames(() -> () -> 11111, "number"))
                .build();

        final DummyRules dummyRules = factory.build(DummyRules.class);
        assertEquals(value, dummyRules.getCode());
        assertEquals(value, dummyRules.getName());
        assertEquals(11111, dummyRules.getNumber());
    }

    @Test
    void classTypeFieldRuleAppliedForGenericFieldType() {
        final String value = "CORRECT";
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofClass(DummyRules.class)
                        .registerFieldType(() -> () -> value, String.class))
                .build();

        final DummyRules dummyRules = factory.build(DummyRules.class);
        assertEquals(value, dummyRules.getCode());
        assertEquals(value, dummyRules.getName());
        for (String email : dummyRules.getEmails()) {
            assertEquals(value, email);
        }
    }

    @Test
    void globalTypeFieldRuleAppliedForGenericFieldType() {
        final String value = "CORRECT";
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofGlobal()
                        .registerFieldType(() -> () -> value, String.class))
                .build();

        final DummyRules dummyRules = factory.build(DummyRules.class);
        assertEquals(value, dummyRules.getCode());
        assertEquals(value, dummyRules.getName());
        for (String email : dummyRules.getEmails()) {
            assertEquals(value, email);
        }
    }
}
