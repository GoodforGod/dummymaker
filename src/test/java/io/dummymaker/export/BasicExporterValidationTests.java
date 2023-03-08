package io.dummymaker.export;

import io.dummymaker.testdata.DummyNoZeroConstructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 27.02.2018
 */
public class BasicExporterValidationTests extends Assertions {

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(CsvExporter.build()),
                Arguments.of(JsonExporter.build()),
                Arguments.of(XmlExporter.build()),
                Arguments.of(SqlExporter.build()));
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportNotExportable(Exporter exporter) {
        DummyNoZeroConstructor d = new DummyNoZeroConstructor(null);
        String s = exporter.exportAsString(d);
        if (exporter instanceof CsvExporter)
            assertTrue(s.isEmpty());
        else
            assertFalse(s.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportNotExportableList(Exporter exporter) {
        List<DummyNoZeroConstructor> dummyNoZeroConstructors = Arrays.asList(
                new DummyNoZeroConstructor(null),
                new DummyNoZeroConstructor(null));

        String s = exporter.exportAsString(dummyNoZeroConstructors);
        if (exporter instanceof CsvExporter)
            assertTrue(s.isEmpty());
        else
            assertFalse(s.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportNullableDummyReturnEmpty(Exporter exporter) {
        String s = exporter.exportAsString(null);
        assertTrue(s.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportNullableDummiesReturnEmptyList(Exporter exporter) {
        String s = exporter.exportAsString(null);
        assertTrue(s.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportEmptyDummiesReturnEmptyList(Exporter exporter) {
        String s = exporter.exportAsString(new ArrayList<>());
        assertTrue(s.isEmpty());
    }
}
