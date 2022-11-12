package io.dummymaker.factory;

import static org.junit.Assert.*;

import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.DummyCollection;
import org.junit.Test;

/**
 * Dummy collection tests
 *
 * @author GoodforGod
 * @since 19.10.2019
 */
public class DummyCollectionTests {

    private final MainGenFactory factory = new MainGenFactory();

    @Test
    public void produceWithCollectionFields() {
        final DummyCollection dummy = factory.build(DummyCollection.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getObjects());
        assertNotNull(dummy.getStrings());
        assertNotNull(dummy.getMap());

        assertNotNull(dummy.getObjectsFix());
        assertNotNull(dummy.getStringsFix());
        assertNotNull(dummy.getMapFix());

        assertFalse(dummy.getObjects().isEmpty());
        assertFalse(dummy.getStrings().isEmpty());
        assertFalse(dummy.getMap().isEmpty());

        assertFalse(dummy.getObjectsFix().isEmpty());
        assertFalse(dummy.getStringsFix().isEmpty());
        assertFalse(dummy.getMapFix().isEmpty());

        assertEquals(4, dummy.getObjectsFix().size());
        assertEquals(5, dummy.getStringsFix().size());
        assertEquals(3, dummy.getMapFix().size());
    }
}
