package io.dummymaker;

import io.dummymaker.bundle.BundleImplTest;
import io.dummymaker.generator.GeneratorPatternValidateTest;
import io.dummymaker.generator.UniqueGeneratorsTest;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
@SuiteClasses({
        BundleImplTest.class,
        UniqueGeneratorsTest.class,
        GeneratorPatternValidateTest.class
})
class GeneratorsSuite {

}
