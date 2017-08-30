package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.CsvExporter;
import io.dummymaker.export.IExporter;
import io.dummymaker.factory.GenProduceFactory;
import io.dummymaker.factory.IProduceFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class CsvExportAsStringTest {

    private IProduceFactory<Dummy> produceFactory = new GenProduceFactory<>(Dummy.class);

    @Test
    public void exportSingleDummyInCsv() {
        Dummy dummy = produceFactory.produce();
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class);

        String dummyAsJsonString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsJsonString);

        String[] csvArray = dummyAsJsonString.split(",");
        assertEquals(3, csvArray.length);

        assertTrue(csvArray[0].matches("[a-zA-Z0-9]+"));
        assertTrue(csvArray[1].matches("[0-9]+"));
        assertTrue(csvArray[2].matches("null"));
    }

    @Test
    public void exportSingleDummyWithStringWrapAndHeader() {
        Dummy dummy = produceFactory.produce();
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class, null, true, true);

        String dummyAsJsonString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsJsonString);

        String[] csvArray = dummyAsJsonString.split("\n");
        assertEquals(2, csvArray.length);

        String[] headerArray = csvArray[0].split(",");
        assertEquals(3, headerArray.length);
        assertTrue(headerArray[0].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[1].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[2].matches("[a-zA-Z0-9]+"));

        String[] valueArray = csvArray[1].split(",");
        assertEquals(3, valueArray.length);
        assertTrue(valueArray[0].matches("\'[a-zA-Z0-9]+\'"));
        assertTrue(valueArray[1].matches("\'[0-9]+\'"));
        assertTrue(valueArray[2].matches("null"));
    }

    @Test
    public void exportListOfDummiesInCsv() {
        List<Dummy> dummies = produceFactory.produce(2);
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class);

        String dummyAsJsonString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsJsonString);

        String[] csvArray = dummyAsJsonString.split("\n");
        assertEquals(2, csvArray.length);

        String[] valueArray1 = csvArray[0].split(",");
        assertEquals(3, valueArray1.length);
        assertTrue(valueArray1[0].matches("[a-zA-Z0-9]+"));
        assertTrue(valueArray1[1].matches("[0-9]+"));
        assertTrue(valueArray1[2].matches("[0-9]+"));

        String[] valueArray2 = csvArray[1].split(",");
        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("[a-zA-Z0-9]+"));
        assertTrue(valueArray2[1].matches("[0-9]+"));
        assertTrue(valueArray2[2].matches("[0-9]+"));
    }

    @Test
    public void exportListDummyWithStringWrapAndHeader() {
        List<Dummy> dummies = produceFactory.produce(2);
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class, null, true, true);

        String dummyAsJsonString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsJsonString);

        String[] csvArray = dummyAsJsonString.split("\n");
        assertEquals(3, csvArray.length);

        String[] headerArray = csvArray[0].split(",");
        assertEquals(3, headerArray.length);
        assertTrue(headerArray[0].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[1].matches("[a-zA-Z0-9]+"));
        assertTrue(headerArray[2].matches("[a-zA-Z0-9]+"));

        String[] valueArray1 = csvArray[1].split(",");
        assertEquals(3, valueArray1.length);
        assertTrue(valueArray1[0].matches("\'[a-zA-Z0-9]+\'"));
        assertTrue(valueArray1[1].matches("\'[0-9]+\'"));
        assertTrue(valueArray1[2].matches("[0-9]+"));

        String[] valueArray2 = csvArray[2].split(",");
        assertEquals(3, valueArray2.length);
        assertTrue(valueArray2[0].matches("\'[a-zA-Z0-9]+\'"));
        assertTrue(valueArray2[1].matches("\'[0-9]+\'"));
        assertTrue(valueArray2[2].matches("[0-9]+"));
    }
}
