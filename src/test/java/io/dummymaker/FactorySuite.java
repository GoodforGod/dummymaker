package io.dummymaker;

import io.dummymaker.factory.ProduceFactoryTest;
import io.dummymaker.scan.ScannerImplTest;
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
        GeneratorsSuite.class,
        ScannerImplTest.class,
        ProduceFactoryTest.class
})
class FactorySuite {

}
