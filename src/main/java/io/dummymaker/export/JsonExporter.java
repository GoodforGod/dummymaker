package io.dummymaker.export;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Export objects in JSON format
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class JsonExporter<T> extends OriginExporter<T> {

    private enum Mode {
        SINGLE,
        LIST
    }

    public JsonExporter(Class<T> primeClass) {
        this(primeClass, null);
    }

    public JsonExporter(Class<T> primeClass, String path) {
        super(primeClass, path, ExportFormat.JSON);
    }

    private String wrapWithQuotes(String value) {
        return "\"" + value + "\"";
    }

    /**
     * Translate object to Json String
     *
     * @param t object to get values from
     * @param mode represent Single JSON object or List of objects
     * @return StringBuilder of Object as JSON String
     */
    private StringBuilder objectToJson(T t, Mode mode) {
        Map<String, String> values = getExportValues(t);

        String fieldTabs = (mode == Mode.SINGLE)
                ? "\t"
                : "\t\t\t";

        String bracketTabs = (mode == Mode.SINGLE)
                ? ""
                : "\t\t";

        StringBuilder builder = new StringBuilder("");

        if(!values.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = values.entrySet().iterator();

            builder.append(bracketTabs).append("{\n");
            while (iterator.hasNext()) {
                Map.Entry<String, String> field = iterator.next();
                builder.append(fieldTabs)
                        .append(wrapWithQuotes(field.getKey()))
                        .append(": ")
                        .append(wrapWithQuotes(field.getValue()));

                if (iterator.hasNext())
                    builder.append(",");

                builder.append("\n");
            }
            builder.append(bracketTabs).append("}");

            return builder;
        }

        return builder;
    }

    @Override
    public void export(T t) {
        writeLine(objectToJson(t, Mode.SINGLE).toString());
        flush();
    }

    @Override
    public void export(List<T> objects) {
        // Open JSON Object List
        String jsonListOpen = "{\n" + "\t\"" + exportClass.getSimpleName() + "\"" + ": " + "[";
        writeLine(jsonListOpen);

        Iterator<T> iterator = objects.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            StringBuilder write = objectToJson(t, Mode.LIST);

            // Write , to the end of the object
            if (iterator.hasNext())
                write.append(",");

            writeLine(write.toString());
        }

        // Close JSON Object List
        String jsonListClose = "\t]\n}";
        writeLine(jsonListClose);
        flush();
    }

}
