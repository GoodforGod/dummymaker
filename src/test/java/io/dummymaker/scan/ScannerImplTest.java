package io.dummymaker.scan;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoPopulateFields;
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
        IAnnotationScanner scanner = new BasicAnnotationScanner();

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
        IAnnotationScanner scanner = new ExportAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(3, fields.size());

        // Check for correct map values
        List<Annotation> groupAnnotations    = fields.get(Dummy.class.getDeclaredField(GROUP.getOriginFieldName()));
        List<Annotation> numAnnotations      = fields.get(Dummy.class.getDeclaredField(NUM.getOriginFieldName()));
        List<Annotation> nameAnnotations     = fields.get(Dummy.class.getDeclaredField(NAME.getOriginFieldName()));


        assertNotNull(groupAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);

        // Check for correct export annotations
        assertTrue(groupAnnotations.iterator().next().annotationType().equals(GenForceExport.class));
        assertTrue(numAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
        assertTrue(nameAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
    }

    @Test
    public void scannerForEnumerateAnnotations() throws NoSuchFieldException {
        IAnnotationScanner scanner = new EnumerateAnnotationScanner();

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
    public void scannerForRenameAnnotations() throws NoSuchFieldException {
        IScanner<String, String> scanner = new RenameAnnotationScanner();

        Map<String, String> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(1, fields.size());

        // Check for correct map values
        String groupFieldRenamedValue = fields.get(GROUP.getOriginFieldName());

        assertNotNull(groupFieldRenamedValue);

        // Check for correct export annotations
        assertEquals("socialGroup", groupFieldRenamedValue);
    }

    @Test
    public void scannerForPopulateAnnotations() throws NoSuchFieldException {
        IAnnotationScanner scanner = new PopulateAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(6, fields.size());

        // Check for correct map values
        List<Annotation> cityAnnotations    = fields.get(Dummy.class.getDeclaredField(CITY.getOriginFieldName()));
        List<Annotation> numAnnotations     = fields.get(Dummy.class.getDeclaredField(NUM.getOriginFieldName()));
        List<Annotation> nameAnnotations    = fields.get(Dummy.class.getDeclaredField(NAME.getOriginFieldName()));
        List<Annotation> bigdAnnotations    = fields.get(Dummy.class.getDeclaredField(BIGD.getOriginFieldName()));
        List<Annotation> lngAnnotations     = fields.get(Dummy.class.getDeclaredField(LNG.getOriginFieldName()));
        List<Annotation> uncompaAnnotations = fields.get(Dummy.class.getDeclaredField(UNCOMPA.getOriginFieldName()));

        assertNotNull(cityAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);
        assertNotNull(bigdAnnotations);
        assertNotNull(lngAnnotations);
        assertNotNull(uncompaAnnotations);

        // Check for correct export annotations
        assertTrue(cityAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
        assertTrue(numAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
        assertTrue(nameAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
        assertTrue(bigdAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
        assertTrue(lngAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
        assertTrue(uncompaAnnotations.iterator().next().annotationType().equals(PrimeGen.class));
    }

    @Test
    public void scanForPopulateAnnotationsWhereThereNoOne() {
        IAnnotationScanner scanner = new PopulateAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }

    @Test
    public void scanForEnumerateWhereThereNoOne() {
        IAnnotationScanner scanner = new EnumerateAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
