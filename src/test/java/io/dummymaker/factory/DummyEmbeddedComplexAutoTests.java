package io.dummymaker.factory;

import io.dummymaker.model.DummyEmbedded;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 11.10.2019
 */
class DummyEmbeddedComplexAutoTests extends Assertions {

    @Test
    void listComplexAutoValid() {
        final GenFactory factory = GenFactory.build();
        final DummyEmbedded build = factory.build(DummyEmbedded.class);

        assertNotNull(build.getEmbeddedList());
        assertFalse(build.getEmbeddedList().isEmpty());

        for (DummyEmbedded embedded : build.getEmbeddedList()) {
            assertNotNull(embedded.getEmbeddedList());
            assertFalse(embedded.getEmbeddedList().isEmpty());
        }
    }
}
