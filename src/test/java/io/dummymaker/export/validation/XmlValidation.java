package io.dummymaker.export.validation;

import io.dummymaker.export.NamingStrategy;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class XmlValidation {

    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("<[a-zA-Z]+>"));
        assertTrue(dummy[1].matches("\\t<" + NAME.getExportFieldName()     + ">" + "[a-zA-Z]+" + "</" + NAME.getExportFieldName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + NUM.getExportFieldName()      + ">" + "null"      + "</" + NUM.getExportFieldName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + GROUP.getExportFieldName()    + ">" + "[0-9]+"    + "</" + GROUP.getExportFieldName() + ">"));
        assertTrue(dummy[4].matches("</[a-zA-Z]+>"));
    }

    public void isTwoDummiesValid(String[] dummies) {
        assertTrue(dummies[0].matches("<[a-zA-Z]+List>"));
        assertTrue(dummies[1].matches("\\t<[a-zA-Z]+>"));
        assertTrue(dummies[2].matches("\\t{2}<" + NAME.getExportFieldName()   + ">" + "[a-zA-Z]+" + "</" + NAME.getExportFieldName() + ">"));
        assertTrue(dummies[3].matches("\\t{2}<" + NUM.getExportFieldName()    + ">" + "[0-9]+"    + "</" + NUM.getExportFieldName() + ">"));
        assertTrue(dummies[4].matches("\\t{2}<" + GROUP.getExportFieldName()  + ">" + "[0-9]+"    + "</" + GROUP.getExportFieldName() + ">"));
        assertTrue(dummies[5].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[6].matches("\\t<[a-zA-Z]+>"));
        assertTrue(dummies[7].matches("\\t{2}<" + NAME.getExportFieldName()   + ">" + "[a-zA-Z]+" + "</" + NAME.getExportFieldName() + ">"));
        assertTrue(dummies[8].matches("\\t{2}<" + NUM.getExportFieldName()    + ">" + "[0-9]+"    + "</" + NUM.getExportFieldName() + ">"));
        assertTrue(dummies[9].matches("\\t{2}<" + GROUP.getExportFieldName()  + ">" + "[0-9]+"    + "</" + GROUP.getExportFieldName() + ">"));
        assertTrue(dummies[10].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[11].matches("</[a-zA-Z]+List>"));
    }

    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, NamingStrategy strategy) {
        final String expectedNameField = strategy.toStrategy(NAME.getExportFieldName());
        final String expectedGroupField = GROUP.getExportFieldName();
        final String expectedNumField = strategy.toStrategy(NUM.getExportFieldName());

        assertTrue(dummies[0].matches("<[a-zA-Z]+List>"));
        assertTrue(dummies[1].matches("\\t<[a-zA-Z]+>"));
        assertTrue(dummies[2].matches("\\t{2}<" + expectedNameField   + ">" + "[a-zA-Z]+" + "</" + expectedNameField + ">"));
        assertTrue(dummies[3].matches("\\t{2}<" + expectedNumField    + ">" + "[0-9]+" + "</" + expectedNumField + ">"));
        assertTrue(dummies[4].matches("\\t{2}<" + expectedGroupField  + ">" + "[0-9]+" + "</" + expectedGroupField + ">"));
        assertTrue(dummies[5].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[6].matches("\\t<[a-zA-Z]+>"));
        assertTrue(dummies[7].matches("\\t{2}<" + expectedNameField   + ">" + "[a-zA-Z]+" + "</" + expectedNameField + ">"));
        assertTrue(dummies[8].matches("\\t{2}<" + expectedNumField    + ">" + "[0-9]+" + "</" + expectedNumField + ">"));
        assertTrue(dummies[9].matches("\\t{2}<" + expectedGroupField  + ">" + "[0-9]+" + "</" + expectedGroupField + ">"));
        assertTrue(dummies[10].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[11].matches("</[a-zA-Z]+List>"));
    }
}
