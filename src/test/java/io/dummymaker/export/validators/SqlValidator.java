package io.dummymaker.export.validators;

import io.dummymaker.data.DummyTimestamp.FieldNames;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class SqlValidator implements IValidator {

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummy[1].matches("\\t" + GROUP.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummy[2].matches("\\t" + NUM.getExportFieldName() + "\\tINT,"));
        assertTrue(dummy[3].matches("\\t" + NAME.getExportFieldName() + "\\tVARCHAR,"));
        assertTrue(dummy[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[5].matches("\\);"));

        assertTrue(dummy[6].matches(""));

        assertTrue(dummy[7].matches("INSERT INTO dummy \\(" + GROUP.getExportFieldName() + ", " + NUM.getExportFieldName() + ", " + NAME.getExportFieldName() + "\\) VALUES"));
        assertTrue(dummy[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        isTwoDummiesValidWithNamingStrategy(dummies, Cases.DEFAULT.value());
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, ICase strategy) {
        final String expectedNameField = strategy.format(NAME.getExportFieldName());
        final String expectedGroupField = GROUP.getExportFieldName();
        final String expectedNumField = strategy.format(NUM.getExportFieldName());

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummies[1].matches("\\t" + expectedGroupField + "\\tVARCHAR,"));
        assertTrue(dummies[2].matches("\\t" + expectedNumField + "\\tINT,"));
        assertTrue(dummies[3].matches("\\t" + expectedNameField + "\\tVARCHAR,"));
        assertTrue(dummies[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[5].matches("\\);"));

        assertTrue(dummies[6].matches(""));

        assertTrue(dummies[7].matches("INSERT INTO dummy \\(" + expectedGroupField + ", " + expectedNumField + ", " + expectedNameField + "\\) VALUES"));
        assertTrue(dummies[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\),"));
        assertTrue(dummies[9].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    public void isTwoTimestampedDummiesValidWithNamingStrategy(String[] dummies, ICase strategy) {
        final String expectedDateField = strategy.format(FieldNames.LOCAL_DATE.getName());
        final String expectedTimeField = strategy.format(FieldNames.LOCAL_TIME.getName());
        final String expectedDateTimeField = strategy.format(FieldNames.LOCAL_DATETIME.getName());
        final String expectedTimestampField = strategy.format(FieldNames.TIMESTAMP.getName());
        final String expectedDateOldField = strategy.format(FieldNames.DATE.getName());

        final String timestampPattern = "[1-9][0-9]{3}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{0,3}";

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.format("TimeDummyClass") + "\\("));
        assertTrue(dummies[1].matches("\\t" + expectedTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummies[2].matches("\\t" + expectedDateField + "\\tTIMESTAMP,"));
        assertTrue(dummies[3].matches("\\t" + expectedDateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummies[4].matches("\\t" + expectedTimestampField + "\\tTIMESTAMP,"));
        assertTrue(dummies[5].matches("\\t" + expectedDateOldField + "\\tTIMESTAMP,"));
        assertTrue(dummies[6].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[7].matches("\\);"));

        assertTrue(dummies[8].matches(""));

        assertTrue(dummies[9].matches("INSERT INTO " + strategy.format("TimeDummyClass") + " \\(" + strategy.format(expectedTimeField) + ", "
                + expectedDateField + ", " + expectedDateTimeField + ", "
                + expectedTimestampField + ", " + expectedDateOldField + "\\) VALUES"));
        assertTrue(dummies[10].matches("\\('" + timestampPattern + "', " +
                "'" + timestampPattern + "', " +
                "'" + timestampPattern + "', " +
                "'" + timestampPattern + "', " +
                "'" + timestampPattern + "'\\),"));
        assertTrue(dummies[11].matches("\\('" + timestampPattern + "', " +
                "'" + timestampPattern + "', " +
                "'" + timestampPattern + "', " +
                "'" + timestampPattern + "', " +
                "'" + timestampPattern + "'\\);"));
    }
}
