package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.export.validators.SqlValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class SqlExportAsFileTest extends ExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final SqlValidator validation = new SqlValidator();

    private final Format format = Format.SQL;

    @Test
    public void exportSingleDummy() throws Exception {
        final Dummy dummy = produceFactory.produce(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new SqlExporter().withPath(null);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(9, sqlArray.length);

        validation.isSingleDummyValid(sqlArray);
    }

    @Test
    public void exportSingleDummyList() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 1);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new SqlExporter().withStrategy(null);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(9, sqlArray.length);

        validation.isSingleDummyValid(sqlArray);
    }

    @Test
    public void exportListOfDummies() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new SqlExporter().withStrategy(null);

        final boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] sqlArray = dummyAsString.split("\n");
        assertEquals(10, sqlArray.length);

        validation.isTwoDummiesValid(sqlArray);
    }

    @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final IStrategy strategy = PresetStrategies.UNDERSCORED_LOW_CASE.getStrategy();

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
}
