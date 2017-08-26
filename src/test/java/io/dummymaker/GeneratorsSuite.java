package io.dummymaker;

import io.dummymaker.bundle.BundleImplTest;
import io.dummymaker.generator.GeneratorPatternValidateTest;
import io.dummymaker.generator.UniqueGeneratorsTest;
import org.junit.runners.Suite;

/**
 * Created by GoodforGod on 20.08.2017.
 */
@Suite.SuiteClasses({
        BundleImplTest.class,
        UniqueGeneratorsTest.class,
        GeneratorPatternValidateTest.class
})
public class GeneratorsSuite {

}
