package io.dummymaker.export.asstring;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.export.CsvExporter;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.validators.CsvValidatorChecker;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class CsvExportAsStringTests extends StringExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final CsvValidatorChecker validation = new CsvValidatorChecker();

    public CsvExportAsStringTests() {
        super(CsvExporter.build(), new CsvValidatorChecker(), 3, 2);
    }

    @Test
    void exportSingleDummyWithStringWrapAndHeader() {
        final Dummy dummy = factory.build(Dummy.class);
        final Exporter exporter = CsvExporter.builder().withHeader(true).build();

        final String dummyAsString = exporter.convert(dummy);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    @Test
    void exportListDummyWithStringWrapAndHeader() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = CsvExporter.builder().withHeader(true).build();

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    @Test
    void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() {
        final Case strategy = Cases.SNAKE_UPPER_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = CsvExporter.builder().withHeader(true).withCase(strategy).build();

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, CsvExporter.DEFAULT_SEPARATOR, strategy);
    }
}
