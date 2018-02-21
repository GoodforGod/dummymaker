package io.dummymaker.writer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class BufferedFileWriter implements IWriter {

    private final Logger logger = Logger.getLogger(BufferedFileWriter.class.getName());

    private BufferedWriter writer = null;

    private String path;

    /**
     * @param fileName file name
     * @param path     path where to create file (NULL IF HOME DIR)
     * @param fileType file extension
     */
    public BufferedFileWriter(final String fileName, final String path, final String fileType) {
        this.path = (path == null || path.trim().isEmpty())
                ? ""
                : path;

        this.path += fileName + fileType;
    }

    @Override
    public boolean initWriter() {
        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(path), "UTF-8")
            );
            return true;
        } catch (IOException e) {
            logger.warning(e.getMessage() + " | CAN NOT CREATE BUFFERED WRITER.");
            return false;
        }
    }

    @Override
    public boolean writeLine(final String value) {
        try {
            writer.write(value);
            writer.newLine();
            return true;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            flush();
            return false;
        }
    }

    @Override
    public boolean flush() {
        try {
            if (writer != null)
                writer.close();
            return true;
        } catch (Exception e) {
            logger.warning(e.getMessage() + " | CAN NOT CLOSE WRITER");
            return false;
        }
    }
}
