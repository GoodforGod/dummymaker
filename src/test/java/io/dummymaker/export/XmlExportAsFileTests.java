package io.dummymaker.export;

import io.dummymaker.GenFactory;
import io.dummymaker.cases.NamingCase;
import io.dummymaker.cases.NamingCases;
import io.dummymaker.export.validators.XmlValidatorChecker;
import io.dummymaker.testdata.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class XmlExportAsFileTests extends FileExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final XmlValidatorChecker validation = new XmlValidatorChecker();
    private final Format format = Format.XML;

    public XmlExportAsFileTests() {
        super(XmlExporter.build(), new XmlValidatorChecker(), Format.XML, 5, 7, 12);
    }

    @Test
    void exportListOfDummiesWithNamingStrategy() {
        final NamingCase strategy = NamingCases.CAMEL_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final Exporter exporter = XmlExporter.builder().withCase(strategy).build();

        exporter.exportAsFile(dummies);

        final String dummyAsString = readFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}