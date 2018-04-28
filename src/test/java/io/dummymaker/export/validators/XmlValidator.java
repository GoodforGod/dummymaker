package io.dummymaker.export.validators;

import io.dummymaker.data.DummyTime;
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
        assertTrue(dummy[0].matches("<DummyTime>"));
        assertTrue(dummy[1].matches("\\t<" + Fields.LOCAL_TIME.getName() + ">"
                + DummyTime.Patterns.LOCAL_TIME.getPattern()
                + "</" + Fields.LOCAL_TIME.getName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + Fields.LOCAL_DATE.getName()   + ">"
                + DummyTime.Patterns.LOCAL_DATE.getPattern()
                + "</" + Fields.LOCAL_DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.LOCAL_DATETIME.getName()  + ">"
                + DummyTime.Patterns.LOCAL_DATETIME.getPattern()
                + "</" + Fields.LOCAL_DATETIME.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.TIMESTAMP.getName()  + ">"
                + DummyTime.Patterns.TIMESTAMP.getPattern()
                + "</" + Fields.TIMESTAMP.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.DATE.getName()  + ">"
                + DummyTime.Patterns.DATE.getPattern()
                + "</" + Fields.DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.LOCAL_DATETIME_STRING.getName()  + ">"
                + DummyTime.Patterns.LOCAL_DATETIME.getPattern()
                + "</" + Fields.LOCAL_DATETIME_STRING.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + Fields.LOCAL_DATETIME_OBJECT.getName()  + ">"
                + DummyTime.Patterns.LOCAL_DATETIME.getPattern()
                + "</" + Fields.LOCAL_DATETIME_OBJECT.getName() + ">"));
        assertTrue(dummy[4].matches("</DummyTime>"));
    }
}
