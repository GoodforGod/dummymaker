package io.dummymaker.factory;

import io.dummymaker.model.DummyAutoNamed;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 12.10.2019
 */
class DummyAutoNamedTests extends Assertions {

    @Test
    void allNamedFieldsCorrect() {
        final GenFactory factory = GenFactory.builder().rule(GenRule.auto(DummyAutoNamed.class)).build();
        final DummyAutoNamed build = factory.build(DummyAutoNamed.class);

        assertNotNull(build);
        assertNotNull(build.getId());
        final UUID uuid = UUID.fromString(build.getId());
        assertNotNull(uuid);
        assertNotNull(build.getName());
        assertNotNull(build.getSurname());
        assertNotNull(build.getCode());

        assertNotNull(build.getSurnames());
        assertFalse(build.getSurnames().isEmpty());

        assertNotNull(build.getBtcHexes());
        assertFalse(build.getBtcHexes().isEmpty());
    }
}
