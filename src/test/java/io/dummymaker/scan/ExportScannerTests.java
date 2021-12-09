package io.dummymaker.scan;


import static io.dummymaker.model.Dummy.DummyFields.*;
import static org.junit.Assert.*;

import io.dummymaker.model.Dummy;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.scan.impl.ExportScanner;
import java.util.Collection;
import org.junit.Test;


/**
 * @author GoodforGod
 * @since 05.10.2019
 */
public class ExportScannerTests {

    @Test
    public void verifyThatExportFieldsContains() {
        final IExportScanner scanner = new ExportScanner();
        final Collection<FieldContainer> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(3, fields.size());

        // Check for correct map values
        final FieldContainer groupAnnotations = fields.stream().filter(c -> c.getExportName().equals(GROUP.exportName()))
                .findFirst().get();
        final FieldContainer numAnnotations = fields.stream().filter(c -> c.getExportName().equals(NUM.exportName())).findFirst()
                .get();
        final FieldContainer nameAnnotations = fields.stream().filter(c -> c.getExportName().equals(NAME.exportName()))
                .findFirst().get();

        assertNotNull(groupAnnotations);
        assertNotNull(numAnnotations);
        assertNotNull(nameAnnotations);

        // Check for correct export annotations
        assertTrue(groupAnnotations.isSimple());
        assertTrue(numAnnotations.isSequential());
        assertTrue(nameAnnotations.isSimple());
    }
}
