package io.goodforgod.dummymaker.export.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.testdata.Dummy;
import io.goodforgod.dummymaker.testdata.DummyTime;
import io.goodforgod.dummymaker.testdata.DummyTimeFormatter;
import io.goodforgod.dummymaker.testdata.DummyUnixTime;

/**
 * @author GoodforGod
 * @since 01.09.2017
 */
public class JsonValidatorChecker implements ValidatorChecker {

    @Override
    public void isSingleDummyListValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\[\\{"
                + "\"" + Dummy.DummyFields.GROUP.exportName() + "\":\"[0-9]+\","
                + "\"" + Dummy.DummyFields.NUM.exportName() + "\":[0-9]+,"
                + "\"" + Dummy.DummyFields.NAME.exportName() + "\":\"[a-zA-Z0-9]+\""
                + "}]"));
    }

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{\"" + Dummy.DummyFields.GROUP.exportName() + "\":\"[0-9]+\","
                + "\"" + Dummy.DummyFields.NUM.exportName() + "\":[0-9]+,"
                + "\"" + Dummy.DummyFields.NAME.exportName() + "\":\"[a-zA-Z0-9]+\""
                + "}"));
    }

    @Override
    public void isSingleAutoDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"
                + "\"anInt\":-?[0-9]+,"
                + "\"aLong\":-?[0-9]+"
                + "}"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        isTwoDummiesValidWithNamingStrategy(dummies, NamingCases.DEFAULT);
    }

    @Override
    public void isTwoDummiesWithNumFieldValid(String[] dummies) {
        final String expectedNumField = Dummy.DummyFields.NUM.exportName();

        assertTrue(dummies[0].matches("\\[\\{\"" + expectedNumField + "\":[0-9]+},"));
        assertTrue(dummies[1].matches("\\{\"" + expectedNumField + "\":[0-9]+}]"));
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, NamingCase strategy) {
        final String expectedNameField = strategy.apply(Dummy.DummyFields.NAME.exportName()).toString();
        final String expectedGroupField = Dummy.DummyFields.GROUP.exportName();
        final String expectedNumField = strategy.apply(Dummy.DummyFields.NUM.exportName()).toString();

        assertTrue(dummies[0].matches("\\["
                + "\\{\"" + expectedGroupField + "\":\"[0-9]+\","
                + "\"" + expectedNumField + "\":[0-9]+,"
                + "\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""
                + "},"));

        assertTrue(dummies[1].matches(
                "\\{\"" + expectedGroupField + "\":\"[0-9]+\","
                        + "\"" + expectedNumField + "\":[0-9]+,"
                        + "\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""
                        + "}" + "]"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"
                + "\"" + DummyTime.Fields.LOCAL_TIME.getName() + "\":\"" + DummyTime.Patterns.LOCAL_TIME.getPattern() + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATE.getName() + "\":\"" + DummyTime.Patterns.LOCAL_DATE.getPattern() + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATETIME.getName() + "\":\"" + DummyTime.Patterns.LOCAL_DATETIME.getPattern()
                + "\","
                + "\"" + DummyTime.Fields.OFFSET_TIME.getName() + "\":\"" + DummyTime.Patterns.OFFSET_TIME.getPattern() + "\","
                + "\"" + DummyTime.Fields.OFFSET_DATETIME.getName() + "\":\"" + DummyTime.Patterns.OFFSET_DATETIME.getPattern()
                + "\","
                + "\"" + DummyTime.Fields.TIMESTAMP.getName() + "\":\"" + DummyTime.Patterns.TIMESTAMP.getPattern() + "\","
                + "\"" + DummyTime.Fields.DATE.getName() + "\":\"" + DummyTime.Patterns.DATE_SQL.getPattern() + "\","
                + "\"" + DummyTime.Fields.DATE_COVERAGE.getName() + "\":\"" + DummyTime.Patterns.DATE.getPattern() + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATETIME_STRING.getName() + "\":\""
                + DummyTime.Patterns.OFFSET_DATETIME.getPattern().pattern()
                + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\""
                + DummyTime.Patterns.OFFSET_DATETIME.getPattern().pattern() + "\""
                + "}"));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"
                + "\"" + DummyUnixTime.Fields.SHORT_PRIM.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.INT_PRIM.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.LONG_PRIM.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.SHORT_BOX.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.INT_BOX.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.LONG_BOX.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.BIG_INT.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.BIT_DECIMAL.getName() + "\":[0-9]+,"
                + "\"" + DummyUnixTime.Fields.STRING.getName() + "\":\"[0-9]+\","
                + "\"" + DummyUnixTime.Fields.OBJECT.getName() + "\":\"[0-9]+\""
                + "}"));
    }

    @Override
    public void isDummyTimeFormatterValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"
                + "\"" + DummyTime.Fields.LOCAL_TIME.getName() + "\":\"" + DummyTimeFormatter.Patterns.LOCAL_TIME.getPattern()
                + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATE.getName() + "\":\"" + DummyTimeFormatter.Patterns.LOCAL_DATE.getPattern()
                + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATETIME.getName() + "\":\""
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern() + "\","
                + "\"" + DummyTime.Fields.OFFSET_TIME.getName() + "\":\"" + DummyTime.Patterns.OFFSET_TIME.getPattern() + "\","
                + "\"" + DummyTime.Fields.OFFSET_DATETIME.getName() + "\":\"" + DummyTime.Patterns.OFFSET_DATETIME.getPattern()
                + "\","
                + "\"" + DummyTime.Fields.TIMESTAMP.getName() + "\":\"" + DummyTimeFormatter.Patterns.TIMESTAMP.getPattern()
                + "\","
                + "\"" + DummyTime.Fields.DATE.getName() + "\":\"" + DummyTimeFormatter.Patterns.DATE_SQL.getPattern() + "\","
                + "\"" + DummyTime.Fields.DATE_COVERAGE.getName() + "\":\"" + DummyTimeFormatter.Patterns.DATE.getPattern()
                + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATETIME_STRING.getName() + "\":\"" + DummyTimeFormatter.ISO_DATE_TIME_PATTERN
                + "\","
                + "\"" + DummyTime.Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + DummyTimeFormatter.ISO_DATE_TIME_PATTERN
                + "\""
                + "}"));
    }
}
