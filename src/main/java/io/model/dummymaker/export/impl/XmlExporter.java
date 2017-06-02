package io.model.dummymaker.export.impl;

import io.model.dummymaker.export.ExportType;
import io.model.dummymaker.export.OriginExporter;

import java.io.IOException;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class XmlExporter<T>  extends OriginExporter<T> {

    public XmlExporter(Class<T> primeClass) throws IOException {
        super(primeClass, "~/", ExportType.XML);
    }

    public XmlExporter(Class<T> primeClass, String path) throws IOException {
        super(primeClass, path, ExportType.XML);
    }

    @Override
    public void export(T t) {

    }

    @Override
    public void export(List<T> t) {

    }
}
