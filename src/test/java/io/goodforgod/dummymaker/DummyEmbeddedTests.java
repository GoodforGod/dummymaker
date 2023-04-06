package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.testdata.DummyEmbedded;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @see DummyEmbedded
 * @since 19.10.2019
 */
class DummyEmbeddedTests extends Assertions {

    @Test
    void embeddedFieldsFilled() {
        final GenFactory factory = GenFactory.build();
        final DummyEmbedded build = factory.build(DummyEmbedded.class);

        assertNotNull(build.getId());
        assertNotNull(build.getName());
        assertNotNull(build.getChild());
        assertNotNull(build.getSimpleChild());
        assertNotNull(build.getEmbeddedList());
        assertFalse(build.getEmbeddedList().isEmpty());

        assertNotNull(build.getChild().getChildId());
        assertNotNull(build.getChild().getName());
        assertNotNull(build.getChild().getEmbedded());

        assertNotEquals(0, build.getSimpleChild().getNumber());
        assertNotNull(build.getSimpleChild().getSimpleName());
        assertNotNull(build.getSimpleChild().getIntoEmbedded());

        assertNotNull(build.getSimpleChild().getIntoEmbedded().getName());
        assertNotNull(build.getSimpleChild().getIntoEmbedded().getEmbedded());
        assertNotEquals(0, build.getSimpleChild().getIntoEmbedded().getNumber());

        assertNotNull(build.getSimpleChild().getIntoEmbedded().getEmbedded().getId());
        assertNotNull(build.getSimpleChild().getIntoEmbedded().getEmbedded().getName());
        assertNull(build.getSimpleChild().getIntoEmbedded().getEmbedded().getChild());
        assertNull(build.getSimpleChild().getIntoEmbedded().getEmbedded().getSimpleChild());
        assertNotNull(build.getSimpleChild().getIntoEmbedded().getEmbedded().getEmbeddedList());
        assertTrue(build.getSimpleChild().getIntoEmbedded().getEmbedded().getEmbeddedList().isEmpty());
    }
}
