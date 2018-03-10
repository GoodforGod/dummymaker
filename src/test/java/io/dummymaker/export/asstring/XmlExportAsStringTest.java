package io.dummymaker.export.asstring;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.Strategies;
import io.dummymaker.export.validators.XmlValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class XmlExportAsStringTest extends StringExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final XmlValidator validation = new XmlValidator();

    public XmlExportAsStringTest() {
        super(new XmlExporter().withPath(null).withStrategy((IStrategy) null).withPath("             "),
                new XmlValidator(), 5, 12);
    }

    @Test
    public void exportListOfDummiesInXmlWithNamingStrategy() throws Exception {
        final IStrategy strategy = Strategies.INITIAL_LOW_CASE.getStrategy();

        final List<Dummy> dummies = produceFactory.produce(Dummy.class, 2);
        final IExporter exporter = new XmlExporter().withStrategy(strategy).withPath("    ");

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}
