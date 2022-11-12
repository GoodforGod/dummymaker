package io.dummymaker.export.asfile;

import io.dummymaker.export.Case;
import io.dummymaker.export.Cases;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.Format;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.XmlValidator;
import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.Dummy;
import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class XmlExportAsFileTest extends FileExportAssert {

    private final MainGenFactory factory = new MainGenFactory();
    private final XmlValidator validation = new XmlValidator();
    private final Format format = Format.XML;

    public XmlExportAsFileTest() {
        super(new XmlExporter(), new XmlValidator(), Format.XML, 5, 7, 12);
    }

    // @Test
    public void exportListOfDummiesWithNamingStrategy() {
        final Case strategy = Cases.CAMEL_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = new XmlExporter().withCase(strategy);

        boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}
