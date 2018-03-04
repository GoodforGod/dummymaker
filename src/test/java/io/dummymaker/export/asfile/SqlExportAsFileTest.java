package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyTimestamp;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.Strategies;
import io.dummymaker.export.validators.SqlValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
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

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final SqlValidator validation = new SqlValidator();

    private final Format format = Format.SQL;

    public SqlExportAsFileTest() {
        super(new SqlExporter().withPath(null).withStrategy(null).withPath("            "),
                new SqlValidator(), Format.SQL, 9, 10);
    }

    @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final IStrategy strategy = Strategies.UNDERSCORED_LOW_CASE.getStrategy();

        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new SqlExporter().withStrategy(strategy).withPath("    ");

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
    public void exportListOfDummiesWithTimestampAndDataTypesWithNamingStrategy() throws Exception {
        final IStrategy strategy = Strategies.LOW_CASE.getStrategy();

        final Map<Class, String> dataTypes = new HashMap<>();
        dataTypes.put(Dummy.class, "keks");

        final List<DummyTimestamp> dummies = produceFactory.produce(DummyTimestamp.class, 2);
        final String filename = DummyTimestamp.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new SqlExporter().withTypes(dataTypes).withStrategy(strategy);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(12, sqlArray.length);

        validation.isTwoTimestampedDummiesValidWithNamingStrategy(sqlArray, strategy);
    }
}
