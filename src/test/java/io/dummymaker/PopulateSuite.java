package io.dummymaker;

import io.dummymaker.factory.GenPopulateImplTest;
import io.dummymaker.factory.GenProduceImplTest;
import io.dummymaker.scan.ScannerImplTest;
import org.junit.runners.Suite;

/**
 * Created by GoodforGod on 20.08.2017.
 */
@Suite.SuiteClasses({
        GeneratorsSuite.class,
        ScannerImplTest.class,
        GenPopulateImplTest.class,
        GenProduceImplTest.class
})
public class PopulateSuite {

}
