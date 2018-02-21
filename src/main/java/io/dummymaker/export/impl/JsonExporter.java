package io.dummymaker.export.impl;

import io.dummymaker.export.NamingStrategy;
import io.dummymaker.export.container.ExportContainer;

import java.util.Iterator;
import java.util.List;


/**
 * Export objects in JSON format
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class JsonExporter<T> extends BasicExporter<T> {

    private enum Mode {
        SINGLE,
        LIST
    }

    public JsonExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public JsonExporter(final Class<T> primeClass,
                        final String path) {
        super(primeClass, path, ExportFormat.JSON, NamingStrategy.DEFAULT);
    }

    /**
     * @param primeClass export class
     * @param path path where to export, 'null' for project HOME path
     * @param strategy naming strategy
     */
    public JsonExporter(final Class<T> primeClass,
                        final String path,
                        final NamingStrategy strategy) {
        super(primeClass, path, ExportFormat.JSON, strategy);
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
        final Iterator<ExportContainer> iterator = extractExportValues(t).iterator();

        final StringBuilder builder = new StringBuilder("");

        final String fieldTabs = (mode == Mode.SINGLE)
                ? "\t"
                : "\t\t\t";

        final String bracketTabs = (mode == Mode.SINGLE)
                ? ""
                : "\t\t";

        if(iterator.hasNext()) {
            builder.append(bracketTabs).append("{\n");
            while (iterator.hasNext()) {
                final ExportContainer container = iterator.next();
                builder.append(fieldTabs)
                        .append(wrapWithQuotes(container.getFieldName()))
                        .append(": ")
                        .append(wrapWithQuotes(container.getFieldValue()));

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
