package io.dummymaker.export.impl;

import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.StringUtils;
import io.dummymaker.writer.IWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.7.2020
 */
public class JsonExporter extends BaseExporter {

    public JsonExporter() {
        super();
    }

    public JsonExporter(@NotNull Function<String, IWriter> writerFunction) {
        super(writerFunction);
    }

    @Override
    protected @NotNull String getExtension() {
        return "json";
    }

    private String wrap(String s) {
        return StringUtils.isEmpty(s) ? "" : "\"" + s + "\"";
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
    protected Predicate<FieldContainer> filter() {
        return c -> c.getType() == FieldContainer.Type.STRING
                || c.getType() == FieldContainer.Type.BOOLEAN
                || c.getType() == FieldContainer.Type.NUMBER
                || c.getType() == FieldContainer.Type.DATE
                || c.getType() == FieldContainer.Type.SEQUENTIAL
                || c.getType() == FieldContainer.Type.ARRAY
                || c.getType() == FieldContainer.Type.ARRAY_2D
                || c.getType() == FieldContainer.Type.COLLECTION
                || c.getType() == FieldContainer.Type.MAP;
    }

    @Override
    protected @NotNull <T> String prefix(T t, Collection<FieldContainer> containers) {
        return "{";
    }

    @Override
    protected @NotNull <T> String suffix(T t, Collection<FieldContainer> containers) {
        return "}";
    }

    @Override
    protected @NotNull <T> String separator(T t, Collection<FieldContainer> containers) {
        return ",\n";
    }

    @Override
    protected <T> @NotNull String map(T t, Collection<FieldContainer> containers) {
        return containers.stream()
                .map(c -> {
                    final String value = getValue(t, c);
                    return StringUtils.isEmpty(value) ? "" : wrap(c.getExportName(naming)) + ":" + value;
                })
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(","));
    }

    @Override
    public <T> boolean export(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return false;

        final T t = collection.iterator().next();
        final IWriter writer = getWriter(t.getClass().getSimpleName());

        return writer.write("[")
                && super.export(collection)
                && writer.write("]");
    }

    @Override
    public @NotNull <T> String convert(@NotNull Collection<T> collection) {
        return "[" + super.convert(collection) + "]";
    }
}
