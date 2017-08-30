package io.dummymaker;

import io.dummymaker.export.*;
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
        CsvExportTest.class,
        JsonExportTest.class,
        SqlExportTest.class,
        XmlExportTest.class,
        SpecialAnnotationExportTest.class
})
public class ExportAsFileSuite {

}
