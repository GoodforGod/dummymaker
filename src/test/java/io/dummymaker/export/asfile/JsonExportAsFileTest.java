package io.dummymaker.export.asfile;

import io.dummymaker.export.Cases;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.validators.JsonValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;

import java.util.List;

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
        super(new JsonExporter().withPretty().withPath(null).withCase(null).withPath("            "),
                new JsonValidator(), Format.JSON, 5, 14);
    }

    // @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final ICase strategy = Cases.UPPER_SNAKE_CASE.value();

        final List<Dummy> dummy = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new JsonExporter().withCase(strategy).withPretty().withCase(null);

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
