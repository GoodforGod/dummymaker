package io.dummymaker;

import io.dummymaker.bundle.BundleImplTest;
import io.dummymaker.generator.GeneratorPatternValidateTest;
import io.dummymaker.generator.UniqueGeneratorsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
@RunWith(Suite.class)
@SuiteClasses({
        BundleImplTest.class,
        UniqueGeneratorsTest.class,
        GeneratorPatternValidateTest.class
})
class GeneratorsSuite {

}
