package io.dummymaker.export.experimental;

import io.dummymaker.export.Format;
import io.dummymaker.model.export.FieldContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public class CsvExporter extends BaseExporter {

    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator for values: value1,value2,value3 ...
     */
    private String separator = String.valueOf(DEFAULT_SEPARATOR);

    /**
     * Generate header for CSV file
     */
    private boolean hasHeader = false;

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
    @NotNull String getExtension() {
        return "csv";
    }

    @Override
    <T> @NotNull String head(T t, Collection<FieldContainer> containers) {
        if(!hasHeader)
            return "";

        return scanner.scan(t.getClass()).stream()
                .filter(c -> Format.CSV.isTypeSupported(c.getType()))
                .map(FieldContainer::getExportName)
                .collect(Collectors.joining(separator));
    }

    @Override
    <T> @NotNull String map(T t, Collection<FieldContainer> containers) {
        return containers.stream()
                .map(c -> getValue(t, c))
                .collect(Collectors.joining(separator));
    }
}
