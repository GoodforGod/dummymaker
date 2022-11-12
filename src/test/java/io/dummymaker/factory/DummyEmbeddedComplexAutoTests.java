package io.dummymaker.factory;

import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyEmbedded;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 11.10.2019
 */
public class DummyEmbeddedComplexAutoTests extends Assert {

    private final MainGenFactory factory = new MainGenFactory(GenRules.of(GenRule.auto(Dummy.class)));

    @Test
    public void listComplexAutoValid() {
        final DummyEmbedded build = factory.build(DummyEmbedded.class);
        final List<DummyEmbedded> list = build.getEmbeddedList();

        assertNotNull(list);
        assertFalse(list.isEmpty());

        for (DummyEmbedded embedded : list) {
            assertNotNull(embedded.getEmbeddedList());
            assertFalse(embedded.getEmbeddedList().isEmpty());
        }
    }
}
