package io.dummymaker.export.container;

import io.dummymaker.scan.ExportAnnotationScanner;
import io.dummymaker.scan.RenameAnnotationScanner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static io.dummymaker.util.NameStrategist.NamingStrategy;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 29.08.2017
 */
public class BaseClassContainer implements IClassContainer {

    private final Class exportClass;

    private final String originClassName;
    private final String finalClassName;

    private final NamingStrategy strategy;

    /**
     * Field origin name as a 'key', field as 'value'
     */
    private final Map<String, Field> originFields;

    /**
     * Field renamed (or origin) field name as 'key', fields as a 'value'
     */
    private final Map<String, Field> finalFields;

    /**
     * Renamed fields to export, Key is renamed (or origin) field name, Value is field
     */
    private final Map<String, String> renamedFields; //TreeMap

    public BaseClassContainer(final Class exportClass,
                       final NamingStrategy strategy) {
        this.exportClass = exportClass;
        this.strategy = strategy;

        this.renamedFields = new RenameAnnotationScanner().scan(exportClass);

        this.originFields = fillExportOriginFields();
        this.finalFields = fillExportRenamedFields();

        this.originClassName = exportClass.getSimpleName();
        this.finalClassName = (renamedFields.containsKey(null))
                ? renamedFields.get(null)
                : exportClass.getSimpleName();

        this.renamedFields.remove(null);
    }

    public String convertUsingNamingStrategy(final String value) {
        switch (strategy) {
            case LOW_CASE:
                return value.toLowerCase();
            case UPPER_CASE:
                return value.toUpperCase();
            case INITIAL_LOW_CASE:
                return value.substring(0, 1);
            case DEFAULT:
            default:
                return value;
        }
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
    public Map<String, Field> originFields() {
        return originFields;
    }

    @Override
    public Map<String, Field> finalFields() {
        return finalFields;
    }

    @Override
    public Map<String, String> renamedFields() {
        return renamedFields;
    }

    /**
     * Construct map where key is field name and field as value
     *
     * @return map with field name as 'key', field as 'value'
     */
    private Map<String, Field> fillExportOriginFields() {
        return new ExportAnnotationScanner().scan(exportClass)
                .entrySet().stream().collect(HashMap<String, Field>::new,
                        (m, c) -> m.put(c.getKey().getName(), c.getKey()),
                        (m, u) -> {
                        });
    }

    /**
     * Construct map with renamed fields names as keys if presented (or origin)
     *
     * @return map with renamed or origin field name as 'key', field as a 'value'
     */
    private Map<String, Field> fillExportRenamedFields() {
        final Map<String, Field> returnFields = new HashMap<>(originFields);

        for(final Map.Entry<String, String> renamed : renamedFields.entrySet()) {
            final Field field = returnFields.get(renamed.getKey());

            if(field != null) {
                returnFields.remove(renamed.getKey());
                returnFields.put(renamed.getValue(), field);
            }
        }

        return returnFields;
    }
}
