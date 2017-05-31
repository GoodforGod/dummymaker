package io.dummymaker.export;

import io.dummymaker.writer.DiskWriter;

import java.io.IOException;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public abstract class IAbstractExporter<T> extends DiskWriter<T> implements IExporter<T> {

    protected Class<T> primeClass;

    public IAbstractExporter(Class<T> primeClass, String path, ExportType type) throws IOException {
        super(primeClass, path, type);
        this.primeClass = primeClass;
    }

    @Override
    public abstract boolean export(T t);

    @Override
    public abstract boolean export(List<T> t);
}
