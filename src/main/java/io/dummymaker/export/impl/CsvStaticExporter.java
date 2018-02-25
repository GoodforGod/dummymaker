package io.dummymaker.export.impl;

import io.dummymaker.export.Format;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.impl.DefaultStrategy;
import io.dummymaker.writer.IWriter;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class CsvStaticExporter extends BasicStaticExporter {

    private final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator
     */
    private char separator = DEFAULT_SEPARATOR;

    /**
     * Flag to indicate wrap text (String) fields with quotes
     */
    private boolean wrapTextValues = false;

    /**
     * Generate header for CSV file
     */
    private boolean generateHeader = false;

    public CsvStaticExporter() {
        super(null, Format.CSV, new DefaultStrategy());
    }

    public CsvStaticExporter withPath(String path) {
        setPath(path);
        return this;
    }

    public CsvStaticExporter withStrategy(IStrategy strategy) {
        setStrategy(strategy);
        return this;
    }

    public CsvStaticExporter withTextWrap() {
        this.wrapTextValues = true;
        return this;
    }

    public CsvStaticExporter withHeader() {
        this.generateHeader = true;
        return this;
    }

    public CsvStaticExporter withSeparator(char separator) {
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

    @Override
    <T> String format(T t, IClassContainer container) {
        final StringBuilder builder = new StringBuilder("");
        final Iterator<ExportContainer> iterator = extractExportContainers(t, container).iterator();

        while (iterator.hasNext()) {
            final ExportContainer exportContainer = iterator.next();

            if (wrapTextValues && container.getField(exportContainer.getExportName()).getType().equals(String.class))
                builder.append(wrapWithQuotes(exportContainer.getExportValue()));
            else
                builder.append(exportContainer.getExportValue());

            if (iterator.hasNext())
                builder.append(separator);
        }

        return builder.toString();
    }

    /**
     * Generates header for CSV file
     *
     * @return csv header
     */
    private String generateCsvHeader(IClassContainer container) {
        return container.getFieldContainers().entrySet().stream()
                .map(e -> e.getValue().getExportName())
                .collect(Collectors.joining(String.valueOf(separator)));
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

        if (generateHeader) {
            if (!writer.write(generateCsvHeader(container)))
                return false;
        }

        return writer.write(format(t, container))
                && writer.flush();
    }

    @Override
    public <T> boolean export(final List<T> list) {
        if (isExportEntityInvalid(list))
            return false;

        final IClassContainer container = buildClassContainer(list.get(0));
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        if (generateHeader) {
            if (!writer.write(generateCsvHeader(container)))
                return false;
        }

        final boolean result = list.stream()
                .map(t -> writer.write(format(t, container)))
                .anyMatch((r) -> !r);

        return result && writer.flush();
    }

    @Override
    public <T> String exportAsString(final T t) {
        if (!isExportEntityInvalid(t))
            return "";

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        final StringBuilder builder = new StringBuilder("");
        if (generateHeader) {
            builder.append(generateCsvHeader(container)).append("\n");
        }

        return builder.append(format(t, container)).toString();
    }

    @Override
    public <T> String exportAsString(final List<T> list) {
        if (!isExportEntityInvalid(list))
            return "";

        final IClassContainer container = buildClassContainer(list.get(0));
        if (!container.isExportable())
            return "";

        final StringBuilder builder = new StringBuilder("");
        if (generateHeader) {
            builder.append(generateCsvHeader(container)).append("\n");
        }

        final String result = list.stream()
                .map(t -> format(t, container))
                .collect(Collectors.joining("\n"));

        return builder.append(result).toString();
    }
}
