package io.dummymaker.factory;

import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Assert;
import org.junit.Test;

/**
 * Ignore rules tests
 *
 * @author GoodforGod
 * @since 15.10.2019
 */
public class DummyIgnoredRulesTests extends Assert {

    private final MainGenFactory factory = new MainGenFactory(GenRules.of(
            GenRule.auto(Dummy.class).ignore("city")));

    private final MainGenFactory factoryGlobal = new MainGenFactory(GenRules.of(
            GenRule.global(1).ignore("city")));

    @Test
    public void cityIsNotPresentForAuto() {
        final Dummy build = factory.build(Dummy.class);
        assertNotNull(build.getBigd());
        assertNotNull(build.getGroup());
        assertNotNull(build.getLng());
        assertNotNull(build.getNum());
        assertNotNull(build.getName());

        assertNull(build.getCity());
    }

    @Test
    public void cityIsNotPresentForGlobal() {
        final Dummy build = factoryGlobal.build(Dummy.class);
        assertNotNull(build.getBigd());
        assertNotNull(build.getGroup());
        assertNotNull(build.getLng());
        assertNotNull(build.getNum());
        assertNotNull(build.getName());

        assertNull(build.getCity());
    }
}
