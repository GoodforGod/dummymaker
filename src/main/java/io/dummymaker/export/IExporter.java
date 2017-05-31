package io.dummymaker.export;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public interface IExporter<T> {
    String export(T t);

    String export(List<T> t);
}
