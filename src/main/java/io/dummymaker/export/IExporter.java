package io.dummymaker.export;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public interface IExporter<T> {
    boolean export(T t);

    boolean export(List<T> t);
}
