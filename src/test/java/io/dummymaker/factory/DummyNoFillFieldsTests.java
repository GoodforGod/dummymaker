package io.dummymaker.factory;


import static org.junit.Assert.*;

import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.DummyNoFillFields;
import org.junit.Test;


/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class DummyNoFillFieldsTests {

    private final GenFactory factory = new GenFactory();

    @Test
    public void allFieldsNull() {
        final String group = "300";
        final DummyNoFillFields dummy = new DummyNoFillFields();
        dummy.setGroup(group);

        final DummyNoFillFields filled = factory.fill(dummy);
        assertNotNull(filled);
        assertNull(filled.getCity());
        assertNull(filled.getName());
        assertNull(filled.getNum());
        assertEquals(group, filled.getGroup());
    }
}
