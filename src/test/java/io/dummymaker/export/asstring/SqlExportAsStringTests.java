package io.dummymaker.export.asstring;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.SqlExporter;
import io.dummymaker.export.validators.SqlValidatorChecker;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyArray;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class SqlExportAsStringTests extends StringExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final SqlValidatorChecker validation = new SqlValidatorChecker();

    public SqlExportAsStringTests() {
        super(SqlExporter.build(), new SqlValidatorChecker(), 8, 9);
    }

    @Test
    void exportListOfDummiesInSqlWithNamingStrategy() {
        final Case strategy = Cases.SNAKE_LOWER_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(9, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    void exportDummiesWithArrays() {
        final Case strategy = Cases.SNAKE_LOWER_CASE.value();

        final List<DummyArray> dummies = factory.build(DummyArray.class, 2);
        final Exporter exporter = SqlExporter.builder().withCase(strategy).build();

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesArrayValidWithNamingStrategy(sqlArray, strategy);
    }
}
