package io.model.dummymaker.export;

import io.model.dummymaker.writer.DiskWriter;

import java.io.IOException;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public abstract class OriginExporter<T> extends DiskWriter<T> implements IExporter<T> {

    protected Class<T> primeClass;

    public OriginExporter(Class<T> primeClass, String path, ExportType type) throws IOException {
        super(primeClass, path, type);
        this.primeClass = primeClass;
    }

    @Override
    public abstract void export(T t);

    @Override
    public abstract void export(List<T> t);
}
