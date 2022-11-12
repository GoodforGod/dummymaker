package io.dummymaker.export.asfile;

import io.dummymaker.export.Exporter;
import io.dummymaker.export.Format;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.*;
import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoExportFields;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
@Ignore
@RunWith(Parameterized.class)
public class ExportAsFileTests extends ExportAssert {

    private final MainGenFactory factory = new MainGenFactory();

    private final Exporter exporter;
    private final IValidator validator;
    private final Format format;

    private final int singleSplitLength;
    private final int listSplitLength;

    public ExportAsFileTests(Exporter exporter,
                             IValidator validator,
                             Format format,
                             int singleSplitLength,
                             int listSplitLength) {
        this.exporter = exporter;
        this.validator = validator;
        this.format = format;
        this.singleSplitLength = singleSplitLength;
        this.listSplitLength = listSplitLength;
    }

    @Parameters(name = "{index}: Exporter - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new JsonExporter(), new JsonValidator(), Format.JSON, 5, 14 },

                { new CsvExporter(), new CsvValidator(), Format.CSV, 3, 2 },

                { new SqlExporter(), new SqlValidator(), Format.SQL, 9, 10 },

                { new XmlExporter(), new XmlValidator(), Format.XML, 5, 12 },
        });
    }

    @Test
    public void exportSingleDummyInvalidExportEntity() {
        final boolean exportResult = exporter.export((DummyNoExportFields) null);
        assertFalse(exportResult);
    }

    @Test
    public void exportDummyListInvalidExportEntity() {
        final boolean exportResult = exporter.export(null);
        assertFalse(exportResult);

        final boolean exportEmptyResult = exporter.export(Collections.emptyList());
        assertFalse(exportEmptyResult);
    }

    @Test
    public void exportSingleDummyEmptyContainer() {
        final DummyNoExportFields dummy = factory.build(DummyNoExportFields.class);
        final String filename = DummyNoExportFields.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummy);

        markFileForRemoval(filename);
        assertFalse(exportResult);
    }

    @Test
    public void exportDummyListEmptyContainer() {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);
        final String filename = DummyNoExportFields.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummy);

        markFileForRemoval(filename);
        assertFalse(exportResult);
    }

    @Test
    public void exportSingleDummy() {
        final Dummy dummy = factory.build(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, csvArray.length);

        validator.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportSingleDummyList() {
        final List<Dummy> dummies = factory.build(Dummy.class, 1);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, csvArray.length);

        validator.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportListOfDummies() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }
}
