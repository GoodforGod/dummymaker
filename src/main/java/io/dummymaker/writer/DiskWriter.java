package io.dummymaker.writer;

import io.dummymaker.export.ExportType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class DiskWriter<T> implements IWriter {

    private BufferedWriter writer = null;

    public DiskWriter(Class<T> primeClass, String path, ExportType type) throws IOException {
        String fileType = (type != null)
                ? type.getType()
                : ".exported";

        this.writer = new BufferedWriter(new FileWriter(path + primeClass.getName() + fileType));
    }

    @Override
    public void writeLine(String value) throws IOException, NullPointerException {
        writer.write(value);
        writer.newLine();
    }

    @Override
    public void flush() throws IOException, NullPointerException {
        writer.close();
    }
}
