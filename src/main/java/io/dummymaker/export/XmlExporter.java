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
public final class XmlExporter extends AbstractExporter {

    /**
     * Is used with className for XML list tag
     */
    private static final String TAG_ENDING = "List";

    private XmlExporter(boolean appendFile, Case fieldCase, @NotNull Function<String, Writer> writerFunction) {
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
        public XmlExporter build() {
            final Function<String, Writer> writer = (writerFunction == null)
                    ? fileName -> new DefaultFileWriter(fileName, true)
                    : writerFunction;

            return new XmlExporter(appendFile, fieldCase, writer);
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
                || c.getType() == ExportField.Type.DATE
                || c.getType() == ExportField.Type.SEQUENTIAL;
    }

    @Override
    protected @NotNull <T> String prefix(T t, Collection<ExportField> containers) {
        return openXmlTag(fieldCase.apply(t.getClass().getSimpleName())) + "\n";
    }

    @Override
    protected @NotNull <T> String suffix(T t, Collection<ExportField> containers) {
        return "\n" + closeXmlTag(fieldCase.apply(t.getClass().getSimpleName()));
    }

    @Override
    protected @NotNull <T> String separator(T t, Collection<ExportField> containers) {
        return "\n";
    }

    @Override
    protected @NotNull <T> String map(T t, Collection<ExportField> containers) {
        return containers.stream()
                .map(c -> {
                    final String value = getValue(t, c);
                    final String tag = c.getName(fieldCase);
                    return StringUtils.isEmpty(value)
                            ? ""
                            : "\t" + openXmlTag(tag) + value + closeXmlTag(tag);
                })
                .collect(Collectors.joining("\n"));
    }

    @Override
    protected @NotNull <T> String head(T t, Collection<ExportField> containers, boolean isCollection) {
        final String type = t.getClass().getSimpleName();
        return isCollection
                ? openXmlTag(fieldCase.apply(type + TAG_ENDING)) + "\n"
                : "";
    }

    @Override
    protected @NotNull <T> String tail(T t, Collection<ExportField> containers, boolean isCollection) {
        final String type = t.getClass().getSimpleName();
        return isCollection
                ? "\n" + closeXmlTag(fieldCase.apply(type + TAG_ENDING))
                : "";
    }
}
