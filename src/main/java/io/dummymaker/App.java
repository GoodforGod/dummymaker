package io.dummymaker;

import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.XmlExporter;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;

import java.util.List;

/**
 * Hello DummyMaker!
 */
class App {
    public static void main(String[] args) {
        IProduceFactory factory = new GenProduceFactory();

        List<Dummy> dummies = factory.produce(Dummy.class, 10);

        IExporter exporter = new XmlExporter();

        String result = exporter.exportAsString(dummies);
    }
}


























