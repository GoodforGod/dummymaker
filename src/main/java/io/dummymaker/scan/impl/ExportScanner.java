package io.dummymaker.scan.impl;

import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.factory.impl.GenSupplier;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.IExportScanner;
import io.dummymaker.scan.IPopulateScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * Scanner for special export annotations
 *
 * @author GoodforGod
 * @see GenExportIgnore
 * @see GenExportForce
 * @see GenExportName
 * @since 03.06.2017
 */
public class ExportScanner extends BasicScanner implements IExportScanner {

    private final Predicate<Annotation> ignoreFilter = a -> a.annotationType().equals(GenExportIgnore.class);
    private final Predicate<Annotation> exportFilter = (a) -> a.annotationType().equals(GenExportForce.class);
    private final Predicate<Annotation> renameFilter = (a) -> a.annotationType().equals(GenExportName.class);

    private final IGenSupplier supplier = new GenSupplier();
    private final IPopulateScanner populateScanner = new PopulateScanner();
    private final IAnnotationScanner annotationScanner = new AnnotationScanner();

    @Override
    public Map<Field, FieldContainer> scan(Class target) {
        return scan(target, Cases.DEFAULT.value());
    }

    @Override
    public Map<Field, FieldContainer> scan(Class target, ICase nameCase) {
        final Map<Field, FieldContainer> resultMap = new LinkedHashMap<>();
        final Map<Field, String> renamedFields = new HashMap<>();

        final Map<Field, GenContainer> scannedContainers = populateScanner.scan(target);
        final Map<Field, List<Annotation>> scannedAnnotations = annotationScanner.scan(target);

        // Scan for renamed fields and fill renamed map
        scannedAnnotations.forEach((k, v) -> v.stream().filter(renameFilter)
                .findFirst().map(a -> ((GenExportName) a).value())
                .ifPresent(n -> renamedFields.put(k, n)));

        // Fill result map with export fields containers
        scannedAnnotations.forEach((k, v) -> {
            // Ignored filters excluded
            if (v.stream().noneMatch(ignoreFilter)) {
                final GenContainer container = scannedContainers.get(k);
                final String fieldName = renamedFields.getOrDefault(k, nameCase.format(k.getName()));

                // Process export field (even if is export only)
                if (v.stream().anyMatch(exportFilter) && container == null) {
                    resultMap.put(k, FieldContainer.as(k, supplier.getSuitable(k), fieldName));
                } else if (container != null) {
                    resultMap.put(k, FieldContainer.as(k, container.getGenerator(), fieldName));
                }

            }
        });

        // Fill class export name
        Arrays.stream(target.getDeclaredAnnotations())
                .filter(renameFilter)
                .map(a -> ((GenExportName) a).value())
                .findFirst()
                .ifPresent(n -> resultMap.put(null, FieldContainer.as(null, null, n)));

        return resultMap;
    }
}
