package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.export.validators.CsvValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class CsvExportAsFileTest  extends ExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final CsvValidator validation = new CsvValidator();

    private final Format format = Format.CSV;

    private final char SEPARATOR = ',';

    @Test
    public void exportSingleDummy() throws Exception {
        final Dummy dummy = produceFactory.produce(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter();

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split(",");
        assertEquals(3, csvArray.length);

        validation.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportListOfDummies() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withPath("             ").withSeparator(SEPARATOR);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isTwoDummiesValid(csvArray);
    }

    @Test
    public void exportSingleDummyList() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 1);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withPath("             ").withSeparator(SEPARATOR);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split(",");
        assertEquals(3, csvArray.length);

        validation.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportSingleDummyWithStringWrapAndHeader() throws Exception {
        final Dummy dummy = produceFactory.produce(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withTextWrap().withHeader().withPath(null);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, SEPARATOR);
    }


    @Test
    public void exportListDummyWithStringWrapAndHeader() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withHeader().withTextWrap().withStrategy(null);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, SEPARATOR);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() throws Exception {
        final IStrategy strategy = PresetStrategies.UNDERSCORED_UPPER_CASE.getStrategy();

        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withHeader().withTextWrap().withStrategy(strategy);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, SEPARATOR, strategy);
    }
}
