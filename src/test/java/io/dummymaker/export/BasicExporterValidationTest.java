package io.dummymaker.export;

import io.dummymaker.data.DummyNoZeroConstructor;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 27.02.2018
 */
@RunWith(Parameterized.class)
public class BasicExporterValidationTest extends Assert {

    private IExporter exporter;

    public BasicExporterValidationTest(IExporter exporter) {
        this.exporter = exporter;
    }

    @Parameters(name = "{index}: Exporter - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { new CsvExporter() },
                { new JsonExporter() },
                { new XmlExporter() },
                { new SqlExporter() }
        });
    }

    @Test
    public void exportNotExportable() {
        DummyNoZeroConstructor d = new DummyNoZeroConstructor(1);
        String s = exporter.exportAsString(d);
        assertTrue(s.isEmpty());
    }

    @Test
    public void exportNotExportableList() {
        List<DummyNoZeroConstructor> dummyNoZeroConstructors = new ArrayList<>();
        dummyNoZeroConstructors.add(new DummyNoZeroConstructor(1));
        dummyNoZeroConstructors.add(new DummyNoZeroConstructor(1));
        String s = exporter.exportAsString(dummyNoZeroConstructors);
        assertTrue(s.isEmpty());
    }

    @Test
    public void exportNullableDummyReturnEmpty() {
        String s = exporter.exportAsString(null);
        assertTrue(s.isEmpty());
    }

    @Test
    public void exportNullableDummiesReturnEmptyList() {
        String s = exporter.exportAsString((List) null);
        assertTrue(s.isEmpty());
    }

    @Test
    public void exportEmptyDummiesReturnEmptyList() {
        String s = exporter.exportAsString(new ArrayList<>());
        assertTrue(s.isEmpty());
    }
}
