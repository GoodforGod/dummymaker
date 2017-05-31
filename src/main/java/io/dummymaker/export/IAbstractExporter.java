package io.dummymaker.export;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public abstract class IAbstractExporter<T> implements IExporter<T> {

    protected Class<T> primeClass;

    private IAbstractExporter() { }

    public IAbstractExporter(Class<T> primeClass) {
        this.primeClass = primeClass;
    }

    @Override
    public abstract String export(T t);

    @Override
    public abstract String export(List<T> t);
}
