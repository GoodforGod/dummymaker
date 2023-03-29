package io.dummymaker.export.validators;

import static io.dummymaker.testdata.Dummy.DummyFields.*;
import static io.dummymaker.testdata.DummyTime.Patterns.OFFSET_DATETIME;
import static io.dummymaker.testdata.DummyTime.Patterns.OFFSET_TIME;
import static io.dummymaker.testdata.DummyTimeFormatter.Patterns.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.dummymaker.cases.NamingCase;
import io.dummymaker.cases.NamingCases;
import io.dummymaker.testdata.DummyTime.Fields;
import io.dummymaker.testdata.DummyTime.Patterns;
import io.dummymaker.testdata.DummyTimeFormatter;
import io.dummymaker.testdata.DummyUnixTime;

/**
 * @author GoodforGod
 * @since 01.09.2017
 */
public class JsonValidatorChecker implements ValidatorChecker {

    @Override
    public void isSingleDummyListValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\[\\{"
                + "\"" + GROUP.exportName() + "\":\"[0-9]+\","
                + "\"" + NUM.exportName() + "\":[0-9]+,"
                + "\"" + NAME.exportName() + "\":\"[a-zA-Z0-9]+\""
                + "}]"));
    }

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{\"" + GROUP.exportName() + "\":\"[0-9]+\","
                + "\"" + NUM.exportName() + "\":[0-9]+,"
                + "\"" + NAME.exportName() + "\":\"[a-zA-Z0-9]+\""
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
        final String expectedNumField = NUM.exportName();

        assertTrue(dummies[0].matches("\\[\\{\"" + expectedNumField + "\":[0-9]+},"));
        assertTrue(dummies[1].matches("\\{\"" + expectedNumField + "\":[0-9]+}]"));
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, NamingCase strategy) {
        final String expectedNameField = strategy.apply(NAME.exportName()).toString();
        final String expectedGroupField = GROUP.exportName();
        final String expectedNumField = strategy.apply(NUM.exportName()).toString();

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
                + "\"" + Fields.LOCAL_TIME.getName() + "\":\"" + Patterns.LOCAL_TIME.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATE.getName() + "\":\"" + Patterns.LOCAL_DATE.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATETIME.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\","
                + "\"" + Fields.OFFSET_TIME.getName() + "\":\"" + OFFSET_TIME.getPattern() + "\","
                + "\"" + Fields.OFFSET_DATETIME.getName() + "\":\"" + Patterns.OFFSET_DATETIME.getPattern() + "\","
                + "\"" + Fields.TIMESTAMP.getName() + "\":\"" + Patterns.TIMESTAMP.getPattern() + "\","
                + "\"" + Fields.DATE.getName() + "\":\"" + Patterns.DATE_SQL.getPattern() + "\","
                + "\"" + Fields.DATE_COVERAGE.getName() + "\":\"" + Patterns.DATE.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\"" + Patterns.OFFSET_DATETIME.getPattern().pattern()
                + "\","
                + "\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + Patterns.OFFSET_DATETIME.getPattern().pattern() + "\""
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
                + "\"" + Fields.LOCAL_TIME.getName() + "\":\"" + LOCAL_TIME.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATE.getName() + "\":\"" + LOCAL_DATE.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATETIME.getName() + "\":\"" + LOCAL_DATETIME.getPattern() + "\","
                + "\"" + Fields.OFFSET_TIME.getName() + "\":\"" + OFFSET_TIME.getPattern() + "\","
                + "\"" + Fields.OFFSET_DATETIME.getName() + "\":\"" + OFFSET_DATETIME.getPattern() + "\","
                + "\"" + Fields.TIMESTAMP.getName() + "\":\"" + TIMESTAMP.getPattern() + "\","
                + "\"" + Fields.DATE.getName() + "\":\"" + DATE_SQL.getPattern() + "\","
                + "\"" + Fields.DATE_COVERAGE.getName() + "\":\"" + DATE.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\"" + DummyTimeFormatter.ISO_DATE_TIME_PATTERN + "\","
                + "\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + DummyTimeFormatter.ISO_DATE_TIME_PATTERN + "\""
                + "}"));
    }
}
