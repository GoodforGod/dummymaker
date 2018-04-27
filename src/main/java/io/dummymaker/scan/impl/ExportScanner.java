package io.dummymaker.scan.impl;

import io.dummymaker.annotation.ComplexGen;
import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.annotation.special.GenRenameExport;
import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.scan.IExportScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scanner for special export annotations
 *
 * @see PrimeGen
 * @see ComplexGen
 *
 * @see GenIgnoreExport
 * @see GenForceExport
 *
 * @see UniqueScanner
 *
 * @author GoodforGod
 * @since 03.06.2017
 */
public class ExportScanner implements IExportScanner {

    @Override
    public Map<Field, FieldContainer> scan(final Class t) {
        final Map<Field, FieldContainer> exportContainerMap = new HashMap<>();

        final Map<Field, String> renameExportMap = new HashMap<>();
        final Set<Field> forceExportFields = new HashSet<>();

        for(final Field field : t.getDeclaredFields()) {
            for(Annotation annotation : field.getDeclaredAnnotations()) {
                if(annotation.annotationType().equals(GenForceExport.class)) {
                    forceExportFields.add(field);
                }

                if(annotation.annotationType().equals(GenRenameExport.class)) {
                    renameExportMap.put(field, ((GenRenameExport) annotation).value());
                }
            }
        }

        final Map<Field, GenContainer> containerMap = new PopulateScanner().scan(t);
        containerMap.forEach((k, v) -> {
            final String exportName = renameExportMap.getOrDefault(k, k.getName());
            exportContainerMap.put(k, FieldContainer.as(k, v.getMarker(),exportName));
        });

        forceExportFields.forEach(f -> {
            final String exportName = renameExportMap.getOrDefault(f, f.getName());
            exportContainerMap.putIfAbsent(f, FieldContainer.as(f, null, exportName));
        });

        return exportContainerMap;

//        final Map<Field, List<Annotation>> classFieldAnnotations = super.scan(t);
//
//        return classFieldAnnotations.entrySet().stream()
//                .filter(e -> e.getValue().stream().noneMatch(isIgnorable))
//                .peek(e -> e.setValue(e.getValue().stream()
//                        .filter(isAcceptable)
//                        .collect(Collectors.toList())))
//                .filter(e -> !e.getValue().isEmpty())
//                .collect(LinkedHashMap<Field, List<Annotation>>::new,
//                        (m, e) -> m.put(e.getKey(), e.getValue()),
//                        (m, u) -> { }
//                );
    }
}
