package io.dummymaker.export.container.impl;

import io.dummymaker.annotation.PrimeGenAnnotation;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.generator.impl.EnumerateGenerator;
import io.dummymaker.scan.impl.ExportAnnotationScanner;
import io.dummymaker.scan.impl.RenameAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @see IClassContainer
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class BasicStaticClassContainer implements IClassContainer {

    /**
     * Export dummy object class
     */
    private final Class exportClass;

    private final String originClassName;

    /**
     * Export dummy object class name (renamed or formatted)
     */
    private final String finalClassName;

    /**
     * Naming strategy applied to field names and class name
     */
    private final IStrategy strategy;

    /**
     * Field origin name as a 'key', fieldContainer as 'value'
     */
    private final Map<String, FieldContainer> fieldContainerMap;

    /**
     * Renamed fields, 'Key' is origin field name, 'Value' is new field name
     */
    private final Map<String, String> renamedFields;

    public <T> BasicStaticClassContainer(final T t,
                                         final IStrategy strategy) {
        this.exportClass = t.getClass();
        this.strategy = strategy;

        this.renamedFields = new RenameAnnotationScanner().scan(exportClass);

        this.originClassName = exportClass.getSimpleName();
        this.finalClassName = renamedFields.getOrDefault(null, strategy.toStrategy(originClassName));

        this.fieldContainerMap = buildExportFieldContainerMap();
        this.renamedFields.remove(null);
    }

    /**
     * If empty then no export values are present and export is pointless
     */
    public boolean isExportable() {
        return !fieldContainerMap.isEmpty();
    }

    @Override
    public String getFieldExportName(final String originFieldName) {
        return fieldContainerMap.get(originFieldName).getExportName();
    }

    @Override
    public Field getField(final String exportFieldName) {
        return fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().getExportName().equals(exportFieldName))
                .findFirst()
                .orElseThrow(NullPointerException::new).getValue().getField();
    }

    @Override
    public String originClassName() {
        return originClassName;
    }

    @Override
    public String exportClassName() {
        return finalClassName;
    }

    @Override
    public Map<String, FieldContainer> getContainers() {
        return fieldContainerMap;
    }

    /**
     * Map with origin field name as 'key', fieldContainer as 'value'
     *
     * @return final fields container map
     */
    private Map<String, FieldContainer> buildExportFieldContainerMap() {
        return new ExportAnnotationScanner().scan(exportClass).entrySet().stream()
                .collect(LinkedHashMap<String, FieldContainer>::new,
                        (m, e) -> m.put(e.getKey().getName(), buildFieldContainer(e.getKey(), e.getValue())),
                        (m, u) -> {
                        }
                );
    }

    /**
     * Return renamed field name if exist or converted origin by via name strategy
     *
     * @param name origin field name
     * @return renamed field name
     */
    private String getRenamedFieldOrConverted(final String name) {
        return renamedFields.getOrDefault(name, strategy.toStrategy(name));
    }

    /**
     * @see io.dummymaker.annotation.special.GenEnumerate
     *
     * @param fieldAnnotations used to set field container enumerate flag
     * @return build field container
     */
    private FieldContainer buildFieldContainer(final Field field,
                                               final List<Annotation> fieldAnnotations) {
        return new FieldContainer(
                field,
                getRenamedFieldOrConverted(field.getName()),
                isAnnotationEnumerable(fieldAnnotations)
        );
    }

    /**
     * Indicates is field enumerated or not
     *
     * @see io.dummymaker.annotation.special.GenEnumerate
     */
    private boolean isAnnotationEnumerable(List<Annotation> annotations) {
        return annotations.stream()
                .anyMatch(a -> a.annotationType().equals(PrimeGenAnnotation.class)
                        && ((PrimeGenAnnotation) a).value().equals(EnumerateGenerator.class));
    }
}
