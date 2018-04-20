package io.dummymaker.export.impl;

import io.dummymaker.container.IClassContainer;
import io.dummymaker.container.impl.ExportContainer;
import io.dummymaker.export.Format;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.writer.IWriter;

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
     * Single mode for single T value export
     * List for multiple T values
     */
    private enum Mode {
        SINGLE,
        LIST
    }

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
     * @see ICase
     * @see Cases
     *
     * @param nameCase naming strategy for exporter
     * @return exporter
     */
    public JsonExporter withCase(final ICase nameCase) {
        setCase(nameCase);
        return this;
    }

    /**
     * @see #isPretty
     * @return exporter
     */
    public JsonExporter withPretty() {
        this.isPretty = true;
        return this;
    }

    private String wrapWithQuotes(final String value) {
        return "\"" + value + "\"";
    }

    /**
     * Tabs between newline and JSON value fields
     */
    private String buildFieldTab(final Mode mode) {
        if(isPretty) {
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
        if(isPretty) {
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
        if(isPretty) {
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
                .map(c -> fieldTabs + wrapWithQuotes(c.getExportName()) + ":" + wrapWithQuotes(c.getExportValue()))
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

        if(isExportEntitySingleList(list))
            return export(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        // Open JSON Object List
        writer.write(openJsonListTag(container.exportClassName()) + "\n");

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

        if(isExportEntitySingleList(list))
            return exportAsString(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return "";

        final StringBuilder builder = new StringBuilder(openJsonListTag(container.exportClassName()))
                .append("\n");

        final String result = list.stream()
                .map(t -> format(t, container, Mode.LIST))
                .collect(Collectors.joining(",\n"));

        return builder.append(result)
                .append(closeJsonListTag())
                .toString();
    }
}
