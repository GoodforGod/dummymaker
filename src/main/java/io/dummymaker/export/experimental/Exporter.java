package io.dummymaker.export.experimental;

import io.dummymaker.export.IExporter;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.FileWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public class Exporter implements IExporter {

    private final IConverter converter;
    private final IWriter writer;

    public Exporter(IConverter converter) {
        this(converter, new FileWriter());
    }

    public Exporter(IConverter converter, IWriter writer) {
        this.converter = converter;
        this.writer = writer;
    }

    @Override
    public <T> boolean export(final T t) {
        return false;
    }

    @Override
    public <T> boolean export(final Collection<T> collection) {
        return false;
    }

    @Override
    public <T> @NotNull String convert(final T t) {
        return "";
    }

    @Override
    public <T> @NotNull String convert(final @NotNull Collection<T> collection) {
        return "";
    }
}
