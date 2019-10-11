package io.dummymaker.factory;

import io.dummymaker.beta.model.Dummy;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Assert;
import org.junit.Test;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 11.10.2019
 */
public class DummyStaticFieldsTests extends Assert {

    private final GenFactory factory = new GenFactory(GenRules.of(GenRule.auto(Dummy.class)));

    @Test
    public void noStaticFieldsFilled() {
        final Dummy build = factory.build(Dummy.class);
        assertEquals(1, build.getIgnoredInt());
        assertEquals(2, build.getIgnoredLong());
        assertEquals(10, build.getIgnoredAuto());
    }
}
