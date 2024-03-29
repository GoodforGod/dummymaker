package io.goodforgod.dummymaker.export.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.testdata.Dummy;
import io.goodforgod.dummymaker.testdata.DummyTime;
import io.goodforgod.dummymaker.testdata.DummyTimeFormatter;

/**
 * @author GoodforGod
 * @since 01.09.2017
 */
public class CsvValidatorChecker implements ValidatorChecker {

    private static final char DEFAULT_SEPARATOR = ',';

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

    @Override
    public void isTwoDummiesWithNumFieldValid(String[] dummies) {
        String[] valueArray1 = dummies[0].split(",");

        // first line values check
        assertEquals(1, valueArray1.length);
        assertTrue(valueArray1[0].matches("[0-9]+"));

        // second line values check
        String[] valueArray2 = dummies[1].split(",");
        assertEquals(1, valueArray2.length);
        assertTrue(valueArray2[0].matches("[0-9]+"));
    }

    public void isSingleDummyValidWithHeader(String[] dummy, char separator) {
        String[] headerArray = dummy[0].split(String.valueOf(separator));
        String[] valueArray = dummy[1].split(String.valueOf(separator));

        // header check
        assertEquals(3, headerArray.length);
        assertTrue(headerArray[0].matches(Dummy.DummyFields.GROUP.exportName()));
        assertTrue(headerArray[1].matches(Dummy.DummyFields.NUM.exportName()));
        assertTrue(headerArray[2].matches(Dummy.DummyFields.NAME.exportName()));

        // first line values check
        assertEquals(3, valueArray.length);
        assertTrue(valueArray[0].matches("[0-9]+"));
        assertTrue(valueArray[1].matches("[0-9]+"));
        assertTrue(valueArray[2].matches("[a-zA-Z0-9]+"));
    }

    public void isTwoDummiesValidWithHeader(String[] dummies, char separator) {
        isTwoDummiesValidWithHeaderAndNameStrategy(dummies, separator, NamingCases.DEFAULT);
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, NamingCase strategy) {
        String[] valueArray1 = dummies[0].split(String.valueOf(DEFAULT_SEPARATOR));
        String[] valueArray2 = dummies[1].split(String.valueOf(DEFAULT_SEPARATOR));

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

    public void isTwoDummiesValidWithHeaderAndNameStrategy(String[] dummies, char separator, NamingCase strategy) {
        final String expectedNameField = strategy.apply(Dummy.DummyFields.NAME.exportName()).toString();
        final String expectedGroupField = Dummy.DummyFields.GROUP.exportName();
        final String expectedNumField = strategy.apply(Dummy.DummyFields.NUM.exportName()).toString();

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
        assertTrue(dummy[0].matches(DummyTime.Patterns.LOCAL_TIME.getPattern().pattern()));
        assertTrue(dummy[1].matches(DummyTime.Patterns.LOCAL_DATE.getPattern().pattern()));
        assertTrue(dummy[2].matches(DummyTime.Patterns.LOCAL_DATETIME.getPattern().pattern()));
        assertTrue(dummy[3].matches(DummyTime.Patterns.OFFSET_TIME.getPattern().pattern()));
        assertTrue(dummy[4].matches(DummyTime.Patterns.OFFSET_DATETIME.getPattern().pattern()));
        assertTrue(dummy[5].matches(DummyTime.Patterns.TIMESTAMP.getPattern().pattern()));
        assertTrue(dummy[6].matches(DummyTime.Patterns.DATE.getPattern().pattern()));
        assertTrue(dummy[7].matches(DummyTime.Patterns.DATE_SQL.getPattern().pattern()));
        assertTrue(dummy[8].matches(DummyTime.Patterns.OFFSET_DATETIME.getPattern().pattern()));
        assertTrue(dummy[9].matches(DummyTime.Patterns.OFFSET_DATETIME.getPattern().pattern()));
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
        assertTrue(dummy[3].matches(DummyTime.Patterns.OFFSET_TIME.getPattern().pattern()));
        assertTrue(dummy[4].matches(DummyTime.Patterns.OFFSET_DATETIME.getPattern().pattern()));
        assertTrue(dummy[5].matches(DummyTimeFormatter.Patterns.TIMESTAMP.getPattern().pattern()));
        assertTrue(dummy[6].matches(DummyTimeFormatter.Patterns.DATE.getPattern().pattern()));
        assertTrue(dummy[7].matches(DummyTimeFormatter.Patterns.DATE_SQL.getPattern().pattern()));
        assertTrue(dummy[8].matches(DummyTimeFormatter.ISO_DATE_TIME_PATTERN));
        assertTrue(dummy[9].matches(DummyTimeFormatter.ISO_DATE_TIME_PATTERN));
    }
}
