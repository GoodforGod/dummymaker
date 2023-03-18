package io.dummymaker.export;

import io.dummymaker.cases.NamingCase;
import io.dummymaker.cases.NamingCases;
import io.dummymaker.export.ExportField.Type;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public final class CsvExporter extends AbstractExporter {

    private static final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator for values: value1,value2,value3 ...
     */
    private final String separator;

    /**
     * Generate header for CSV file
     */
    private final boolean hasHeader;

    private CsvExporter(NamingCase fieldNamingCase,
                        Function<String, Writer> writerFunction,
                        boolean hasHeader,
                        String separator) {
        super(fieldNamingCase, writerFunction);
        this.hasHeader = hasHeader;
        this.separator = separator;
    }

    public static final class Builder {

        private NamingCase fieldNamingCase = NamingCases.DEFAULT;
        private boolean hasHeader = false;
        private String separator = String.valueOf(DEFAULT_SEPARATOR);
        private Function<String, Writer> writerFunction = fileName -> new SimpleFileWriter(false, fileName);

        private Builder() {}

        /**
         * @param fieldNamingCase apply to CSV header field name
         * @return self
         */
        @NotNull
        public Builder withCase(@NotNull NamingCase fieldNamingCase) {
            this.fieldNamingCase = fieldNamingCase;
            return this;
        }

        @NotNull
        public Builder withWriter(@NotNull Function<String, Writer> writerFunction) {
            this.writerFunction = writerFunction;
            return this;
        }

        @NotNull
        public Builder withHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        @NotNull
        public Builder withSeparator(@NotNull String separator) {
            this.separator = separator;
            return this;
        }

        @NotNull
        public Builder withSeparator(@NotNull Character separator) {
            this.separator = String.valueOf(separator);
            return this;
        }

        @NotNull
        public CsvExporter build() {
            return new CsvExporter(fieldNamingCase, writerFunction, hasHeader, separator);
        }
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull
    public static CsvExporter build() {
        return new Builder().build();
    }

    @Override
    protected @NotNull String getExtension() {
        return "csv";
    }

    @Override
    protected Predicate<ExportField> filter() {
        return c -> c.getType() == Type.STRING
                || c.getType() == Type.BOOLEAN
                || c.getType() == Type.NUMBER
                || c.getType() == Type.DATE;
    }

    @Override
    protected <T> @NotNull String head(Class<T> type, Collection<ExportField> containers, boolean isCollection) {
        if (!hasHeader)
            return "";

        return containers.stream()
                .map(c -> c.getName(fieldNamingCase))
                .collect(Collectors.joining(separator, "", "\n"));
    }

    @Override
    protected <T> @NotNull String map(T value, Collection<ExportField> containers) {
        return containers.stream()
                .map(c -> getValue(value, c))
                .collect(Collectors.joining(separator));
    }

    @Override
    protected @NotNull <T> String separator(Class<T> type, Collection<ExportField> containers) {
        return "\n";
    }
}
