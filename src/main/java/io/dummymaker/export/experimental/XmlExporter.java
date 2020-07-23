package io.dummymaker.export.experimental;

import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.StringUtils;
import io.dummymaker.writer.IWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
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
    @NotNull String getExtension() {
        return "xml";
    }

    private String openXmlTag(String value) {
        return "<" + value + ">";
    }

    private String closeXmlTag(String value) {
        return "</" + value + ">";
    }

    @Override
    @NotNull <T> String prefix(T t, Collection<FieldContainer> containers) {
        return openXmlTag(t.getClass().getSimpleName()) + "\n";
    }

    @Override
    @NotNull <T> String suffix(T t, Collection<FieldContainer> containers) {
        return closeXmlTag(t.getClass().getSimpleName());
    }

    @Override
    @NotNull <T> String map(T t, Collection<FieldContainer> containers) {
        return containers.stream()
                .map(c -> "\t" + openXmlTag(c.getExportName()) + getValue(t, c) + closeXmlTag(c.getExportName()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public <T> boolean export(Collection<T> collection) {
        if(CollectionUtils.isEmpty(collection))
            return false;

        final String type = collection.iterator().next().getClass().getSimpleName();
        final IWriter writer = getWriter(type);

        return writer.write(openXmlTag(type + tagEnding))
                && super.export(collection)
                && writer.write(closeXmlTag(type + tagEnding));
    }

    @Override
    public @NotNull <T> String convert(@NotNull Collection<T> collection) {
        final String convert = super.convert(collection);
        if (StringUtils.isEmpty(convert))
            return convert;

        final String type = collection.iterator().next().getClass().getSimpleName();
        return openXmlTag(type + tagEnding)
                + convert
                + closeXmlTag(type + tagEnding);
    }
}
