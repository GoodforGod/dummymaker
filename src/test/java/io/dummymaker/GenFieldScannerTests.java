package io.dummymaker;

import static io.dummymaker.testdata.Dummy.DummyFields.*;

import io.dummymaker.testdata.Dummy;
import io.dummymaker.testdata.DummyCollection;
import io.dummymaker.testdata.DummyNoFillFields;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
class GenFieldScannerTests extends Assertions {

    @Test
    void verifyThatFieldsFound() {
        final GenFieldScanner scanner = new GenFieldScanner(new GeneratorSupplier(1), GenRules.EMPTY.context(), false);
        final List<GenField> fields = scanner.scan(GenType.ofClass(DummyCollection.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(7, fields.size());
    }

    @Test
    void verifyScannedFields() {
        final GenFieldScanner scanner = new GenFieldScanner(new GeneratorSupplier(1), GenRules.EMPTY.context(), false);
        final List<GenField> fields = scanner.scan(GenType.ofClass(Dummy.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());

        // Check for correct map values
        final GenField cityAnnotations = fields.stream().filter(c -> c.name().equals(CITY.getField().getName()))
                .findFirst().orElse(null);
        final GenField numAnnotations = fields.stream().filter(c -> c.name().equals(NUM.getField().getName()))
                .findFirst().orElse(null);
        final GenField nameAnnotations = fields.stream().filter(c -> c.name().equals(NAME.getField().getName()))
                .findFirst().orElse(null);
        final GenField bigdAnnotations = fields.stream().filter(c -> c.name().equals(BIGD.getField().getName()))
                .findFirst().orElse(null);
        final GenField lngAnnotations = fields.stream().filter(c -> c.name().equals(LNG.getField().getName()))
                .findFirst().orElse(null);
        final GenField uncompaAnnotations = fields.stream()
                .filter(c -> c.name().equals(UNCOMPA.getField().getName())).findFirst().orElse(null);

        assertNotNull(cityAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);
        assertNotNull(bigdAnnotations);
        assertNotNull(lngAnnotations);
        assertNotNull(uncompaAnnotations);
    }

    @Test
    void noFieldsScanned() {
        final GenFieldScanner scanner = new GenFieldScanner(new GeneratorSupplier(1), GenRules.EMPTY.context(), false);
        final List<GenField> fields = scanner.scan(GenType.ofClass(DummyNoFillFields.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
