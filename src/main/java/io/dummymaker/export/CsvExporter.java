package io.dummymaker.export;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
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

    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * CSV format separator for values: value1,value2,value3 ...
     */
    private final String separator;

    /**
     * Generate header for CSV file
     */
    private final boolean hasHeader;

    private CsvExporter(boolean appendFile,
                        Case fieldCase,
                        boolean hasHeader,
                        String separator,
                        @NotNull Function<String, Writer> writerFunction) {
        super(appendFile, fieldCase, writerFunction);
        this.hasHeader = hasHeader;
        this.separator = separator;
    }

    public static final class Builder {

        private boolean appendFile = false;
        private Case fieldCase = Cases.DEFAULT.value();
        private boolean hasHeader = false;
        private String separator = String.valueOf(DEFAULT_SEPARATOR);
        private Function<String, Writer> writerFunction;

        private Builder() {}

        @NotNull
        public Builder appendFile(boolean appendFile) {
            this.appendFile = appendFile;
            return this;
        }

        @NotNull
        public Builder withCase(@NotNull Case fieldCase) {
            this.fieldCase = fieldCase;
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
            final Function<String, Writer> writer = (writerFunction == null)
                    ? fileName -> new DefaultFileWriter(fileName, true)
                    : writerFunction;

            return new CsvExporter(appendFile, fieldCase, hasHeader, separator, writer);
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
                || c.getType() == Type.DATE
                || c.getType() == Type.SEQUENTIAL;
    }

    @Override
    protected <T> @NotNull String head(T t, Collection<ExportField> containers, boolean isCollection) {
        if (!hasHeader)
            return "";

        return containers.stream()
                .map(c -> c.getName(fieldCase))
                .collect(Collectors.joining(separator, "", "\n"));
    }

    @Override
    protected <T> @NotNull String map(T t, Collection<ExportField> containers) {
        return containers.stream()
                .map(c -> getValue(t, c))
                .collect(Collectors.joining(separator));
    }

    @Override
    protected @NotNull <T> String separator(T t, Collection<ExportField> containers) {
        return "\n";
    }
}
