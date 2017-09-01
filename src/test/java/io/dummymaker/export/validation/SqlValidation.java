package io.dummymaker.export.validation;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class SqlValidation implements IValidation {

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummy[1].matches("\\t" + NAME.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummy[2].matches("\\t" + GROUP.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummy[3].matches("\\t" + NUM.getExportFieldName() + "\\tINT"));
        assertTrue(dummy[4].matches("\\);"));

        assertTrue(dummy[5].matches(""));

        assertTrue(dummy[6].matches("INSERT INTO dummy \\(" + NAME.getExportFieldName() + ", " + GROUP.getExportFieldName() + ", " + NUM.getExportFieldName() + "\\) VALUES "));
        assertTrue(dummy[7].matches("\\('[a-zA-Z]+', '[0-9]+', null\\);"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummies[1].matches("\\t" + NAME.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummies[2].matches("\\t" + GROUP.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummies[3].matches("\\t" + NUM.getExportFieldName() + "\\tINT"));
        assertTrue(dummies[4].matches("\\);"));

        assertTrue(dummies[5].matches(""));

        assertTrue(dummies[6].matches("INSERT INTO dummy \\(" + NAME.getExportFieldName() + ", " + GROUP.getExportFieldName() + ", " + NUM.getExportFieldName() + "\\) VALUES "));
        assertTrue(dummies[7].matches("\\('[a-zA-Z]+', '[0-9]+', [0-9]+\\),"));
        assertTrue(dummies[8].matches("\\('[a-zA-Z]+', '[0-9]+', [0-9]+\\);"));
    }
}
