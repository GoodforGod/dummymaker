package io.dummymaker;

import io.dummymaker.factory.GenFactoryImplTest;
import io.dummymaker.factory.GenPopulateImplTest;
import org.junit.runners.Suite;

/**
 * Created by GoodforGod on 20.08.2017.
 */
@Suite.SuiteClasses({
        GeneratorsSuite.class,
        GenPopulateImplTest.class,
        GenFactoryImplTest.class
})
public class PopulateSuite {

}
