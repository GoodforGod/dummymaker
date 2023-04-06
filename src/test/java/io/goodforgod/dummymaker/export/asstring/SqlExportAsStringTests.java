package io.goodforgod.dummymaker.export.asstring;

import io.goodforgod.dummymaker.GenFactory;
import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.export.Exporter;
import io.goodforgod.dummymaker.export.SqlExporter;
import io.goodforgod.dummymaker.export.validators.SqlValidatorChecker;
import io.goodforgod.dummymaker.testdata.Dummy;
import io.goodforgod.dummymaker.testdata.DummyArray;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class SqlExportAsStringTests extends StringExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final SqlValidatorChecker validation = new SqlValidatorChecker();

    public SqlExportAsStringTests() {
        super(SqlExporter.build(), new SqlValidatorChecker(), 9, 10);
    }

    @Test
    void exportListOfDummiesInSqlWithNamingStrategy() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesWithArrays() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final List<DummyArray> dummies = factory.build(DummyArray.class, 2);
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(11, sqlArray.length);

        validation.isTwoDummiesArrayValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesListTwoBatches() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 4);
        final Exporter exporter = SqlExporter.builder().withBatchSize(2).withCase(strategy).build();

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(14, sqlArray.length);

        validation.isTwoDummiesValidInTwoBatchesWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesStream() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final Stream<Dummy> dummies = factory.stream(Dummy.class, 2);
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        final String dummyAsString = exporter.streamToString(dummies, Dummy.class);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesStreamTwoBatches() {
        final NamingCase strategy = NamingCases.SNAKE_LOWER_CASE;

        final Stream<Dummy> dummies = factory.stream(Dummy.class, 4);
        final Exporter exporter = SqlExporter.builder().withBatchSize(2).withCase(strategy).build();

        final String dummyAsString = exporter.streamToString(dummies, Dummy.class);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(14, sqlArray.length);

        validation.isTwoDummiesValidInTwoBatchesWithNamingStrategy(sqlArray, strategy);
    }
}
