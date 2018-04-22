package io.dummymaker.container.impl;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.container.IClassContainer;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.generator.simple.impl.EnumerateGenerator;
import io.dummymaker.scan.impl.ExportScanner;
import io.dummymaker.scan.impl.RenameScanner;

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
public class ClassContainer implements IClassContainer {

    /** Export dummy object class */
    private final Class exportClass;

    /** Export dummy object class name (renamed or formatted) */
    private final String finalClassName;

    /** Naming strategy applied to field names and class name */
    private final ICase strategy;

    /** Field origin name as a 'key', fieldContainer as 'value' */
    private final Map<String, FieldContainer> fieldContainerMap;

    /** Renamed fields, 'Key' is origin field name, 'Value' is new field name */
    private final Map<String, String> renamedFields;

    public <T> ClassContainer(final T t,
                              final ICase strategy) {
        this.exportClass    = t.getClass();
        this.strategy       = strategy;

        this.renamedFields  = new RenameScanner().scan(exportClass);

        this.finalClassName = renamedFields.getOrDefault(null, strategy.format(exportClass.getSimpleName()));

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
    public Field getField(final String exportFieldName) {
        return fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().getExportName().equals(exportFieldName))
                .findFirst()
                .orElseThrow(NullPointerException::new).getValue().getField();
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
        return new ExportScanner().scan(exportClass).entrySet().stream()
                .collect(LinkedHashMap<String, FieldContainer>::new,
                        (m, e) -> m.put(e.getKey().getName(), buildFieldContainer(e.getKey(), e.getValue())),
                        (m, u) -> { }
                );
    }

    /**
     * Return renamed field name if exist or converted origin by via name strategy
     *
     * @return renamed field name
     */
    private String getFinalFieldName(final Field field) {
        return this.renamedFields.getOrDefault(field.getName(), strategy.format(field.getName()));
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
                getFinalFieldName(field),
                isAnnotationEnumerable(fieldAnnotations)
        );
    }

    /**
     * Indicates is field enumerated or not
     *
     * @see io.dummymaker.annotation.special.GenEnumerate
     */
    private boolean isAnnotationEnumerable(final List<Annotation> annotations) {
        return annotations.stream()
                .anyMatch(a -> a.annotationType().equals(PrimeGen.class)
                        && ((PrimeGen) a).value().equals(EnumerateGenerator.class));
    }

    /**
     * Indicates is field enumerated or not
     *
     * @see io.dummymaker.annotation.special.GenEnumerate
     */
    private boolean isAnnotationEmbedded(final List<Annotation> annotations) {
        return annotations.stream()
                .anyMatch(a -> a.annotationType().equals(PrimeGen.class)
                        && ((PrimeGen) a).value().equals(EnumerateGenerator.class));
    }
}
