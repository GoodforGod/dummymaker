package io.dummymaker.export.validation;

import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class SqlValidation {

    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummy[1].matches("\\t" + NAME.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummy[2].matches("\\t" + GROUP.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummy[3].matches("\\t" + NUM.getExportFieldName() + "\\tINT,"));
        assertTrue(dummy[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[5].matches("\\);"));

        assertTrue(dummy[6].matches(""));

        assertTrue(dummy[7].matches("INSERT INTO dummy \\(" + NAME.getExportFieldName() + ", " + GROUP.getExportFieldName() + ", " + NUM.getExportFieldName() + "\\) VALUES"));
        assertTrue(dummy[8].matches("\\('[a-zA-Z]+', '[0-9]+', null\\);"));
    }

    public void isTwoDummiesValid(String[] dummies) {
        isTwoDummiesValidWithNamingStrategy(dummies, PresetStrategies.DEFAULT.getStrategy());
    }

    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, IStrategy strategy) {
        final String expectedNameField = strategy.toStrategy(NAME.getExportFieldName());
        final String expectedGroupField = GROUP.getExportFieldName();
        final String expectedNumField = strategy.toStrategy(NUM.getExportFieldName());

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummies[1].matches("\\t" + expectedNameField + "\\tVARCHAR,"));
        assertTrue(dummies[2].matches("\\t" + expectedGroupField + "\\tVARCHAR,"));
        assertTrue(dummies[3].matches("\\t" + expectedNumField + "\\tINT,"));
        assertTrue(dummies[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[5].matches("\\);"));

        assertTrue(dummies[6].matches(""));

        assertTrue(dummies[7].matches("INSERT INTO dummy \\(" + expectedNameField + ", " + expectedGroupField + ", " + expectedNumField + "\\) VALUES"));
        assertTrue(dummies[8].matches("\\('[a-zA-Z]+', '[0-9]+', [0-9]+\\),"));
        assertTrue(dummies[9].matches("\\('[a-zA-Z]+', '[0-9]+', [0-9]+\\);"));
    }
}
