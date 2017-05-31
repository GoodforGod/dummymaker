package io.dummymaker.writer;

import io.dummymaker.export.ExportType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class DiskWriter<T> implements IWriter<T> {

    private Class<T> primeClass;

    private DiskWriter() { }

    public DiskWriter(Class<T> primeClass) {
        this.primeClass = primeClass;
    }

    @Override
    public boolean flush(T t, ExportType type) {
        return false;
    }

    @Override
    public boolean flush(List<T> t, ExportType type) {
        return false;
    }

    @Override
    public boolean flush(String str, ExportType type) {

        String fileType = (type != null)
                ? type.getType()
                : ".exported";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("\\" + primeClass.getName() + fileType));
            writer.write(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
