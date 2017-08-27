package io.dummymaker;

import io.dummymaker.export.ExportAsStringImplTest;
import io.dummymaker.export.ExportImplTest;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * Created by GoodforGod on 20.08.2017.
 */
@SuiteClasses({
        FactorySuite.class,
        ExportImplTest.class,
        ExportAsStringImplTest.class
})
class ExportSuite {

}
