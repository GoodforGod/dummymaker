package io.dummymaker.export.asfile;

import io.dummymaker.export.Cases;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.validators.JsonValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.writer.impl.FileWriter;
import java.util.List;
import org.junit.Test;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class JsonExportAsFileTest extends FileExportAssert {

    private final GenFactory factory = new GenFactory();
    private final JsonValidator validation = new JsonValidator();
    private final Format format = Format.JSON;

    public JsonExportAsFileTest() {
        super(new JsonExporter(), new JsonValidator(), Format.JSON, 1, 1, 2);
    }

    @Test
    public void exportStreamingToFileMultiBatch() {
        final JsonExporter exporter = new JsonExporter(name -> new FileWriter(name, false));
        final boolean exported = factory.export(Dummy::new, 31000, exporter);
        assertTrue(exported);

        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final String dummyAsString = readFromFile(filename);

        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(31000, jsonArray.length);
    }

    @Test
    public void exportStreamingToFileSingleBatch() {
        final JsonExporter exporter = new JsonExporter(name -> new FileWriter(name, false));
        final boolean exported = factory.export(Dummy::new, 11000, exporter);
        assertTrue(exported);

        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final String dummyAsString = readFromFile(filename);

        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(11000, jsonArray.length);
    }

    @Test
    public void exportStreamingToFileNoBatch() {
        final JsonExporter exporter = new JsonExporter(name -> new FileWriter(name, false));
        final boolean exported = factory.export(Dummy::new, 1000, exporter);
        assertTrue(exported);

        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final String dummyAsString = readFromFile(filename);

        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(1000, jsonArray.length);
    }

    // @Test
    public void exportListOfDummiesWithNamingStrategy() {
        final ICase strategy = Cases.UPPER_SNAKE_CASE.value();

        final List<Dummy> dummy = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new JsonExporter().withCase(strategy);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(14, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy);
    }
}
