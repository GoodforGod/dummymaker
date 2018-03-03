package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoExportFields;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.validators.IValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 28.02.2018
 */
abstract class ExportAssert extends Assert {

    private static final Logger logger = Logger.getLogger(ExportAssert.class.getName());

    private String fileToDelete;

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final IExporter exporter;
    private final IValidator validator;
    private final Format format;

    private final int singleSplitLength;
    private final int listSplitLength;

    public ExportAssert() {
        this.exporter = null;
        this.validator = null;
        this.format = Format.CSV;
        this.singleSplitLength = 0;
        this.listSplitLength = 0;
    }

    public ExportAssert(IExporter exporter, IValidator validator, Format format, int singleSplitLength, int listSplitLength) {
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

    @After
    public void cleanFile() {
        try {
            if(fileToDelete != null) {
                final File file = new File(fileToDelete);
                Files.deleteIfExists(file.toPath());
                fileToDelete = null;
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    protected void setFilenameToBeRemoved(String filename) {
        this.fileToDelete = filename;
    }

    protected String readDummyFromFile(String filename) throws Exception {
        final StringBuilder builder = new StringBuilder();
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), "UTF-8")
        );

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }

        builder.deleteCharAt(builder.length() - 1);

        reader.close();
        return builder.toString();
    }
}
