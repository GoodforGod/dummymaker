package io.dummymaker.export;

import io.dummymaker.export.validators.*;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.DummyTime;
import io.dummymaker.model.DummyTimeFormatter;
import io.dummymaker.model.DummyUnixTime;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 28.04.2018
 */
public class ValidatorExporterTest extends Assertions {

    private final GenFactory factory = GenFactory.build();

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { CsvExporter.build(), new CsvValidatorChecker() },
                { JsonExporter.build(), new JsonValidatorChecker() },
                { XmlExporter.build(), new XmlValidatorChecker() },
                { SqlExporter.build(), new SqlValidatorChecker() }
        });
    }

    @MethodSource("data")
    @ParameterizedTest
    void datesPatternMatchWhenExported(Exporter exporter, ValidatorChecker validator) {
        final DummyTime dummy = factory.build(DummyTime.class);
        assertNotNull(dummy);

        final String exported = exporter.exportAsString(dummy);

        final String splitter = (exporter instanceof CsvExporter)
                ? ","
                : "\n";
        final String[] split = exported.split(splitter);
        assertNotNull(split);

        validator.isDummyTimeValid(split);
    }

    @MethodSource("data")
    @ParameterizedTest
    void datetimeUnixWhenExported(Exporter exporter, ValidatorChecker validator) {
        final DummyUnixTime dummy = factory.build(DummyUnixTime.class);
        assertNotNull(dummy);

        final String exported = exporter.exportAsString(dummy);
        final String splitter = (exporter instanceof CsvExporter)
                ? ","
                : "\n";

        final String[] split = exported.split(splitter);
        assertNotNull(split);

        validator.isDummyUnixTimeValid(split);
    }

    @MethodSource("data")
    @ParameterizedTest
    void datetimeFormatterWhenExported(Exporter exporter, ValidatorChecker validator) {
        final DummyTimeFormatter dummy = factory.build(DummyTimeFormatter.class);
        assertNotNull(dummy);

        final String exported = exporter.exportAsString(dummy);

        final String splitter = (exporter instanceof CsvExporter)
                ? ","
                : "\n";
        final String[] split = exported.split(splitter);
        assertNotNull(split);

        validator.isDummyTimeFormatterValid(split);
    }
}
