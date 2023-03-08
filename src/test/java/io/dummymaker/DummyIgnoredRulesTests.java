package io.dummymaker;

import io.dummymaker.testdata.Dummy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Ignore rules tests
 *
 * @author GoodforGod
 * @since 15.10.2019
 */
class DummyIgnoredRulesTests extends Assertions {

    @Test
    void cityIsNotPresentForAuto() {
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofClass(Dummy.class)
                        .excludeFields("city"))
                .build();

        final Dummy build = factory.build(Dummy.class);
        assertNotNull(build.getBigd());
        assertNotNull(build.getGroup());
        assertNotNull(build.getLng());
        assertNotNull(build.getNum());
        assertNotNull(build.getName());

        assertNull(build.getCity());
    }

    @Test
    void cityIsNotPresentForGlobal() {
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofGlobal()
                        .excludeFields("city"))
                .build();

        final Dummy build = factory.build(Dummy.class);
        assertNotNull(build.getBigd());
        assertNotNull(build.getGroup());
        assertNotNull(build.getLng());
        assertNotNull(build.getNum());
        assertNotNull(build.getName());

        assertNull(build.getCity());
    }
}
