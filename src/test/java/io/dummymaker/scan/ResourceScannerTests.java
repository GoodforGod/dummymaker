package io.dummymaker.scan;

import io.dummymaker.scan.impl.ResourceScanner;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Resource scanner tests
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class ResourceScannerTests extends Assert {

    @Test
    public void validResourcesScanned() {
        final ResourceScanner scanner = new ResourceScanner();
        final Collection<String> packages = scanner.scan("io.dummymaker.writer");

        assertNotNull(packages);
        assertFalse(packages.isEmpty());

        assertTrue(packages.contains("io/dummymaker/writer/impl"));
        assertTrue(packages.contains("io/dummymaker/writer/IWriter.class"));
        assertTrue(packages.contains("io/dummymaker/writer/impl/BufferedFileWriter.class"));
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
        assertTrue(packages.contains(absolute + classpath + "io/dummymaker/writer/impl"));
        assertTrue(packages.contains(absolute + classpath + "io/dummymaker/writer/IWriter.class"));
        assertTrue(packages.contains(absolute + classpath + "io/dummymaker/writer/impl/BufferedFileWriter.class"));
    }
}
