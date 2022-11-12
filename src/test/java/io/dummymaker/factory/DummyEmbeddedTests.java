package io.dummymaker.factory;

import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.DummyEmbedded;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author GoodforGod
 * @see io.dummymaker.model.DummyEmbedded
 * @since 19.10.2019
 */
public class DummyEmbeddedTests extends Assert {

    private final MainGenFactory factory = new MainGenFactory();

    @Test
    public void embeddedFieldsFilled() {
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
