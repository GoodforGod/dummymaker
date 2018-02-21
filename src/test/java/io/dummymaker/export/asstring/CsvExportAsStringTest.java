package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.export.validation.CsvValidation;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class CsvExportAsStringTest {

    private IProduceFactory produceFactory = new GenProduceFactory();

    private CsvValidation validation = new CsvValidation();

    private final char SEPARATOR = ',';

    @Test
    public void exportSingleDummyInCsv() {
        Dummy dummy = produceFactory.produce(Dummy.class);
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split(",");
        assertEquals(3, csvArray.length);

        validation.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportSingleDummyWithStringWrapAndHeader() {
        Dummy dummy = produceFactory.produce(Dummy.class);
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class, null, PresetStrategies.DEFAULT.getStrategy(), true, true, SEPARATOR);

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, SEPARATOR);
    }

    @Test
    public void exportListOfDummiesInCsv() {
        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isTwoDummiesValid(csvArray);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeader() {
        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class, null, PresetStrategies.DEFAULT.getStrategy(),true, true, SEPARATOR);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, SEPARATOR);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() {
        final PresetStrategies strategy = PresetStrategies.UNDERSCORED_UPPER_CASE;

        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter<Dummy> exporter = new CsvExporter<>(Dummy.class, null, strategy.getStrategy(),true, true, SEPARATOR);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, SEPARATOR, strategy);
    }
}
