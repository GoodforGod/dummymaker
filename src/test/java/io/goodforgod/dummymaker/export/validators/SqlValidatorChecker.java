package io.goodforgod.dummymaker.export.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.testdata.*;
import io.goodforgod.dummymaker.testdata.DummyArray;
import io.goodforgod.dummymaker.testdata.DummyTime;
import io.goodforgod.dummymaker.testdata.DummyTimeFormatter;
import io.goodforgod.dummymaker.testdata.DummyUnixTime;

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
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummy[1].matches("\\t" + Dummy.DummyFields.GROUP.exportName() + "\\tVARCHAR,"));
        assertTrue(dummy[2].matches("\\t" + strategy.apply(Dummy.DummyFields.NUM.exportName()) + "\\tINT,"));
        assertTrue(dummy[3].matches("\\t" + strategy.apply(Dummy.DummyFields.NAME.exportName()) + "\\tVARCHAR,"));
        assertTrue(dummy[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[5].matches("\\);"));
        assertTrue(dummy[6].matches(""));
        assertTrue(dummy[7].matches("INSERT INTO dummy\\(" + Dummy.DummyFields.GROUP.exportName() + ", "
                + strategy.apply(Dummy.DummyFields.NUM.exportName()) + ", "
                + strategy.apply(Dummy.DummyFields.NAME.exportName()) + "\\) VALUES"));
        assertTrue(dummy[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    @Override
    public void isSingleAutoDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS dummy_auto\\("));
        assertTrue(dummy[1].matches("\\tan_int\\tINT,"));
        assertTrue(dummy[2].matches("\\ta_long\\tBIGINT,"));
        assertTrue(dummy[3].matches("\\tPRIMARY KEY \\([a-zA-Z_]+\\)"));
        assertTrue(dummy[4].matches("\\);"));
        assertTrue(dummy[5].matches(""));
        assertTrue(dummy[6].matches("INSERT INTO dummy_auto\\(an_int, a_long\\) VALUES"));
        assertTrue(dummy[7].matches("\\(-?[0-9]+, -?[0-9]+\\);"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        isTwoDummiesValidWithNamingStrategy(dummies, NamingCases.DEFAULT);
    }

    @Override
    public void isTwoDummiesWithNumFieldValid(String[] dummies) {
        final String expectedNumField = Dummy.DummyFields.NUM.exportName();

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummies[1].matches("\\t" + expectedNumField + "\\tINT,"));
        assertTrue(dummies[2].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[3].matches("\\);"));
        assertTrue(dummies[4].matches(""));
        assertTrue(dummies[5].matches("INSERT INTO dummy\\(" + expectedNumField + "\\) VALUES"));
        assertTrue(dummies[6].matches("\\([0-9]+\\),"));
        assertTrue(dummies[7].matches("\\([0-9]+\\);"));
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, NamingCase strategy) {
        final String expectedNameField = strategy.apply(Dummy.DummyFields.NAME.exportName()).toString();
        final String expectedGroupField = Dummy.DummyFields.GROUP.exportName();
        final String expectedNumField = strategy.apply(Dummy.DummyFields.NUM.exportName()).toString();

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummies[1].matches("\\t" + expectedGroupField + "\\tVARCHAR,"));
        assertTrue(dummies[2].matches("\\t" + expectedNumField + "\\tINT,"));
        assertTrue(dummies[3].matches("\\t" + expectedNameField + "\\tVARCHAR,"));
        assertTrue(dummies[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[5].matches("\\);"));
        assertTrue(dummies[6].matches(""));
        assertTrue(dummies[7].matches("INSERT INTO dummy\\(" + expectedGroupField + ", " + expectedNumField + ", "
                + expectedNameField + "\\) VALUES"));
        assertTrue(dummies[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\),"));
        assertTrue(dummies[9].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    public void isTwoDummiesArrayValidWithNamingStrategy(String[] dummies, NamingCase strategy) {
        final String className = strategy.apply(DummyArray.class.getSimpleName()).toString();
        final String shortSimple = strategy.apply("shortSimple").toString();
        final String longSimple = strategy.apply("longSimple").toString();
        final String IntegerObjDouble = strategy.apply("IntegerObjDouble").toString();
        final String DoubleObjDouble = strategy.apply("DoubleObjDouble").toString();

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS " + className + "\\("));
        assertTrue(dummies[1].matches("\\t" + shortSimple + "\\tSMALLINT\\[],"));
        assertTrue(dummies[2].matches("\\t" + longSimple + "\\tBIGINT\\[],"));
        assertTrue(dummies[3].matches("\\t" + IntegerObjDouble + "\\tINT\\[]\\[],"));
        assertTrue(dummies[4].matches("\\t" + DoubleObjDouble + "\\tDOUBLE PRECISION\\[]\\[],"));
        assertTrue(dummies[5].matches("\\tPRIMARY KEY \\([a-zA-Z_]+\\)"));
        assertTrue(dummies[6].matches("\\);"));
        assertTrue(dummies[7].matches(""));
        assertTrue(dummies[8].matches("INSERT INTO " + className + "\\(" + shortSimple + ", " + longSimple + ", "
                + IntegerObjDouble + ", " + DoubleObjDouble + "\\) VALUES"));
    }

    public void isTwoDummiesValidInTwoBatchesWithNamingStrategy(String[] dummies, NamingCase strategy) {
        final String expectedNameField = strategy.apply(Dummy.DummyFields.NAME.exportName()).toString();
        final String expectedGroupField = Dummy.DummyFields.GROUP.exportName();
        final String expectedNumField = strategy.apply(Dummy.DummyFields.NUM.exportName()).toString();

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS dummy\\("));
        assertTrue(dummies[1].matches("\\t" + expectedGroupField + "\\tVARCHAR,"));
        assertTrue(dummies[2].matches("\\t" + expectedNumField + "\\tINT,"));
        assertTrue(dummies[3].matches("\\t" + expectedNameField + "\\tVARCHAR,"));
        assertTrue(dummies[4].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[5].matches("\\);"));
        assertTrue(dummies[6].matches(""));
        assertTrue(dummies[7].matches("INSERT INTO dummy\\(" + expectedGroupField + ", " + expectedNumField + ", "
                + expectedNameField + "\\) VALUES"));
        assertTrue(dummies[8].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\),"));
        assertTrue(dummies[9].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
        assertTrue(dummies[10].matches(""));
        assertTrue(dummies[11].matches("INSERT INTO dummy\\(" + expectedGroupField + ", " + expectedNumField + ", "
                + expectedNameField + "\\) VALUES"));
        assertTrue(dummies[12].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\),"));
        assertTrue(dummies[13].matches("\\('100', [0-9]+, '[a-zA-Z]+'\\);"));
    }

    public void isDummyTimeValidWithNamingStrategy(String[] dummies, NamingCase strategy) {
        final String timeField = strategy.apply(DummyTime.Fields.LOCAL_TIME.getName()).toString();
        final String dateField = strategy.apply(DummyTime.Fields.LOCAL_DATE.getName()).toString();
        final String dateTimeField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME.getName()).toString();
        final String offsetTimeField = strategy.apply(DummyTime.Fields.OFFSET_TIME.getName()).toString();
        final String offsetDateTimeField = strategy.apply(DummyTime.Fields.OFFSET_DATETIME.getName()).toString();
        final String timestampField = strategy.apply(DummyTime.Fields.TIMESTAMP.getName()).toString();
        final String dateOldField = strategy.apply(DummyTime.Fields.DATE.getName()).toString();
        final String dateOldCoverageField = strategy.apply(DummyTime.Fields.DATE_COVERAGE.getName()).toString();
        final String dateTimeStringField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME_STRING.getName()).toString();
        final String dateTimeObjectField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME_OBJECT.getName()).toString();

        assertTrue(dummies[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.apply("DummyTime") + "\\("));
        assertTrue(dummies[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummies[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummies[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummies[4].matches("\\t" + offsetTimeField + "\\tTIME WITH TIME ZONE,"));
        assertTrue(dummies[5].matches("\\t" + offsetDateTimeField + "\\tTIMESTAMP WITH TIME ZONE,"));
        assertTrue(dummies[6].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummies[7].matches("\\t" + dateOldField + "\\tTIMESTAMP,"));
        assertTrue(dummies[8].matches("\\t" + dateOldCoverageField + "\\tTIMESTAMP,"));
        assertTrue(dummies[9].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummies[10].matches("\\t" + dateTimeObjectField + "\\tTIMESTAMP,"));
        assertTrue(dummies[11].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummies[12].matches("\\);"));
        assertTrue(dummies[13].matches(""));
        assertTrue(dummies[14].matches("INSERT INTO " + strategy.apply("DummyTime")
                + "\\("
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

        assertTrue(dummies[15].matches("\\('"
                + DummyTime.ISO_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "'\\),"));

        assertTrue(dummies[16].matches("\\('"
                + DummyTime.ISO_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "'\\);"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final String timeField = strategy.apply(DummyTime.Fields.LOCAL_TIME.getName()).toString();
        final String dateField = strategy.apply(DummyTime.Fields.LOCAL_DATE.getName()).toString();
        final String dateTimeField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME.getName()).toString();
        final String offsetTimeField = strategy.apply(DummyTime.Fields.OFFSET_TIME.getName()).toString();
        final String offsetDateTimeField = strategy.apply(DummyTime.Fields.OFFSET_DATETIME.getName()).toString();
        final String timestampField = strategy.apply(DummyTime.Fields.TIMESTAMP.getName()).toString();
        final String dateOldField = strategy.apply(DummyTime.Fields.DATE.getName()).toString();
        final String dateOldCoverageField = strategy.apply(DummyTime.Fields.DATE_COVERAGE.getName()).toString();
        final String dateTimeStringField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME_STRING.getName()).toString();
        final String dateTimeObjectField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME_OBJECT.getName()).toString();

        final String timePattern = "[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{0,3})?";
        final String datePattern = "[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}";
        final String timestampPattern = datePattern + "T" + timePattern;

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.apply(DummyTime.class.getSimpleName()) + "\\("));
        assertTrue(dummy[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummy[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummy[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummy[4].matches("\\t" + offsetTimeField + "\\tTIME WITH TIME ZONE,"));
        assertTrue(dummy[5].matches("\\t" + offsetDateTimeField + "\\tTIMESTAMP WITH TIME ZONE,"));
        assertTrue(dummy[6].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummy[7].matches("\\t" + dateOldField + "\\tTIMESTAMP,"));
        assertTrue(dummy[8].matches("\\t" + dateOldCoverageField + "\\tTIMESTAMP,"));
        assertTrue(dummy[9].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummy[10].matches("\\t" + dateTimeObjectField + "\\tVARCHAR,"));
        assertTrue(dummy[11].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[12].matches("\\);"));
        assertTrue(dummy[13].matches(""));
        assertTrue(dummy[14].matches("INSERT INTO " + strategy.apply(DummyTime.class.getSimpleName())
                + "\\("
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

        assertTrue(dummy[15].matches("\\('"
                + DummyTime.ISO_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "', '"
                + DummyTime.ISO_OFFSET_DATE_TIME_PATTERN + "'\\);"));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final String shortPrime = strategy.apply(DummyUnixTime.Fields.SHORT_PRIM.getName()).toString();
        final String intPrime = strategy.apply(DummyUnixTime.Fields.INT_PRIM.getName()).toString();
        final String longPrime = strategy.apply(DummyUnixTime.Fields.LONG_PRIM.getName()).toString();
        final String shortBox = strategy.apply(DummyUnixTime.Fields.SHORT_BOX.getName()).toString();
        final String intBox = strategy.apply(DummyUnixTime.Fields.INT_BOX.getName()).toString();
        final String longBox = strategy.apply(DummyUnixTime.Fields.LONG_BOX.getName()).toString();
        final String bigInt = strategy.apply(DummyUnixTime.Fields.BIG_INT.getName()).toString();
        final String bigDecimal = strategy.apply(DummyUnixTime.Fields.BIT_DECIMAL.getName()).toString();
        final String string = strategy.apply(DummyUnixTime.Fields.STRING.getName()).toString();
        final String object = strategy.apply(DummyUnixTime.Fields.OBJECT.getName()).toString();

        assertTrue(dummy[0].matches("CREATE TABLE IF NOT EXISTS " + strategy.apply(DummyUnixTime.class.getSimpleName()) + "\\("));
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
        assertTrue(dummy[11].matches("\\tPRIMARY KEY \\([a-zA-Z_]+\\)"));
        assertTrue(dummy[12].matches("\\);"));
        assertTrue(dummy[13].matches(""));
        assertTrue(dummy[14].matches("INSERT INTO " + strategy.apply(DummyUnixTime.class.getSimpleName())
                + "\\("
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

        assertTrue(dummy[15].matches("\\("
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
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final String timeField = strategy.apply(DummyTime.Fields.LOCAL_TIME.getName()).toString();
        final String dateField = strategy.apply(DummyTime.Fields.LOCAL_DATE.getName()).toString();
        final String dateTimeField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME.getName()).toString();
        final String offsetTimeField = strategy.apply(DummyTime.Fields.OFFSET_TIME.getName()).toString();
        final String offsetDateTimeField = strategy.apply(DummyTime.Fields.OFFSET_DATETIME.getName()).toString();
        final String timestampField = strategy.apply(DummyTime.Fields.TIMESTAMP.getName()).toString();
        final String dateOldField = strategy.apply(DummyTime.Fields.DATE.getName()).toString();
        final String dateOldCoverageField = strategy.apply(DummyTime.Fields.DATE_COVERAGE.getName()).toString();
        final String dateTimeStringField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME_STRING.getName()).toString();
        final String dateTimeObjectField = strategy.apply(DummyTime.Fields.LOCAL_DATETIME_OBJECT.getName()).toString();

        assertTrue(dummy[0]
                .matches("CREATE TABLE IF NOT EXISTS " + strategy.apply(DummyTimeFormatter.class.getSimpleName()) + "\\("));
        assertTrue(dummy[1].matches("\\t" + timeField + "\\tTIME,"));
        assertTrue(dummy[2].matches("\\t" + dateField + "\\tDATE,"));
        assertTrue(dummy[3].matches("\\t" + dateTimeField + "\\tTIMESTAMP,"));
        assertTrue(dummy[4].matches("\\t" + offsetTimeField + "\\tTIME WITH TIME ZONE,"));
        assertTrue(dummy[5].matches("\\t" + offsetDateTimeField + "\\tTIMESTAMP WITH TIME ZONE,"));
        assertTrue(dummy[6].matches("\\t" + timestampField + "\\tTIMESTAMP,"));
        assertTrue(dummy[7].matches("\\t" + dateOldField + "\\tTIMESTAMP,"));
        assertTrue(dummy[8].matches("\\t" + dateOldCoverageField + "\\tTIMESTAMP,"));
        assertTrue(dummy[9].matches("\\t" + dateTimeStringField + "\\tVARCHAR,"));
        assertTrue(dummy[10].matches("\\t" + dateTimeObjectField + "\\tVARCHAR,"));
        assertTrue(dummy[11].matches("\\tPRIMARY KEY \\([a-zA-Z]+\\)"));
        assertTrue(dummy[12].matches("\\);"));
        assertTrue(dummy[13].matches(""));
        assertTrue(dummy[14].matches("INSERT INTO " + strategy.apply(DummyTimeFormatter.class.getSimpleName())
                + "\\("
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

        assertTrue(dummy[15].matches("\\('"
                + DummyTimeFormatter.Patterns.TIME.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATE.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern().pattern() + "', '"
                + DummyTime.Patterns.OFFSET_TIME.getPattern().pattern() + "', '"
                + DummyTime.Patterns.OFFSET_DATETIME.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.TIMESTAMP.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.DATE.getPattern().pattern() + "', '"
                + DummyTimeFormatter.Patterns.DATE_SQL.getPattern().pattern() + "', '"
                + DummyTimeFormatter.ISO_DATE_TIME_PATTERN + "', '"
                + DummyTimeFormatter.ISO_DATE_TIME_PATTERN + "'\\);"));
    }
}
