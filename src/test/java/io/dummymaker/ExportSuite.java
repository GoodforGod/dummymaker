package io.dummymaker;

import io.dummymaker.export.ExportImplTest;
import io.dummymaker.export.asstring.JsonExportAsStringTest;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
@SuiteClasses({
        FactorySuite.class,
        ExportImplTest.class,
        JsonExportAsStringTest.class
})
class ExportSuite {

}
