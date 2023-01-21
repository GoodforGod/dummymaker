package io.dummymaker.export;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.export.validators.JsonValidatorChecker;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class JsonExportAsFileTests extends FileExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final JsonValidatorChecker validation = new JsonValidatorChecker();
    private final Format format = Format.JSON;

    public JsonExportAsFileTests() {
        super(JsonExporter.build(), new JsonValidatorChecker(), Format.JSON, 1, 1, 2);
    }

    @Test
    void exportStreamingToFileMultiBatch() {
        final JsonExporter exporter = JsonExporter.builder().withWriter(name -> new DefaultFileWriter(name, false)).build();
        final List<Dummy> dummies = factory.build(Dummy::new, 31000);
        final boolean exported = exporter.exportAsFile(dummies);
        assertTrue(exported);

        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final String dummyAsString = readFromFile(filename);

        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(31000, jsonArray.length);
    }

    @Test
    void exportStreamingToFileSingleBatch() {
        final JsonExporter exporter = JsonExporter.builder().withWriter(name -> new DefaultFileWriter(name, false)).build();
        final List<Dummy> dummies = factory.build(Dummy::new, 11000);
        final boolean exported = exporter.exportAsFile(dummies);
        assertTrue(exported);

        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final String dummyAsString = readFromFile(filename);

        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(11000, jsonArray.length);
    }

    @Test
    void exportStreamingToFileNoBatch() {
        final JsonExporter exporter = JsonExporter.builder().withWriter(name -> new DefaultFileWriter(name, false)).build();
        final List<Dummy> dummies = factory.build(Dummy::new, 1000);
        final boolean exported = exporter.exportAsFile(dummies);
        assertTrue(exported);

        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final String dummyAsString = readFromFile(filename);

        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(1000, jsonArray.length);
    }

    @Test
    void exportListOfDummiesWithNamingStrategy() {
        final Case strategy = Cases.SNAKE_UPPER_CASE.value();

        final List<Dummy> dummy = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = JsonExporter.builder().withCase(strategy).build();

        final boolean exportResult = exporter.exportAsFile(dummy);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(2, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy);
    }
}
