package io.dummymaker.container.impl;

import io.dummymaker.container.IClassContainer;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.scan.impl.ExportScanner;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @see IClassContainer
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class ClassContainer implements IClassContainer {

    /**
     * Export dummy object class
     */
    private final Class exportClass;

    /**
     * Export dummy object class name (renamed or formatted)
     */
    private final String finalClassName;

    /**
     * Field origin name as a 'key', fieldContainer as 'value'
     */
    private final Map<Field, FieldContainer> fieldContainerMap;

    public <T> ClassContainer(final T t,
                              final ICase strategy) {
        this.exportClass = t.getClass();

        this.fieldContainerMap = new ExportScanner().scan(t.getClass(), strategy);
        final FieldContainer container = this.fieldContainerMap.get(null);
        if (container != null) {
            this.finalClassName = container.getExportName();
            this.fieldContainerMap.remove(null);
        } else {
            this.finalClassName = strategy.format(t.getClass().getSimpleName());
        }
    }

    /**
     * If empty then no export values are present and export is pointless
     */
    public boolean isExportable() {
        return fieldContainerMap.entrySet().stream().anyMatch(e -> e.getValue().isSimple());
    }

    @Override
    public Field getField(final String exportFieldName) {
        return this.fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().getExportName().equals(exportFieldName))
                .findFirst()
                .orElseThrow(NullPointerException::new).getKey();
    }

    public Class getExportClass() {
        return exportClass;
    }

    @Override
    public String getExportClassName() {
        return this.finalClassName;
    }

    @Override
    public Map<Field, FieldContainer> getContainers() {
        return this.fieldContainerMap;
    }

    @Override
    public Map<Field, FieldContainer> getSimpleContainers() {
        return this.fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().isSimple() || e.getValue().isEnumerable())
                .collect(LinkedHashMap<Field, FieldContainer>::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        (m, u) -> {
                        });
    }
}
