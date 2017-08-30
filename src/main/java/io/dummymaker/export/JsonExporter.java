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
public class JsonExporter<T> extends BaseExporter<T> {

    private enum Mode {
        SINGLE,
        LIST
    }

    public JsonExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public JsonExporter(final Class<T> primeClass, final String path) {
        super(primeClass, path, ExportFormat.JSON);
    }

    private String wrapWithQuotes(final String value) {
        return "\"" + value + "\"";
    }

    /**
     * Translate object to Json String
     *
     * @param t object to get values from
     * @param mode represent Single JSON object or List of objects
     * @return StringBuilder of Object as JSON String
     */
    private String objectToJson(final T t, final Mode mode) {
        final Map<String, String> values = extractExportValues(t);

        final StringBuilder builder = new StringBuilder("");

        final String fieldTabs = (mode == Mode.SINGLE)
                ? "\t"
                : "\t\t\t";

        final String bracketTabs = (mode == Mode.SINGLE)
                ? ""
                : "\t\t";

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

            return builder.toString();
        }

        return builder.toString();
    }

    private String openJsonList() {
        return "{\n" + "\t\"" + classContainer.finalClassName() + "\"" + ": " + "[";
    }

    private String closeJsonList() {
        return "\t]\n}";
    }

    @Override
    public boolean export(final T t) {
        init();
        return (isExportStateValid(t)) && (writeLine(objectToJson(t, Mode.SINGLE)) && flush());
    }

    @Override
    public boolean export(final List<T> list) {
        if(!isExportStateValid(list))
            return false;

        init();

        // Open JSON Object List
        writeLine(openJsonList());

        final Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            final T t = iterator.next();
            final String write = objectToJson(t, Mode.LIST);

            // Write , symbol to the end of the object
            writeLine((iterator.hasNext()) ? write + "," : write);
        }

        // Close JSON Object List
        return writeLine(closeJsonList()) && flush();
    }

    @Override
    public String exportAsString(final T t) {
        return (!isExportStateValid(t))
                ? ""
                : objectToJson(t, Mode.SINGLE);
    }

    @Override
    public String exportAsString(final List<T> list) {
        if(!isExportStateValid(list))
            return "";

        final StringBuilder result = new StringBuilder();
        // Open JSON Object List
        result.append(openJsonList());

        final Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            final T t = iterator.next();
            final String write = objectToJson(t, Mode.LIST);

            // Write , symbol to the end of the object
            result.append("\n").append((iterator.hasNext()) ? write + "," : write);
        }

        // Close JSON Object List
        return result.append("\n").append(closeJsonList()).toString();
    }
}
