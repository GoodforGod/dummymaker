package io.goodforgod.dummymaker.export.asstring;

import io.goodforgod.dummymaker.GenFactory;
import io.goodforgod.dummymaker.export.CsvExporter;
import io.goodforgod.dummymaker.export.Exporter;
import io.goodforgod.dummymaker.export.validators.ValidatorChecker;
import io.goodforgod.dummymaker.testdata.Dummy;
import io.goodforgod.dummymaker.testdata.DummyNoExportFields;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 03.03.2018
 */
public abstract class StringExportAssert extends Assertions {

    private final GenFactory factory = GenFactory.build();
    private final Exporter exporter;
    private final ValidatorChecker validator;

    private final int singleSplitLength;
    private final int singleListSplit;
    private final int listSplitLength;

    public StringExportAssert(Exporter exporter, ValidatorChecker validator, int singleSplitLength, int listSplitLength) {
        this(exporter, validator, singleSplitLength, singleSplitLength, listSplitLength);
    }

    public StringExportAssert(Exporter exporter,
                              ValidatorChecker validator,
                              int singleSplitLength,
                              int singleListSplit,
                              int listSplitLength) {
        this.exporter = exporter;
        this.validator = validator;
        this.singleSplitLength = singleSplitLength;
        this.singleListSplit = singleListSplit;
        this.listSplitLength = listSplitLength;
    }

    protected String getEmptyListResult() {
        return "";
    }

    @Test
    void exportSingleDummyInvalidExportEntity() {
        final String exportResult = exporter.exportAsString((DummyNoExportFields) null);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @Test
    void exportDummyListInvalidExportEntity() {
        final String exportResult = exporter.exportAsString(null);
        assertNotNull(exportResult);
        assertEquals(getEmptyListResult(), exportResult);

        final String exportEmptyResult = exporter.exportAsString(Collections.emptyList());
        assertNotNull(exportEmptyResult);
        assertEquals(getEmptyListResult(), exportResult);
    }

    @Test
    void exportDummyListEmptyContainer() {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);

        final String exportResult = exporter.exportAsString(dummy);
        assertNotNull(exportResult);
        assertEquals(getEmptyListResult(), exportResult);
    }

    @Test
    void exportSingleDummy() {
        final Dummy dummy = factory.build(Dummy.class);

        final String dummyAsString = exporter.exportAsString(dummy);
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
    void exportSingleDummyList() {
        final List<Dummy> dummies = factory.build(Dummy.class, 1);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] split = dummyAsString.split(splitter);
        assertEquals(singleListSplit, split.length);

        validator.isSingleDummyListValid(split);
    }

    @Test
    void exportListOfDummies() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }
}
