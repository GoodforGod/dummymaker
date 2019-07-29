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
import java.util.*;
import java.util.stream.Collectors;

import static io.dummymaker.util.GenUtils.getAutoGenerator;

/**
 * Scanner for special export annotations
 *
 * @author GoodforGod
 * @see PrimeGen
 * @see ComplexGen
 * @see GenExportIgnore
 * @see GenExportForce
 * @see UniqueScanner
 * @since 03.06.2017
 */
public class ExportScanner extends BasicScanner implements IExportScanner {

    @Override
    public Map<Field, FieldContainer> scan(final Class target) {
        return scan(target, Cases.DEFAULT.value());
    }

    public Map<Field, FieldContainer> scan(final Class target,
                                           final ICase nameCase) {

        // Add all fields in correct order for setup (to save order)
        final List<Field> declaredFields = getAllDeclaredFields(target);
        final Map<Field, FieldContainer> exportFields = declaredFields.stream()
                .filter(f -> !f.isSynthetic())
                .collect(LinkedHashMap::new,
                        (m, e) -> m.put(e, null),
                        (m, u) -> { });

        final Map<Field, String> renamedFields = new HashMap<>();
        for (final Field field : declaredFields) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(GenExportForce.class)) {
                    //TODO research why geb auto is presented here
                    exportFields.replace(field, FieldContainer.as(field, getAutoGenerator(field, field.getType()), field.getName()));
                } else if (annotation.annotationType().equals(GenExportIgnore.class)) {
                    exportFields.remove(field);
                }

                if (annotation.annotationType().equals(GenExportName.class)) {
                    renamedFields.put(field, ((GenExportName) annotation).value());
                }
            }
        }

        Arrays.stream(target.getDeclaredAnnotations())
                .filter(a -> a.annotationType().equals(GenExportName.class))
                .findFirst()
                .ifPresent(annotation -> exportFields.put(
                        null,
                        FieldContainer.as(null, null, ((GenExportName) annotation).value()))
                );

        final Map<Field, GenContainer> containerMap = new PopulateScanner().scan(target);
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
                        exportFields.replace(f, FieldContainer.as(f, getAutoGenerator(f, f.getType()), e.getValue()));
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
