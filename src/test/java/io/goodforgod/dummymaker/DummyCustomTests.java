package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.testdata.DummyCustom;
import java.util.concurrent.BlockingQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 12.10.2019
 */
class DummyCustomTests extends Assertions {

    @Test
    void allCustomFieldsCorrect() throws InterruptedException {
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofGlobal()
                        .generateForTypes(BlockingQueue.class, DummyCustom.QueueGenerator::new))
                .build();

        final DummyCustom build = factory.build(DummyCustom.class);

        assertNotNull(build.getInn());
        assertNotNull(build.getType());
        assertNotNull(build.getQueue());
        assertFalse(build.getQueue().isEmpty());

        final Short nnn = Short.valueOf(build.getInn());
        final Boolean type = Boolean.valueOf(build.getType());
        assertNotNull(nnn);
        assertNotNull(type);

        final String value = build.getQueue().take();
        assertNotNull(value);
        assertNotNull(Float.valueOf(value));
    }
}
