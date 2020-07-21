package io.dummymaker.export.asstring;

import io.dummymaker.export.Cases;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.validators.JsonValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;
import org.junit.Test;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class JsonExportAsStringTest extends StringExportAssert {

    private final GenFactory factory = new GenFactory();
    private final JsonValidator validation = new JsonValidator();

    public JsonExportAsStringTest() {
        super(new JsonExporter().withPretty().withPath(null).withCase(null).withPath("            "),
                new JsonValidator(), 5, 12);
    }

    @Test
    public void exportListOfDummiesInJsonWithNamingStrategy() throws Exception {
        final Cases strategy = Cases.UPPER_SNAKE_CASE;

        final List<Dummy> dummy = factory.build(Dummy.class, 2);
        final IExporter exporter = new JsonExporter().withCase(strategy.value()).withPretty().withCase(null);

        final String dummyAsString = exporter.convert(dummy);
        assertNotNull(dummyAsString);

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(12, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy.value());
    }
}
