package io.dummymaker.export.asstring;

import io.dummymaker.export.Exporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.validators.IValidator;
import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoExportFields;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
public abstract class StringExportAssert extends Assert {

    private final MainGenFactory factory = new MainGenFactory();
    private final Exporter exporter;
    private final IValidator validator;

    private final int singleSplitLength;
    private final int singleListSplit;
    private final int listSplitLength;

    public StringExportAssert(Exporter exporter, IValidator validator, int singleSplitLength, int listSplitLength) {
        this(exporter, validator, singleSplitLength, singleSplitLength, listSplitLength);
    }

    public StringExportAssert(Exporter exporter,
                              IValidator validator,
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
    public void exportSingleDummyInvalidExportEntity() {
        final String exportResult = exporter.convert((DummyNoExportFields) null);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @Test
    public void exportDummyListInvalidExportEntity() {
        final String exportResult = exporter.convert(null);
        assertNotNull(exportResult);
        assertEquals(getEmptyListResult(), exportResult);

        final String exportEmptyResult = exporter.convert(Collections.emptyList());
        assertNotNull(exportEmptyResult);
        assertEquals(getEmptyListResult(), exportResult);
    }

    @Test
    public void exportDummyListEmptyContainer() {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);

        final String exportResult = exporter.convert(dummy);
        assertNotNull(exportResult);
        assertEquals(getEmptyListResult(), exportResult);
    }

    @Test
    public void exportSingleDummy() {
        final Dummy dummy = factory.build(Dummy.class);

        final String dummyAsString = exporter.convert(dummy);
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

        final String dummyAsString = exporter.convert(dummies);
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
    public void exportListOfDummies() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }
}
