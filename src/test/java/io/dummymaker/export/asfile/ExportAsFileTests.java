package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoExportFields;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.*;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
@Ignore
@RunWith(Parameterized.class)
public class ExportAsFileTests extends ExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final IExporter exporter;
    private final IValidator validator;
    private final Format format;

    private final int singleSplitLength;
    private final int listSplitLength;

    public ExportAsFileTests(IExporter exporter, IValidator validator, Format format, int singleSplitLength, int listSplitLength) {
        this.exporter = exporter;
        this.validator = validator;
        this.format = format;
        this.singleSplitLength = singleSplitLength;
        this.listSplitLength = listSplitLength;
    }

    @Parameters(name = "{index}: Exporter - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]
                {
                        { new JsonExporter().withPretty(), new JsonValidator(), Format.JSON, 5, 14 },
                        { new JsonExporter().withPretty().withPath(null), new JsonValidator(), Format.JSON, 5, 14 },
                        { new JsonExporter().withPretty().withPath("    "), new JsonValidator(), Format.JSON, 5, 14 },
                        { new JsonExporter().withPretty().withCase(null), new JsonValidator(), Format.JSON, 5, 14 },

                        { new CsvExporter(), new CsvValidator(), Format.CSV, 3, 2 },
                        { new CsvExporter().withPath(null), new CsvValidator(), Format.CSV, 3, 2 },
                        { new CsvExporter().withPath("    "), new CsvValidator(), Format.CSV, 3, 2 },
                        { new CsvExporter().withCase(null), new CsvValidator(), Format.CSV, 3, 2 },

                        { new SqlExporter(), new SqlValidator(), Format.SQL, 9, 10 },
                        { new SqlExporter().withPath(null), new SqlValidator(), Format.SQL, 9, 10 },
                        { new SqlExporter().withPath("    "), new SqlValidator(), Format.SQL, 9, 10 },
                        { new SqlExporter().withCase(null), new SqlValidator(), Format.SQL, 9, 10 },

                        { new XmlExporter(), new XmlValidator(), Format.XML, 5, 12 },
                        { new XmlExporter().withPath(null), new XmlValidator(), Format.XML, 5, 12 },
                        { new XmlExporter().withPath("     "), new XmlValidator(), Format.XML, 5, 12 },
                        { new XmlExporter().withCase(null), new XmlValidator(), Format.XML, 5, 12 }
                }
        );
    }

    @Test
    public void exportSingleDummyInvalidExportEntity() throws Exception {
        final boolean exportResult = exporter.export((DummyNoExportFields) null);
        assertFalse(exportResult);
    }

    @Test
    public void exportDummyListInvalidExportEntity() throws Exception {
        final boolean exportResult = exporter.export(null);
        assertFalse(exportResult);

        final boolean exportEmptyResult = exporter.export(Collections.emptyList());
        assertFalse(exportEmptyResult);
    }

    @Test
    public void exportSingleDummyEmptyContainer() throws Exception {
        final DummyNoExportFields dummy = produceFactory.produce(DummyNoExportFields.class);

        final boolean exportResult = exporter.export(dummy);
        assertFalse(exportResult);
    }

    @Test
    public void exportDummyListEmptyContainer() throws Exception {
        final List<DummyNoExportFields> dummy = produceFactory.produce(DummyNoExportFields.class, 2);

        final boolean exportResult = exporter.export(dummy);
        assertFalse(exportResult);
    }

    @Test
    public void exportSingleDummy() throws Exception {
        final Dummy dummy = produceFactory.produce(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class)) ? "," : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, csvArray.length);

        validator.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportSingleDummyList() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 1);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class)) ? "," : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, csvArray.length);

        validator.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportListOfDummies() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }
}
