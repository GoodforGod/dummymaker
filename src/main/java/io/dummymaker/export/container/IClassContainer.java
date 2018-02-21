package io.dummymaker.export.container;

import io.dummymaker.export.naming.PresetStrategies;

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

    String originClassName();
    String finalClassName();

    /**
     * Convert field origin name to export field name
     *
     * @param originFieldName origin class field name
     * @return export field name
     */
    String getExportFieldName(final String originFieldName);

    /**
     *
     * @param finalFieldName field container with final name and
     * @return field value
     */
    Field getFieldByFinalName(final String finalFieldName);

    /**
     * Convert string value via choose NameStrategy
     *
     * @param value value to convert
     * @return converted value
     *
     * @see PresetStrategies
     */
    String convertByNamingStrategy(final String value);

    Map<String, FieldContainer> fieldContainerMap();

    /**
     * @return renamed field map
     */
    Map<String, String> renamedFields();
}
