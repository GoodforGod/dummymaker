package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.export.validation.JsonValidation;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class JsonExportAsStringTest {

    private IProduceFactory produceFactory = new GenProduceFactory();

    private JsonValidation validation = new JsonValidation();

    @Test
    public void exportSingleDummyInJson() throws Exception {
        Dummy dummy = produceFactory.produce(Dummy.class);
        IExporter exporter = new JsonExporter().withPretty();

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] jsonArray = dummyAsString.split("\n");
        assertEquals(5, jsonArray.length);

        validation.isSingleDummyValid(jsonArray);
    }

    @Test
    public void exportListOfDummiesInJson() throws Exception {
        List<Dummy> dummy = produceFactory.produce(Dummy.class, 2);
        IExporter exporter = new JsonExporter().withPretty();

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] jsonArray = dummyAsString.split("\n");
        assertEquals(14, jsonArray.length);

        validation.isTwoDummiesValid(jsonArray);
    }

    @Test
    public void exportListOfDummiesInJsonWithNamingStrategy() throws Exception {
        final IStrategy strategy = PresetStrategies.UNDERSCORED_UPPER_CASE.getStrategy();

        List<Dummy> dummy = produceFactory.produce(Dummy.class, 2);
        IExporter exporter = new JsonExporter().withStrategy(strategy).withPretty();

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] jsonArray = dummyAsString.split("\n");
        assertEquals(14, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy);
    }
}
