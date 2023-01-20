package io.dummymaker.factory;

import io.dummymaker.model.Dummy;
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
                .rule(GenRule.auto(Dummy.class)
                        .ignore("city"))
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
                .rule(GenRule.global(1)
                        .ignore("city"))
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
