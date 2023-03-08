package io.dummymaker.export;

import io.dummymaker.GenFactory;
import io.dummymaker.cases.NamingCase;
import io.dummymaker.cases.NamingCases;
import io.dummymaker.export.validators.CsvValidatorChecker;
import io.dummymaker.testdata.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class CsvExportAsFileTests extends FileExportAssert {

    private static final char DEFAULT_SEPARATOR = ',';

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
        final Exporter exporter = CsvExporter.builder().withHeader(true).withSeparator(DEFAULT_SEPARATOR).build();

        exporter.exportAsFile(dummy);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, DEFAULT_SEPARATOR);
    }

    @Test
    void exportListDummyWithStringWrapAndHeader() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = CsvExporter.builder().withHeader(true).build();

        exporter.exportAsFile(dummies);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, DEFAULT_SEPARATOR);
    }

    @Test
    void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() {
        final NamingCase strategy = NamingCases.SNAKE_UPPER_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = CsvExporter.builder().withHeader(true).withCase(strategy).build();

        exporter.exportAsFile(dummies);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, DEFAULT_SEPARATOR, strategy);
    }
}
