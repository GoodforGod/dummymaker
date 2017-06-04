package io.dummymaker;

import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.CsvExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.impl.SqlExporter;
import io.dummymaker.export.impl.XmlExporter;

import java.util.List;

/**
 * Hello Generator Factory!
 *
 */
public class App {

    public static void main(String[] args) {

    }























    private void init() {
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
        sqlExporter.export(tList);
    }
}
