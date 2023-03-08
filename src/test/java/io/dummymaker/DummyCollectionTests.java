package io.dummymaker;

import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.testdata.DummyCollection;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 19.10.2019
 */
class DummyCollectionTests {

    @Test
    void produceWithCollectionFields() {
        final GenFactory factory = GenFactory.build();
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
