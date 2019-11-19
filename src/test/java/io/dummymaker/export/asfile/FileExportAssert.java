package io.dummymaker.export.asfile;

import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.validators.IValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoExportFields;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 28.02.2018
 */
abstract class FileExportAssert extends ExportAssert {

    private final GenFactory factory = new GenFactory();

    private final IExporter exporter;
    private final IValidator validator;
    private final Format format;

    private final int singleSplitLength;
    private final int listSplitLength;

    public FileExportAssert(IExporter exporter, IValidator validator, Format format, int singleSplitLength, int listSplitLength) {
        this.exporter = exporter;
        this.validator = validator;
        this.format = format;
        this.singleSplitLength = singleSplitLength;
        this.listSplitLength = listSplitLength;
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
        final DummyNoExportFields dummy = factory.build(DummyNoExportFields.class);

        final boolean exportResult = exporter.export(dummy);
        assertFalse(exportResult);
    }

    @Test
    public void exportDummyListEmptyContainer() throws Exception {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);

        final boolean exportResult = exporter.export(dummy);
        assertFalse(exportResult);
    }

    @Test
    public void exportSingleDummy() throws Exception {
        final Dummy dummy = factory.build(Dummy.class);
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
        final List<Dummy> dummies = factory.build(Dummy.class, 1);
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
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
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
