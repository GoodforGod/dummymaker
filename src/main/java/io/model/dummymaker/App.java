package io.model.dummymaker;

import io.model.dummymaker.export.IExporter;
import io.model.dummymaker.export.impl.CsvExporter;
import io.model.dummymaker.export.impl.JsonExporter;
import io.model.dummymaker.export.impl.SqlExporter;
import io.model.dummymaker.export.impl.XmlExporter;

import java.util.List;

/**
 * Hello Generator Factory!
 *
 */
public class App {

    public static void main(String[] args) {

        IPrimeFactory<TestCaseClass> factory = new GenPrimeFactory<>(TestCaseClass.class);
        IExporter<TestCaseClass> jsonExporter = new JsonExporter<>(TestCaseClass.class);
        IExporter<TestCaseClass> csvExporter = new CsvExporter<>(TestCaseClass.class);
        IExporter<TestCaseClass> xmlExporter = new XmlExporter<>(TestCaseClass.class);
        IExporter<TestCaseClass> sqlExporter = new SqlExporter<>(TestCaseClass.class);

        TestCaseClass t = factory.produce();
        List<TestCaseClass> tList = factory.produce(10);

        jsonExporter.export(tList);
        csvExporter.export(tList);
        xmlExporter.export(tList);
        sqlExporter.export(t);

        System.out.println( "Hello World!" );
    }
}
