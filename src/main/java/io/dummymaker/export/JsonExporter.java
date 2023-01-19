package io.dummymaker.export;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.util.StringUtils;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.7.2020
 */
public final class JsonExporter extends AbstractExporter {

    private JsonExporter(boolean appendFile, Case fieldCase, @NotNull Function<String, Writer> writerFunction) {
        super(appendFile, fieldCase, writerFunction);
    }

    public static final class Builder {

        private boolean appendFile = false;
        private Case fieldCase = Cases.DEFAULT.value();
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
        public JsonExporter build() {
            final Function<String, Writer> writer = (writerFunction == null)
                    ? fileName -> new DefaultFileWriter(fileName, true)
                    : writerFunction;

            return new JsonExporter(appendFile, fieldCase, writer);
        }
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull
    public static JsonExporter build() {
        return new Builder().build();
    }

    @Override
    protected @NotNull String getExtension() {
        return "json";
    }

    private String wrap(String s) {
        return StringUtils.isEmpty(s)
                ? ""
                : "\"" + s + "\"";
    }

    @Override
    protected String convertString(String s) {
        return wrap(s);
    }

    @Override
    protected String convertDate(Object date, String formatterPattern) {
        return wrap(super.convertDate(date, formatterPattern));
    }

    @Override
    protected String convertNull() {
        return "null";
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected Predicate<ExportField> filter() {
        return c -> c.getType() == ExportField.Type.STRING
                || c.getType() == ExportField.Type.BOOLEAN
                || c.getType() == ExportField.Type.NUMBER
                || c.getType() == ExportField.Type.DATE
                || c.getType() == ExportField.Type.SEQUENTIAL
                || c.getType() == ExportField.Type.ARRAY
                || c.getType() == ExportField.Type.ARRAY_2D
                || c.getType() == ExportField.Type.COLLECTION
                || c.getType() == ExportField.Type.MAP;
    }

    @Override
    protected @NotNull <T> String prefix(T t, Collection<ExportField> containers) {
        return "{";
    }

    @Override
    protected @NotNull <T> String suffix(T t, Collection<ExportField> containers) {
        return "}";
    }

    @Override
    protected @NotNull <T> String separator(T t, Collection<ExportField> containers) {
        return ",\n";
    }

    @Override
    protected <T> @NotNull String map(T t, Collection<ExportField> containers) {
        return containers.stream()
                .map(c -> {
                    final String value = getValue(t, c);
                    return StringUtils.isEmpty(value)
                            ? ""
                            : wrap(c.getName(fieldCase)) + ":" + value;
                })
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(","));
    }

    @Override
    protected @NotNull <T> String head(T t, Collection<ExportField> containers, boolean isCollection) {
        return isCollection
                ? "["
                : "";
    }

    @Override
    protected @NotNull <T> String tail(T t, Collection<ExportField> containers, boolean isCollection) {
        return isCollection
                ? "]"
                : "";
    }
}
