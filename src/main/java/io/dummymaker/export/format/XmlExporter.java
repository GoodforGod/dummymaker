package io.dummymaker.export.format;

import io.dummymaker.export.IAbstractExporter;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class XmlExporter<T> extends IAbstractExporter<T> {

    public XmlExporter(Class<T> primeClass) {
        super(primeClass);
    }

    @Override
    public String export(T t) {
        return null;
    }

    @Override
    public String export(List<T> t) {
        return null;
    }
}
