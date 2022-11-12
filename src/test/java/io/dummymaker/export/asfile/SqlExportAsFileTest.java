package io.dummymaker.export.asfile;

import io.dummymaker.export.Case;
import io.dummymaker.export.Cases;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.Format;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.validators.SqlValidator;
import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class SqlExportAsFileTest extends FileExportAssert {

    private final MainGenFactory factory = new MainGenFactory();
    private final SqlValidator validation = new SqlValidator();
    private final Format format = Format.SQL;

    public SqlExportAsFileTest() {
        super(new SqlExporter(), new SqlValidator(), Format.SQL, 8, 8, 9);
    }

    // @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final Case strategy = Cases.SNAKE_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = new SqlExporter().withCase(strategy);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    public void exportSqlWithTimestampWithNamingStrategy() throws Exception {
        final Case strategy = Cases.LOW_CASE.value();
        final MainGenFactory factory = new MainGenFactory();

        final Map<Class<?>, String> dataTypes = new HashMap<>();
        dataTypes.put(Object.class, "TIMESTAMP");

        final List<DummyTime> dummies = factory.build(DummyTime.class, 2);
        final Exporter exporter = new SqlExporter()
                .withTypes(dataTypes)
                .withCase(strategy);

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
