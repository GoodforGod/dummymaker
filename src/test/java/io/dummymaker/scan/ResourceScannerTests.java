package io.dummymaker.scan;

import io.dummymaker.scan.impl.ResourceScanner;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

/**
 * Resource scanner tests
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class ResourceScannerTests extends Assert {

    @Test
    public void validJarResourcesForLogLibrary() {
        final ResourceScanner scanner = new ResourceScanner();
        final Collection<String> packages = scanner.scan("org.slf4j");

        assertNotNull(packages);
        assertFalse(packages.isEmpty());

        assertTrue(packages.contains("org/slf4j/"));
    }

    @Test
    public void validResourcesScanned() {
        final ResourceScanner scanner = new ResourceScanner();
        final Collection<String> packages = scanner.scan("io.dummymaker.writer");

        assertNotNull(packages);
        assertFalse(packages.isEmpty());

        assertTrue(packages.contains("io/dummymaker/writer/impl"));
        assertTrue(packages.contains("io/dummymaker/writer/" + IWriter.class.getSimpleName() + ".class"));
        assertTrue(packages.contains("io/dummymaker/writer/impl/" + FileWriter.class.getSimpleName() + ".class"));
    }

    @Test
    public void validResourcesAbsoluteScanned() {
        final ResourceScanner scanner = new ResourceScanner();
        final Collection<String> packages = scanner.scanAbsolute("io.dummymaker.writer");

        assertNotNull(packages);
        assertFalse(packages.isEmpty());

        final Path currentRelativePath = Paths.get("");
        final String absolute = "/" + currentRelativePath.toAbsolutePath().toString().replace('\\', '/');
        final String classpath = "/build/classes/java/main/";
        assertTrue(packages.stream().anyMatch(s -> s.contains("io/dummymaker/writer/impl")));
        assertTrue(
                packages.stream().anyMatch(s -> s.contains("io/dummymaker/writer/" + IWriter.class.getSimpleName() + ".class")));
        assertTrue(packages.stream()
                .anyMatch(s -> s.contains("io/dummymaker/writer/impl/" + FileWriter.class.getSimpleName() + ".class")));
    }
}
