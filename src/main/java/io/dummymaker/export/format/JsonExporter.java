package io.dummymaker.export.format;

import io.dummymaker.export.IAbstractExporter;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class JsonExporter<T> extends IAbstractExporter<T> {

    public JsonExporter(Class<T> primeClass) {
        super(primeClass);
    }

    @Override
    public String export(T t) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(t);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String export(List<T> t) {
        return null;
    }
}
