package io.dummymaker.factory.refactored;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static org.junit.Assert.*;

import io.dummymaker.annotation.GenDepth;
import io.dummymaker.annotation.simple.number.GenDoubleBig;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.annotation.GenSequence;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyCollection;
import io.dummymaker.model.DummyNoFillFields;

import java.util.List;

import org.junit.Test;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
public class GenScannerTests {

    @Test
    public void verifyThatFieldsFound() {
        final GenScanner scanner = new GenScanner(new GenGeneratorSupplier(1), GenRules.EMPTY, true, GenDepth.DEFAULT);
        final List<GenContainer> fields = scanner.scan(SimpleGenType.ofClass(DummyCollection.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(7, fields.size());
    }

    @Test
    public void verifyScannedFields() {
        final GenScanner scanner = new GenScanner(new GenGeneratorSupplier(1), GenRules.EMPTY, true, GenDepth.DEFAULT);
        final List<GenContainer> fields = scanner.scan(SimpleGenType.ofClass(Dummy.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());

        // Check for correct map values
        final GenContainer cityAnnotations = fields.stream().filter(c -> c.getField().getName().equals(CITY.getField().getName())).findFirst().orElse(null);
        final GenContainer numAnnotations = fields.stream().filter(c -> c.getField().getName().equals(NUM.getField().getName())).findFirst().orElse(null);
        final GenContainer nameAnnotations = fields.stream().filter(c -> c.getField().getName().equals(NAME.getField().getName())).findFirst().orElse(null);
        final GenContainer bigdAnnotations = fields.stream().filter(c -> c.getField().getName().equals(BIGD.getField().getName())).findFirst().orElse(null);
        final GenContainer lngAnnotations = fields.stream().filter(c -> c.getField().getName().equals(LNG.getField().getName())).findFirst().orElse(null);
        final GenContainer uncompaAnnotations = fields.stream().filter(c -> c.getField().getName().equals(UNCOMPA.getField().getName())).findFirst().orElse(null);

        assertNotNull(cityAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);
        assertNotNull(bigdAnnotations);
        assertNotNull(lngAnnotations);
        assertNotNull(uncompaAnnotations);

        // Check for correct export annotations
        assertEquals(cityAnnotations.getMarker().annotationType(), GenCity.class);
        assertEquals(numAnnotations.getMarker().annotationType(), GenSequence.class);
        assertEquals(nameAnnotations.getMarker().annotationType(), GenName.class);
        assertEquals(bigdAnnotations.getMarker().annotationType(), GenDoubleBig.class);
        assertEquals(lngAnnotations.getMarker().annotationType(), GenSequence.class);
        assertEquals(uncompaAnnotations.getMarker().annotationType(), GenDoubleBig.class);
    }

    @Test
    public void noFieldsScanned() {
        final GenScanner scanner = new GenScanner(new GenGeneratorSupplier(1), GenRules.EMPTY, true, GenDepth.DEFAULT);
        final List<GenContainer> fields = scanner.scan(SimpleGenType.ofClass(DummyNoFillFields.class));

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
