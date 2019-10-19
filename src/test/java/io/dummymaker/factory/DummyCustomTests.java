package io.dummymaker.factory;

import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.DummyCustom;
import org.junit.Assert;
import org.junit.Test;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class DummyCustomTests extends Assert {

    private final GenFactory factory = new GenFactory();

    @Test
    public void allCustomFieldsCorrect() throws InterruptedException {
        final DummyCustom build = factory.build(DummyCustom.class);

        assertNotNull(build.getNnn());
        assertNotNull(build.getType());
        assertNotNull(build.getQueue());
        assertFalse(build.getQueue().isEmpty());

        final Short nnn = Short.valueOf(build.getNnn());
        final Boolean type = Boolean.valueOf(build.getType());
        assertNotNull(nnn);
        assertNotNull(type);

        final String value = build.getQueue().take();
        assertNotNull(value);
        assertNotNull(Float.valueOf(value));
    }
}
