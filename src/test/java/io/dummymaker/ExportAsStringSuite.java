package io.dummymaker;

import io.dummymaker.export.asstring.*;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 31.08.2017
 */
@SuiteClasses({
        CsvExportAsStringTest.class,
        JsonExportAsStringTest.class,
        SqlExportAsStringTest.class,
        XmlExportAsStringTest.class,
        SpecialAnnotationExportAsStringTest.class
})
class ExportAsStringSuite {

}
