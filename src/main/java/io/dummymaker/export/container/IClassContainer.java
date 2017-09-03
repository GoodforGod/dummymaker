package io.dummymaker.export.container;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * "Default Description"
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
     * @return
     */
    Field getFieldByFinalName(final String finalFieldName);

    /**
     * Convert string value via choose NameStrategy
     *
     * @param value value to convert
     * @return converted value
     *
     * @see io.dummymaker.util.NameStrategist
     */
    String convertByNamingStrategy(final String value);

    Map<String, FieldContainer> fieldContainerMap();

    Map<String, String> renamedFields();
}
