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
public class JsonExporter extends BaseExporter {

    @Override
    @NotNull String getExtension() {
        return "json";
    }

    @Override
    String convertString(String s) {
        return "\"" + s + "\"";
    }

    @Override
    @NotNull <T> String prefix(T t, Collection<FieldContainer> containers) {
        return "{";
    }

    @Override
    @NotNull <T> String suffix(T t, Collection<FieldContainer> containers) {
        return "}";
    }

    @Override
    <T> @NotNull String map(T t, Collection<FieldContainer> containers) {
        return containers.stream()
                .map(c -> getValue(t, c))
                .collect(Collectors.joining(","));
    }

    @Override
    public <T> boolean export(Collection<T> collection) {
        if(CollectionUtils.isEmpty(collection))
            return false;

        final T t = collection.iterator().next();
        final IWriter writer = getWriter(t.getClass().getSimpleName());

        return writer.write("[")
                && super.export(collection)
                && writer.write("]") ;
    }

    @Override
    public @NotNull <T> String convert(@NotNull Collection<T> collection) {
        final String convert = super.convert(collection);
        return StringUtils.isEmpty(convert) ? convert : "[" + convert + "]";
    }
}
