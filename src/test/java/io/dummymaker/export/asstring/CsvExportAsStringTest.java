package io.dummymaker.export.asstring;

import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.validators.CsvValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import org.junit.Test;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class CsvExportAsStringTest extends StringExportAssert {

    private final GenFactory factory = new GenFactory();
    private final CsvValidator validation = new CsvValidator();

    public CsvExportAsStringTest() {
        super(new CsvExporter(), new CsvValidator(), 3, 2);
    }

    @Test
    public void exportSingleDummyWithStringWrapAndHeader() {
        final Dummy dummy = factory.build(Dummy.class);
        final IExporter exporter = new CsvExporter().withHeader();

        final String dummyAsString = exporter.convert(dummy);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(2, csvArray.length);

        validation.isSingleDummyValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeader() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final IExporter exporter = new CsvExporter().withHeader();

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeader(csvArray, CsvExporter.DEFAULT_SEPARATOR);
    }

    @Test
    public void exportListDummyWithStringWrapAndHeaderAndNamingStrategy() {
        final ICase strategy = Cases.UPPER_SNAKE_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final IExporter exporter = new CsvExporter().withHeader().withCase(strategy);

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(3, csvArray.length);

        validation.isTwoDummiesValidWithHeaderAndNameStrategy(csvArray, CsvExporter.DEFAULT_SEPARATOR, strategy);
    }
}
