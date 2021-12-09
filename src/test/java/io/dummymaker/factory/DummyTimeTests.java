package io.dummymaker.factory;


import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.DummyTime;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author GoodforGod
 * @see DummyTime
 * @since 20.10.2019
 */
public class DummyTimeTests extends Assert {

    @Test
    public void validFieldsFilled() {
        final GenFactory factory = new GenFactory();
        final DummyTime build = factory.build(DummyTime.class);

        assertNotNull(build.getDate());
        assertNotNull(build.getDateTime());
        assertNotNull(build.getDateOld());
        assertNotNull(build.getTime());
        assertNotNull(build.getTimestamp());
        assertNotNull(build.getDateTimeObject());
        assertNotNull(build.getDateTimeString());
        assertNotNull(build.getDateOld());
    }
}
