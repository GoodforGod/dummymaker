package io.dummymaker.export;

import io.dummymaker.data.DummyTime;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 28.04.2018
 */
@RunWith(Parameterized.class)
public class UniqueExporterTest {

    private final IProduceFactory factory = new GenProduceFactory();

    private IExporter exporter;

    public UniqueExporterTest(IExporter exporter) {
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
    public void checkDateClassActsAsMillisLongWhenExport() {
        DummyTime produce = factory.produce(DummyTime.class);

    }
}
