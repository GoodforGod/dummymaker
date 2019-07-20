package io.dummymaker.scan.impl;

import io.dummymaker.annotation.core.ComplexGen;
import io.dummymaker.annotation.core.PrimeGen;
import io.dummymaker.annotation.export.GenExportForce;
import io.dummymaker.annotation.export.GenExportIgnore;
import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.scan.IExportScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * Scanner for special export annotations
 *
 * @see PrimeGen
 * @see ComplexGen
 * @see GenExportIgnore
 * @see GenExportForce
 * @see UniqueScanner
 *
 * @author GoodforGod
 * @since 03.06.2017
 */
public class ExportScanner implements IExportScanner {

    @Override
    public Map<Field, FieldContainer> scan(final Class t) {
        return scan(t, Cases.DEFAULT.value());
    }

    public Map<Field, FieldContainer> scan(final Class t,
                                           final ICase nameCase) {

        // Add all fields in correct order for setup (to save order)
        final Map<Field, FieldContainer> exportFields = Arrays.stream(t.getDeclaredFields())
                .collect(LinkedHashMap<Field, FieldContainer>::new,
                        (m, e) -> m.put(e, null),
                        (m, u) -> {
                        });

        final Map<Field, String> renamedFields = new HashMap<>();
        for (final Field field : t.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(GenExportForce.class)) {
                    exportFields.replace(field, FieldContainer.as(field, getAutoGenerator(field.getType()), field.getName()));
                } else if (annotation.annotationType().equals(GenExportIgnore.class)) {
                    exportFields.remove(field);
                }

                if (annotation.annotationType().equals(GenExportName.class)) {
                    renamedFields.put(field, ((GenExportName) annotation).value());
                }
            }
        }

        Arrays.stream(t.getDeclaredAnnotations())
                .filter(a -> a.annotationType().equals(GenExportName.class))
                .findFirst()
                .ifPresent(annotation -> exportFields.put(
                        null,
                        FieldContainer.as(null, null, ((GenExportName) annotation).value()))
                );

        final Map<Field, GenContainer> containerMap = new PopulateScanner().scan(t);
        containerMap.entrySet().stream()
                .filter(e -> exportFields.containsKey(e.getKey()))
                .forEach(e -> {
                    final Field f = e.getKey();
                    final String exportName = renamedFields.getOrDefault(f, nameCase.format(f.getName()));
                    exportFields.replace(f, FieldContainer.as(f, e.getValue().getGenerator(), exportName));
                    renamedFields.remove(f);
                });

        // Update renamed force export fields
        renamedFields.entrySet().stream()
                .filter(e -> exportFields.containsKey(e.getKey()))
                .forEach(e -> {
                    final Field f = e.getKey();
                    final FieldContainer container = exportFields.get(e.getKey());
                    if (container != null) {
                        exportFields.replace(f, FieldContainer.as(f, getAutoGenerator(f.getType()), e.getValue()));
                    }
                });

        exportFields.entrySet().stream()
                .filter(e -> e.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                .forEach(exportFields::remove);

        return exportFields;
    }
}
