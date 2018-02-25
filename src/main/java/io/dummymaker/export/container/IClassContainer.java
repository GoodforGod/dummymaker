package io.dummymaker.export.container;

import io.dummymaker.export.container.impl.FieldContainer;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Container class used to store class field information and different field states
 *
 * Class Container for class origin/final name
 * Fields origin/final names
 * Fields values as Field type
 *
 * @author GoodforGod
 * @since 29.08.2017
 */
public interface IClassContainer {

    /**
     * Show whenever export values are presented
     */
    boolean isExportable();

    /**
     * Origin class name
     */
    String originClassName();

    /**
     * Export class name (after naming strategy applied or renamed)
     */
    String exportClassName();

    /**
     * Retrieve export field name by its origin name
     *
     * @param originFieldName origin class field name
     * @return export field name
     */
    String getExportFieldName(final String originFieldName);

    /**
     * Retrieve field by its export name
     *
     * @see io.dummymaker.export.naming.IStrategy
     * @see io.dummymaker.annotation.special.GenRenameExport
     *
     * @param exportFieldName field container with final name
     * @return field value
     */
    Field getField(final String exportFieldName);

    /**
     * Map with origin field name as 'key', value as 'field container'
     *
     * @see FieldContainer
     *
     * @return export containers
     */
    Map<String, FieldContainer> getFieldContainers();
}
