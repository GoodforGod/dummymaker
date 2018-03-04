package io.dummymaker.export.impl;

import io.dummymaker.export.Format;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.Strategies;
import io.dummymaker.writer.IWriter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Export objects in CSV format
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class CsvExporter extends BasicExporter {

    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator for values: value1,value2,value3 ...
     */
    private char separator = DEFAULT_SEPARATOR;

    /**
     * Flag to indicate wrap text (String) fields with quotes
     */
    private boolean areTextValuesWrapped = false;

    /**
     * Generate header for CSV file
     */
    private boolean hasHeader = false;

    public CsvExporter() {
        super(null, Format.CSV, Strategies.DEFAULT.getStrategy());
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     */
    public CsvExporter withPath(final String path) {
        setPath(path);
        return this;
    }

    /**
     * Build exporter with naming strategy
     *
     * @see IStrategy
     *
     * @param strategy naming strategy for exporter
     */
    public CsvExporter withStrategy(final IStrategy strategy) {
        setStrategy(strategy);
        return this;
    }

    /**
     * @see #areTextValuesWrapped
     */
    public CsvExporter withTextWrap() {
        this.areTextValuesWrapped = true;
        return this;
    }

    /**
     * @see #hasHeader
     */
    public CsvExporter withHeader() {
        this.hasHeader = true;
        return this;
    }

    /**
     * @see #separator
     *
     * @param separator char separator for CSV values
     */
    public CsvExporter withSeparator(final char separator) {
        this.separator = separator;
        return this;
    }

    /**
     * Wraps text values (String) with quotes like: value - 'value'
     *
     * @param value values to wrap
     * @return wrapped values
     */
    private String wrapWithQuotes(final String value) {
        return "'" + value + "'";
    }

    private <T> String format(final T t,
                              final IClassContainer container) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);

        final String separatorAsStr = String.valueOf(separator);
        return exportContainers.stream()
                .map(c -> (areTextValuesWrapped && container.getField(c.getExportName()).getType().equals(String.class))
                        ? wrapWithQuotes(c.getExportValue())
                        : c.getExportValue())
                .collect(Collectors.joining(separatorAsStr));
    }

    /**
     * Generates header for CSV file
     *
     * @return csv header
     */
    private String generateCsvHeader(final IClassContainer container) {
        final String separatorAsStr = String.valueOf(separator);
        return container.getContainers().entrySet().stream()
                .map(e -> e.getValue().getExportName())
                .collect(Collectors.joining(separatorAsStr));
    }

    @Override
    public <T> boolean export(final T t) {
        if (isExportEntityInvalid(t))
            return false;

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        if (hasHeader) {
            if (!writer.write(generateCsvHeader(container) + "\n"))
                return false;
        }

        return writer.write(format(t, container))
                && writer.flush();
    }

    @Override
    public <T> boolean export(final List<T> list) {
        if (isExportEntityInvalid(list))
            return false;

        if(isExportEntitySingleList(list))
            return export(list.get(0));

        final IClassContainer container = buildClassContainer(list.get(0));
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        if (hasHeader) {
            if (!writer.write(generateCsvHeader(container) + "\n"))
                return false;
        }

        final boolean writerHadError = list.stream()
                .anyMatch(t -> !writer.write(format(t, container) + "\n"));

        return !writerHadError && writer.flush();
    }

    @Override
    public <T> String exportAsString(final T t) {
        if (isExportEntityInvalid(t))
            return "";

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        final StringBuilder builder = new StringBuilder("");
        if (hasHeader) {
            builder.append(generateCsvHeader(container)).append("\n");
        }

        return builder.append(format(t, container)).toString();
    }

    @Override
    public <T> String exportAsString(final List<T> list) {
        if (isExportEntityInvalid(list))
            return "";

        if(isExportEntitySingleList(list))
            return exportAsString(list.get(0));

        final IClassContainer container = buildClassContainer(list.get(0));
        if (!container.isExportable())
            return "";

        final StringBuilder builder = new StringBuilder("");
        if (hasHeader) {
            builder.append(generateCsvHeader(container)).append("\n");
        }

        final String result = list.stream()
                .map(t -> format(t, container))
                .collect(Collectors.joining("\n"));

        return builder.append(result).toString();
    }
}
