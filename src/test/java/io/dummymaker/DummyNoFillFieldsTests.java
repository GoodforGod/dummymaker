package io.dummymaker;

import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.testdata.DummyNoFillFields;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
class DummyNoFillFieldsTests {

    @Test
    void allFieldsNull() {
        final GenFactory factory = GenFactory.builder().autoByDefault(false).build();
        final String group = "300";
        final DummyNoFillFields dummy = new DummyNoFillFields();
        dummy.setGroup(group);

        final DummyNoFillFields filled = factory.build(() -> dummy);
        assertNotNull(filled);
        assertNull(filled.getCity());
        assertNull(filled.getName());
        assertNull(filled.getNum());
        assertEquals(group, filled.getGroup());
    }
}
