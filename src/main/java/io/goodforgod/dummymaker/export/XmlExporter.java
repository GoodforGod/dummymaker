package io.goodforgod.dummymaker.export;

import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.util.StringUtils;
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
public final class XmlExporter extends AbstractExporter {

    /**
     * Is used with className for XML list tag
     */
    private static final String DEFAULT_TAG_LIST_SUFFIX = "List";

    private final Function<String, String> listTagSuffix;

    private XmlExporter(Set<String> fieldsInclude,
                        Set<String> fieldsExclude,
                        NamingCase fieldNamingCase,
                        Function<String, Writer> writerFunction,
                        Function<String, String> listTagSuffix) {
        super(fieldsInclude, fieldsExclude, fieldNamingCase, writerFunction);
        this.listTagSuffix = listTagSuffix;
    }

    public static final class Builder {

        private final Set<String> fieldsInclude = new HashSet<>();
        private final Set<String> fieldsExclude = new HashSet<>();
        private NamingCase fieldNamingCase = NamingCases.DEFAULT;
        private Function<String, Writer> writerFunction = fileName -> new SimpleFileWriter(false, fileName);
        private Function<String, String> listTagSuffix = name -> name + DEFAULT_TAG_LIST_SUFFIX;

        private Builder() {}

        /**
         * @param fieldNamingCase apply to XML tag name
         * @return self
         */
        @NotNull
        public Builder withCase(@NotNull NamingCase fieldNamingCase) {
            this.fieldNamingCase = fieldNamingCase;
            return this;
        }

        /**
         * @param writerFunction that receive File Name and return {@link Writer}
         * @return self
         */
        @NotNull
        public Builder withWriter(@NotNull Function<String, Writer> writerFunction) {
            this.writerFunction = writerFunction;
            return this;
        }

        /**
         * @param listTagSuffix that receive Type Name and return its associated List Tag (users ->
         *                      usersList)
         * @return self
         */
        @NotNull
        public Builder withListSuffix(@NotNull Function<String, String> listTagSuffix) {
            this.listTagSuffix = listTagSuffix;
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
        public XmlExporter build() {
            return new XmlExporter(fieldsInclude, fieldsExclude, fieldNamingCase, writerFunction, listTagSuffix);
        }
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull
    public static XmlExporter build() {
        return new Builder().build();
    }

    @Override
    protected @NotNull String getExtension() {
        return "xml";
    }

    private String openXmlTag(String value) {
        return "<" + value + ">";
    }

    private String closeXmlTag(String value) {
        return "</" + value + ">";
    }

    @Override
    protected Predicate<ExportField> filter() {
        return c -> c.getType() == ExportField.Type.STRING
                || c.getType() == ExportField.Type.BOOLEAN
                || c.getType() == ExportField.Type.NUMBER
                || c.getType() == ExportField.Type.DATE;
    }

    @Override
    protected @NotNull <T> String prefix(Class<T> type, Collection<ExportField> containers) {
        return openXmlTag(fieldNamingCase.apply(type.getSimpleName()).toString()) + "\n";
    }

    @Override
    protected @NotNull <T> String suffix(Class<T> type, Collection<ExportField> containers) {
        return "\n" + closeXmlTag(fieldNamingCase.apply(type.getSimpleName()).toString());
    }

    @Override
    protected @NotNull <T> String separator(Class<T> type, Collection<ExportField> containers) {
        return "\n";
    }

    @Override
    protected @NotNull <T> String map(T t, Collection<ExportField> containers) {
        return containers.stream()
                .map(c -> {
                    final String value = getValue(t, c);
                    if (StringUtils.isEmpty(value)) {
                        return "";
                    }

                    final String tag = c.getName();
                    return "\t" + openXmlTag(tag) + value + closeXmlTag(tag);
                })
                .collect(Collectors.joining("\n"));
    }

    @Override
    protected @NotNull <T> String head(Class<T> type, Collection<ExportField> containers, boolean isCollection) {
        final String typeAsString = type.getSimpleName();
        return isCollection
                ? openXmlTag(fieldNamingCase.apply(listTagSuffix.apply(typeAsString)).toString()) + "\n"
                : "";
    }

    @Override
    protected @NotNull <T> String tail(Class<T> type, Collection<ExportField> containers, boolean isCollection) {
        final String typeAsString = type.getSimpleName();
        return isCollection
                ? "\n" + closeXmlTag(fieldNamingCase.apply(listTagSuffix.apply(typeAsString)).toString())
                : "";
    }
}
