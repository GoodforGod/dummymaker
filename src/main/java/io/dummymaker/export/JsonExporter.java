package io.dummymaker.export;

import io.dummymaker.cases.NamingCase;
import io.dummymaker.cases.NamingCases;
import io.dummymaker.util.StringUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.7.2020
 */
public final class JsonExporter extends AbstractExporter {

    private JsonExporter(Set<String> fieldsInclude,
                         Set<String> fieldsExclude,
                         NamingCase fieldNamingCase,
                         Function<String, Writer> writerFunction) {
        super(fieldsInclude, fieldsExclude, fieldNamingCase, writerFunction);
    }

    public static final class Builder {

        private final Set<String> fieldsInclude = new HashSet<>();
        private final Set<String> fieldsExclude = new HashSet<>();
        private NamingCase fieldNamingCase = NamingCases.DEFAULT;
        private Function<String, Writer> writerFunction = fileName -> new SimpleFileWriter(false, fileName);

        private Builder() {}

        /**
         * @param fieldNamingCase apply to JSON field name
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
        public Builder includeFields(@NotNull String... fields) {
            return includeFields(Arrays.asList(fields));
        }

        @NotNull
        public Builder includeFields(@NotNull Collection<String> fields) {
            if (!fieldsExclude.isEmpty()) {
                throw new IllegalStateException("Can't Include Fields when Exclude Fields is present!");
            }

            this.fieldsInclude.addAll(fields);
            return this;
        }

        @NotNull
        public Builder excludeFields(@NotNull String... fields) {
            return excludeFields(Arrays.asList(fields));
        }

        @NotNull
        public Builder excludeFields(@NotNull Collection<String> fields) {
            if (!fieldsInclude.isEmpty()) {
                throw new IllegalStateException("Can't Exclude Fields when Include Fields is present!");
            }

            this.fieldsExclude.addAll(fields);
            return this;
        }

        @NotNull
        public JsonExporter build() {
            return new JsonExporter(fieldsInclude, fieldsExclude, fieldNamingCase, writerFunction);
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
    protected String convertDate(Object date, DateExportField formatterPattern) {
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
                || c.getType() == ExportField.Type.ARRAY
                || c.getType() == ExportField.Type.ARRAY_2D
                || c.getType() == ExportField.Type.COLLECTION
                || c.getType() == ExportField.Type.MAP;
    }

    @Override
    protected @NotNull <T> String prefix(Class<T> type, Collection<ExportField> containers) {
        return "{";
    }

    @Override
    protected @NotNull <T> String suffix(Class<T> type, Collection<ExportField> containers) {
        return "}";
    }

    @Override
    protected @NotNull <T> String separator(Class<T> type, Collection<ExportField> containers) {
        return ",\n";
    }

    @Override
    protected <T> @NotNull String map(T t, Collection<ExportField> containers) {
        return containers.stream()
                .map(c -> {
                    final String value = getValue(t, c);
                    return StringUtils.isEmpty(value)
                            ? ""
                            : wrap(c.getName()) + ":" + value;
                })
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(","));
    }

    @Override
    protected @NotNull <T> String head(Class<T> type, Collection<ExportField> containers, boolean isCollection) {
        return isCollection
                ? "["
                : "";
    }

    @Override
    protected @NotNull <T> String tail(Class<T> type, Collection<ExportField> containers, boolean isCollection) {
        return isCollection
                ? "]"
                : "";
    }
}
