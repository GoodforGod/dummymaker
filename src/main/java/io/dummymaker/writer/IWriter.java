package io.dummymaker.writer;

import io.dummymaker.export.ExportType;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public interface IWriter<T> {
    boolean flush(T t, ExportType type);

    boolean flush(List<T> t, ExportType type);

    boolean flush(String value, ExportType type);
}
