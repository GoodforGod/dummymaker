package io.dummymaker.factory;

import io.dummymaker.beta.model.DummyArray;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Assert;
import org.junit.Test;

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

        assertNotEquals(0, build.getShortSimple().length);
        assertNull(build.getShortDouble());

        assertNull(build.getIntSimple());
        assertNull(build.getIntDouble());

        assertNotEquals(0, build.getLongSimple().length);
        assertNull(build.getLongDouble());

        assertNull(build.getFloatSimple());
        assertNull(build.getFloatDouble());

        assertNull(build.getByteObjSimple());
        assertNull(build.getByteObjDouble());

        assertNull(build.getShortObjSimple());
        assertNull(build.getShortObjDouble());

        assertNull(build.getIntegerObjSimple());
        assertNotEquals(0, build.getIntegerObjDouble().length);

        assertNull(build.getLongObjSimple());
        assertNull(build.getLongObjDouble());

        assertNull(build.getFloatObjSimple());
        assertNull(build.getFloatObjDouble());

        assertNull(build.getDoubleObjSimple());
        assertNotEquals(0, build.getDoubleObjDouble().length);

        assertNull(build.getDummies());
    }

    @Test
    public void arrayFieldsFilledWhenAuto() {
        final GenFactory factory = new GenFactory(GenRules.of(GenRule.auto(DummyArray.class)));
        final DummyArray build = factory.build(DummyArray.class);

        assertNotEquals(0, build.getByteSimple().length);
        assertNotEquals(0, build.getByteDouble().length);

        assertNotEquals(0, build.getShortSimple().length);
        assertNotEquals(0, build.getShortDouble().length);

        assertNotEquals(0, build.getIntSimple().length);
        assertNotEquals(0, build.getIntDouble().length);

        assertNotEquals(0, build.getLongSimple().length);
        assertNotEquals(0, build.getLongDouble().length);

        assertNotEquals(0, build.getFloatSimple().length);
        assertNotEquals(0, build.getFloatDouble().length);

        assertNotEquals(0, build.getByteObjSimple().length);
        assertNotEquals(0, build.getByteObjDouble().length);

        assertNotEquals(0, build.getShortObjSimple().length);
        assertNotEquals(0, build.getShortObjDouble().length);

        assertNotEquals(0, build.getIntegerObjSimple().length);
        assertNotEquals(0, build.getIntegerObjDouble().length);

        assertNotEquals(0, build.getLongObjSimple().length);
        assertNotEquals(0, build.getLongObjDouble().length);

        assertNotEquals(0, build.getFloatObjSimple().length);
        assertNotEquals(0, build.getFloatObjDouble().length);

        assertNotEquals(0, build.getDoubleObjSimple().length);
        assertNotEquals(0, build.getDoubleObjDouble().length);

        assertNotEquals(0, build.getDummies().length);
    }
}
