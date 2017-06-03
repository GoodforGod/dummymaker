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

    private String wrapWithComma(String value) {
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
                        .append(wrapWithComma(field.getKey()))
                        .append(": ")
                        .append(wrapWithComma(field.getValue()));

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
        try {
            writeLine(objectToJson(t, Mode.SINGLE).toString());
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        finally {
            try {
                flush();
            } catch (IOException e) {
                logger.warning(e.getMessage() + " | CAN NOT FLUSH FILE WRITER");
            }
        }
    }

    @Override
    public void export(List<T> objects) {
        try {
            // Open JSON Object List
            String jsonListOpen = "{\n" + "\t\"" + primeClass.getSimpleName() + "\"" + ": " + "[";
            writeLine(jsonListOpen);

            Iterator<T> iterator = objects.iterator();
            while(iterator.hasNext()) {
                T t = iterator.next();
                StringBuilder write = objectToJson(t, Mode.LIST);

                // Write , to the end of the object
                if(iterator.hasNext())
                    write.append(",");

                writeLine(write.toString());
            }

            // Close JSON Object List
            String jsonListClose = "\t]\n}";
            writeLine(jsonListClose);
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
        finally {
            try {
                flush();
            } catch (IOException e) {
                logger.warning(e.getMessage() + " | CAN NOT FLUSH FILE WRITER");
            }
        }
    }

}
