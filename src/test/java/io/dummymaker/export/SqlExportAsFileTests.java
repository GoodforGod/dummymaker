package io.dummymaker.export;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.export.validators.SqlValidatorChecker;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        super(SqlExporter.build(), new SqlValidatorChecker(), Format.SQL, 8, 8, 9);
    }

    @Test
    void exportListOfDummiesWithNamingStrategy() {
        final Case strategy = Cases.SNAKE_LOWER_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(9, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportSqlWithTimestampWithNamingStrategy() {
        final Case strategy = Cases.LOWER_CASE.value();

        final Map<Class<?>, String> dataTypes = new HashMap<>();
        dataTypes.put(Object.class, "TIMESTAMP");

        final List<DummyTime> dummies = factory.build(DummyTime.class, 2);
        final Exporter exporter = SqlExporter.builder()
                .withCase(strategy)
                .withDataTypes(dataTypes)
                .build();

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String filename = "DummyTime" + format.getExtension();

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(16, sqlArray.length);

        validation.isDummyTimeValidWithNamingStrategy(sqlArray, strategy);
    }
}
