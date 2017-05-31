package io.dummymaker.export.format;

import io.dummymaker.export.IExporter;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class CsvExporter<T> implements IExporter<T> {

    @Override
    public String export(T t) {
        return null;
    }

    @Override
    public String export(List<T> t) {
        return null;
    }
}
