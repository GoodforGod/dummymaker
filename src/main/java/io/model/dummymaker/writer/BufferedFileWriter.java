package io.model.dummymaker.writer;

import io.model.dummymaker.export.ExportType;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class BufferedFileWriter<T> implements IWriter {

    private final Logger logger = Logger.getLogger(BufferedFileWriter.class.getName());

    private BufferedWriter writer = null;

    protected final String HOME_PATH = "";

    public BufferedFileWriter(Class<T> primeClass, String path, ExportType type) {
        String fileType = (type != null)
                ? type.getValue()
                : ".exported";

        String writePath = (path == null || path.trim().isEmpty())
                ? HOME_PATH
                : path;

        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(writePath+ primeClass.getSimpleName() + fileType), "UTF-8"));
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void writeLine(String value) throws IOException, NullPointerException {
        try {
            writer.write(value);
            writer.newLine();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());

            try {
                if(writer != null)
                    writer.close();
            } catch (IOException e1) { }
        }
    }

    @Override
    public void flush() throws IOException, NullPointerException {
        try {
            writer.close();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
