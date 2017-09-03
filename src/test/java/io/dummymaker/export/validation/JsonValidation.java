package io.dummymaker.export.validation;

import io.dummymaker.util.INameStrategist;
import io.dummymaker.util.NameStrategist;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static io.dummymaker.util.NameStrategist.NamingStrategy;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class JsonValidation {

    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("\\{"));
        assertTrue(dummy[1].matches("\\t\"" + NAME.getExportFieldName() + "\":\\s\"[a-zA-Z0-9]+\","));
        assertTrue(dummy[2].matches("\\t\"" + NUM.getExportFieldName()  + "\":\\s\"null\","));
        assertTrue(dummy[3].matches("\\t\"" + GROUP.getExportFieldName() + "\":\\s\"[0-9]+\""));
        assertTrue(dummy[4].matches("}"));
    }

    public void isTwoDummiesValid(String[] dummies) {
        assertTrue(dummies[0].matches("\\{"));
        assertTrue(dummies[1].matches("\\t\"[a-zA-Z]+\": \\["));

        assertTrue(dummies[2].matches("\\t{2}\\{"));
        assertTrue(dummies[3].matches("\\t{3}\"" + NAME.getExportFieldName() + "\":\\s\"[a-zA-Z0-9]+\","));
        assertTrue(dummies[4].matches("\\t{3}\"" + NUM.getExportFieldName()  + "\":\\s\"[0-9]+\","));
        assertTrue(dummies[5].matches("\\t{3}\"" + GROUP.getExportFieldName() + "\":\\s\"[0-9]+\""));
        assertTrue(dummies[6].matches("\\t{2}},"));

        assertTrue(dummies[7].matches("\\t{2}\\{"));
        assertTrue(dummies[8].matches("\\t{3}\"" + NAME.getExportFieldName() + "\":\\s\"[a-zA-Z0-9]+\","));
        assertTrue(dummies[10].matches("\\t{3}\"" + NUM.getExportFieldName()  + "\":\\s\"[0-9]+\","));
        assertTrue(dummies[9].matches("\\t{3}\"" + GROUP.getExportFieldName() + "\":\\s\"[0-9]+\""));
        assertTrue(dummies[11].matches("\\t{2}}"));

        assertTrue(dummies[12].matches("\\t]"));
        assertTrue(dummies[13].matches("}"));
    }

    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, NamingStrategy strategy) {
        final INameStrategist strategist = new NameStrategist();

        final String expectedNameField = strategist.toNamingStrategy(NAME.getExportFieldName(), strategy);
        final String expectedGroupField = GROUP.getExportFieldName();
        final String expectedNumField = strategist.toNamingStrategy(NUM.getExportFieldName(), strategy);

        assertTrue(dummies[0].matches("\\{"));
        assertTrue(dummies[1].matches("\\t\"[a-zA-Z]+\": \\["));

        assertTrue(dummies[2].matches("\\t{2}\\{"));
        assertTrue(dummies[3].matches("\\t{3}\"" + expectedNameField + "\":\\s\"[a-zA-Z0-9]+\","));
        assertTrue(dummies[4].matches("\\t{3}\"" + expectedNumField+ "\":\\s\"[0-9]+\","));
        assertTrue(dummies[5].matches("\\t{3}\"" + expectedGroupField + "\":\\s\"[0-9]+\""));
        assertTrue(dummies[6].matches("\\t{2}},"));

        assertTrue(dummies[7].matches("\\t{2}\\{"));
        assertTrue(dummies[8].matches("\\t{3}\"" + expectedNameField + "\":\\s\"[a-zA-Z0-9]+\","));
        assertTrue(dummies[9].matches("\\t{3}\"" + expectedNumField  + "\":\\s\"[0-9]+\","));
        assertTrue(dummies[10].matches("\\t{3}\"" + expectedGroupField + "\":\\s\"[0-9]+\""));
        assertTrue(dummies[11].matches("\\t{2}}"));

        assertTrue(dummies[12].matches("\\t]"));
        assertTrue(dummies[13].matches("}"));
    }
}
