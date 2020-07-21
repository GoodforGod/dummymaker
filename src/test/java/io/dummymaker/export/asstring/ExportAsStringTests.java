package io.dummymaker.export.asstring;

import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.*;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoExportFields;
import io.dummymaker.model.deprecated.DummyAuto;
import org.junit.Assert;
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
public class ExportAsStringTests extends Assert {

    private final GenFactory factory = new GenFactory();
    private final IExporter exporter;
    private final IValidator validator;

    private final int singleSplitLength;
    private final int listSplitLength;

    public ExportAsStringTests(IExporter exporter, IValidator validator, int singleSplitLength, int listSplitLength) {
        this.exporter = exporter;
        this.validator = validator;
        this.singleSplitLength = singleSplitLength;
        this.listSplitLength = listSplitLength;
    }

    @Parameters(name = "{index}: Exporter - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new JsonExporter().withPretty(), new JsonValidator(), 5, 14 },
                { new JsonExporter().withPretty().withPath(null), new JsonValidator(), 5, 14 },
                { new JsonExporter().withPretty().withPath("    "), new JsonValidator(), 5, 14 },
                { new JsonExporter().withPretty().withCase(null), new JsonValidator(), 5, 14 },

                { new CsvExporter(), new CsvValidator(), 3, 2 },
                { new CsvExporter().withPath(null), new CsvValidator(), 3, 2 },
                { new CsvExporter().withPath("    "), new CsvValidator(), 3, 2 },
                { new CsvExporter().withCase(null), new CsvValidator(), 3, 2 },

                { new SqlExporter(), new SqlValidator(), 9, 10 },
                { new SqlExporter().withPath(null), new SqlValidator(), 9, 10 },
                { new SqlExporter().withPath("    "), new SqlValidator(), 9, 10 },
                { new SqlExporter().withCase(null), new SqlValidator(), 9, 10 },

                { new XmlExporter(), new XmlValidator(), 5, 12 },
                { new XmlExporter().withPath(null), new XmlValidator(), 5, 12 },
                { new XmlExporter().withPath("     "), new XmlValidator(), 5, 12 },
                { new XmlExporter().withCase(null), new XmlValidator(), 5, 12 }
        });
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
        assertTrue(exportResult.isEmpty());

        final String exportEmptyResult = exporter.convert(Collections.emptyList());
        assertNotNull(exportEmptyResult);
        assertTrue(exportEmptyResult.isEmpty());
    }

    @Test
    public void exportSingleDummyEmptyContainer() {
        final DummyNoExportFields dummy = factory.build(DummyNoExportFields.class);

        final String exportResult = exporter.convert(dummy);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @Test
    public void exportDummyListEmptyContainer() {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);

        final String exportResult = exporter.convert(dummy);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @Test
    public void exportSingleDummy() {
        final Dummy dummy = factory.build(Dummy.class);

        final String dummyAsString = exporter.convert(dummy);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class)) ? "," : "\n";

        final String[] strings = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, strings.length);

        validator.isSingleDummyValid(strings);
    }

    @Test
    public void exportSingleAutoDummy() {
        final DummyAuto dummy = factory.build(DummyAuto.class);

        final String dummyAsString = exporter.convert(dummy);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class)) ? "," : "\n";

        final String[] strings = dummyAsString.split(splitter);
        assertEquals(singleSplitLength - 1, strings.length);

        validator.isSingleAutoDummyValid(strings);
    }

    @Test
    public void exportSingleDummyList() {
        final List<Dummy> dummies = factory.build(Dummy.class, 1);

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class)) ? "," : "\n";

        final String[] strings = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, strings.length);

        validator.isSingleDummyValid(strings);
    }

    @Test
    public void exportListOfDummies() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] strings = dummyAsString.split("\n");
        assertEquals(listSplitLength, strings.length);

        validator.isTwoDummiesValid(strings);
    }
}
