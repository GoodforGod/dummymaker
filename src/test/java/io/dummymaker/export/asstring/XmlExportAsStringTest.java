package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.XmlExporter;
import io.dummymaker.export.validation.XmlValidation;
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
public class XmlExportAsStringTest {

    private IProduceFactory<Dummy> produceFactory = new GenProduceFactory<>(Dummy.class);

    private XmlValidation validation = new XmlValidation();

    @Test
    public void exportSingleDummyInXml() {
        Dummy dummy = produceFactory.produce();
        IExporter<Dummy> exporter = new XmlExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        String[] xmlArray = dummyAsString.split("\n");
        assertEquals(5, xmlArray.length);

        validation.isSingleDummyValid(xmlArray);
    }

    @Test
    public void exportListOfDummiesInXml() {
        List<Dummy> dummies = produceFactory.produce(2);
        IExporter<Dummy> exporter = new XmlExporter<>(Dummy.class);

        String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValid(xmlArray);
    }
}
