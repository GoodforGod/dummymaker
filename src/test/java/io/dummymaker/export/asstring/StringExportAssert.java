package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoExportFields;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.validators.IValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
public abstract class StringExportAssert extends Assert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final IExporter exporter;
    private final IValidator validator;

    private final int singleSplitLength;
    private final int listSplitLength;

    public StringExportAssert(IExporter exporter, IValidator validator, int singleSplitLength, int listSplitLength) {
        this.exporter = exporter;
        this.validator = validator;
        this.singleSplitLength = singleSplitLength;
        this.listSplitLength = listSplitLength;
    }

    @Test
    public void exportSingleDummyInvalidExportEntity() {
        final String exportResult = exporter.exportAsString((DummyNoExportFields) null);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @Test
    public void exportDummyListInvalidExportEntity() {
        final String exportResult = exporter.exportAsString(null);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());

        final String exportEmptyResult = exporter.exportAsString(Collections.emptyList());
        assertNotNull(exportEmptyResult);
        assertTrue(exportEmptyResult.isEmpty());
    }

    @Test
    public void exportSingleDummyEmptyContainer() {
        final DummyNoExportFields dummy = produceFactory.produce(DummyNoExportFields.class);

        final String exportResult = exporter.exportAsString(dummy);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @Test
    public void exportDummyListEmptyContainer() {
        final List<DummyNoExportFields> dummy = produceFactory.produce(DummyNoExportFields.class, 2);

        final String exportResult = exporter.exportAsString(dummy);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @Test
    public void exportSingleDummy() {
        final Dummy dummy = produceFactory.produce(Dummy.class);

        final String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class)) ? "," : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, csvArray.length);

        validator.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportSingleDummyList() {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 1);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class)) ? "," : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, csvArray.length);

        validator.isSingleDummyValid(csvArray);
    }

    @Test
    public void exportListOfDummies() {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }
}
