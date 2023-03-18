package io.dummymaker.export;

import io.dummymaker.GenFieldScanner;
import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import java.util.Arrays;
import java.util.List;
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
final class ExportScanner {

    private static final ExportFieldFactory FACTORY = new ExportFieldFactory();

    @NotNull
    public List<ExportField> scan(Class<?> target) {
        return GenFieldScanner.scan(target).stream()
                .filter(genField -> Arrays.stream(genField.field().getDeclaredAnnotations())
                        .noneMatch(a -> GenExportIgnore.class.equals(a.annotationType())))
                .map(genField -> FACTORY.build(genField.field(), genField.type()))
                .collect(Collectors.toList());
    }
}
