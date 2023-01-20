package io.dummymaker.export.validators;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.export.CsvExporter;
import io.dummymaker.model.DummyTime.Patterns;
import io.dummymaker.model.DummyTimeFormatter;

/**
 * @author GoodforGod
 * @since 01.09.2017
 */
public class CsvValidatorChecker implements ValidatorChecker {

    @Override
    public void isSingleDummyListValid(String[] dummy) {
        // first line values check
        assertTrue(dummy[0].matches("[0-9]+"));
        assertTrue(dummy[1].matches("[0-9]+"));
        assertTrue(dummy[2].matches("[a-zA-Z0-9]+"));
    }

    @Override
    public void isSingleDummyValid(String[] dummy) {
        // first line values check
        assertTrue(dummy[0].matches("[0-9]+"));
        assertTrue(dummy[1].matches("[0-9]+"));
        assertTrue(dummy[2].matches("[a-zA-Z0-9]+"));
    }

    @Override
    public void isSingleAutoDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("-?[0-9]+"));
        assertTrue(dummy[1].matches("-?[0-9]+"));
    }

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        String[] valueArray1 = dummies[0].split(",");

        // first line values check
        assertEquals(3, valueArray1.length);
        assertTrue(valueArray1[0].matches("[0-9]+"));
        assertTrue(valueArray1[1].matches("[0-9]+"));
        assertTrue(valueArray1[2].matches("[a-zA-Z0-9]+"));

        // second line values check
        String[] valueArray2 = dummies[1].split(",");
        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("[0-9]+"));
        assertTrue(valueArray2[1].matches("[0-9]+"));
        assertTrue(valueArray2[2].matches("[a-zA-Z0-9]+"));
    }

    public void isSingleDummyValidWithHeader(String[] dummy, char separator) {
        String[] headerArray = dummy[0].split(String.valueOf(separator));
        String[] valueArray = dummy[1].split(String.valueOf(separator));

        // header check
        assertEquals(3, headerArray.length);
        assertTrue(headerArray[0].matches(GROUP.exportName()));
        assertTrue(headerArray[1].matches(NUM.exportName()));
        assertTrue(headerArray[2].matches(NAME.exportName()));

        // first line values check
        assertEquals(3, valueArray.length);
        assertTrue(valueArray[0].matches("[0-9]+"));
        assertTrue(valueArray[1].matches("[0-9]+"));
        assertTrue(valueArray[2].matches("[a-zA-Z0-9]+"));
    }

    public void isTwoDummiesValidWithHeader(String[] dummies, char separator) {
        isTwoDummiesValidWithHeaderAndNameStrategy(dummies, separator, Cases.DEFAULT.value());
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, Case strategy) {
        String[] valueArray1 = dummies[0].split(String.valueOf(CsvExporter.DEFAULT_SEPARATOR));
        String[] valueArray2 = dummies[1].split(String.valueOf(CsvExporter.DEFAULT_SEPARATOR));

        // first line values check
        assertEquals(3, valueArray1.length);
        assertTrue(valueArray1[0].matches("\'[0-9]+\'"));
        assertTrue(valueArray1[1].matches("[0-9]+"));
        assertTrue(valueArray1[2].matches("\'[a-zA-Z0-9]+\'"));

        // second line values check
        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("\'[0-9]+\'"));
        assertTrue(valueArray2[1].matches("[0-9]+"));
        assertTrue(valueArray2[2].matches("\'[a-zA-Z0-9]+\'"));
    }

    public void isTwoDummiesValidWithHeaderAndNameStrategy(String[] dummies, char separator, Case strategy) {
        final String expectedNameField = strategy.apply(NAME.exportName());
        final String expectedGroupField = GROUP.exportName();
        final String expectedNumField = strategy.apply(NUM.exportName());

        String[] headerArray = dummies[0].split(String.valueOf(separator));
        String[] valueArray1 = dummies[1].split(String.valueOf(separator));
        String[] valueArray2 = dummies[2].split(String.valueOf(separator));

        // header check
        assertEquals(3, headerArray.length);
        assertTrue(headerArray[0].matches(expectedGroupField));
        assertTrue(headerArray[1].matches(expectedNumField));
        assertTrue(headerArray[2].matches(expectedNameField));

        // first line values check
        assertEquals(3, valueArray1.length);
        assertTrue(valueArray1[0].matches("[0-9]+"));
        assertTrue(valueArray1[1].matches("[0-9]+"));
        assertTrue(valueArray1[2].matches("[a-zA-Z0-9]+"));

        // second line values check
        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("[0-9]+"));
        assertTrue(valueArray2[1].matches("[0-9]+"));
        assertTrue(valueArray2[2].matches("[a-zA-Z0-9]+"));
    }

    @Override
    public void isDummyTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches(Patterns.LOCAL_TIME.getPattern().pattern()));
        assertTrue(dummy[1].matches(Patterns.LOCAL_DATE.getPattern().pattern()));
        assertTrue(dummy[2].matches(Patterns.LOCAL_DATETIME.getPattern().pattern()));
        assertTrue(dummy[3].matches(Patterns.OFFSET_TIME.getPattern().pattern()));
        assertTrue(dummy[4].matches(Patterns.OFFSET_DATETIME.getPattern().pattern()));
        assertTrue(dummy[5].matches(Patterns.TIMESTAMP.getPattern().pattern()));
        assertTrue(dummy[6].matches(Patterns.DATE.getPattern().pattern()));
        assertTrue(dummy[7].matches(Patterns.DATE_SQL.getPattern().pattern()));
        assertTrue(dummy[8].matches(Patterns.OFFSET_DATETIME.getPattern().pattern()));
        assertTrue(dummy[9].matches(Patterns.OFFSET_DATETIME.getPattern().pattern()));
    }

    @Override
    public void isDummyUnixTimeValid(String[] dummy) {
        assertTrue(dummy[0].matches("[0-9]+"));
        assertTrue(dummy[1].matches("[0-9]+"));
        assertTrue(dummy[2].matches("[0-9]+"));
        assertTrue(dummy[3].matches("[0-9]+"));
        assertTrue(dummy[4].matches("[0-9]+"));
        assertTrue(dummy[5].matches("[0-9]+"));
        assertTrue(dummy[6].matches("[0-9]+"));
        assertTrue(dummy[7].matches("[0-9]+"));
        assertTrue(dummy[8].matches("[0-9]+"));
        assertTrue(dummy[9].matches("[0-9]+"));
    }

    @Override
    public void isDummyTimeFormatterValid(String[] dummy) {
        assertTrue(dummy[0].matches(DummyTimeFormatter.Patterns.LOCAL_TIME.getPattern().pattern()));
        assertTrue(dummy[1].matches(DummyTimeFormatter.Patterns.LOCAL_DATE.getPattern().pattern()));
        assertTrue(dummy[2].matches(DummyTimeFormatter.Patterns.LOCAL_DATETIME.getPattern().pattern()));
        assertTrue(dummy[3].matches(Patterns.OFFSET_TIME.getPattern().pattern()));
        assertTrue(dummy[4].matches(Patterns.OFFSET_DATETIME.getPattern().pattern()));
        assertTrue(dummy[5].matches(DummyTimeFormatter.Patterns.TIMESTAMP.getPattern().pattern()));
        assertTrue(dummy[6].matches(DummyTimeFormatter.Patterns.DATE.getPattern().pattern()));
        assertTrue(dummy[7].matches(DummyTimeFormatter.Patterns.DATE_SQL.getPattern().pattern()));
        assertTrue(dummy[8].matches(DummyTimeFormatter.ISO_DATE_TIME_PATTERN));
        assertTrue(dummy[9].matches(DummyTimeFormatter.ISO_DATE_TIME_PATTERN));
    }
}
