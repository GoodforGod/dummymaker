package io.dummymaker.export;

import io.dummymaker.export.validators.ValidatorChecker;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoExportFields;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 28.02.2018
 */
abstract class FileExportAssert extends ExportAssert {

    private final GenFactory factory = GenFactory.build();

    private final Exporter exporter;
    private final ValidatorChecker validator;
    private final Format format;

    private final int singleSplitLength;
    private final int singleListSplit;
    private final int listSplitLength;

    public FileExportAssert(Exporter exporter,
                            ValidatorChecker validator,
                            Format format,
                            int singleSplitLength,
                            int singleListSplit,
                            int listSplitLength) {
        this.exporter = exporter;
        this.validator = validator;
        this.format = format;
        this.singleSplitLength = singleSplitLength;
        this.singleListSplit = singleListSplit;
        this.listSplitLength = listSplitLength;
    }

    @Test
    public void exportSingleDummyInvalidExportEntity() {
        final boolean exportResult = exporter.exportAsFile((DummyNoExportFields) null);
        assertFalse(exportResult);
    }

    @Test
    public void exportDummyListInvalidExportEntity() {
        final boolean exportResult = exporter.exportAsFile(null);
        assertFalse(exportResult);

        final boolean exportEmptyResult = exporter.exportAsFile(Collections.emptyList());
        assertFalse(exportEmptyResult);
    }

    @Test
    public void exportSingleDummyEmptyContainer() {
        final DummyNoExportFields dummy = factory.build(DummyNoExportFields.class);
        final String filename = DummyNoExportFields.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.exportAsFile(dummy);
        markFileForRemoval(filename);
        assertFalse(exportResult);
    }

    @Test
    public void exportDummyListEmptyContainer() {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);
        final String filename = DummyNoExportFields.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.exportAsFile(dummy);
        markFileForRemoval(filename);
        assertFalse(exportResult);
    }

    @Test
    public void exportSingleDummy() {
        final Dummy dummy = factory.build(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.exportAsFile(dummy);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter instanceof CsvExporter)
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

        final boolean exportResult = exporter.exportAsFile(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter instanceof CsvExporter)
                ? ","
                : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleListSplit, csvArray.length);

        validator.isSingleDummyListValid(csvArray);
    }

    @Test
    public void exportListOfDummies() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        final boolean exportResult = exporter.exportAsFile(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }
}
