package io.dummymaker.model.export;

import io.dummymaker.annotation.export.GenExportName;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.error.GenException;
import io.dummymaker.scan.impl.ExportScanner;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Container class used to store class field information and different field states
 * <p>
 * Class Container for class origin/final name
 * Fields origin/final names
 * Fields values as Field type
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class ClassContainer {

    /**
     * Export dummy object class name (renamed or formatted)
     */
    private final String finalClassName;

    /**
     * Field origin name as a 'key', fieldContainer as 'value'
     */
    private final Map<Field, FieldContainer> fieldContainerMap;
    private final Format format;

    public <T> ClassContainer(T t, ICase strategy, Format format, GenRules rules) {
        this.format = format;

        this.fieldContainerMap = new ExportScanner(rules).scan(t.getClass(), strategy);
        final FieldContainer container = this.fieldContainerMap.get(null);
        if (container != null) {
            this.finalClassName = container.getExportName();
            this.fieldContainerMap.remove(null);
        } else {
            this.finalClassName = strategy.format(t.getClass().getSimpleName());
        }
    }

    /**
     * Show whenever export values are presented
     * If empty then no export values are present and export is pointless
     *
     * @return boolean value stated if container is exportable
     */
    public boolean isExportable() {
        return fieldContainerMap.entrySet().stream().anyMatch(e -> format.isTypeSupported(e.getValue().getType()));
    }

    /**
     * Retrieve field by its export name (formatted via strategy or renamed via annotation)
     *
     * @param exportFieldName field container with final name
     * @return field value
     * @see ICase
     * @see GenExportName
     */
    public Field getField(final String exportFieldName) {
        return this.fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().getExportName().equals(exportFieldName))
                .findFirst()
                .orElseThrow(GenException::new).getKey();
    }

    /**
     * Export class name (after naming strategy applied or renamed)
     *
     * @return class final export name
     * @see ICase
     * @see GenExportName
     */
    public String getExportClassName() {
        return this.finalClassName;
    }

    /**
     * Retrieve container by its export name (formatted via strategy or renamed via annotation)
     *
     * @param exportFieldName field container with final name
     * @return field container
     */
    public FieldContainer getContainer(final String exportFieldName) {
        return this.fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().getExportName().equals(exportFieldName))
                .findFirst()
                .orElseThrow(GenException::new).getValue();
    }

    /**
     * Returns only format support field containers map
     *
     * @param format to filter on
     * @return field format supported container map
     * @see FieldContainer
     */
    public Map<Field, FieldContainer> getFormatSupported(final Format format) {
        return this.fieldContainerMap.entrySet().stream()
                .filter(e -> format.getSupported().contains(e.getValue().getType()))
                .collect(LinkedHashMap::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        (m, u) -> { });
    }
}
