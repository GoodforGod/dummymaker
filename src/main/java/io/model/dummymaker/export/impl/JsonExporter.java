package io.model.dummymaker.export.impl;

import io.model.dummymaker.export.ExportType;
import io.model.dummymaker.export.OriginExporter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class JsonExporter<T> extends OriginExporter<T> {

    private enum Mode {
        SINGLE,
        LIST
    }

    public JsonExporter(Class<T> primeClass) {
        super(primeClass, ExportType.JSON);
    }

    public JsonExporter(Class<T> primeClass, String path) {
        super(primeClass, path, ExportType.JSON);
    }

    private String objectToJson(T t, Mode mode) {
        Map<String, String> values = getExportValues(t);

        String tabs = (mode == Mode.SINGLE)
                ? "\t"
                : "\t\t\t";

        if(!values.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            Iterator<Map.Entry<String, String>> iterator = values.entrySet().iterator();

            builder.append("{\n");
            while (iterator.hasNext()) {
                Map.Entry<String, String> field = iterator.next();
                builder.append(tabs).append("\"").append(field.getKey()).append("\"")
                        .append(": ")
                        .append("\"").append(field.getValue()).append("\"");

                if (iterator.hasNext())
                    builder.append(",");

                builder.append("\n");
            }
            builder.append("}");

            return builder.toString();
        }

        return "";
    }

    @Override
    public void export(T t) {
        try {
            String obj = objectToJson(t, Mode.SINGLE);
            logger.warning(obj);
            writeLine(obj);

            flush();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    @Override
    public void export(List<T> objects) {
        try {
            String jsonListOpen = "{\n" + "\t\"" + primeClass.getName() + "\"" + ": " + "[\n";
            writeLine(jsonListOpen);

            for(T t : objects)
                writeLine(objectToJson(t, Mode.LIST));

            String jsonListClose = "\n\t]\n}";
            writeLine(jsonListClose);

            flush();
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

}
