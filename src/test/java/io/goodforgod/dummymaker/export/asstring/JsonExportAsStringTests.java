package io.goodforgod.dummymaker.export.asstring;

import io.goodforgod.dummymaker.GenFactory;
import io.goodforgod.dummymaker.GenRule;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.export.Exporter;
import io.goodforgod.dummymaker.export.JsonExporter;
import io.goodforgod.dummymaker.export.validators.JsonValidatorChecker;
import io.goodforgod.dummymaker.testdata.Dummy;
import io.goodforgod.dummymaker.testdata.DummyArray;
import java.util.List;
import java.util.regex.Pattern;
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
        final NamingCases strategy = NamingCases.SNAKE_UPPER_CASE;

        final List<Dummy> dummy = factory.build(Dummy.class, 2);
        final Exporter exporter = JsonExporter.builder().withCase(strategy).build();

        final String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(2, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy);
    }

    @Test
    void arrayDummyToJson() {
        final GenFactory factory = GenFactory.builder().addRule(GenRule.ofClass(DummyArray.class)).build();
        final DummyArray dummyArray = factory.build(DummyArray.class);

        final Pattern patternSingleArray = Pattern.compile("\"shortSimple\":\\[[0-9]+");
        final Pattern patternDoubleArray = Pattern.compile("\"DoubleObjDouble\":\\[\\[-?[0-9]+\\.[0-9]+");
        final String json = JsonExporter.build().exportAsString(dummyArray);

        assertTrue(patternSingleArray.matcher(json).find());
        assertTrue(patternDoubleArray.matcher(json).find());
    }
}
