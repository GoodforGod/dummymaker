package io.dummymaker.export.asfile;


import io.dummymaker.export.Cases;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.validators.CsvValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import java.util.List;
import org.junit.Test;


/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class CsvExportAsFileTest extends FileExportAssert {

    private final GenFactory factory = new GenFactory();
    private final CsvValidator validation = new CsvValidator();

    private final Format format = Format.CSV;

    public CsvExportAsFileTest() {
        super(new CsvExporter(), new CsvValidator(), Format.CSV, 3, 3, 2);
    }

    // @Test
    public void exportSingleDummyWithStringWrapAndHeader() throws Exception {
        final Dummy dummy = factory.build(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withHeader().withSeparator(CsvExporter.DEFAULT_SEPARATOR);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeader() throws Exception {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withHeader();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    // @Test
    public void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() throws Exception {
        final ICase strategy = Cases.UPPER_SNAKE_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new CsvExporter().withHeader().withCase(strategy);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, CsvExporter.DEFAULT_SEPARATOR, strategy);
    }
}
