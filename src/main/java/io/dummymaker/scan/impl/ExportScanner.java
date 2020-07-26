package io.dummymaker.scan.impl;

import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.model.export.FieldContainerFactory;
import io.dummymaker.scan.IExportScanner;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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

    private final FieldContainerFactory factory = new FieldContainerFactory();

    @Override
    public @NotNull Collection<FieldContainer> scan(Class<?> target) {
        final List<FieldContainer> collect = getValidFields(target).stream()
                .filter(f -> Arrays.stream(f.getDeclaredAnnotations())
                        .noneMatch(a -> GenExportIgnore.class.equals(a.annotationType())))
                .map(factory::build)
                .collect(Collectors.toList());

        return collect;
    }
}
