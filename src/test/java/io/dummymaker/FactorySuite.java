package io.dummymaker;

import io.dummymaker.factory.GenFactoriesImplTest;
import io.dummymaker.scan.ScannerImplTest;
import org.junit.runners.Suite;

/**
 * Created by GoodforGod on 20.08.2017.
 */
@Suite.SuiteClasses({
        GeneratorsSuite.class,
        ScannerImplTest.class,
        GenFactoriesImplTest.class,
        GenFactoriesImplTest.class
})
public class FactorySuite {

}
