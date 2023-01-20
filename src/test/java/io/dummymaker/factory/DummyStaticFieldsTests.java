package io.dummymaker.factory;

import io.dummymaker.model.Dummy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 11.10.2019
 */
class DummyStaticFieldsTests extends Assertions {

    @Test
    void noStaticFieldsFilled() {
        final GenFactory factory = GenFactory.builder()
                .rule(GenRule.auto(Dummy.class))
                .build();

        final Dummy build = factory.build(Dummy.class);
        assertEquals(1, build.getIgnoredInt());
        assertEquals(2, build.getIgnoredLong());
        assertEquals(10, build.getIgnoredAuto());
    }
}
