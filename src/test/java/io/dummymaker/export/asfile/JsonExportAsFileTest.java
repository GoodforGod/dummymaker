package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.export.validators.JsonValidator;
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
public class JsonExportAsFileTest extends ExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final JsonValidator validation = new JsonValidator();

    private final Format format = Format.JSON;

    @Test
    public void exportSingleDummy() throws Exception {
        final Dummy dummy = produceFactory.produce(Dummy.class);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new JsonExporter().withPretty().withPath(null);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(5, jsonArray.length);

        validation.isSingleDummyValid(jsonArray);
    }

    @Test
    public void exportListOfDummies() throws Exception {
        final List<Dummy> dummy = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new JsonExporter().withPretty().withPath("   ");

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(14, jsonArray.length);

        validation.isTwoDummiesValid(jsonArray);
    }

    @Test
    public void exportSingleDummyList() throws Exception {
        final List<Dummy> dummy = produceFactory.produce(Dummy.class, 1);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new JsonExporter().withPretty().withPath("   ");

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(5, jsonArray.length);

        validation.isSingleDummyValid(jsonArray);
    }

    @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final IStrategy strategy = PresetStrategies.UNDERSCORED_UPPER_CASE.getStrategy();

        final List<Dummy> dummy = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new JsonExporter().withStrategy(strategy).withPretty().withStrategy(null);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(14, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy);
    }
}
