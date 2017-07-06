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

    public BufferedFileWriter(final String fileName, final String path, final String fileType) {
        final String filePath = (path == null || path.trim().isEmpty()) ? "" : path;

        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filePath + fileName + fileType), "UTF-8"));
        } catch (IOException e) {
            logger.warning(e.getMessage() + " | CAN NOT CREATE BUFFERED WRITER.");
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
