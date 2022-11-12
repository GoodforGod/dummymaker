package io.dummymaker.export.asstring;

import io.dummymaker.export.Case;
import io.dummymaker.export.Cases;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.XmlValidator;
import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import java.util.List;
import org.junit.Test;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class XmlExportAsStringTest extends StringExportAssert {

    private final MainGenFactory factory = new MainGenFactory();
    private final XmlValidator validation = new XmlValidator();

    public XmlExportAsStringTest() {
        super(new XmlExporter(), new XmlValidator(), 5, 7, 12);
    }

    @Test
    public void exportListOfDummiesInXmlWithNamingStrategy() {
        final Case strategy = Cases.CAMEL_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = new XmlExporter().withCase(strategy);

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}
