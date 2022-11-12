package io.dummymaker.export;

import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.model.DummyNoZeroConstructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 27.02.2018
 */
@RunWith(Parameterized.class)
public class BasicExporterValidationTest extends Assert {

    private Exporter exporter;

    public BasicExporterValidationTest(Exporter exporter) {
        this.exporter = exporter;
    }

    @Parameters(name = "{index}: Exporter - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new CsvExporter() },
                { new JsonExporter() },
                { new XmlExporter() },
                { new SqlExporter() }
        });
    }

    @Test
    public void exportNotExportable() {
        DummyNoZeroConstructor d = new DummyNoZeroConstructor(null);
        String s = exporter.convert(d);
        if (exporter instanceof CsvExporter)
            assertTrue(s.isEmpty());
        else
            assertFalse(s.isEmpty());
    }

    @Test
    public void exportNotExportableList() {
        List<DummyNoZeroConstructor> dummyNoZeroConstructors = Arrays.asList(
                new DummyNoZeroConstructor(null),
                new DummyNoZeroConstructor(null));

        String s = exporter.convert(dummyNoZeroConstructors);
        if (exporter instanceof CsvExporter)
            assertTrue(s.isEmpty());
        else
            assertFalse(s.isEmpty());
    }

    @Test
    public void exportNullableDummyReturnEmpty() {
        String s = exporter.convert(null);
        assertTrue(s.isEmpty());
    }

    @Test
    public void exportNullableDummiesReturnEmptyList() {
        String s = exporter.convert(null);
        assertTrue(s.isEmpty());
    }

    @Test
    public void exportEmptyDummiesReturnEmptyList() {
        String s = exporter.convert(new ArrayList<>());
        assertTrue(s.isEmpty());
    }
}
