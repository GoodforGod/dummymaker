package io.dummymaker.beta;

import io.dummymaker.beta.model.DummyAutoComplex;
import io.dummymaker.data.DummyAuto;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.generator.simple.number.ByteGenerator;
import io.dummymaker.generator.simple.number.ShortGenerator;
import io.dummymaker.model.GenRule;
import io.dummymaker.model.GenRules;
import org.junit.Test;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 01.08.2019
 */
public class Tester {

    @Test
    public void test() {
        GenRules rules = GenRules.of(
                GenRule.of(DummyAuto.class)
                        .add(ByteGenerator.class, "aLong")
                        .add(ShortGenerator.class, int.class)
        );

        GenFactory factory = new GenFactory(rules);
        DummyAuto build = factory.build(DummyAuto.class);
        System.out.println(build);
    }

    @Test
    public void testEmbedded() {
        GenFactory factory = new GenFactory();
        DummyAutoComplex build = factory.build(DummyAutoComplex.class);
        System.out.println(build);
    }
}
