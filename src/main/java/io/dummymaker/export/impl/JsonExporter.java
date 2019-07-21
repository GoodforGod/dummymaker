package io.dummymaker.export.impl;

import io.dummymaker.container.IClassContainer;
import io.dummymaker.container.impl.ExportContainer;
import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.export.Format;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.writer.IWriter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Export objects in JSON format
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class JsonExporter extends BasicExporter {

    /**
     * Should export in json pretty mode or raw
     */
    private boolean isPretty;

    public JsonExporter() {
        super(null, Format.JSON, Cases.DEFAULT.value());
        this.isPretty = false;
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     * @return exporter
     */
    public JsonExporter withPath(final String path) {
        setPath(path);
        return this;
    }

    /**
     * Build exporter with naming strategy
     *
     * @param nameCase naming strategy for exporter
     * @return exporter
     * @see ICase
     * @see Cases
     */
    public JsonExporter withCase(final ICase nameCase) {
        setCase(nameCase);
        return this;
    }

    /**
     * @return exporter
     * @see #isPretty
     */
    public JsonExporter withPretty() {
        this.isPretty = true;
        return this;
    }

    private String wrapWithQuotes(final String value) {
        return "\"" + value + "\"";
    }

    private String wrapWithQuotes(final ExportContainer container,
                                  final IClassContainer classContainer) {
        Class<?> aClass = extractType(container.getType(), classContainer.getField(container.getExportName()));
        boolean quotes = isTypeNeedQuotes(aClass);
        if (!quotes)
            return container.getExportValue();

        if (container.getType().equals(FieldContainer.Type.ARRAY)) {
            return container.getExportValue().replace("[", "[\"").replace("]", "\"]")
                    .replace("], ", "],").replace(", ", "\",\"").replace(" ", "");
        } else if (container.getType().equals(FieldContainer.Type.ARRAY_2D)) {
            return container.getExportValue().replace("[[", "[[\"").replace("]]", "\"]]")
                    .replace("], ", "],").replace(", ", "\",\"").replace("],[", "\"],[\"").replace(" ", "");
        } else if (container.getType().equals(FieldContainer.Type.COLLECTION)
                || container.getType().equals(FieldContainer.Type.MAP)) {
            return container.getExportValue();
        }

        return "\"" + container.getExportValue() + "\"";
    }

    private boolean isTypeNeedQuotes(Class<?> classType) {
        return !classType.equals(int.class)
                && !classType.equals(long.class)
                && !classType.equals(short.class)
                && !classType.equals(byte.class)
                && !classType.equals(double.class)
                && !classType.equals(float.class)
                && !classType.equals(Integer.class)
                && !classType.equals(Byte.class)
                && !classType.equals(Long.class)
                && !classType.equals(Short.class)
                && !classType.equals(Double.class)
                && !classType.equals(Float.class);
    }

    private Class<?> extractType(FieldContainer.Type type,
                                 Field field) {
        switch (type) {
            case ARRAY:
                return field.getType().getComponentType();
            case ARRAY_2D:
                return field.getType().getComponentType().getComponentType();
            case COLLECTION:
                return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            default:
                return field.getType();
        }
    }

    /**
     * Tabs between newline and JSON value fields
     */
    private String buildFieldTab(final Mode mode) {
        if (isPretty) {
            return (mode == Mode.SINGLE)
                    ? "\t"
                    : "\t\t\t";
        }

        return "";
    }

    /**
     * Build JSON open entity tag
     */
    private String buildOpenTag(final Mode mode) {
        if (isPretty) {
            return (mode == Mode.SINGLE)
                    ? "{\n"
                    : "\t\t{\n";
        }

        return "{";
    }

    /**
     * Build JSON close entity tag
     */
    private String buildCloseTag(final Mode mode) {
        if (isPretty) {
            return (mode == Mode.SINGLE)
                    ? "\n}"
                    : "\n\t\t}";
        }

        return "}";
    }

    /**
     * Format single T value to JSON format
     *
     * @param mode represent Single JSON object or List of objects
     */
    private <T> String format(final T t,
                              final IClassContainer container,
                              final Mode mode) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);

        final String fieldTabs = buildFieldTab(mode);

        final String openValueTag = buildOpenTag(mode);
        final String closeValueTag = buildCloseTag(mode);
        final String valueDelimiter = (isPretty) ? ",\n" : ",";

        final StringBuilder builder = new StringBuilder(openValueTag);

        final String valueResult = exportContainers.stream()
                .map(c -> fieldTabs + wrapWithQuotes(c.getExportName()) + ":" + wrapWithQuotes(c, container))
                .collect(Collectors.joining(valueDelimiter));

        builder.append(valueResult)
                .append(closeValueTag);

        return builder.toString();
    }

    private String openJsonListTag(final String exportClassName) {
        return (isPretty)
                ? "{\n\t\"" + exportClassName + "\": ["
                : "{\"" + exportClassName + "\": [";
    }

    private String closeJsonListTag() {
        return (isPretty)
                ? "\n\t]\n}"
                : "]}";
    }

    @Override
    public <T> boolean export(final T t) {
        if (isExportEntityInvalid(t))
            return false;

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        return writer != null
                && writer.write(format(t, container, Mode.SINGLE))
                && writer.flush();
    }

    @Override
    public <T> boolean export(final List<T> list) {
        if (isExportEntityInvalid(list))
            return false;

        if (isExportEntitySingleList(list))
            return export(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        // Open JSON Object List
        writer.write(openJsonListTag(container.getExportClassName()) + "\n");

        final String result = list.stream()
                .map(t -> format(t, container, Mode.LIST))
                .collect(Collectors.joining(",\n"));

        return writer.write(result)
                && writer.write(closeJsonListTag())
                && writer.flush();
    }

    @Override
    public <T> String exportAsString(final T t) {
        if (isExportEntityInvalid(t))
            return "";

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        return format(t, container, Mode.SINGLE);
    }

    @Override
    public <T> String exportAsString(final List<T> list) {
        if (isExportEntityInvalid(list))
            return "";

        if (isExportEntitySingleList(list))
            return exportAsString(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return "";

        final StringBuilder builder = new StringBuilder(openJsonListTag(container.getExportClassName()))
                .append("\n");

        final String result = list.stream()
                .map(t -> format(t, container, Mode.LIST))
                .collect(Collectors.joining(",\n"));

        return builder.append(result)
                .append(closeJsonListTag())
                .toString();
    }

    /**
     * Single mode for single T value export
     * List for multiple T values
     */
    private enum Mode {
        SINGLE,
        LIST
    }
}
