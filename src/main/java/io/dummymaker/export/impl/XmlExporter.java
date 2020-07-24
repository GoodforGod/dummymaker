package io.dummymaker.export.impl;

import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.StringUtils;
import io.dummymaker.writer.IWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.7.2020
 */
public class XmlExporter extends BaseExporter {

    /**
     * Is used with className for XML list tag
     */
    private final String tagEnding = "List";

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
        return openXmlTag(naming.format(t.getClass().getSimpleName())) + "\n";
    }

    @Override
    protected @NotNull <T> String suffix(T t, Collection<FieldContainer> containers) {
        return "\n" + closeXmlTag(naming.format(t.getClass().getSimpleName()));
    }

    @Override
    protected @NotNull <T> String separator(T t, Collection<FieldContainer> containers) {
        return "\n";
    }

    @Override
    protected @NotNull <T> String map(T t, Collection<FieldContainer> containers) {
        return containers.stream()
                .map(c -> "\t" + openXmlTag(c.getExportName(naming))
                        + getValue(t, c)
                        + closeXmlTag(c.getExportName(naming)))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public <T> boolean export(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return false;

        final String type = collection.iterator().next().getClass().getSimpleName();
        final IWriter writer = getWriter(type);

        return writer.append(openXmlTag(naming.format(type + tagEnding)) + "\n")
                && super.export(collection)
                && writer.append("\n" + closeXmlTag(naming.format(type + tagEnding)));
    }

    @Override
    public @NotNull <T> String convert(@NotNull Collection<T> collection) {
        final String convert = super.convert(collection);
        if (StringUtils.isEmpty(convert))
            return convert;

        final String type = collection.iterator().next().getClass().getSimpleName();
        return openXmlTag(naming.format(type + tagEnding))
                + "\n"
                + convert
                +"\n"
                + closeXmlTag(naming.format(type + tagEnding));
    }
}
