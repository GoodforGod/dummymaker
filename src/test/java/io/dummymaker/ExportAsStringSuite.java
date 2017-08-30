package io.dummymaker;

import io.dummymaker.export.asstring.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 31.08.2017
 */
@RunWith(Suite.class)
@SuiteClasses({
        CsvExportAsStringTest.class,
        JsonExportAsStringTest.class,
        SqlExportAsStringTest.class,
        XmlExportAsStringTest.class,
        SpecialAnnotationExportAsStringTest.class
})
class ExportAsStringSuite {

}
