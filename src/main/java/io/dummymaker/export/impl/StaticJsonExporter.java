package io.dummymaker.export.impl;

import io.dummymaker.export.Format;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.writer.IWriter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Export objects in JSON format
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class StaticJsonExporter extends BasicStaticExporter {

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

    public StaticJsonExporter() {
        super(null, Format.JSON, PresetStrategies.DEFAULT.getStrategy());
        this.isPretty = false;
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     */
    public StaticJsonExporter withPath(String path) {
        setPath(path);
        return this;
    }

    /**
     * Build exporter with naming strategy
     *
     * @see IStrategy
     * @see PresetStrategies
     *
     * @param strategy naming strategy for exporter
     */
    public StaticJsonExporter withStrategy(IStrategy strategy) {
        setStrategy(strategy);
        return this;
    }

    /**
     * @see #isPretty
     */
    public StaticJsonExporter withPretty() {
        this.isPretty = true;
        return this;
    }

    private String wrapWithQuotes(final String value) {
        return "\"" + value + "\"";
    }

    /**
     * Tabs between newline and JSON value fields
     */
    private String buildFieldTab(Mode mode) {
        if(isPretty) {
            return (mode == Mode.SINGLE)
                    ? "\t"
                    : "\t\t\t";
        }

        return "";
    }

    /**
     * Tabs between newline and brackets
     */
    private String buildBracketTabs(Mode mode) {
        if(isPretty) {
            return (mode == Mode.SINGLE)
                    ? ""
                    : "\t\t";
        }

        return "";
    }

    /**
     * Format single T value to JSON format
     *
     * @param mode represent Single JSON object or List of objects
     */
    private <T> String format(T t, IClassContainer container, Mode mode) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);
        if (exportContainers.isEmpty())
            return "";

        final String fieldTabs = buildFieldTab(mode);
        final String bracketTabs = buildBracketTabs(mode);

        final String openValueBracket = (isPretty) ? "\n" + bracketTabs + "{\n" : "{";
        final String closeValueBracket = (isPretty) ? "\n" + bracketTabs + "}" : "}";
        final String valueDelimiter = (isPretty) ? ",\n" : ",";


        final StringBuilder builder = new StringBuilder(bracketTabs)
                .append(openValueBracket);

        final String valueResult = exportContainers.stream()
                .map(c -> fieldTabs + wrapWithQuotes(c.getExportName()) + ":" + wrapWithQuotes(c.getExportValue()))
                .collect(Collectors.joining(valueDelimiter));

        builder.append(valueResult)
                .append(bracketTabs)
                .append(closeValueBracket);

        return builder.toString();
    }

    private String openJsonList(String exportClassName) {
        return (isPretty)
                ? "{\n\t\"" + exportClassName + "\": ["
                : "{\"" + exportClassName + "\": [";
    }

    private String closeJsonList() {
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

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        // Open JSON Object List
        writer.write(openJsonList(container.exportClassName()));

        final String result = list.stream()
                .map(t -> format(t, container, Mode.LIST))
                .collect(Collectors.joining(","));

        return writer.write(result)
                && writer.write(closeJsonList())
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

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return "";

        final StringBuilder builder = new StringBuilder(openJsonList(container.exportClassName()));
        final String result = list.stream()
                .map(t -> format(t, container, Mode.LIST))
                .collect(Collectors.joining("\n,"));

        return builder.append(result)
                .append("\n")
                .append(closeJsonList())
                .toString();
    }
}
