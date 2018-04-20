package io.dummymaker.export.validators;

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
public class JsonValidator implements IValidator {

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"));
        assertTrue(dummy[1].matches("\\t\"" + GROUP.getExportFieldName() + "\":\"[0-9]+\","));
        assertTrue(dummy[2].matches("\\t\"" + NUM.getExportFieldName()  + "\":\"[0-9]+\","));
        assertTrue(dummy[3].matches("\\t\"" + NAME.getExportFieldName() + "\":\"[a-zA-Z0-9]+\""));
        assertTrue(dummy[4].matches("}"));
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

        assertTrue(dummies[0].matches("\\{"));
        assertTrue(dummies[1].matches("\\t\"[a-zA-Z]+\": \\["));

        assertTrue(dummies[2].matches("\\t{2}\\{"));
        assertTrue(dummies[3].matches("\\t{3}\"" + expectedGroupField + "\":\"[0-9]+\","));
        assertTrue(dummies[4].matches("\\t{3}\"" + expectedNumField+ "\":\"[0-9]+\","));
        assertTrue(dummies[5].matches("\\t{3}\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""));
        assertTrue(dummies[6].matches("\\t{2}},"));

        assertTrue(dummies[7].matches("\\t{2}\\{"));
        assertTrue(dummies[8].matches("\\t{3}\"" + expectedGroupField + "\":\"[0-9]+\","));
        assertTrue(dummies[9].matches("\\t{3}\"" + expectedNumField  + "\":\"[0-9]+\","));
        assertTrue(dummies[10].matches("\\t{3}\"" + expectedNameField + "\":\"[a-zA-Z0-9]+\""));
        assertTrue(dummies[11].matches("\\t{2}}"));

        assertTrue(dummies[12].matches("\\t]"));
        assertTrue(dummies[13].matches("}"));
    }
}
