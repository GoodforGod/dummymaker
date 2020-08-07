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
        super(new JsonExporter(), new JsonValidator(), 1, 2);
    }

    @Override
    protected String getEmptyListResult() {
        return "";
    }

    @Test
    public void exportListOfDummiesInJsonWithNamingStrategy() {
        final Cases strategy = Cases.UPPER_SNAKE_CASE;

        final List<Dummy> dummy = factory.build(Dummy.class, 2);
        final IExporter exporter = new JsonExporter().withCase(strategy.value());

        final String dummyAsString = exporter.convert(dummy);
        assertNotNull(dummyAsString);

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(2, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy.value());
    }
}
