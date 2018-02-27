package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.naming.IStrategy;
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
    public void exportSingleDummyInCsv() throws Exception {
        Dummy dummy = produceFactory.produce(Dummy.class);
        IExporter exporter = new CsvExporter();

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split(",");
        assertEquals(3, csvArray.length);

        validation.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportSingleDummyWithStringWrapAndHeader() throws Exception {
        Dummy dummy = produceFactory.produce(Dummy.class);
        IExporter exporter = new CsvExporter().withTextWrap().withHeader().withPath(null);

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, SEPARATOR);
    }

    @Test
    public void exportListOfDummiesInCsv() throws Exception {
        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter exporter = new CsvExporter().withPath("             ").withSeparator(SEPARATOR);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isTwoDummiesValid(csvArray);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeader() throws Exception {
        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter exporter = new CsvExporter().withHeader().withTextWrap().withStrategy(null);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, SEPARATOR);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() throws Exception {
        final IStrategy strategy = PresetStrategies.UNDERSCORED_UPPER_CASE.getStrategy();

        List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        IExporter exporter = new CsvExporter().withHeader().withTextWrap().withStrategy(strategy);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, SEPARATOR, strategy);
    }
}
