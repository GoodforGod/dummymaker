package io.dummymaker.factory;

import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.DummyAutoNamed;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Assert;
import org.junit.Test;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class DummyAutoNamedTests extends Assert {

    private final GenFactory factory = new GenFactory(GenRules.of(GenRule.auto(DummyAutoNamed.class)));

    @Test
    public void allNamedFieldsCorrect() {
        final DummyAutoNamed build = factory.build(DummyAutoNamed.class);

        assertNotNull(build);
        assertNotNull(build.getName());
        assertNotNull(build.getSurname());
        assertNotNull(build.getCode());

        assertNotNull(build.getSurnames());
        assertFalse(build.getSurnames().isEmpty());

        assertNotNull(build.getBtcHexes());
        assertFalse(build.getBtcHexes().isEmpty());
    }
}
