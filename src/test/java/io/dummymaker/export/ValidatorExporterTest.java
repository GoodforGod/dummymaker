package io.dummymaker.export;

import io.dummymaker.GenFactory;
import io.dummymaker.export.validators.*;
import io.dummymaker.testdata.DummyTime;
import io.dummymaker.testdata.DummyTimeFormatter;
import io.dummymaker.testdata.DummyUnixTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 28.04.2018
 */
public class ValidatorExporterTest extends Assertions {

    private final GenFactory factory = GenFactory.build();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(CsvExporter.builder().withHeader(false).build(), new CsvValidatorChecker()),
                Arguments.of(JsonExporter.build(), new JsonValidatorChecker()),
                Arguments.of(XmlExporter.build(), new XmlValidatorChecker()),
                Arguments.of(SqlExporter.build(), new SqlValidatorChecker()));
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
