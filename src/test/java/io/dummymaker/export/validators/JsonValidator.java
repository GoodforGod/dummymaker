package io.dummymaker.export.validators;

import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.model.DummyTime.Fields;
import io.dummymaker.model.DummyTime.Patterns;
import io.dummymaker.model.DummyTimeFormatter;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class JsonValidator implements IValidator {

    @Override
    public void isSingleDummyListValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\[\\{"));
        assertTrue(dummy[1].matches("\\t\"" + GROUP.exportName() + "\":\"[0-9]+\","));
        assertTrue(dummy[2].matches("\\t\"" + NUM.exportName() + "\":[0-9]+,"));
        assertTrue(dummy[3].matches("\\t\"" + NAME.exportName() + "\":\"[a-zA-Z0-9]+\""));
        assertTrue(dummy[4].matches("}]"));
    }

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"));
        assertTrue(dummy[1].matches("\\t\"" + GROUP.exportName() + "\":\"[0-9]+\","));
        assertTrue(dummy[2].matches("\\t\"" + NUM.exportName() + "\":[0-9]+,"));
        assertTrue(dummy[3].matches("\\t\"" + NAME.exportName() + "\":\"[a-zA-Z0-9]+\""));
        assertTrue(dummy[4].matches("}"));
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
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, ICase strategy) {
        final String expectedNameField = strategy.format(NAME.exportName());
        final String expectedGroupField = GROUP.exportName();
        final String expectedNumField = strategy.format(NUM.exportName());

        assertTrue(dummies[0].matches("\\["
                + "\\{\"" + expectedGroupField + "\":\"[0-9]+\","
                + "\"" + expectedNumField + "\":[0-9]+,"
                + "\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""
                + "},"
        ));

        assertTrue(dummies[1].matches(
                "\\{\"" + expectedGroupField + "\":\"[0-9]+\","
                + "\"" + expectedNumField + "\":[0-9]+,"
                + "\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""
                + "}" + "]"
        ));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"));
        assertTrue(dummy[1].matches("\\t\"" + Fields.LOCAL_TIME.getName() + "\":\"" + Patterns.LOCAL_TIME.getPattern() + "\","));
        assertTrue(dummy[2].matches("\\t\"" + Fields.LOCAL_DATE.getName() + "\":\"" + Patterns.LOCAL_DATE.getPattern() + "\","));
        assertTrue(dummy[3]
                .matches("\\t\"" + Fields.LOCAL_DATETIME.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\","));
        assertTrue(dummy[4].matches("\\t\"" + Fields.TIMESTAMP.getName() + "\":\"" + Patterns.TIMESTAMP.getPattern() + "\","));
        assertTrue(dummy[5].matches("\\t\"" + Fields.DATE.getName() + "\":\"" + Patterns.DATE_SQL.getPattern() + "\","));
        assertTrue(dummy[6].matches("\\t\"" + Fields.DATE_COVERAGE.getName() + "\":\"" + Patterns.DATE.getPattern() + "\","));
        assertTrue(dummy[7].matches(
                "\\t\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\","));
        assertTrue(dummy[8].matches(
                "\\t\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + Patterns.LOCAL_DATETIME.getPattern() + "\""));
        assertTrue(dummy[9].matches("}"));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"));
        assertTrue(dummy[1].matches("\\t\"" + Fields.LOCAL_TIME.getName() + "\":\"" + "[0-9]+" + "\","));
        assertTrue(dummy[2].matches("\\t\"" + Fields.LOCAL_DATE.getName() + "\":\"" + "[0-9]+" + "\","));
        assertTrue(dummy[3].matches("\\t\"" + Fields.LOCAL_DATETIME.getName() + "\":\"" + "[0-9]+" + "\","));
        assertTrue(dummy[4].matches("\\t\"" + Fields.TIMESTAMP.getName() + "\":\"" + "[0-9]+" + "\","));
        assertTrue(dummy[5].matches("\\t\"" + Fields.DATE.getName() + "\":\"" + "[0-9]+" + "\","));
        assertTrue(dummy[6].matches("\\t\"" + Fields.DATE_COVERAGE.getName() + "\":\"" + "[0-9]+" + "\","));
        assertTrue(dummy[7].matches("\\t\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\""
                + Patterns.LOCAL_DATETIME.getPattern().pattern() + "\","));
        assertTrue(dummy[8].matches("\\t\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\""
                + Patterns.LOCAL_DATETIME.getPattern().pattern() + "\""));
        assertTrue(dummy[9].matches("}"));
    }

    @Override
    public void isDummyTimeFormatterValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"));
        assertTrue(dummy[1].matches(
                "\\t\"" + Fields.LOCAL_TIME.getName() + "\":\"" + DummyTimeFormatter.Patterns.LOCAL_TIME.getPattern() + "\","));
        assertTrue(dummy[2].matches(
                "\\t\"" + Fields.LOCAL_DATE.getName() + "\":\"" + DummyTimeFormatter.Patterns.LOCAL_DATE.getPattern() + "\","));
        assertTrue(dummy[3]
                .matches("\\t\"" + Fields.LOCAL_DATETIME.getName() + "\":\""
                        + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern() + "\","));
        assertTrue(dummy[4].matches(
                "\\t\"" + Fields.TIMESTAMP.getName() + "\":\"" + DummyTimeFormatter.Patterns.TIMESTAMP.getPattern() + "\","));
        assertTrue(dummy[5]
                .matches("\\t\"" + Fields.DATE.getName() + "\":\"" + DummyTimeFormatter.Patterns.DATE_SQL.getPattern() + "\","));
        assertTrue(dummy[6].matches(
                "\\t\"" + Fields.DATE_COVERAGE.getName() + "\":\"" + DummyTimeFormatter.Patterns.DATE.getPattern() + "\","));
        assertTrue(dummy[7].matches(
                "\\t\"" + Fields.LOCAL_DATETIME_STRING.getName() + "\":\""
                        + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern() + "\","));
        assertTrue(dummy[8].matches(
                "\\t\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\""
                        + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern() + "\""));
        assertTrue(dummy[9].matches("}"));
    }
}
