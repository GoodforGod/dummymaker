package io.dummymaker.export.asstring;

import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.XmlValidator;
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
public class XmlExportAsStringTest extends StringExportAssert {

    private final GenFactory factory = new GenFactory();
    private final XmlValidator validation = new XmlValidator();

    public XmlExportAsStringTest() {
        super(new XmlExporter().withPath(null).withCase((ICase) null).withPath("             "),
                new XmlValidator(), 5, 12);
    }

    @Test
    public void exportListOfDummiesInXmlWithNamingStrategy() throws Exception {
        final ICase strategy = Cases.CAMEL_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final IExporter exporter = new XmlExporter().withCase(strategy).withPath("    ");

        final String dummyAsString = exporter.convert(dummies);
        assertNotNull(dummyAsString);

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}
