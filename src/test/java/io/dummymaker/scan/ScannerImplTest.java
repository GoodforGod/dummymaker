package io.dummymaker.scan;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.simple.number.GenDoubleBig;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyCollection;
import io.dummymaker.data.DummyNoPopulateFields;
import io.dummymaker.data.DummyNoZeroConstructor;
import io.dummymaker.scan.impl.*;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static io.dummymaker.data.Dummy.DummyFieldNames.*;
import static org.junit.Assert.*;

/**
 * Tests correct scanners outputs
 *
 * @author GoodforGod
 * @since 31.07.2017
 */
public class ScannerImplTest {

    @Test
    public void baseScannerFindAllAnnotations() throws NoSuchFieldException {
        IAnnotationScanner scanner = new BasicScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(7, fields.size());

        // Check for map values
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(CITY.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(GROUP.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(NUM.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(NAME.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(LNG.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(BIGD.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(UNCOMPA.getOriginFieldName())));
    }

    @Test
    public void exportAnnotationScannerTest() throws NoSuchFieldException {
        IExportScanner scanner = new ExportScanner();

        Map<Field, FieldContainer> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(3, fields.size());

        // Check for correct map values
        FieldContainer groupAnnotations    = fields.get(Dummy.class.getDeclaredField(GROUP.getOriginFieldName()));
        FieldContainer numAnnotations      = fields.get(Dummy.class.getDeclaredField(NUM.getOriginFieldName()));
        FieldContainer nameAnnotations     = fields.get(Dummy.class.getDeclaredField(NAME.getOriginFieldName()));

        assertNotNull(groupAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);

        // Check for correct export annotations
        assertTrue(groupAnnotations.isSimple());
        assertTrue(numAnnotations.isEnumerable());
        assertTrue(nameAnnotations.isSimple());
    }

    @Test
    public void scannerForEnumerateAnnotations() throws NoSuchFieldException {
        IAnnotationScanner scanner = new EnumerateScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(2, fields.size());

        // Check for correct map values
        List<Annotation> numAnnotations = fields.get(Dummy.class.getDeclaredField(NUM.getOriginFieldName()));

        assertNotNull(numAnnotations);

        // Check for correct export annotations
        assertTrue(numAnnotations.iterator().next().annotationType().equals(GenEnumerate.class));
    }

    @Test
    public void scannerForExportEmbeddedFreeAnnotation() throws NoSuchFieldException {
        IPopulateScanner scanner = new PopulateEmbeddedFreeScanner();

        Map<Field, GenContainer> fields = scanner.scan(DummyCollection.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());
    }

    @Test
    public void scannerForExportEmbeddedFreeCollectionAnnotation() {
        IPopulateScanner scanner = new PopulateEmbeddedFreeScanner();

        Map<Field, GenContainer> fields = scanner.scan(DummyNoZeroConstructor.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
    }

    @Test
    public void scannerForPopulateAnnotations() throws NoSuchFieldException {
        IPopulateScanner scanner = new PopulateScanner();

        Map<Field, GenContainer> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());

        // Check for correct map values
        GenContainer cityAnnotations    = fields.get(Dummy.class.getDeclaredField(CITY.getOriginFieldName()));
        GenContainer numAnnotations     = fields.get(Dummy.class.getDeclaredField(NUM.getOriginFieldName()));
        GenContainer nameAnnotations    = fields.get(Dummy.class.getDeclaredField(NAME.getOriginFieldName()));
        GenContainer bigdAnnotations    = fields.get(Dummy.class.getDeclaredField(BIGD.getOriginFieldName()));
        GenContainer lngAnnotations     = fields.get(Dummy.class.getDeclaredField(LNG.getOriginFieldName()));
        GenContainer uncompaAnnotations = fields.get(Dummy.class.getDeclaredField(UNCOMPA.getOriginFieldName()));

        assertNotNull(cityAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);
        assertNotNull(bigdAnnotations);
        assertNotNull(lngAnnotations);
        assertNotNull(uncompaAnnotations);

        // Check for correct export annotations
        assertTrue(cityAnnotations.getCore().annotationType().equals(PrimeGen.class));
        assertTrue(numAnnotations.getCore().annotationType().equals(PrimeGen.class));
        assertTrue(nameAnnotations.getCore().annotationType().equals(PrimeGen.class));
        assertTrue(bigdAnnotations.getCore().annotationType().equals(PrimeGen.class));
        assertTrue(lngAnnotations.getCore().annotationType().equals(PrimeGen.class));
        assertTrue(uncompaAnnotations.getCore().annotationType().equals(PrimeGen.class));

        assertTrue(cityAnnotations.getMarker().annotationType().equals(GenCity.class));
        assertTrue(numAnnotations.getMarker().annotationType().equals(GenEnumerate.class));
        assertTrue(nameAnnotations.getMarker().annotationType().equals(GenName.class));
        assertTrue(bigdAnnotations.getMarker().annotationType().equals(GenDoubleBig.class));
        assertTrue(lngAnnotations.getMarker().annotationType().equals(GenEnumerate.class));
        assertTrue(uncompaAnnotations.getMarker().annotationType().equals(GenDoubleBig.class));
    }

    @Test
    public void scanForPopulateAnnotationsWhereThereNoOne() {
        IPopulateScanner scanner = new PopulateScanner();

        Map<Field, GenContainer> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }

    @Test
    public void scanForEnumerateWhereThereNoOne() {
        IAnnotationScanner scanner = new EnumerateScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
