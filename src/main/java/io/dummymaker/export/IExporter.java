package io.dummymaker.export;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public interface IExporter<T> {
    void export(T t);

    void export(List<T> t);
}
