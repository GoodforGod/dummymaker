package io.dummymaker.scan;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static org.junit.Assert.*;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.simple.number.GenDoubleBig;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyCollection;
import io.dummymaker.model.DummyNoFillFields;
import io.dummymaker.model.GenContainer;
import io.dummymaker.scan.impl.MainGenScanner;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.Test;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class GenScannerTests {

    @Test
    public void verifyThatFieldsFound() {
        final GenScanner scanner = new MainGenScanner();
        final Map<Field, GenContainer> fields = scanner.scan(DummyCollection.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(7, fields.size());
    }

    @Test
    public void verifyScannedFields() throws NoSuchFieldException {
        final GenScanner scanner = new MainGenScanner();
        final Map<Field, GenContainer> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());

        // Check for correct map values
        final GenContainer cityAnnotations = fields.get(CITY.getField());
        final GenContainer numAnnotations = fields.get(NUM.getField());
        final GenContainer nameAnnotations = fields.get(NAME.getField());
        final GenContainer bigdAnnotations = fields.get(BIGD.getField());
        final GenContainer lngAnnotations = fields.get(LNG.getField());
        final GenContainer uncompaAnnotations = fields.get(UNCOMPA.getField());

        assertNotNull(cityAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);
        assertNotNull(bigdAnnotations);
        assertNotNull(lngAnnotations);
        assertNotNull(uncompaAnnotations);

        // Check for correct export annotations
        assertEquals(cityAnnotations.getCore().annotationType(), PrimeGen.class);
        assertEquals(numAnnotations.getCore().annotationType(), PrimeGen.class);
        assertEquals(nameAnnotations.getCore().annotationType(), PrimeGen.class);
        assertEquals(bigdAnnotations.getCore().annotationType(), PrimeGen.class);
        assertEquals(lngAnnotations.getCore().annotationType(), PrimeGen.class);
        assertEquals(uncompaAnnotations.getCore().annotationType(), PrimeGen.class);

        assertEquals(cityAnnotations.getMarker().annotationType(), GenCity.class);
        assertEquals(numAnnotations.getMarker().annotationType(), GenSequence.class);
        assertEquals(nameAnnotations.getMarker().annotationType(), GenName.class);
        assertEquals(bigdAnnotations.getMarker().annotationType(), GenDoubleBig.class);
        assertEquals(lngAnnotations.getMarker().annotationType(), GenSequence.class);
        assertEquals(uncompaAnnotations.getMarker().annotationType(), GenDoubleBig.class);
    }

    @Test
    public void noFieldsScanned() {
        final GenScanner scanner = new MainGenScanner();
        final Map<Field, GenContainer> fields = scanner.scan(DummyNoFillFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
