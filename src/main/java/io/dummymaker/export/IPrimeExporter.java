package io.dummymaker.export;

import java.util.List;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public interface IPrimeExporter<T> {
    boolean export(T t, ExportType type);
    boolean export(T t, String path, ExportType type);

    boolean export(List<T> t, ExportType type);
    boolean export(List<T> t, String path, ExportType type);
}
