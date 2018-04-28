package io.dummymaker.export.validators;

import io.dummymaker.data.DummyTime.Fields;
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
public class XmlValidator implements IValidator {

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("<[a-zA-Z]+>"));
        assertTrue(dummy[1].matches("\\t<" + GROUP.exportName()    + ">" + "[0-9]+"    + "</" + GROUP.exportName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + NUM.exportName()      + ">" + "[0-9]+"      + "</" + NUM.exportName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + NAME.exportName()     + ">" + "[a-zA-Z]+" + "</" + NAME.exportName() + ">"));
        assertTrue(dummy[4].matches("</[a-zA-Z]+>"));
    }

    @Override
    public void isSingleAutoDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("<DummyAuto>"));
        assertTrue(dummy[1].matches("\\t<aLong>" + "-?[0-9]+" + "</aLong>"));
        assertTrue(dummy[2].matches("\\t<anInt>" + "-?[0-9]+" + "</anInt>"));
        assertTrue(dummy[3].matches("</DummyAuto>"));
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

        assertTrue(dummies[0].matches("<[a-zA-Z]+List>"));
        assertTrue(dummies[1].matches("\\t<[a-zA-Z]+>"));
        assertTrue(dummies[2].matches("\\t{2}<" + expectedGroupField  + ">" + "[0-9]+" + "</" + expectedGroupField + ">"));
        assertTrue(dummies[3].matches("\\t{2}<" + expectedNumField    + ">" + "[0-9]+" + "</" + expectedNumField + ">"));
        assertTrue(dummies[4].matches("\\t{2}<" + expectedNameField   + ">" + "[a-zA-Z]+" + "</" + expectedNameField + ">"));
        assertTrue(dummies[5].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[6].matches("\\t<[a-zA-Z]+>"));
        assertTrue(dummies[7].matches("\\t{2}<" + expectedGroupField  + ">" + "[0-9]+" + "</" + expectedGroupField + ">"));
        assertTrue(dummies[8].matches("\\t{2}<" + expectedNumField    + ">" + "[0-9]+" + "</" + expectedNumField + ">"));
        assertTrue(dummies[9].matches("\\t{2}<" + expectedNameField   + ">" + "[a-zA-Z]+" + "</" + expectedNameField + ">"));
        assertTrue(dummies[10].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[11].matches("</[a-zA-Z]+List>"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("<[a-zA-Z]+>"));
        assertTrue(dummy[1].matches("\\t<" + Fields.LOCAL_TIME.getName() + ">" + "[0-9]+"
                + "</" + Fields.LOCAL_TIME.getName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + Fields.LOCAL_DATE.getName()   + ">" + "[0-9]+"
                + "</" + Fields.LOCAL_DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.LOCAL_DATETIME.getName()  + ">" + "[a-zA-Z]+"
                + "</" + Fields.LOCAL_DATETIME.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.TIMESTAMP.getName()  + ">" + "[a-zA-Z]+"
                + "</" + Fields.TIMESTAMP.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.DATE.getName()  + ">" + "[a-zA-Z]+"
                + "</" + Fields.DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.DATE.getName()  + ">" + "[a-zA-Z]+"
                + "</" + Fields.DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.DATE.getName()  + ">" + "[a-zA-Z]+"
                + "</" + Fields.DATE.getName() + ">"));
        assertTrue(dummy[4].matches("</[a-zA-Z]+>"));
    }
}
