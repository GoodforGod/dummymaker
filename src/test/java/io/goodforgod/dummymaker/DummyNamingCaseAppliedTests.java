package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.generator.simple.string.NameGenerator;
import io.goodforgod.dummymaker.testdata.DummySimple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DummyNamingCaseAppliedTests extends Assertions {

    @Test
    void allValuesInUpperCase() {
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofClass(DummySimple.class)
                        .generateForNames("name", "female", NameGenerator::new))
                .applyCase(NamingCases.UPPER_CASE)
                .build();

        final DummySimple build = factory.build(DummySimple.class);
        assertNotNull(build);

        for (char c : build.getName().toCharArray()) {
            assertTrue(Character.isUpperCase(c));
        }

        for (char c : build.getFemale().toCharArray()) {
            assertTrue(Character.isUpperCase(c));
        }
    }

    @Test
    void allValuesInLowerCase() {
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofClass(DummySimple.class)
                        .generateForNames("name", "female", NameGenerator::new))
                .applyCase(NamingCases.LOWER_CASE)
                .build();

        final DummySimple build = factory.build(DummySimple.class);
        assertNotNull(build);

        for (char c : build.getName().toCharArray()) {
            assertTrue(Character.isLowerCase(c));
        }

        for (char c : build.getFemale().toCharArray()) {
            assertTrue(Character.isLowerCase(c));
        }
    }
}
