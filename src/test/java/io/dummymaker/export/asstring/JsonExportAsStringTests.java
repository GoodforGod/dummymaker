package io.dummymaker.export.asstring;

import io.dummymaker.cases.Cases;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.JsonExporter;
import io.dummymaker.export.validators.JsonValidatorChecker;
import io.dummymaker.factory.GenFactory;
import io.dummymaker.model.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class JsonExportAsStringTests extends StringExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final JsonValidatorChecker validation = new JsonValidatorChecker();

    public JsonExportAsStringTests() {
        super(JsonExporter.build(), new JsonValidatorChecker(), 1, 2);
    }

    @Override
    protected String getEmptyListResult() {
        return "";
    }

    @Test
    void exportListOfDummiesInJsonWithNamingStrategy() {
        final Cases strategy = Cases.SNAKE_UPPER_CASE;

        final List<Dummy> dummy = factory.build(Dummy.class, 2);
        final Exporter exporter = JsonExporter.builder().withCase(strategy.value()).build();

        final String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(2, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy.value());
    }
}
