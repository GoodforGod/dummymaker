package io.dummymaker.factory;

import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.DummyArray;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Dummy array tests
 *
 * @author GoodforGod
 * @since 18.10.2019
 */
public class DummyArrayTests extends Assert {

    @Test
    public void arrayFieldsFilled() {
        final GenFactory factory = new GenFactory();
        final DummyArray build = factory.build(DummyArray.class);

        assertNull(build.getByteSimple());
        assertNull(build.getByteDouble());

        assertEquals(15, build.getShortSimple().length);
        assertNull(build.getShortDouble());

        assertNull(build.getIntSimple());
        assertNull(build.getIntDouble());

        assertEquals(15, build.getLongSimple().length);
        assertNull(build.getLongDouble());

        assertNull(build.getFloatSimple());
        assertNull(build.getFloatDouble());

        assertNull(build.getByteObjSimple());
        assertNull(build.getByteObjDouble());

        assertNull(build.getShortObjSimple());
        assertNull(build.getShortObjDouble());

        assertNull(build.getIntegerObjSimple());
        assertEquals(15, build.getIntegerObjDouble().length);

        assertNull(build.getLongObjSimple());
        assertNull(build.getLongObjDouble());

        assertNull(build.getFloatObjSimple());
        assertNull(build.getFloatObjDouble());

        assertNull(build.getDoubleObjSimple());
        assertEquals(15, build.getDoubleObjDouble().length);

        assertNull(build.getDummies());
    }

    @Test
    public void arrayFieldsFilledWhenAuto() {
        final GenFactory factory = new GenFactory(GenRules.of(GenRule.auto(DummyArray.class)));
        final DummyArray build = factory.build(DummyArray.class);

        assertNotEquals(0, build.getByteSimple().length);
        assertNotEquals(0, build.getByteDouble().length);
        assertTrue(build.getByteSimple()[0] < Byte.MAX_VALUE);

        assertEquals(15, build.getShortSimple().length);
        assertNotEquals(0, build.getShortDouble().length);
        assertTrue(build.getShortSimple()[0] < Short.MAX_VALUE);

        assertNotEquals(0, build.getIntSimple().length);
        assertNotEquals(0, build.getIntDouble().length);
        assertTrue(build.getIntSimple()[0] < Integer.MAX_VALUE);

        assertEquals(15, build.getLongSimple().length);
        assertNotEquals(0, build.getLongDouble().length);

        assertNotEquals(0, build.getFloatSimple().length);
        assertNotEquals(0, build.getFloatDouble().length);
        assertTrue(build.getFloatSimple()[0] < 10000.1);

        assertNotEquals(0, build.getByteObjSimple().length);
        assertNotEquals(0, build.getByteObjDouble().length);

        assertNotEquals(0, build.getShortObjSimple().length);
        assertNotEquals(0, build.getShortObjDouble().length);

        assertNotEquals(0, build.getIntegerObjSimple().length);
        assertEquals(15, build.getIntegerObjDouble().length);

        assertNotEquals(0, build.getLongObjSimple().length);
        assertNotEquals(0, build.getLongObjDouble().length);
        assertTrue(build.getLongObjSimple()[0] < Long.MAX_VALUE);

        assertNotEquals(0, build.getFloatObjSimple().length);
        assertNotEquals(0, build.getFloatObjDouble().length);

        assertNotEquals(0, build.getDoubleObjSimple().length);
        assertEquals(15, build.getDoubleObjDouble().length);

        assertNotEquals(0, build.getDummies().length);
    }

    @Test
    public void arrayDummyToJson() {
        final GenRules rules = GenRules.of(GenRule.auto(DummyArray.class));
        final GenFactory factory = new GenFactory(rules);
        final DummyArray dummyArray = factory.build(DummyArray.class);

        final Pattern patternSingleArray = Pattern.compile("\"floatSimple\":\\[-?[0-9]+\\.[0-9]+");
        final Pattern patternDoubleArray = Pattern.compile("\"byteDouble\":\\[\\[[\\-0-9.]");
        final String json = new JsonExporter(rules).convert(dummyArray);

        assertTrue(patternSingleArray.matcher(json).find());
        assertTrue(patternDoubleArray.matcher(json).find());
    }
}
