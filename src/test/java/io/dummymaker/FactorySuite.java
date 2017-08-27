package io.dummymaker;

import io.dummymaker.factory.GenFactoriesImplTest;
import io.dummymaker.scan.ScannerImplTest;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * Created by GoodforGod on 20.08.2017.
 */
@SuiteClasses({
        GeneratorsSuite.class,
        ScannerImplTest.class,
        GenFactoriesImplTest.class
})
class FactorySuite {

}
