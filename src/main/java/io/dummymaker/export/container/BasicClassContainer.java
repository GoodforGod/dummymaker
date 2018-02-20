package io.dummymaker.export.container;

import io.dummymaker.scan.ExportAnnotationScanner;
import io.dummymaker.scan.RenameAnnotationScanner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 29.08.2017
 */
public class BasicClassContainer implements IClassContainer {

    private final Class exportClass;

    private final String originClassName;
    private final String finalClassName;

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

    public BasicClassContainer(final Class exportClass) {
        this.exportClass = exportClass;

        this.renamedFields = new RenameAnnotationScanner().scan(exportClass);

        this.originFields = fillExportOriginFields();
        this.finalFields = fillExportRenamedFields();

        this.originClassName = exportClass.getSimpleName();
        this.finalClassName = (renamedFields.containsKey(null))
                ? renamedFields.get(null)
                : exportClass.getSimpleName();

        this.renamedFields.remove(null);
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
        return new ExportAnnotationScanner().scan(exportClass).entrySet().stream()
                .collect(HashMap<String, Field>::new,
                        (m, c) -> m.put(c.getKey().getName(), c.getKey()),
                        (m, u) -> { });
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
