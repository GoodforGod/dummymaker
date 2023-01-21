package io.dummymaker.export.asstring;

import io.dummymaker.export.*;
import io.dummymaker.export.validators.*;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyNoExportFields;
import io.dummymaker.model.deprecated.DummyAuto;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 03.03.2018
 */
public class ExportAsStringTests extends Assertions {

    private final GenFactory factory = GenFactory.build();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(JsonExporter.build(), new JsonValidatorChecker(), 1, 1, 2),
                Arguments.of(CsvExporter.build(), new CsvValidatorChecker(), 3, 3, 2),
                Arguments.of(SqlExporter.build(), new SqlValidatorChecker(), 8, 8, 9),
                Arguments.of(XmlExporter.build(), new XmlValidatorChecker(), 5, 7, 12));
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummyInvalidExportEntity(Exporter exporter) {
        final String exportResult = exporter.exportAsString((DummyNoExportFields) null);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportDummyListInvalidExportEntity(Exporter exporter) {
        final String exportResult = exporter.exportAsString(null);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());

        final String exportEmptyResult = exporter.exportAsString(Collections.emptyList());
        assertNotNull(exportEmptyResult);
        assertTrue(exportEmptyResult.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummyEmptyContainer(Exporter exporter) {
        final DummyNoExportFields dummy = factory.build(DummyNoExportFields.class);

        final String exportResult = exporter.exportAsString(dummy);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportDummyListEmptyContainer(Exporter exporter) {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);

        final String exportResult = exporter.exportAsString(dummy);
        assertNotNull(exportResult);
        assertTrue(exportResult.isEmpty());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummy(Exporter exporter, ValidatorChecker validator, int singleSplitLength) {
        final Dummy dummy = factory.build(Dummy.class);

        final String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] strings = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, strings.length);

        validator.isSingleDummyValid(strings);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleAutoDummy(Exporter exporter, ValidatorChecker validator, int singleSplitLength) {
        final DummyAuto dummy = factory.build(DummyAuto.class);

        final String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] strings = dummyAsString.split(splitter);
        assertEquals(Math.max(1, singleSplitLength - 1), strings.length);

        validator.isSingleAutoDummyValid(strings);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummyList(Exporter exporter, ValidatorChecker validator, int singleSplitLength, int singleListSplitLength) {
        final List<Dummy> dummies = factory.build(Dummy.class, 1);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] strings = dummyAsString.split(splitter);
        assertEquals(singleListSplitLength, strings.length);

        validator.isSingleDummyListValid(strings);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportListOfDummies(Exporter exporter,
                             ValidatorChecker validator,
                             int singleSplitLength,
                             int singleListSplitLength,
                             int listSplitLength) {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] strings = dummyAsString.split("\n");
        assertEquals(listSplitLength, strings.length);

        validator.isTwoDummiesValid(strings);
    }
}
