package io.dummymaker.scan;

import static io.dummymaker.model.Dummy.DummyFields.*;
import static org.junit.Assert.*;

import io.dummymaker.model.Dummy;
import io.dummymaker.scan.impl.AnnotationScanner;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class AnnotationScannerTests {

    @Test
    public void verifyThatFieldsScanned() throws NoSuchFieldException {
        final IAnnotationScanner scanner = new AnnotationScanner();
        final Map<Field, List<Annotation>> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(7, fields.size());

        // Check for map values
        assertTrue(fields.containsKey(CITY.getField()));
        assertTrue(fields.containsKey(GROUP.getField()));
        assertTrue(fields.containsKey(NUM.getField()));
        assertTrue(fields.containsKey(NAME.getField()));
        assertTrue(fields.containsKey(LNG.getField()));
        assertTrue(fields.containsKey(BIGD.getField()));
        assertTrue(fields.containsKey(UNCOMPA.getField()));
    }
}
