package io.dummymaker.export.asstring;

import io.dummymaker.GenFactory;
import io.dummymaker.export.*;
import io.dummymaker.export.validators.*;
import io.dummymaker.testdata.Dummy;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ExportExcludeAsStringTests extends Assertions {

    private final GenFactory factory = GenFactory.build();

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(JsonExporter.builder().excludeFields("name", "group").build(), new JsonValidatorChecker(), 2),
                Arguments.of(CsvExporter.builder().excludeFields("name", "group").withHeader(false).build(),
                        new CsvValidatorChecker(), 2),
                Arguments.of(SqlExporter.builder().excludeFields("name", "group").build(), new SqlValidatorChecker(), 8),
                Arguments.of(XmlExporter.builder().excludeFields("name", "group").build(), new XmlValidatorChecker(), 8));
    }

    @MethodSource("data")
    @ParameterizedTest
    void exportListOfDummiesWithExcludes(Exporter exporter,
                                         ValidatorChecker validator,
                                         int listSplitLength) {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] strings = dummyAsString.split("\n");
        assertEquals(listSplitLength, strings.length);

        validator.isTwoDummiesWithNumFieldValid(strings);
    }
}
