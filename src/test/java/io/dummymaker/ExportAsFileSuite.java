package io.dummymaker;

import io.dummymaker.export.*;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 31.08.2017
 */
@SuiteClasses({
        CsvExportTest.class,
        JsonExportTest.class,
        SqlExportTest.class,
        XmlExportTest.class,
        SpecialAnnotationExportTest.class
})
class ExportAsFileSuite {

}
