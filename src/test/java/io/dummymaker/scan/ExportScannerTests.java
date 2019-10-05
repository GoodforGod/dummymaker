package io.dummymaker.scan;

import io.dummymaker.beta.model.Dummy;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.scan.impl.ExportScanner;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static io.dummymaker.beta.model.Dummy.DummyFields.*;
import static org.junit.Assert.*;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class ExportScannerTests {

    @Test
    public void verifyThatExportFieldsContains() throws NoSuchFieldException {
        final IExportScanner scanner = new ExportScanner();
        final Map<Field, FieldContainer> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(3, fields.size());

        // Check for correct map values
        final FieldContainer groupAnnotations    = fields.get(GROUP.getField());
        final FieldContainer numAnnotations      = fields.get(NUM.getField());
        final FieldContainer nameAnnotations     = fields.get(NAME.getField());

        assertNotNull(groupAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);

        // Check for correct export annotations
        assertTrue(groupAnnotations.isSimple());
        assertTrue(numAnnotations.isSequential());
        assertTrue(nameAnnotations.isSimple());
    }
}
