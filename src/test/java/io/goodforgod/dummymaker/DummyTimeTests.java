package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.testdata.DummyTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @see DummyTime
 * @since 20.10.2019
 */
class DummyTimeTests extends Assertions {

    @Test
    void validFieldsFilled() {
        final GenFactory factory = GenFactory.build();
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
