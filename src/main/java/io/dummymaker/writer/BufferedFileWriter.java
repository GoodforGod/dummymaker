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
public class BufferedFileWriter<T> implements IWriter {

    private final Logger logger = Logger.getLogger(BufferedFileWriter.class.getName());

    private BufferedWriter writer = null;

    public BufferedFileWriter(Class<T> primeClass, String path, String fileType) {
        String filePath = (path == null || path.trim().isEmpty())
                ? "."
                : path;

        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filePath + primeClass.getSimpleName() + fileType), "UTF-8"));
        } catch (IOException e) {
            logger.warning(e.getMessage() + " | CAN NOT CREATE BUFFERED WRITER");
        }
    }

    @Override
    public void writeLine(String value) {
        try {
            writer.write(value);
            writer.newLine();
        } catch (Exception e) {
            logger.warning(e.getMessage());

            try {
                if(writer != null)
                    writer.close();
            } catch (IOException e1) {
                logger.warning(e1.getMessage() + " | CAN NOT CLOSE WRITER");
            }
        }
    }

    @Override
    public void flush() {
        try {
            writer.close();
        } catch (Exception e) {
            logger.warning(e.getMessage() + " | CAN NOT CLOSE WRITER");
        }
    }
}
