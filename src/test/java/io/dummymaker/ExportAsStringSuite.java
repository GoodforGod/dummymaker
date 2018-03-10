package io.dummymaker;

import io.dummymaker.export.asstring.CsvExportAsStringTest;
import io.dummymaker.export.asstring.JsonExportAsStringTest;
import io.dummymaker.export.asstring.SqlExportAsStringTest;
import io.dummymaker.export.asstring.XmlExportAsStringTest;
import io.dummymaker.util.NameStrategyTest;
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
        NameStrategyTest.class,
        CsvExportAsStringTest.class,
        JsonExportAsStringTest.class,
        SqlExportAsStringTest.class,
        XmlExportAsStringTest.class
})
class ExportAsStringSuite {

}
