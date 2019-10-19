package io.dummymaker.scan;

import io.dummymaker.annotation.special.GenSequence;
import io.dummymaker.model.DummyArray;
import io.dummymaker.model.DummySimple;
import io.dummymaker.scan.impl.SequenceScanner;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class SequenceScannerTests {

    @Test
    public void sequenceIsEmpty() {
        final IAnnotationScanner scanner = new SequenceScanner();
        final Map<Field, List<Annotation>> fields = scanner.scan(DummyArray.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }

    @Test
    public void sequenceIsPresent() throws NoSuchFieldException {
        final IAnnotationScanner scanner = new SequenceScanner();
        final Map<Field, List<Annotation>> fields = scanner.scan(DummySimple.class);

        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(1, fields.size());

        // Check for correct map values
        final List<Annotation> numAnnotations = fields.get(DummySimple.class.getDeclaredField("number"));
        assertNotNull(numAnnotations);

        // Check for correct export annotations
        assertEquals(numAnnotations.get(0).annotationType(), GenSequence.class);
    }
}
