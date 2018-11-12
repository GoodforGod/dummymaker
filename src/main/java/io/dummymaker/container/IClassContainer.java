package io.dummymaker.container;

import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.export.Format;
import io.dummymaker.export.naming.ICase;

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
     *
     * @return boolean value stated if container is exportable
     */
    boolean isExportable();

    /**
     * Export class name (after naming strategy applied or renamed)
     *
     * @see ICase
     * @see io.dummymaker.annotation.special.GenRenameExport
     * @return class final export name
     */
    String getExportClassName();

    /**
     * Retrieve field by its export name (formatted via strategy or renamed via annotation)
     *
     * @see ICase
     * @see io.dummymaker.annotation.special.GenRenameExport
     *
     * @param exportFieldName field container with final name
     * @return field value
     */
    Field getField(final String exportFieldName);

    /**
     * Retrieve container by its export name (formatted via strategy or renamed via annotation)
     *
     * @param exportFieldName field container with final name
     * @return field container
     */
    FieldContainer getContainer(final String exportFieldName);

    /**
     * Returns field containers map
     *
     * @see FieldContainer
     */
    Map<Field, FieldContainer> getContainers();

    /**
     * Returns only format support field containers map
     *
     * @see FieldContainer
     */
    Map<Field, FieldContainer> getFormatSupported(Format format);
}
