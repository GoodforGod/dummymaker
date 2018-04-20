package io.dummymaker.export.validators;

import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class CsvValidator implements IValidator {

    @Override
    public void isSingleDummyValid(String[] dummy) {
        // first line values check
        assertTrue(dummy[0].matches("[0-9]+"));
        assertTrue(dummy[1].matches("[0-9]+"));
        assertTrue(dummy[2].matches("[a-zA-Z0-9]+"));
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
        assertTrue(headerArray[0].matches(GROUP.getExportFieldName()));
        assertTrue(headerArray[1].matches(NUM.getExportFieldName()));
        assertTrue(headerArray[2].matches(NAME.getExportFieldName()));

        // first line values check
        assertEquals(3, valueArray.length);
        assertTrue(valueArray[0].matches("\'[0-9]+\'"));
        assertTrue(valueArray[1].matches("[0-9]+"));
        assertTrue(valueArray[2].matches("\'[a-zA-Z0-9]+\'"));
    }

    public void isTwoDummiesValidWithHeader(String[] dummies, char separator) {
        isTwoDummiesValidWithHeaderAndNameStrategy(dummies, separator, Cases.DEFAULT.value());
    }

    @Override
    public void isTwoDummiesValidWithNamingStrategy(String[] dummies, ICase strategy) {
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

    public void isTwoDummiesValidWithHeaderAndNameStrategy(String[] dummies, char separator, ICase strategy) {
        final String expectedNameField = strategy.format(NAME.getExportFieldName());
        final String expectedGroupField = GROUP.getExportFieldName();
        final String expectedNumField = strategy.format(NUM.getExportFieldName());

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
        assertTrue(valueArray1[0].matches("\'[0-9]+\'"));
        assertTrue(valueArray1[1].matches("[0-9]+"));
        assertTrue(valueArray1[2].matches("\'[a-zA-Z0-9]+\'"));

        // second line values check
        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("\'[0-9]+\'"));
        assertTrue(valueArray2[1].matches("[0-9]+"));
        assertTrue(valueArray2[2].matches("\'[a-zA-Z0-9]+\'"));
    }
}
