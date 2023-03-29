package io.dummymaker.export;

import io.dummymaker.GenFactory;
import io.dummymaker.export.validators.*;
import io.dummymaker.testdata.Dummy;
import io.dummymaker.testdata.DummyNoExportFields;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 03.03.2018
 */
public class ExportAsFileTests extends ExportAssert {

    private final GenFactory factory = GenFactory.build();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(JsonExporter.build(), new JsonValidatorChecker(), Format.JSON, 1, 1, 2),
                Arguments.of(CsvExporter.builder().withHeader(false).build(), new CsvValidatorChecker(), Format.CSV, 3, 3, 2),
                Arguments.of(SqlExporter.build(), new SqlValidatorChecker(), Format.SQL, 9, 9, 10),
                Arguments.of(XmlExporter.build(), new XmlValidatorChecker(), Format.XML, 5, 7, 12));
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummyInvalidExportEntity(Exporter exporter) {
        exporter.exportAsFile((DummyNoExportFields) null);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportDummyListInvalidExportEntity(Exporter exporter) {
        exporter.exportAsFile(Collections.emptyList());
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummyEmptyContainer(Exporter exporter, ValidatorChecker validator, Format format) {
        final DummyNoExportFields dummy = factory.build(DummyNoExportFields.class);
        final String filename = DummyNoExportFields.class.getSimpleName() + format.getExtension();

        exporter.exportAsFile(dummy);
        markFileForRemoval(filename);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportDummyListEmptyContainer(Exporter exporter, ValidatorChecker validator, Format format) {
        final List<DummyNoExportFields> dummy = factory.build(DummyNoExportFields.class, 2);
        final String filename = DummyNoExportFields.class.getSimpleName() + format.getExtension();

        exporter.exportAsFile(dummy);
        markFileForRemoval(filename);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummy(Exporter exporter,
                           ValidatorChecker validator,
                           Format format,
                           int singleSplitLength) {
        final Dummy dummy = factory.build(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        exporter.exportAsFile(dummy);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] csvArray = dummyAsString.split(splitter);
        assertEquals(singleSplitLength, csvArray.length);

        validator.isSingleDummyValid(csvArray);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportSingleDummyList(Exporter exporter,
                               ValidatorChecker validator,
                               Format format,
                               int singleSplitLength,
                               int singleListSplitLength) {
        final List<Dummy> dummies = factory.build(Dummy.class, 1);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        exporter.exportAsFile(dummies);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String splitter = (exporter.getClass().equals(CsvExporter.class))
                ? ","
                : "\n";

        final String[] array = dummyAsString.split(splitter);
        assertEquals(singleListSplitLength, array.length);

        validator.isSingleDummyListValid(array);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportListOfDummies(Exporter exporter,
                             ValidatorChecker validator,
                             Format format,
                             int singleSplitLength,
                             int singleListSplitLength,
                             int listSplitLength) {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        exporter.exportAsFile(dummies);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportStreamOfDummies(Exporter exporter,
                               ValidatorChecker validator,
                               Format format,
                               int singleSplitLength,
                               int singleListSplitLength,
                               int listSplitLength) {
        final Stream<Dummy> dummies = factory.stream(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();

        exporter.streamToFile(dummies, Dummy.class);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] csvArray = dummyAsString.split("\n");
        assertEquals(listSplitLength, csvArray.length);

        validator.isTwoDummiesValid(csvArray);
    }
}
