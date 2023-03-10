package io.dummymaker;

import io.dummymaker.annotation.GenDepth;
import io.dummymaker.annotation.simple.number.GenDoubleSmall;
import io.dummymaker.annotation.simple.number.GenSequence;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.testdata.Dummy;
import io.dummymaker.testdata.DummyCollection;
import io.dummymaker.testdata.DummyNoFillFields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.dummymaker.testdata.Dummy.DummyFields.*;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
class GenScannerTests extends Assertions {

    @Test
    void verifyThatFieldsFound() {
        final GenScanner scanner = new GenScanner(new DefaultGeneratorSupplier(1), GenRules.EMPTY, false, GenDepth.DEFAULT);
        final List<GenContainer> fields = scanner.scan(SimpleGenType.ofClass(DummyCollection.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(7, fields.size());
    }

    @Test
    void verifyScannedFields() {
        final GenScanner scanner = new GenScanner(new DefaultGeneratorSupplier(1), GenRules.EMPTY, false, GenDepth.DEFAULT);
        final List<GenContainer> fields = scanner.scan(SimpleGenType.ofClass(Dummy.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());

        // Check for correct map values
        final GenContainer cityAnnotations = fields.stream().filter(c -> c.field().getName().equals(CITY.getField().getName()))
                .findFirst().orElse(null);
        final GenContainer numAnnotations = fields.stream().filter(c -> c.field().getName().equals(NUM.getField().getName()))
                .findFirst().orElse(null);
        final GenContainer nameAnnotations = fields.stream().filter(c -> c.field().getName().equals(NAME.getField().getName()))
                .findFirst().orElse(null);
        final GenContainer bigdAnnotations = fields.stream().filter(c -> c.field().getName().equals(BIGD.getField().getName()))
                .findFirst().orElse(null);
        final GenContainer lngAnnotations = fields.stream().filter(c -> c.field().getName().equals(LNG.getField().getName()))
                .findFirst().orElse(null);
        final GenContainer uncompaAnnotations = fields.stream()
                .filter(c -> c.field().getName().equals(UNCOMPA.getField().getName())).findFirst().orElse(null);

        assertNotNull(cityAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);
        assertNotNull(bigdAnnotations);
        assertNotNull(lngAnnotations);
        assertNotNull(uncompaAnnotations);

        // Check for correct export annotations
        assertEquals(cityAnnotations.marker().annotationType(), GenCity.class);
        assertEquals(numAnnotations.marker().annotationType(), GenSequence.class);
        assertEquals(nameAnnotations.marker().annotationType(), GenName.class);
        assertEquals(bigdAnnotations.marker().annotationType(), GenDoubleSmall.class);
        assertEquals(lngAnnotations.marker().annotationType(), GenSequence.class);
        assertEquals(uncompaAnnotations.marker().annotationType(), GenDoubleSmall.class);
    }

    @Test
    void noFieldsScanned() {
        final GenScanner scanner = new GenScanner(new DefaultGeneratorSupplier(1), GenRules.EMPTY, false, GenDepth.DEFAULT);
        final List<GenContainer> fields = scanner.scan(SimpleGenType.ofClass(DummyNoFillFields.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
