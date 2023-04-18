package io.goodforgod.dummymaker.export;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

/**
 * @author GoodforGod
 * @since 03.03.2018
 */
abstract class ExportAssert extends Assertions {

    private final Set<String> fileToDelete = new HashSet<>();

    @AfterEach
    public void cleanFile() {
        fileToDelete.forEach(f -> {
            try {
                final File file = new File(f);
                Files.deleteIfExists(file.toPath());
            } catch (Exception e) {}
        });

        fileToDelete.clear();
    }

    void markFileForRemoval(String filename) {
        fileToDelete.add(filename);
    }

    protected String readFromFile(String filename) {
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            markFileForRemoval(filename);
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            if (!filename.endsWith("."))
                readFromFile(filename + ".");

            final FilenameFilter filter = (dir, name) -> !name.startsWith(".")
                    && !name.contains("gradle")
                    && !name.contains("README");

            final String[] list = new File(".").list(filter);
            fail("Filename - '" + filename + "', Files on path - " + Arrays.toString(list) + ", Error - " + e.getMessage());
            return "";
        }
    }
}
