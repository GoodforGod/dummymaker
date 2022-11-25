package io.dummymaker.export.impl;

import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.util.StringUtils;
import io.dummymaker.writer.Writer;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.7.2020
 */
public class XmlExporter extends AbstractExporter {

    /**
     * Is used with className for XML list tag
     */
    private static final String TAG_ENDING = "List";

    public XmlExporter() {
        super();
    }

    public XmlExporter(@NotNull Function<String, Writer> writerFunction) {
        super(writerFunction);
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
    protected Predicate<FieldContainer> filter() {
        return c -> c.getType() == FieldContainer.Type.STRING
                || c.getType() == FieldContainer.Type.BOOLEAN
                || c.getType() == FieldContainer.Type.NUMBER
                || c.getType() == FieldContainer.Type.DATE
                || c.getType() == FieldContainer.Type.SEQUENTIAL;
    }

    @Override
    protected @NotNull <T> String prefix(T t, Collection<FieldContainer> containers) {
        return openXmlTag(naming.apply(t.getClass().getSimpleName())) + "\n";
    }

    @Override
    protected @NotNull <T> String suffix(T t, Collection<FieldContainer> containers) {
        return "\n" + closeXmlTag(naming.apply(t.getClass().getSimpleName()));
    }

    @Override
    protected @NotNull <T> String separator(T t, Collection<FieldContainer> containers) {
        return "\n";
    }

    @Override
    protected @NotNull <T> String map(T t, Collection<FieldContainer> containers) {
        return containers.stream()
                .map(c -> {
                    final String value = getValue(t, c);
                    final String tag = c.getExportName(naming);
                    return StringUtils.isEmpty(value)
                            ? ""
                            : "\t" + openXmlTag(tag) + value + closeXmlTag(tag);
                })
                .collect(Collectors.joining("\n"));
    }

    @Override
    protected @NotNull <T> String head(T t, Collection<FieldContainer> containers, boolean isCollection) {
        final String type = t.getClass().getSimpleName();
        return isCollection
                ? openXmlTag(naming.apply(type + TAG_ENDING)) + "\n"
                : "";
    }

    @Override
    protected @NotNull <T> String tail(T t, Collection<FieldContainer> containers, boolean isCollection) {
        final String type = t.getClass().getSimpleName();
        return isCollection
                ? "\n" + closeXmlTag(naming.apply(type + TAG_ENDING))
                : "";
    }
}
