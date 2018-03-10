package io.dummymaker.export.asfile;

import org.junit.After;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.logging.Logger;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
abstract class ExportAssert extends Assert {

    private static final Logger logger = Logger.getLogger(FileExportAssert.class.getName());

    private String fileToDelete;

    @After
    public void cleanFile() {
        try {
            if(fileToDelete != null) {
                final File file = new File(fileToDelete);
                Files.deleteIfExists(file.toPath());
                fileToDelete = null;
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    protected void setFilenameToBeRemoved(String filename) {
        this.fileToDelete = filename;
    }

    protected String readDummyFromFile(String filename) throws Exception {
        final StringBuilder builder = new StringBuilder();
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), "UTF-8")
        );

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }

        builder.deleteCharAt(builder.length() - 1);

        reader.close();
        return builder.toString();
    }
}
