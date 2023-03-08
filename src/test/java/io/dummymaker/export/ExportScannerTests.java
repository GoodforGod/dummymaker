package io.dummymaker.export;

import static io.dummymaker.testdata.Dummy.DummyFields.*;
import static org.junit.jupiter.api.Assertions.*;

import io.dummymaker.testdata.Dummy;
import java.util.Collection;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 05.10.2019
 */
class ExportScannerTests {

    @Test
    void verifyThatExportFieldsContains() {
        final ExportScanner scanner = new ExportScanner();
        final Collection<ExportField> fields = scanner.scan(Dummy.class);

        // Check for correct fields number in map
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
        assertEquals(3, fields.size());

        // Check for correct map values
        final ExportField groupAnnotations = fields.stream().filter(c -> c.getName().equals(GROUP.exportName()))
                .findFirst().get();
        final ExportField numAnnotations = fields.stream().filter(c -> c.getName().equals(NUM.exportName())).findFirst()
                .get();
        final ExportField nameAnnotations = fields.stream().filter(c -> c.getName().equals(NAME.exportName()))
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
