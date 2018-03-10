package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.Strategies;
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
public class CsvExportAsFileTest extends FileExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final CsvValidator validation = new CsvValidator();

    private final Format format = Format.CSV;

    public CsvExportAsFileTest() {
        super(new CsvExporter().withPath(null).withPath("             ").withStrategy(null),
                new CsvValidator(), Format.CSV, 3, 2);
    }

    @Test
    public void exportSingleDummyWithStringWrapAndHeader() throws Exception {
        final Dummy dummy = produceFactory.produce(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withStrategy(null).withTextWrap()
                .withHeader().withSeparator(CsvExporter.DEFAULT_SEPARATOR);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
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

        validation.isTwoDummiesValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() throws Exception {
        final IStrategy strategy = Strategies.UNDERSCORED_UPPER_CASE.getStrategy();

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

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, CsvExporter.DEFAULT_SEPARATOR, strategy);
    }
}
