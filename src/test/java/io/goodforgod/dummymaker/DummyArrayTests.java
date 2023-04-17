package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.testdata.DummyArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 18.10.2019
 */
class DummyArrayTests extends Assertions {

    @Test
    void arrayFieldsFilled() {
        final GenFactory factory = GenFactory.builder()
                .autoByDefault(false)
                .build();
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
    void arrayFieldsFilledWhenAuto() {
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofClass(DummyArray.class))
                .build();
        final DummyArray build = factory.build(DummyArray.class);

        assertNotEquals(0, build.getByteSimple().length);
        assertNotEquals(0, build.getByteDouble().length);

        assertEquals(15, build.getShortSimple().length);
        assertNotEquals(0, build.getShortDouble().length);

        assertNotEquals(0, build.getIntSimple().length);
        assertNotEquals(0, build.getIntDouble().length);

        assertEquals(15, build.getLongSimple().length);
        assertNotEquals(0, build.getLongDouble().length);

        assertNotEquals(0, build.getFloatSimple().length);
        assertNotEquals(0, build.getFloatDouble().length);

        assertNotEquals(0, build.getByteObjSimple().length);
        assertNotEquals(0, build.getByteObjDouble().length);

        assertNotEquals(0, build.getShortObjSimple().length);
        assertNotEquals(0, build.getShortObjDouble().length);

        assertNotEquals(0, build.getIntegerObjSimple().length);
        assertEquals(15, build.getIntegerObjDouble().length);

        assertNotEquals(0, build.getLongObjSimple().length);
        assertNotEquals(0, build.getLongObjDouble().length);

        assertNotEquals(0, build.getFloatObjSimple().length);
        assertNotEquals(0, build.getFloatObjDouble().length);

        assertNotEquals(0, build.getDoubleObjSimple().length);
        assertEquals(15, build.getDoubleObjDouble().length);

        assertNotEquals(0, build.getDummies().length);
    }
}
