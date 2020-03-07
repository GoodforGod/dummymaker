package io.dummymaker.export.validators;

import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.model.DummyTime.Patterns;
import io.dummymaker.model.DummyTimeFormatter;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static io.dummymaker.model.DummyTime.Fields.*;
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
        assertTrue(dummy[1].matches("\\t<" + GROUP.exportName() + ">" + "[0-9]+" + "</" + GROUP.exportName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + NUM.exportName() + ">" + "[0-9]+" + "</" + NUM.exportName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + NAME.exportName() + ">" + "[a-zA-Z]+" + "</" + NAME.exportName() + ">"));
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
        assertTrue(dummies[2].matches("\\t{2}<" + expectedGroupField + ">" + "[0-9]+" + "</" + expectedGroupField + ">"));
        assertTrue(dummies[3].matches("\\t{2}<" + expectedNumField + ">" + "[0-9]+" + "</" + expectedNumField + ">"));
        assertTrue(dummies[4].matches("\\t{2}<" + expectedNameField + ">" + "[a-zA-Z]+" + "</" + expectedNameField + ">"));
        assertTrue(dummies[5].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[6].matches("\\t<[a-zA-Z]+>"));
        assertTrue(dummies[7].matches("\\t{2}<" + expectedGroupField + ">" + "[0-9]+" + "</" + expectedGroupField + ">"));
        assertTrue(dummies[8].matches("\\t{2}<" + expectedNumField + ">" + "[0-9]+" + "</" + expectedNumField + ">"));
        assertTrue(dummies[9].matches("\\t{2}<" + expectedNameField + ">" + "[a-zA-Z]+" + "</" + expectedNameField + ">"));
        assertTrue(dummies[10].matches("\\t</[a-zA-Z]+>"));
        assertTrue(dummies[11].matches("</[a-zA-Z]+List>"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("<TimeDummyClass>"));
        assertTrue(dummy[1].matches("\\t<" + LOCAL_TIME.getName() + ">"
                + Patterns.LOCAL_TIME.getPattern()
                + "</" + LOCAL_TIME.getName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + LOCAL_DATE.getName() + ">"
                + Patterns.LOCAL_DATE.getPattern()
                + "</" + LOCAL_DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + LOCAL_DATETIME.getName() + ">"
                + Patterns.LOCAL_DATETIME.getPattern()
                + "</" + LOCAL_DATETIME.getName() + ">"));
        assertTrue(dummy[4].matches("\\t<" + TIMESTAMP.getName() + ">"
                + Patterns.TIMESTAMP.getPattern()
                + "</" + TIMESTAMP.getName() + ">"));
        assertTrue(dummy[5].matches("\\t<" + DATE.getName() + ">"
                + Patterns.DATE_SQL.getPattern()
                + "</" + DATE.getName() + ">"));
        assertTrue(dummy[6].matches("\\t<" + DATE_COVERAGE.getName() + ">"
                + Patterns.DATE.getPattern()
                + "</" + DATE_COVERAGE.getName() + ">"));
        assertTrue(dummy[7].matches("\\t<" + LOCAL_DATETIME_STRING.getName() + ">"
                + Patterns.LOCAL_DATETIME.getPattern()
                + "</" + LOCAL_DATETIME_STRING.getName() + ">"));
        assertTrue(dummy[8].matches("\\t<" + LOCAL_DATETIME_OBJECT.getName() + ">"
                + Patterns.LOCAL_DATETIME.getPattern()
                + "</" + LOCAL_DATETIME_OBJECT.getName() + ">"));
        assertTrue(dummy[9].matches("</TimeDummyClass>"));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("<DummyUnixTime>"));
        assertTrue(dummy[1].matches("\\t<" + LOCAL_TIME.getName() + ">"
                + "[0-9]+"
                + "</" + LOCAL_TIME.getName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + LOCAL_DATE.getName() + ">"
                + "[0-9]+"
                + "</" + LOCAL_DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + LOCAL_DATETIME.getName() + ">"
                + "[0-9]+"
                + "</" + LOCAL_DATETIME.getName() + ">"));
        assertTrue(dummy[4].matches("\\t<" + TIMESTAMP.getName() + ">"
                + "[0-9]+"
                + "</" + TIMESTAMP.getName() + ">"));
        assertTrue(dummy[5].matches("\\t<" + DATE.getName() + ">"
                + "[0-9]+"
                + "</" + DATE.getName() + ">"));
        assertTrue(dummy[6].matches("\\t<" + DATE_COVERAGE.getName() + ">"
                + "[0-9]+"
                + "</" + DATE_COVERAGE.getName() + ">"));
        assertTrue(dummy[7].matches("\\t<" + LOCAL_DATETIME_STRING.getName() + ">"
                + Patterns.LOCAL_DATETIME.getPattern().pattern()
                + "</" + LOCAL_DATETIME_STRING.getName() + ">"));
        assertTrue(dummy[8].matches("\\t<" + LOCAL_DATETIME_OBJECT.getName() + ">"
                + Patterns.LOCAL_DATETIME.getPattern().pattern()
                + "</" + LOCAL_DATETIME_OBJECT.getName() + ">"));
        assertTrue(dummy[9].matches("</DummyUnixTime>"));
    }

    @Override
    public void isDummyTimeFormatterValid(String[] dummy) {
        assertTrue(dummy[0].matches("<DummyTimeFormatter>"));
        assertTrue(dummy[1].matches("\\t<" + LOCAL_TIME.getName() + ">"
                + DummyTimeFormatter.Patterns.LOCAL_TIME.getPattern()
                + "</" + LOCAL_TIME.getName() + ">"));
        assertTrue(dummy[2].matches("\\t<" + LOCAL_DATE.getName() + ">"
                + DummyTimeFormatter.Patterns.LOCAL_DATE.getPattern()
                + "</" + LOCAL_DATE.getName() + ">"));
        assertTrue(dummy[3].matches("\\t<" + LOCAL_DATETIME.getName() + ">"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern()
                + "</" + LOCAL_DATETIME.getName() + ">"));
        assertTrue(dummy[4].matches("\\t<" + TIMESTAMP.getName() + ">"
                + DummyTimeFormatter.Patterns.TIMESTAMP.getPattern()
                + "</" + TIMESTAMP.getName() + ">"));
        assertTrue(dummy[5].matches("\\t<" + DATE.getName() + ">"
                + DummyTimeFormatter.Patterns.DATE_SQL.getPattern()
                + "</" + DATE.getName() + ">"));
        assertTrue(dummy[6].matches("\\t<" + DATE_COVERAGE.getName() + ">"
                + DummyTimeFormatter.Patterns.DATE.getPattern()
                + "</" + DATE_COVERAGE.getName() + ">"));
        assertTrue(dummy[7].matches("\\t<" + LOCAL_DATETIME_STRING.getName() + ">"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern()
                + "</" + LOCAL_DATETIME_STRING.getName() + ">"));
        assertTrue(dummy[8].matches("\\t<" + LOCAL_DATETIME_OBJECT.getName() + ">"
                + DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern()
                + "</" + LOCAL_DATETIME_OBJECT.getName() + ">"));
        assertTrue(dummy[9].matches("</DummyTimeFormatter>"));
    }
}
