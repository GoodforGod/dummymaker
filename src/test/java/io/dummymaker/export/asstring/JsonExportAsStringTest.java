package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.JsonExporter;
import io.dummymaker.export.validation.JsonValidation;
import io.dummymaker.factory.GenProduceFactory;
import io.dummymaker.factory.IProduceFactory;
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

    private IProduceFactory<Dummy> produceFactory = new GenProduceFactory<>(Dummy.class);

    private JsonValidation validation = new JsonValidation();

    @Test
    public void exportSingleDummyInJson() {
        Dummy dummy = produceFactory.produce();
        IExporter<Dummy> exporter = new JsonExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] jsonArray = dummyAsString.split("\n");
        assertEquals(5, jsonArray.length);

        validation.isSingleDummyValid(jsonArray);
    }

    @Test
    public void exportListOfDummiesInJson() {
        List<Dummy> dummy = produceFactory.produce(2);
        IExporter<Dummy> exporter = new JsonExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] jsonArray = dummyAsString.split("\n");
        assertEquals(14, jsonArray.length);

        validation.isTwoDummiesValid(jsonArray);
    }
}
