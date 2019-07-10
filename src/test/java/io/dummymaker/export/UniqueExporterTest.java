package io.dummymaker.export;

import io.dummymaker.data.DummyTime;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.*;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenFactory;
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

    private final IProduceFactory factory = new GenFactory();

    private IExporter exporter;
    private IValidator validator;

    public UniqueExporterTest(IExporter exporter, IValidator validator) {
        this.exporter = exporter;
        this.validator = validator;
    }

    @Parameters(name = "{index}: Exporter - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { new CsvExporter(), new CsvValidator() },
                { new JsonExporter().withPretty(), new JsonValidator()},
                { new XmlExporter(), new XmlValidator()},
                { new SqlExporter(), new SqlValidator()}
        });
    }

    @Test
    public void checkDateClassActsAsMillisLongWhenExport() {
        DummyTime dummyTime = factory.produce(DummyTime.class);
        String s = exporter.exportAsString(dummyTime);
        String[] split = s.split((exporter.getClass().equals(CsvExporter.class)) ? "," : "\n");
        validator.isDummyTimeValid(split);
    }
}
