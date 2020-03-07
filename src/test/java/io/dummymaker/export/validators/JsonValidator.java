package io.dummymaker.export.validators;

import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.model.DummyTime.Fields;
import io.dummymaker.model.DummyTime.Patterns;

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

        assertTrue(dummies[0].matches("\\["));

        assertTrue(dummies[1].matches("\\t{2}\\{"));
        assertTrue(dummies[2].matches("\\t{3}\"" + expectedGroupField + "\":\"[0-9]+\","));
        assertTrue(dummies[3].matches("\\t{3}\"" + expectedNumField + "\":[0-9]+,"));
        assertTrue(dummies[4].matches("\\t{3}\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""));
        assertTrue(dummies[5].matches("\\t{2}},"));

        assertTrue(dummies[6].matches("\\t{2}\\{"));
        assertTrue(dummies[7].matches("\\t{3}\"" + expectedGroupField + "\":\"[0-9]+\","));
        assertTrue(dummies[8].matches("\\t{3}\"" + expectedNumField + "\":[0-9]+,"));
        assertTrue(dummies[9].matches("\\t{3}\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""));
        assertTrue(dummies[10].matches("\\t{2}}"));

        assertTrue(dummies[11].matches("\\t]"));
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
        assertTrue(dummy[8].matches("\\t\"" + Fields.LOCAL_DATETIME_OBJECT.getName() + "\":\"" + "[0-9]+" + "\""));
        assertTrue(dummy[9].matches("}"));
    }
}
