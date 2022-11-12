package io.dummymaker.export.validators;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static io.dummymaker.model.DummyTime.Patterns.OFFSET_DATETIME;
import static io.dummymaker.model.DummyTime.Patterns.OFFSET_TIME;
import static io.dummymaker.model.DummyTimeFormatter.Patterns.*;
import static org.junit.Assert.assertTrue;

import io.dummymaker.export.Case;
import io.dummymaker.export.Cases;
import io.dummymaker.model.DummyTime.Fields;
import io.dummymaker.model.DummyTime.Patterns;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class JsonValidator implements IValidator {

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
        assertTrue(dummy[0].matches("\\{"));
        assertTrue(dummy[1].matches("\\t\"aLong\":-?[0-9]+,"));
        assertTrue(dummy[2].matches("\\t\"anInt\":-?[0-9]+"));
        assertTrue(dummy[3].matches("}"));
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
                + "\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\""
                + "}"));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"
                + "\"" + Fields.LOCAL_TIME.getName() + "\":[0-9]+,"
                + "\"" + Fields.LOCAL_DATE.getName() + "\":[0-9]+,"
                + "\"" + Fields.LOCAL_DATETIME.getName() + "\":[0-9]+,"
                + "\"" + Fields.OFFSET_TIME.getName() + "\":[0-9]+,"
                + "\"" + Fields.OFFSET_DATETIME.getName() + "\":[0-9]+,"
                + "\"" + Fields.TIMESTAMP.getName() + "\":[0-9]+,"
                + "\"" + Fields.DATE.getName() + "\":[0-9]+,"
                + "\"" + Fields.DATE_COVERAGE.getName() + "\":[0-9]+,"
                + "\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\""
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
                + "\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\"" + LOCAL_DATETIME.getPattern() + "\","
                + "\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + LOCAL_DATETIME.getPattern() + "\""
                + "}"));
    }
}
