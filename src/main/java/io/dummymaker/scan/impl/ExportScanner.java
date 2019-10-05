package io.dummymaker.scan.impl;

import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.factory.IGenSupplier;
import io.dummymaker.factory.impl.GenSupplier;
import io.dummymaker.model.GenContainer;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.IExportScanner;
import io.dummymaker.scan.IGenScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * Scanner for special export annotations and produces export containers
 * Which are used in exporters
 *
 * @author GoodforGod
 * @see io.dummymaker.export.IExporter
 * @see GenExportIgnore
 * @see GenExportForce
 * @see GenExportName
 * @since 03.06.2017
 */
public class ExportScanner extends BasicScanner implements IExportScanner {

    private final Predicate<Annotation> ignoreFilter = a -> GenExportIgnore.class.equals(a.annotationType());
    private final Predicate<Annotation> exportFilter = (a) -> GenExportForce.class.equals(a.annotationType());
    private final Predicate<Annotation> renameFilter = (a) -> GenExportName.class.equals(a.annotationType());

    private final IGenSupplier supplier;
    private final IGenScanner genScanner;
    private final IAnnotationScanner annotationScanner;

    public ExportScanner() {
        this.supplier = new GenSupplier();
        this.genScanner = new GenAutoScanner(this.supplier);
        this.annotationScanner = new AnnotationScanner();
    }

    @Override
    public Map<Field, FieldContainer> scan(Class target) {
        return scan(target, Cases.DEFAULT.value());
    }

    @Override
    public Map<Field, FieldContainer> scan(Class target, ICase nameCase) {
        final Map<Field, FieldContainer> resultMap = new LinkedHashMap<>();
        final Map<Field, String> renamedFields = new HashMap<>();

        final Map<Field, GenContainer> scannedContainers = genScanner.scan(target);
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
