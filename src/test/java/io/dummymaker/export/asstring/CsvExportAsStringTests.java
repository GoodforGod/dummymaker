package io.dummymaker.export.asstring;

import io.dummymaker.GenFactory;
import io.dummymaker.cases.NamingCase;
import io.dummymaker.cases.NamingCases;
import io.dummymaker.export.CsvExporter;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.validators.CsvValidatorChecker;
import io.dummymaker.testdata.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class CsvExportAsStringTests extends StringExportAssert {

    private static final char DEFAULT_SEPARATOR = ',';

    private final GenFactory factory = GenFactory.build();
    private final CsvValidatorChecker validation = new CsvValidatorChecker();

    public CsvExportAsStringTests() {
        super(CsvExporter.build(), new CsvValidatorChecker(), 3, 2);
    }

    @Test
    void exportSingleDummyWithStringWrapAndHeader() {
        final Dummy dummy = factory.build(Dummy.class);
        final Exporter exporter = CsvExporter.builder().withHeader(true).build();

        final String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, DEFAULT_SEPARATOR);
    }

    @Test
    void exportListDummyWithStringWrapAndHeader() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = CsvExporter.builder().withHeader(true).build();

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, DEFAULT_SEPARATOR);
    }

    @Test
    void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() {
        final NamingCase strategy = NamingCases.SNAKE_UPPER_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = CsvExporter.builder().withHeader(true).withCase(strategy).build();

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, DEFAULT_SEPARATOR, strategy);
    }
}
