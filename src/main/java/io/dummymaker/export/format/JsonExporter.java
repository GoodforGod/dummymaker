package io.dummymaker.export.format;

import io.dummymaker.export.ExportType;
import io.dummymaker.export.IAbstractExporter;

import java.io.IOException;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class JsonExporter<T> extends IAbstractExporter<T> {

    public JsonExporter(Class<T> primeClass) throws IOException {
        super(primeClass, "~/", ExportType.JSON);
    }

    public JsonExporter(Class<T> primeClass, String path) throws IOException {
        super(primeClass, path, ExportType.JSON);
    }

    @Override
    public boolean export(T t) {

        return false;
    }

    @Override
    public boolean export(List<T> t) {

        return false;
    }

}
