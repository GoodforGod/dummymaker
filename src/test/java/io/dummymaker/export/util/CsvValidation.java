package io.dummymaker.export.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 01.09.2017
 */
public class CsvValidation implements IValidation {

    @Override
    public void isSingleDummyValid(String[] dummy) {
        assertTrue(dummy[0].matches("[a-zA-Z0-9]+"));
        assertTrue(dummy[1].matches("[0-9]+"));
        assertTrue(dummy[2].matches("null"));
}

    @Override
    public void isTwoDummiesValid(String[] dummies) {
        String[] valueArray1 = dummies[0].split(",");
        assertEquals(3, valueArray1.length);
        assertTrue(valueArray1[0].matches("[a-zA-Z0-9]+"));
        assertTrue(valueArray1[1].matches("[0-9]+"));
        assertTrue(valueArray1[2].matches("[0-9]+"));

        String[] valueArray2 = dummies[1].split(",");
        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("[a-zA-Z0-9]+"));
        assertTrue(valueArray2[1].matches("[0-9]+"));
        assertTrue(valueArray2[2].matches("[0-9]+"));
    }

    public void isSingleDummyValidWithHeader(String[] dummy) {
        String[] headerArray = dummy[0].split(",");
        assertEquals(3, headerArray.length);
        assertTrue(headerArray[0].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[1].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[2].matches("[a-zA-Z0-9]+"));

        String[] valueArray = dummy[1].split(",");
        assertEquals(3, valueArray.length);
        assertTrue(valueArray[0].matches("\'[a-zA-Z0-9]+\'"));
        assertTrue(valueArray[1].matches("\'[0-9]+\'"));
        assertTrue(valueArray[2].matches("null"));
    }

    public void isTwoDummiesValidWithHeader(String[] dummies) {
        String[] headerArray = dummies[0].split(",");
        String[] valueArray1 = dummies[1].split(",");
        String[] valueArray2 = dummies[2].split(",");

        assertEquals(3, headerArray.length);
        assertTrue(headerArray[0].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[1].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[2].matches("[a-zA-Z0-9]+"));

        assertEquals(3, valueArray1.length);
        assertTrue(valueArray1[0].matches("\'[a-zA-Z0-9]+\'"));
        assertTrue(valueArray1[1].matches("\'[0-9]+\'"));
        assertTrue(valueArray1[2].matches("[0-9]+"));

        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("\'[a-zA-Z0-9]+\'"));
        assertTrue(valueArray2[1].matches("\'[0-9]+\'"));
        assertTrue(valueArray2[2].matches("[0-9]+"));
    }
}
