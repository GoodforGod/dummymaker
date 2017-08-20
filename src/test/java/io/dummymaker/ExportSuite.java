package io.dummymaker;

import io.dummymaker.export.ExportAsStringImplTest;
import io.dummymaker.export.ExportImplTest;
import org.junit.runners.Suite;

/**
 * Created by GoodforGod on 20.08.2017.
 */
@Suite.SuiteClasses({
        PopulateSuite.class,
        ExportImplTest.class,
        ExportAsStringImplTest.class
})
public class ExportSuite {

}
