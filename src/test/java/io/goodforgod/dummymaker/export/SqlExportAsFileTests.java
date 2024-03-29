package io.goodforgod.dummymaker.export;

import io.goodforgod.dummymaker.GenFactory;
import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.export.validators.SqlValidatorChecker;
import io.goodforgod.dummymaker.testdata.Dummy;
import io.goodforgod.dummymaker.testdata.DummyTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class SqlExportAsFileTests extends FileExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final SqlValidatorChecker validation = new SqlValidatorChecker();
    private final Format format = Format.SQL;

    public SqlExportAsFileTests() {
        super(SqlExporter.build(), new SqlValidatorChecker(), Format.SQL, 9, 9, 10);
    }

    @Test
    void exportListOfDummiesWithNamingStrategy() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        exporter.exportAsFile(dummies);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportSqlWithTimestampWithNamingStrategy() {
        final NamingCase strategy = NamingCases.LOWER_CASE;

        final Map<Class<?>, String> dataTypes = new HashMap<>();
        dataTypes.put(Object.class, "TIMESTAMP");

        final List<DummyTime> dummies = factory.build(DummyTime.class, 2);
        final Exporter exporter = SqlExporter.builder()
                .withCase(strategy)
                .withDataTypes(dataTypes)
                .build();

        exporter.exportAsFile(dummies);

        final String filename = "DummyTime" + format.getExtension();

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(17, sqlArray.length);

        validation.isDummyTimeValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesListTwoBatches() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 4);
        final Exporter exporter = SqlExporter.builder().withBatchSize(2).withCase(strategy).build();

        exporter.exportAsFile(dummies);

        final String filename = "Dummy" + format.getExtension();

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(14, sqlArray.length);

        validation.isTwoDummiesValidInTwoBatchesWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesStream() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final Stream<Dummy> dummies = factory.stream(Dummy.class, 2);
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        exporter.streamToFile(dummies, Dummy.class);

        final String filename = "Dummy" + format.getExtension();

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesStreamTwoBatches() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final Stream<Dummy> dummies = factory.stream(Dummy.class, 4);
        final Exporter exporter = SqlExporter.builder().withBatchSize(2).withCase(strategy).build();

        exporter.streamToFile(dummies, Dummy.class);

        final String filename = "Dummy" + format.getExtension();

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(14, sqlArray.length);

        validation.isTwoDummiesValidInTwoBatchesWithNamingStrategy(sqlArray, strategy);
    }
}
