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
                        .generateForNames("code", "name", () -> () -> value))
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
                        .generateForNames("code", "name", () -> () -> "WRONG"))
                .addRule(GenRule.ofClass(DummyRules.class)
                        .generateForNames("code", "name", () -> () -> value))
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
                        .generateForNames("code", "name", () -> () -> value))
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
                        .generateForNames("code", "name", () -> () -> value))
                .addRule(GenRule.ofClass(DummyRules.class)
                        .generateForNames("number", () -> () -> 11111))
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
                        .generateForTypes(String.class, () -> () -> value))
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
                        .generateForTypes(String.class, () -> () -> value))
                .build();

        final DummyRules dummyRules = factory.build(DummyRules.class);
        assertEquals(value, dummyRules.getCode());
        assertEquals(value, dummyRules.getName());
        for (String email : dummyRules.getEmails()) {
            assertEquals(value, email);
        }
    }
}
