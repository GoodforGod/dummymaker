package io.dummymaker.export.validators;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static io.dummymaker.model.DummyTime.*;
import static io.dummymaker.model.DummyTime.Fields.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.model.DummyArray;
import io.dummymaker.model.DummyTimeFormatter;
import io.dummymaker.model.DummyUnixTime;

/**
 * @author GoodforGod
 * @since 01.09.2017
 */
public class SqlValidatorChecker implements ValidatorChecker {

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
        assertTrue(dummy[1].matches("\\tanInt\\tINT,"));
        assertTrue(dummy[2].matches("\\taLong\\tBIGINT,"));
        assertTrue(dummy[3].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[4].matches("\\);"));

        assertTrue(dummy[5].matches("INSERT INTO dummyauto \\(anInt, aLong\\) VALUES"));
        assertTrue(dummy[6].matches("\\(-?[0-9]+, -?[0-9]+\\);"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        isTwoDummiesValidWithNamingStrategy(dummies, Cases.DEFAULT.value());
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, Case strategy) {
        final String expectedNameField = strategy.apply(NAME.exportName());
        final String expectedGroupField = GROUP.exportName();
        final String expectedNumField = strategy.apply(NUM.exportName());

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummies[1].matches("\\t" + expectedGroupField + "\\tVARCHAR,"));
        assertTrue(dummies[2].matches("\\t" + expectedNumField + "\\tINT,"));
        assertTrue(dummies[3].matches("\\t" + expectedNameField + "\\tVARCHAR,"));
        assertTrue(dummies[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[5].matches("\\);"));

        assertTrue(dummies[6].matches("INSERT INTO dummy \\(" + expectedGroupField + ", " + expectedNumField + ", "
                + expectedNameField + "\\) VALUES"));
        assertTrue(dummies[7].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\),"));
        assertTrue(dummies[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    public void isTwoDummiesArrayValidWithNamingStrategy(String[] dummies, Case strategy) {
        final String className = strategy.apply(DummyArray.class.getSimpleName());
        final String shortSimple = strategy.apply("shortSimple");
        final String longSimple = strategy.apply("longSimple");
        final String IntegerObjDouble = strategy.apply("IntegerObjDouble");
        final String DoubleObjDouble = strategy.apply("DoubleObjDouble");

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS " + className + "\\("));
        assertTrue(dummies[1].matches("\\t" + shortSimple + "\\tSMALLINT\\[],"));
        assertTrue(dummies[2].matches("\\t" + longSimple + "\\tBIGINT\\[],"));
        assertTrue(dummies[3].matches("\\t" + IntegerObjDouble + "\\tINT\\[]\\[],"));
        assertTrue(dummies[4].matches("\\t" + DoubleObjDouble + "\\tDOUBLE PRECISION\\[]\\[],"));
        assertTrue(dummies[5].matches("\\tPRIMARY KEY \\([a-zA-Z_]+\\)"));
        assertTrue(dummies[6].matches("\\);"));

        assertTrue(dummies[7].matches("INSERT INTO " + className + " \\(" + shortSimple + ", " + longSimple + ", "
                + IntegerObjDouble + ", " + DoubleObjDouble + "\\) VALUES"));
    }

    public void isDummyTimeValidWithNamingStrategy(String[] dummies, Case strategy) {
        final String timeField = strategy.apply(LOCAL_TIME.getName());
        final String dateField = strategy.apply(LOCAL_DATE.getName());
        final String dateTimeField = strategy.apply(LOCAL_DATETIME.getName());
        final String offsetTimeField = strategy.apply(OFFSET_TIME.getName());
        final String offsetDateTimeField = strategy.apply(OFFSET_DATETIME.getName());
        final String timestampField = strategy.apply(TIMESTAMP.getName());
        final String dateOldField = strategy.apply(DATE.getName());
        final String dateOldCoverageField = strategy.apply(DATE_COVERAGE.getName());
        final String dateTimeStringField = strategy.apply(LOCAL_DATETIME_STRING.getName());
        final String dateTimeObjectField = strategy.apply(LOCAL_DATETIME_OBJECT.getName());

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.apply("DummyTime") + "\\("));
        assertTrue(dummies[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummies[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummies[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummies[4].matches("\\t" + offsetTimeField + "\\tVARCHAR,"));
        assertTrue(dummies[5].matches("\\t" + offsetDateTimeField + "\\tVARCHAR,"));
        assertTrue(dummies[6].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummies[7].matches("\\t" + dateOldField + "\\tDATETIME,"));
        assertTrue(dummies[8].matches("\\t" + dateOldCoverageField + "\\tDATETIME,"));
        assertTrue(dummies[9].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummies[10].matches("\\t" + dateTimeObjectField + "\\tTIMESTAMP,"));
        assertTrue(dummies[11].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[12].matches("\\);"));

        assertTrue(dummies[13].matches("INSERT INTO " + strategy.apply("DummyTime")
                + " \\("
                + timeField + ", "
                + dateField + ", "
                + dateTimeField + ", "
                + offsetTimeField + ", "
                + offsetDateTimeField + ", "
                + timestampField + ", "
                + dateOldField + ", "
                + dateOldCoverageField + ", "
                + dateTimeStringField + ", "
                + dateTimeObjectField
                + "\\) VALUES"));

        assertTrue(dummies[14].matches("\\('"
                + ISO_TIME_PATTERN + "', '"
                + ISO_DATE_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "'\\),"));

        assertTrue(dummies[15].matches("\\('"
                + ISO_TIME_PATTERN + "', '"
                + ISO_DATE_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "'\\);"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        final Case strategy = Cases.DEFAULT.value();

        final String timeField = strategy.apply(LOCAL_TIME.getName());
        final String dateField = strategy.apply(LOCAL_DATE.getName());
        final String dateTimeField = strategy.apply(LOCAL_DATETIME.getName());
        final String offsetTimeField = strategy.apply(OFFSET_TIME.getName());
        final String offsetDateTimeField = strategy.apply(OFFSET_DATETIME.getName());
        final String timestampField = strategy.apply(TIMESTAMP.getName());
        final String dateOldField = strategy.apply(DATE.getName());
        final String dateOldCoverageField = strategy.apply(DATE_COVERAGE.getName());
        final String dateTimeStringField = strategy.apply(LOCAL_DATETIME_STRING.getName());
        final String dateTimeObjectField = strategy.apply(LOCAL_DATETIME_OBJECT.getName());

        final String timePattern = "[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{0,3})?";
        final String datePattern = "[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}";
        final String timestampPattern = datePattern + "T" + timePattern;

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.apply("dummytime") + "\\("));
        assertTrue(dummy[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummy[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummy[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummy[4].matches("\\t" + offsetTimeField + "\\tVARCHAR,"));
        assertTrue(dummy[5].matches("\\t" + offsetDateTimeField + "\\tVARCHAR,"));
        assertTrue(dummy[6].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummy[7].matches("\\t" + dateOldField + "\\tDATETIME,"));
        assertTrue(dummy[8].matches("\\t" + dateOldCoverageField + "\\tDATETIME,"));
        assertTrue(dummy[9].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummy[10].matches("\\t" + dateTimeObjectField + "\\tVARCHAR,"));
        assertTrue(dummy[11].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[12].matches("\\);"));

        assertTrue(dummy[13].matches("INSERT INTO " + strategy.apply("dummytime")
                + " \\("
                + timeField + ", "
                + dateField + ", "
                + dateTimeField + ", "
                + offsetTimeField + ", "
                + offsetDateTimeField + ", "
                + timestampField + ", "
                + dateOldField + ", "
                + dateOldCoverageField + ", "
                + dateTimeStringField + ", "
                + dateTimeObjectField
                + "\\) VALUES"));

        assertTrue(dummy[14].matches("\\('"
                + ISO_TIME_PATTERN + "', '"
                + ISO_DATE_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + ISO_OFFSET_DATE_TIME_PATTERN + "'\\);"));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        final Case strategy = Cases.DEFAULT.value();

        final String shortPrime = strategy.apply(DummyUnixTime.Fields.SHORT_PRIM.getName());
        final String intPrime = strategy.apply(DummyUnixTime.Fields.INT_PRIM.getName());
        final String longPrime = strategy.apply(DummyUnixTime.Fields.LONG_PRIM.getName());
        final String shortBox = strategy.apply(DummyUnixTime.Fields.SHORT_BOX.getName());
        final String intBox = strategy.apply(DummyUnixTime.Fields.INT_BOX.getName());
        final String longBox = strategy.apply(DummyUnixTime.Fields.LONG_BOX.getName());
        final String bigInt = strategy.apply(DummyUnixTime.Fields.BIG_INT.getName());
        final String bigDecimal = strategy.apply(DummyUnixTime.Fields.BIT_DECIMAL.getName());
        final String string = strategy.apply(DummyUnixTime.Fields.STRING.getName());
        final String object = strategy.apply(DummyUnixTime.Fields.OBJECT.getName());

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.apply("dummyunixtime") + "\\("));
        assertTrue(dummy[1].matches("\\t" + shortPrime + "\\tSMALLINT,"));
        assertTrue(dummy[2].matches("\\t" + intPrime + "\\tINT,"));
        assertTrue(dummy[3].matches("\\t" + longPrime + "\\tBIGINT,"));
        assertTrue(dummy[4].matches("\\t" + shortBox + "\\tSMALLINT,"));
        assertTrue(dummy[5].matches("\\t" + intBox + "\\tINT,"));
        assertTrue(dummy[6].matches("\\t" + longBox + "\\tBIGINT,"));
        assertTrue(dummy[7].matches("\\t" + bigInt + "\\tBIGINT,"));
        assertTrue(dummy[8].matches("\\t" + bigDecimal + "\\tNUMERIC,"));
        assertTrue(dummy[9].matches("\\t" + string + "\\tVARCHAR,"));
        assertTrue(dummy[10].matches("\\t" + object + "\\tVARCHAR,"));
        assertTrue(dummy[11].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[12].matches("\\);"));

        assertTrue(dummy[13].matches("INSERT INTO " + strategy.apply("dummyunixtime")
                + " \\("
                + shortPrime + ", "
                + intPrime + ", "
                + longPrime + ", "
                + shortBox + ", "
                + intBox + ", "
                + longBox + ", "
                + bigInt + ", "
                + bigDecimal + ", "
                + string + ", "
                + object
                + "\\) VALUES"));

        assertTrue(dummy[14].matches("\\("
                + "[0-9]+" + ", "
                + "[0-9]+" + ", "
                + "[0-9]+" + ", "
                + "[0-9]+" + ", "
                + "[0-9]+" + ", "
                + "[0-9]+" + ", "
                + "[0-9]+" + ", "
                + "[0-9]+" + ", "
                + "'[0-9]+'" + ", "
                + "'[0-9]+'" + "\\);"));
    }

    @Override
    public void isDummyTimeFormatterValid(String[] dummy) {
        final Case strategy = Cases.DEFAULT.value();

        final String timeField = strategy.apply(LOCAL_TIME.getName());
        final String dateField = strategy.apply(LOCAL_DATE.getName());
        final String dateTimeField = strategy.apply(LOCAL_DATETIME.getName());
        final String offsetTimeField = strategy.apply(OFFSET_TIME.getName());
        final String offsetDateTimeField = strategy.apply(OFFSET_DATETIME.getName());
        final String timestampField = strategy.apply(TIMESTAMP.getName());
        final String dateOldField = strategy.apply(DATE.getName());
        final String dateOldCoverageField = strategy.apply(DATE_COVERAGE.getName());
        final String dateTimeStringField = strategy.apply(LOCAL_DATETIME_STRING.getName());
        final String dateTimeObjectField = strategy.apply(LOCAL_DATETIME_OBJECT.getName());

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.apply("dummytimeformatter") + "\\("));
        assertTrue(dummy[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummy[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummy[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummy[4].matches("\\t" + offsetTimeField + "\\tVARCHAR,"));
        assertTrue(dummy[5].matches("\\t" + offsetDateTimeField + "\\tVARCHAR,"));
        assertTrue(dummy[6].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummy[7].matches("\\t" + dateOldField + "\\tDATETIME,"));
        assertTrue(dummy[8].matches("\\t" + dateOldCoverageField + "\\tDATETIME,"));
        assertTrue(dummy[9].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummy[10].matches("\\t" + dateTimeObjectField + "\\tVARCHAR,"));
        assertTrue(dummy[11].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[12].matches("\\);"));

        assertTrue(dummy[13].matches("INSERT INTO " + strategy.apply("dummytimeformatter")
                + " \\("
                + timeField + ", "
                + dateField + ", "
                + dateTimeField + ", "
                + offsetTimeField + ", "
                + offsetDateTimeField + ", "
                + timestampField + ", "
                + dateOldField + ", "
                + dateOldCoverageField + ", "
                + dateTimeStringField + ", "
                + dateTimeObjectField
                + "\\) VALUES"));

        assertTrue(dummy[14].matches("\\('"
                + DummyTimeFormatter.Patterns.TIME.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATE.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern().pattern() + "', '"
                + Patterns.OFFSET_TIME.getPattern().pattern() + "', '"
                + Patterns.OFFSET_DATETIME.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.TIMESTAMP.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.DATE.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.DATE_SQL.getPattern().pattern() + "', '"
                + DummyTimeFormatter.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTimeFormatter.ISO_DATE_TIME_PATTERN + "'\\);"));
    }
}
