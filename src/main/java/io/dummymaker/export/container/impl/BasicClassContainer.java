package io.dummymaker.export.container.impl;

import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.scan.impl.ExportAnnotationScanner;
import io.dummymaker.scan.impl.RenameAnnotationScanner;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author GoodforGod
 * @see IClassContainer
 * @since 29.08.2017
 */
public class BasicClassContainer implements IClassContainer {

    private final Class exportClass;

    private final String originClassName;
    private final String finalClassName;

    private final IStrategy strategy;

    /**
     * Field origin name as a 'key', fieldContainer as 'value'
     */
    private final Map<String, FieldContainer> fieldContainerMap;

    /**
     * Renamed fields, 'Key' is origin field name, 'Value' is new field name
     */
    private final Map<String, String> renamedFields;

    public BasicClassContainer(final Class exportClass,
                               final IStrategy strategy) {
        this.exportClass = exportClass;
        this.strategy = strategy;
        this.originClassName = exportClass.getSimpleName();

        this.renamedFields = new RenameAnnotationScanner().scan(exportClass);

        this.fieldContainerMap = fillExportFieldContainerMap();

        this.finalClassName = (renamedFields.containsKey(null))
                ? renamedFields.get(null)
                : convertByNamingStrategy(originClassName);

        this.renamedFields.remove(null);
    }

    @Override
    public String convertByNamingStrategy(final String value) {
        return strategy.toStrategy(value);
    }

    @Override
    public String getExportFieldName(final String originFieldName) {
        return fieldContainerMap.get(originFieldName).getFinalFieldName();
    }

    public Field getFieldByFinalName(final String finalFieldName) {
        return fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().getFinalFieldName().equals(finalFieldName))
                .findFirst()
                .orElseThrow(NullPointerException::new).getValue().getField();
    }

    @Override
    public String originClassName() {
        return originClassName;
    }

    @Override
    public String finalClassName() {
        return finalClassName;
    }

    @Override
    public Map<String, FieldContainer> fieldContainerMap() {
        return fieldContainerMap;
    }

    @Override
    public Map<String, String> renamedFields() {
        return renamedFields;
    }

    /**
     * Map with origin field name as 'key', fieldContainer as 'value'
     *
     * @return final fields container map
     */
    private Map<String, FieldContainer> fillExportFieldContainerMap() {
        return fillExportOriginFields().entrySet().stream()
                .collect(LinkedHashMap<String, FieldContainer>::new,
                        (m, e) -> m.put(e.getKey(), new FieldContainer(e.getValue(), getRenamedFieldOrConverted(e.getKey()))),
                        (m, u) -> { }
                );
    }

    /**
     * Construct map where key is field name and field as value
     *
     * @return map with field name as 'key', field as 'value'
     */
    private Map<String, Field> fillExportOriginFields() {
        return new ExportAnnotationScanner().scan(exportClass).entrySet().stream()
                .collect(LinkedHashMap<String, Field>::new,
                        (m, e) -> m.put(e.getKey().getName(), e.getKey()),
                        (m, u) -> { }
                );
    }

    /**
     * Return renamed field name if exist or converted origin by via name strategy
     *
     * @param name origin field name
     * @return renamed field name
     */
    private String getRenamedFieldOrConverted(String name) {
        return renamedFields.getOrDefault(name, convertByNamingStrategy(name));
    }
}
