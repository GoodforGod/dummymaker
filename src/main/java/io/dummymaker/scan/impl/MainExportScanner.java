package io.dummymaker.scan.impl;

import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.export.Exporter;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.model.export.FieldContainerFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Scanner for special export annotations and produces export containers Which are used in exporters
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @see GenExportIgnore
 * @see GenExportForce
 * @see GenExportName
 * @since 03.06.2017
 */
public class MainExportScanner extends AbstractScanner implements io.dummymaker.scan.ExportScanner {

    private final FieldContainerFactory factory = new FieldContainerFactory();

    @Override
    public @NotNull Collection<FieldContainer> scan(Class<?> target) {
        return getValidFields(target).stream()
                .filter(f -> Arrays.stream(f.getDeclaredAnnotations())
                        .noneMatch(a -> GenExportIgnore.class.equals(a.annotationType())))
                .map(factory::build)
                .collect(Collectors.toList());
    }
}
