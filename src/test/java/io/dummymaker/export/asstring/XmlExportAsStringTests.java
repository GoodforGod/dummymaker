package io.dummymaker.export.asstring;

import io.dummymaker.GenFactory;
import io.dummymaker.cases.NamingCase;
import io.dummymaker.cases.NamingCases;
import io.dummymaker.export.Exporter;
import io.dummymaker.export.XmlExporter;
import io.dummymaker.export.validators.XmlValidatorChecker;
import io.dummymaker.testdata.Dummy;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class XmlExportAsStringTests extends StringExportAssert {

    private final GenFactory factory = GenFactory.build();
    private final XmlValidatorChecker validation = new XmlValidatorChecker();

    public XmlExportAsStringTests() {
        super(XmlExporter.build(), new XmlValidatorChecker(), 5, 7, 12);
    }

    @Test
    void exportListOfDummiesInXmlWithNamingStrategy() {
        final NamingCase strategy = NamingCases.CAMEL_CASE;

        final List<Dummy> dummies = factory.build(Dummy.class, 2);
        final Exporter exporter = XmlExporter.builder().withCase(strategy).build();

        final String dummyAsString = exporter.exportAsString(dummies);
        assertNotNull(dummyAsString);

        final String[] xmlArray = dummyAsString.split("\n");
        assertEquals(12, xmlArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(xmlArray, strategy);
    }
}
