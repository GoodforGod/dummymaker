package io.dummymaker;

import io.dummymaker.export.CsvExporter;
import io.dummymaker.export.JsonExporter;
import io.dummymaker.export.SqlExporter;
import io.dummymaker.export.XmlExporter;
import io.dummymaker.factory.GenProduceFactory;

import java.util.List;

/**
 * Hello Dummy Maker!
 */
class App {
    public static void main(String[] args) {
        List<User> users = new GenProduceFactory<>(User.class).produce(2);

        new JsonExporter<>(User.class).export(users);
        new SqlExporter<>(User.class).export(users);
        new CsvExporter<>(User.class, false, true).export(users);
        new XmlExporter<>(User.class).export(users);
    }
}
