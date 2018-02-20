package io.dummymaker.scan;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.annotation.special.GenEnumerate;
import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.data.Dummy;
import io.dummymaker.data.DummyNoPopulateFields;
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
        IFieldScanner scanner = new AnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(4, fields.size());

        // Check for map values
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(CITY.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(GROUP.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(NUM.getOriginFieldName())));
        assertTrue(fields.containsKey(Dummy.class.getDeclaredField(NAME.getOriginFieldName())));
    }

    @Test
    public void exportAnnotationScannerTest() throws NoSuchFieldException {
        IFieldScanner scanner = new ExportAnnotationScanner();

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
        assertTrue(numAnnotations.iterator().next().annotationType().equals(PrimeGenAnnotation.class));
        assertTrue(nameAnnotations.iterator().next().annotationType().equals(PrimeGenAnnotation.class));
    }

    @Test
    public void scannerForEnumerateAnnotations() throws NoSuchFieldException {
        IFieldScanner scanner = new EnumerateAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(1, fields.size());

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
        IFieldScanner scanner = new PopulateAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(3, fields.size());

        // Check for correct map values
        List<Annotation> cityAnnotations    = fields.get(Dummy.class.getDeclaredField(CITY.getOriginFieldName()));
        List<Annotation> numAnnotations      = fields.get(Dummy.class.getDeclaredField(NUM.getOriginFieldName()));
        List<Annotation> nameAnnotations     = fields.get(Dummy.class.getDeclaredField(NAME.getOriginFieldName()));

        assertNotNull(cityAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);

        // Check for correct export annotations
        assertTrue(cityAnnotations.iterator().next().annotationType().equals(PrimeGenAnnotation.class));
        assertTrue(numAnnotations.iterator().next().annotationType().equals(PrimeGenAnnotation.class));
        assertTrue(nameAnnotations.iterator().next().annotationType().equals(PrimeGenAnnotation.class));
    }

    @Test
    public void scanForPopulateAnnotationsWhereThereNoOne() {
        IFieldScanner scanner = new PopulateAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }

    @Test
    public void scanForEnumerateWhereThereNoOne() {
        IFieldScanner scanner = new EnumerateAnnotationScanner();

        Map<Field, List<Annotation>> fields = scanner.scan(DummyNoPopulateFields.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }
}
