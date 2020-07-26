package io.dummymaker.export.validators;

import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.export.cases.DefaultCase;
import io.dummymaker.model.DummyArray;
import io.dummymaker.model.DummyTimeFormatter;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static io.dummymaker.model.DummyTime.*;
import static io.dummymaker.model.DummyTime.Fields.*;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class SqlValidator implements IValidator {

    @Override
    public void isSingleDummyListValid(String[] dummy) {
        isSingleDummyValid(dummy);
    }

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummy[1].matches("\\t" + GROUP.exportName() + "\\tVARCHAR,"));
        assertTrue(dummy[2].matches("\\t" + NUM.exportName() + "\\tINT,"));
        assertTrue(dummy[3].matches("\\t" + NAME.exportName() + "\\tVARCHAR,"));
        assertTrue(dummy[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[5].matches("\\);"));

        assertTrue(dummy[6].matches("INSERT INTO dummy \\(" + GROUP.exportName() + ", " + NUM.exportName() + ", "
                + NAME.exportName() + "\\) VALUES"));
        assertTrue(dummy[7].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
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

        assertTrue(dummies[7].matches("INSERT INTO dummy \\(" + expectedGroupField + ", " + expectedNumField + ", "
                + expectedNameField + "\\) VALUES"));
        assertTrue(dummies[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\),"));
        assertTrue(dummies[9].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    public void isTwoDummiesArrayValidWithNamingStrategy(String[] dummies, ICase strategy) {
        final String className = strategy.format(DummyArray.class.getSimpleName());
        final String shortSimple = strategy.format("shortSimple");
        final String longSimple = strategy.format("longSimple");
        final String IntegerObjDouble = strategy.format("IntegerObjDouble");
        final String DoubleObjDouble = strategy.format("DoubleObjDouble");

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS " + className + "\\("));
        assertTrue(dummies[1].matches("\\t" + shortSimple + "\\tSMALLINT\\[],"));
        assertTrue(dummies[2].matches("\\t" + longSimple + "\\tBIGINT\\[],"));
        assertTrue(dummies[3].matches("\\t" + IntegerObjDouble + "\\tINT\\[]\\[],"));
        assertTrue(dummies[4].matches("\\t" + DoubleObjDouble + "\\tDOUBLE PRECISION\\[]\\[],"));
        assertTrue(dummies[5].matches("\\tPRIMARY KEY \\([a-zA-Z_]+\\)"));
        assertTrue(dummies[6].matches("\\);"));

        assertTrue(dummies[7].matches(""));

        assertTrue(dummies[8].matches("INSERT INTO " + className + " \\(" + shortSimple + ", " + longSimple + ", "
                + IntegerObjDouble + ", " + DoubleObjDouble + "\\) VALUES"));
    }

    public void isDummyTimeValidWithNamingStrategy(String[] dummies, ICase strategy) {
        final String timeField = strategy.format(LOCAL_TIME.getName());
        final String dateField = strategy.format(LOCAL_DATE.getName());
        final String dateTimeField = strategy.format(LOCAL_DATETIME.getName());
        final String timestampField = strategy.format(TIMESTAMP.getName());
        final String dateOldField = strategy.format(DATE.getName());
        final String dateOldCoverageField = strategy.format(DATE_COVERAGE.getName());
        final String dateTimeStringField = strategy.format(LOCAL_DATETIME_STRING.getName());
        final String dateTimeObjectField = strategy.format(LOCAL_DATETIME_OBJECT.getName());

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.format("TimeDummyClass") + "\\("));
        assertTrue(dummies[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummies[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummies[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummies[4].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummies[5].matches("\\t" + dateOldField + "\\tDATETIME,"));
        assertTrue(dummies[6].matches("\\t" + dateOldCoverageField + "\\tDATETIME,"));
        assertTrue(dummies[7].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummies[8].matches("\\t" + dateTimeObjectField + "\\tTIMESTAMP,"));
        assertTrue(dummies[9].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[10].matches("\\);"));

        assertTrue(dummies[11].matches(""));

        assertTrue(dummies[12].matches("INSERT INTO " + strategy.format("TimeDummyClass")
                + " \\("
                + timeField + ", "
                + dateField + ", "
                + dateTimeField + ", "
                + timestampField + ", "
                + dateOldField + ", "
                + dateOldCoverageField + ", "
                + dateTimeStringField + ", "
                + dateTimeObjectField
                + "\\) VALUES"));

        assertTrue(dummies[13].matches("\\('"
                + ISO_TIME_PATTERN + "', '"
                + ISO_DATE_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "'\\),"));

        assertTrue(dummies[14].matches("\\('"
                + ISO_TIME_PATTERN + "', '"
                + ISO_DATE_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "'\\);"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        final ICase strategy = new DefaultCase();

        final String timeField = strategy.format(LOCAL_TIME.getName());
        final String dateField = strategy.format(LOCAL_DATE.getName());
        final String dateTimeField = strategy.format(LOCAL_DATETIME.getName());
        final String timestampField = strategy.format(TIMESTAMP.getName());
        final String dateOldField = strategy.format(DATE.getName());
        final String dateOldCoverageField = strategy.format(DATE_COVERAGE.getName());
        final String dateTimeStringField = strategy.format(LOCAL_DATETIME_STRING.getName());
        final String dateTimeObjectField = strategy.format(LOCAL_DATETIME_OBJECT.getName());

        final String timePattern = "[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{0,3})?";
        final String datePattern = "[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}";
        final String timestampPattern = datePattern + "T" + timePattern;

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.format("timedummyclass") + "\\("));
        assertTrue(dummy[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummy[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummy[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummy[4].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummy[5].matches("\\t" + dateOldField + "\\tDATETIME,"));
        assertTrue(dummy[6].matches("\\t" + dateOldCoverageField + "\\tDATETIME,"));
        assertTrue(dummy[7].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummy[8].matches("\\t" + dateTimeObjectField + "\\tVARCHAR,"));
        assertTrue(dummy[9].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[10].matches("\\);"));

        assertTrue(dummy[11].matches(""));

        assertTrue(dummy[12].matches("INSERT INTO " + strategy.format("timedummyclass")
                + " \\("
                + timeField + ", "
                + dateField + ", "
                + dateTimeField + ", "
                + timestampField + ", "
                + dateOldField + ", "
                + dateOldCoverageField + ", "
                + dateTimeStringField + ", "
                + dateTimeObjectField
                + "\\) VALUES"));

        assertTrue(dummy[13].matches("\\('"
                + ISO_TIME_PATTERN + "', '"
                + ISO_DATE_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "'\\);"));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        final ICase strategy = new DefaultCase();

        final String timeField = strategy.format(LOCAL_TIME.getName());
        final String dateField = strategy.format(LOCAL_DATE.getName());
        final String dateTimeField = strategy.format(LOCAL_DATETIME.getName());
        final String timestampField = strategy.format(TIMESTAMP.getName());
        final String dateOldField = strategy.format(DATE.getName());
        final String dateOldCoverageField = strategy.format(DATE_COVERAGE.getName());
        final String dateTimeStringField = strategy.format(LOCAL_DATETIME_STRING.getName());
        final String dateTimeObjectField = strategy.format(LOCAL_DATETIME_OBJECT.getName());

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.format("dummyunixtime") + "\\("));
        assertTrue(dummy[1].matches("\\t" + timeField + "\\tBIGINT,"));
        assertTrue(dummy[2].matches("\\t" + dateField + "\\tBIGINT,"));
        assertTrue(dummy[3].matches("\\t" + dateTimeField + "\\tBIGINT,"));
        assertTrue(dummy[4].matches("\\t" + timestampField + "\\tBIGINT,"));
        assertTrue(dummy[5].matches("\\t" + dateOldField + "\\tBIGINT,"));
        assertTrue(dummy[6].matches("\\t" + dateOldCoverageField + "\\tBIGINT,"));
        assertTrue(dummy[7].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummy[8].matches("\\t" + dateTimeObjectField + "\\tBIGINT,"));
        assertTrue(dummy[9].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[10].matches("\\);"));

        assertTrue(dummy[11].matches(""));

        assertTrue(dummy[12].matches("INSERT INTO " + strategy.format("dummyunixtime")
                + " \\("
                + timeField + ", "
                + dateField + ", "
                + dateTimeField + ", "
                + timestampField + ", "
                + dateOldField + ", "
                + dateOldCoverageField + ", "
                + dateTimeStringField + ", "
                + dateTimeObjectField
                + "\\) VALUES"));

        assertTrue(dummy[13].matches("\\('"
                + "[0-9]+" + "', '"
                + "[0-9]+" + "', '"
                + "[0-9]+" + "', '"
                + "[0-9]+" + "', '"
                + "[0-9]+" + "', '"
                + "[0-9]+" + "', '"
                + Patterns.LOCAL_DATETIME.getPattern().pattern() + "', '"
                + Patterns.LOCAL_DATETIME.getPattern().pattern() + "'\\);"));
    }

    @Override
    public void isDummyTimeFormatterValid(String[] dummy) {
        final ICase strategy = new DefaultCase();

        final String timeField = strategy.format(LOCAL_TIME.getName());
        final String dateField = strategy.format(LOCAL_DATE.getName());
        final String dateTimeField = strategy.format(LOCAL_DATETIME.getName());
        final String timestampField = strategy.format(TIMESTAMP.getName());
        final String dateOldField = strategy.format(DATE.getName());
        final String dateOldCoverageField = strategy.format(DATE_COVERAGE.getName());
        final String dateTimeStringField = strategy.format(LOCAL_DATETIME_STRING.getName());
        final String dateTimeObjectField = strategy.format(LOCAL_DATETIME_OBJECT.getName());

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.format("dummytimeformatter") + "\\("));
        assertTrue(dummy[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummy[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummy[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummy[4].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummy[5].matches("\\t" + dateOldField + "\\tDATETIME,"));
        assertTrue(dummy[6].matches("\\t" + dateOldCoverageField + "\\tDATETIME,"));
        assertTrue(dummy[7].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummy[8].matches("\\t" + dateTimeObjectField + "\\tVARCHAR,"));
        assertTrue(dummy[9].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[10].matches("\\);"));

        assertTrue(dummy[11].matches(""));

        assertTrue(dummy[12].matches("INSERT INTO " + strategy.format("dummytimeformatter")
                + " \\("
                + timeField + ", "
                + dateField + ", "
                + dateTimeField + ", "
                + timestampField + ", "
                + dateOldField + ", "
                + dateOldCoverageField + ", "
                + dateTimeStringField + ", "
                + dateTimeObjectField
                + "\\) VALUES"));

        assertTrue(dummy[13].matches("\\('"
                + DummyTimeFormatter.Patterns.TIME.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATE.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.TIMESTAMP.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.DATE.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.DATE_SQL.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern().pattern() + "'\\);"));
    }
}
