package io.dummymaker.export.impl;

import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.model.export.FieldContainer.Type;
import io.dummymaker.writer.Writer;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public class CsvExporter extends AbstractExporter {

    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator for values: value1,value2,value3 ...
     */
    private String separator = String.valueOf(DEFAULT_SEPARATOR);

    /**
     * Generate header for CSV file
     */
    private boolean hasHeader = false;

    public CsvExporter() {
        super();
    }

    public CsvExporter(@NotNull Function<String, Writer> writerFunction) {
        super(writerFunction);
    }

    /**
     * @return exporter
     */
    public CsvExporter withHeader() {
        this.hasHeader = true;
        return this;
    }

    /**
     * @param separator char separator for CSV values
     * @return exporter
     */
    public CsvExporter withSeparator(char separator) {
        this.separator = String.valueOf(separator);
        return this;
    }

    @Override
    protected @NotNull String getExtension() {
        return "csv";
    }

    @Override
    protected Predicate<FieldContainer> filter() {
        return c -> c.getType() == Type.STRING
                || c.getType() == Type.BOOLEAN
                || c.getType() == Type.NUMBER
                || c.getType() == Type.DATE
                || c.getType() == Type.SEQUENTIAL;
    }

    @Override
    protected <T> @NotNull String head(T t, Collection<FieldContainer> containers, boolean isCollection) {
        if (!hasHeader)
            return "";

        return containers.stream()
                .map(c -> c.getExportName(naming))
                .collect(Collectors.joining(separator, "", "\n"));
    }

    @Override
    protected <T> @NotNull String map(T t, Collection<FieldContainer> containers) {
        return containers.stream()
                .map(c -> getValue(t, c))
                .collect(Collectors.joining(separator));
    }

    @Override
    protected @NotNull <T> String separator(T t, Collection<FieldContainer> containers) {
        return "\n";
    }
}
