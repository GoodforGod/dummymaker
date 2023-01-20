package io.dummymaker.export;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.export.validators.CsvValidatorChecker;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class CsvExportAsFileTests extends FileExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final CsvValidatorChecker validation = new CsvValidatorChecker();

    private final Format format = Format.CSV;

    public CsvExportAsFileTests() {
        super(CsvExporter.build(), new CsvValidatorChecker(), Format.CSV, 3, 3, 2);
    }

    @Test
    void exportSingleDummyWithStringWrapAndHeader() {
        final Dummy dummy = factory.build(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = CsvExporter.builder().withHeader(true).withSeparator(CsvExporter.DEFAULT_SEPARATOR).build();

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
    void exportListDummyWithStringWrapAndHeader() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = CsvExporter.builder().withHeader(true).build();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    @Test
    void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() {
        final Case strategy = Cases.SNAKE_UPPER_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = CsvExporter.builder().withHeader(true).withCase(strategy).build();

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
