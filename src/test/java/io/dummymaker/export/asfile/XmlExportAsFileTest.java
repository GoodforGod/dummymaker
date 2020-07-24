package io.dummymaker.export.asfile;

import io.dummymaker.export.Cases;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.export.validators.XmlValidator;
import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.Dummy;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class XmlExportAsFileTest extends FileExportAssert {

    private final GenFactory factory = new GenFactory();
    private final XmlValidator validation = new XmlValidator();
    private final Format format = Format.XML;

    public XmlExportAsFileTest() {
        super(new XmlExporter(), new XmlValidator(), Format.XML, 5, 12);
    }

    // @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final ICase strategy = Cases.CAMEL_CASE.value();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new XmlExporter().withCase(strategy);

        boolean exportResult = exporter.export(dummies);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}
