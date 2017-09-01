package io.dummymaker.export.validation;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class XmlValidation implements IValidation {

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("<[a-zA-Z]>"));
        assertTrue(dummy[1].matches("<" + NAME.getExportFieldName() + "\":\\s\"[a-zA-Z0-9]+\","));
        assertTrue(dummy[2].matches("<" + GROUP.getExportFieldName() + "\":\\s\"[0-9]+\","));
        assertTrue(dummy[3].matches("<" + NUM.getExportFieldName()  + "\":\\s\"[0-9]+\""));
        assertTrue(dummy[4].matches("<[a-zA-Z]>"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        assertTrue(dummies[2].matches("\\t{2}\\{"));
        assertTrue(dummies[3].matches("\\t{3}\"" + NAME.getExportFieldName() + "\":\\s\"[a-zA-Z0-9]+\","));
        assertTrue(dummies[4].matches("\\t{3}\"" + GROUP.getExportFieldName() + "\":\\s\"[0-9]+\","));
        assertTrue(dummies[5].matches("\\t{3}\"" + NUM.getExportFieldName()  + "\":\\s\"[0-9]+\""));
        assertTrue(dummies[6].matches("\\t{2}},"));

        assertTrue(dummies[7].matches("\\t{2}\\{"));
        assertTrue(dummies[10].matches("\\t{3}\"" + NUM.getExportFieldName()  + "\":\\s\"[0-9]+\""));
        assertTrue(dummies[11].matches("\\t{2}}"));
    }
}
