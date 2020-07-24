package io.dummymaker.export.asfile;

import io.dummymaker.export.Cases;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.validators.SqlValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyTime;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class SqlExportAsFileTest extends FileExportAssert {

    private final GenFactory factory = new GenFactory();
    private final SqlValidator validation = new SqlValidator();
    private final Format format = Format.SQL;

    public SqlExportAsFileTest() {
        super(new SqlExporter(), new SqlValidator(), Format.SQL, 9, 10);
    }

    // @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final ICase strategy = Cases.SNAKE_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new SqlExporter().withCase(strategy);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(sqlArray, strategy);
    }

    @Test
    public void exportSqlWithTimestampWithNamingStrategy() throws Exception {
        final ICase strategy = Cases.LOW_CASE.value();
        final GenFactory factory = new GenFactory();

        final Map<Class<?>, String> dataTypes = new HashMap<>();
        dataTypes.put(Object.class, "TIMESTAMP");

        final List<DummyTime> dummies = factory.build(DummyTime.class, 2);
        final IExporter exporter = new SqlExporter()
                .withTypes(dataTypes)
                .withCase(strategy);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String filename = "TimeDummyClass" + format.getExtension();
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(15, sqlArray.length);

        validation.isDummyTimeValidWithNamingStrategy(sqlArray, strategy);
    }
}
