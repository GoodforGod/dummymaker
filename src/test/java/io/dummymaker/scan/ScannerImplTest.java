package io.dummymaker.scan;

import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.simple.number.GenDoubleBig;
import io.dummymaker.annotation.simple.string.GenCity;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyCollection;
import io.dummymaker.data.DummyNoPopulateFields;
import io.dummymaker.data.DummyNoZeroConstructor;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.export.FieldContainer;
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
        IAnnotationScanner scanner = new AnnotationScanner();

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
        assertTrue(numAnnotations.isSequential());
        assertTrue(nameAnnotations.isSimple());
    }

    @Test
    public void scannerForEnumerateAnnotations() throws NoSuchFieldException {
        IAnnotationScanner scanner = new SequenceScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(2, fields.size());

        // Check for correct map values
        List<Annotation> numAnnotations = fields.get(Dummy.class.getDeclaredField(NUM.getOriginFieldName()));

        assertNotNull(numAnnotations);

        // Check for correct export annotations
        assertEquals(numAnnotations.iterator().next().annotationType(), GenSequence.class);
    }

    @Test
    public void scannerForExportEmbeddedFreeAnnotation() {
        IPopulateScanner scanner = new PopulateSimpleScanner();

        Map<Field, GenContainer> fields = scanner.scan(DummyCollection.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());
    }

    @Test
    public void scannerForExportEmbeddedFreeCollectionAnnotation() {
        IPopulateScanner scanner = new PopulateSimpleScanner();

        Map<Field, GenContainer> fields = scanner.scan(DummyNoZeroConstructor.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
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
    public void scanForPopulateAnnotationsWhereThereNoOne() {
        IPopulateScanner scanner = new PopulateScanner();

        Map<Field, GenContainer> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }

    @Test
    public void scanForEnumerateWhereThereNoOne() {
        IAnnotationScanner scanner = new SequenceScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
