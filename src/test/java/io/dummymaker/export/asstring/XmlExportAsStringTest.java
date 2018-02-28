package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.export.validators.XmlValidator;
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
public class XmlExportAsStringTest {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final XmlValidator validation = new XmlValidator();

    @Test
    public void exportSingleDummyInXml() throws Exception {
        final Dummy dummy = produceFactory.produce(Dummy.class);
        final IExporter exporter = new XmlExporter().withStrategy(null);

        final String dummyAsString = exporter.exportAsString(dummy);
        assertNotNull(dummyAsString);

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(5, xmlArray.length);

        validation.isSingleDummyValid(xmlArray);
    }

    @Test
    public void exportListOfDummiesInXml() throws Exception {
        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final IExporter exporter = new XmlExporter().withPath(null);

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValid(xmlArray);
    }

    @Test
    public void exportListOfDummiesInXmlWithNamingStrategy() throws Exception {
        final IStrategy strategy = PresetStrategies.INITIAL_LOW_CASE.getStrategy();

        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final IExporter exporter = new XmlExporter().withStrategy(strategy).withPath("    ");

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}
