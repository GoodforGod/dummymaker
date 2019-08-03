package io.dummymaker.beta;

import io.dummymaker.container.GenRule;
import io.dummymaker.container.GenRules;
import io.dummymaker.data.DummyAuto;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;
import io.dummymaker.generator.simple.impl.number.ShortGenerator;
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
                        .add("aLong", ByteGenerator.class)
                        .add(int.class, ShortGenerator.class)
        );


    }
}
