package io.dummymaker.export.container.impl;

import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.scan.impl.ExportAnnotationScanner;
import io.dummymaker.scan.impl.RenameAnnotationScanner;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class BasicStaticClassContainer implements IClassContainer {

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
    public String getExportFieldName(final String originFieldName) {
        return fieldContainerMap.get(originFieldName).getExportName();
    }

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
    public Map<String, FieldContainer> getFieldContainers() {
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
                        (m, e) -> m.put(e.getKey().getName(), buildFieldContainer(e.getKey())),
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
        return renamedFields.getOrDefault(name, strategy.toStrategy(name));
    }

    private FieldContainer buildFieldContainer(Field field) {
        return new FieldContainer(field, getRenamedFieldOrConverted(field.getName()));
    }
}
