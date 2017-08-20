package io.dummymaker;

import io.dummymaker.export.SqlExporter;
import io.dummymaker.factory.GenProduceFactory;

/**
 * Hello Dummy Maker!
 */
class App {
    public static void main(String[] args) {
        new SqlExporter<>(Dummy.class).export(new GenProduceFactory<>(Dummy.class).produce(10));
    }
}
