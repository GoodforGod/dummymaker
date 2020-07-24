package io.dummymaker.scan.impl;

import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.model.export.FieldContainerFactory;
import io.dummymaker.scan.IExportScanner;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Scanner for special export annotations and produces export containers Which
 * are used in exporters
 *
 * @author GoodforGod
 * @see io.dummymaker.export.IExporter
 * @see GenExportIgnore
 * @see GenExportForce
 * @see GenExportName
 * @since 03.06.2017
 */
public class ExportScanner extends BasicScanner implements IExportScanner {

    private final FieldContainerFactory factory;

    public ExportScanner() {
        this.factory = new FieldContainerFactory();
    }

    @Override
    public @NotNull Collection<FieldContainer> scan(Class<?> target) {
        return scan(target, Cases.DEFAULT.value());
    }

    @Override
    public @NotNull Collection<FieldContainer> scan(Class<?> target, ICase naming) {
        return getAllFields(target).stream()
                .filter(f -> Arrays.stream(f.getDeclaredAnnotations())
                        .noneMatch(a -> GenExportIgnore.class.equals(a.annotationType())))
                .map(f -> factory.build(f, naming))
                .collect(Collectors.toList());
    }
}
