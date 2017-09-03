package io.dummymaker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.SuiteClasses;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
@RunWith(Suite.class)
@SuiteClasses({
        FactorySuite.class,
        ExportAsStringSuite.class
})
class ExportSuite {

}
