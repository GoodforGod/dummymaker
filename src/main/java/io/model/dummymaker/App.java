package io.model.dummymaker;

import io.model.dummymaker.export.IExporter;
import io.model.dummymaker.export.impl.JsonExporter;

import java.util.List;

/**
 * Hello Generator Factory!
 *
 */
public class App {

    public static void main(String[] args) {

        IPrimeFactory<TestCaseClass> factory = new GenPrimeFactory<>(TestCaseClass.class);
        IExporter<TestCaseClass> exporter = new JsonExporter<>(TestCaseClass.class);

        TestCaseClass t = factory.produce();
        List<TestCaseClass> tList = factory.produce(10);

        exporter.export(tList);

        System.out.println( "Hello World!" );
    }
}
