package io.dummymaker.scan;

import io.dummymaker.scan.impl.ClassScanner;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.BufferedFileWriter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Tests that recursion class scanner works
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class ClassScannerTests extends Assert {

    @Test
    public void validClassesScanned() {
        final ClassScanner scanner = new ClassScanner();
        final Collection<Class> classes = scanner.scan("io.dummymaker.writer");

        assertNotNull(scanner);
        assertEquals(2, classes.size());
        assertTrue(classes.contains(IWriter.class));
        assertTrue(classes.contains(BufferedFileWriter.class));
    }
}
