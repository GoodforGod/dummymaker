package io.dummymaker.export.validators;

import io.dummymaker.beta.model.DummyTime;
import io.dummymaker.beta.model.DummyTime.Fields;
import io.dummymaker.data.DummyTimestamp.FieldNames;
import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;

import static io.dummymaker.beta.model.Dummy.DummyFields.*;
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
        assertTrue(dummy[1].matches("\\t" + GROUP.exportName() + "\\tVARCHAR,"));
        assertTrue(dummy[2].matches("\\t" + NUM.exportName() + "\\tINT,"));
        assertTrue(dummy[3].matches("\\t" + NAME.exportName() + "\\tVARCHAR,"));
        assertTrue(dummy[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[5].matches("\\);"));

        assertTrue(dummy[6].matches(""));

        assertTrue(dummy[7].matches("INSERT INTO dummy \\(" + GROUP.exportName() + ", " + NUM.exportName() + ", " + NAME.exportName() + "\\) VALUES"));
        assertTrue(dummy[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    @Override
    public void isSingleAutoDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummyauto\\("));
        assertTrue(dummy[1].matches("\\taLong\\tBIGINT,"));
        assertTrue(dummy[2].matches("\\tanInt\\tINT,"));
        assertTrue(dummy[3].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[4].matches("\\);"));

        assertTrue(dummy[5].matches(""));

        assertTrue(dummy[6].matches("INSERT INTO dummyauto \\(aLong, anInt\\) VALUES"));
        assertTrue(dummy[7].matches("\\(-?[0-9]+, -?[0-9]+\\);"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        isTwoDummiesValidWithNamingStrategy(dummies, Cases.DEFAULT.value());
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, ICase strategy) {
        final String expectedNameField = strategy.format(NAME.exportName());
        final String expectedGroupField = GROUP.exportName();
        final String expectedNumField = strategy.format(NUM.exportName());

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
        assertTrue(dummies[5].matches("\\t" + expectedDateOldField + "\\tBIGINT,"));
        assertTrue(dummies[6].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[7].matches("\\);"));

        assertTrue(dummies[8].matches(""));

        assertTrue(dummies[9].matches("INSERT INTO " + strategy.format("TimeDummyClass") + " \\(" + strategy.format(expectedTimeField) + ", "
                + expectedDateField + ", " + expectedDateTimeField + ", "
                + expectedTimestampField + ", " + expectedDateOldField + "\\) VALUES"));
        assertTrue(dummies[10].matches("\\('" + timestampPattern + "', "
                + "'" + timestampPattern + "', "
                + "'" + timestampPattern + "', "
                + "'" + timestampPattern + "', "
                + "[0-9]+" + "\\),"));
        assertTrue(dummies[11].matches("\\('" + timestampPattern + "', "
                + "'" + timestampPattern + "', "
                + "'" + timestampPattern + "', "
                + "'" + timestampPattern + "', "
                + "[0-9]+" + "\\);"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummytime\\("));
        assertTrue(dummy[1].matches("\\t" + Fields.LOCAL_TIME.getName() + "\\tTIMESTAMP,"));
        assertTrue(dummy[2].matches("\\t" + Fields.LOCAL_DATE.getName() + "\\tTIMESTAMP,"));
        assertTrue(dummy[3].matches("\\t" + Fields.LOCAL_DATETIME.getName() + "\\tTIMESTAMP,"));
        assertTrue(dummy[4].matches("\\t" + Fields.TIMESTAMP.getName() + "\\tTIMESTAMP,"));
        assertTrue(dummy[5].matches("\\t" + Fields.DATE.getName() + "\\tBIGINT,"));
        assertTrue(dummy[6].matches("\\t" + Fields.DATE_COVERAGE.getName() + "\\tBIGINT,"));
        assertTrue(dummy[7].matches("\\t" + Fields.LOCAL_DATETIME_STRING.getName() + "\\tVARCHAR,"));
        assertTrue(dummy[8].matches("\\t" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\\tVARCHAR,"));
        assertTrue(dummy[9].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[10].matches("\\);"));

        assertTrue(dummy[11].matches(""));

        assertTrue(dummy[12].matches("INSERT INTO dummytime \\(" + Fields.LOCAL_TIME.getName()
                + ", " + Fields.LOCAL_DATE.getName() + ", " + Fields.LOCAL_DATETIME.getName()
                + ", " + Fields.TIMESTAMP.getName()
                + ", " + Fields.DATE.getName() + ", " + Fields.DATE_COVERAGE.getName()
                + ", " + Fields.LOCAL_DATETIME_STRING.getName() + ", " + Fields.LOCAL_DATETIME_OBJECT.getName()
                + "\\) VALUES"));

        assertTrue(dummy[13].matches("\\(" +
                "'" + DummyTime.Patterns.TIMESTAMP.getPattern() + "'"
                + ", '" + DummyTime.Patterns.TIMESTAMP.getPattern() + "'"
                + ", '" + DummyTime.Patterns.TIMESTAMP.getPattern() + "'"
                + ", '" + DummyTime.Patterns.TIMESTAMP.getPattern() + "'"
                + ", " + "[0-9]+"
                + ", " + "[0-9]+"
                + ", '" + DummyTime.Patterns.LOCAL_DATETIME.getPattern() + "'"
                + ", " + DummyTime.Patterns.LOCAL_DATETIME.getPattern()
                + "\\);"));
    }
}
