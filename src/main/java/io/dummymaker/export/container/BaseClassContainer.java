package io.dummymaker.export.container;

import io.dummymaker.scan.ExportAnnotationScanner;
import io.dummymaker.scan.RenameAnnotationScanner;
import io.dummymaker.util.INameStrategist;
import io.dummymaker.util.NameStrategist;

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

    private final INameStrategist strategist = new NameStrategist();

    /**
     * Field origin name as a 'key', field as 'value'
     */
    private final Map<String, Field> originFields;

    /**
     * Field renamed (or converted via NameStrategy) field name as 'key', fields as a 'value'
     */
    private final Map<String, Field> finalFields;

    /**
     * Renamed fields, 'Key' is origin field name, 'Value' is new field name
     */
    private final Map<String, String> renamedFields; //TreeMap

    public BaseClassContainer(final Class exportClass,
                              final NamingStrategy strategy) {
        this.exportClass = exportClass;
        this.strategy = strategy;
        this.originClassName = exportClass.getSimpleName();

        this.renamedFields = new RenameAnnotationScanner().scan(exportClass);

        this.originFields = fillExportOriginFields();
        this.finalFields = fillExportRenamedFields();

        this.finalClassName = (renamedFields.containsKey(null))
                ? renamedFields.get(null)
                : convertByNamingStrategy(originClassName);

        this.renamedFields.remove(null);
    }

    @Override
    public String convertByNamingStrategy(final String value) {
        return strategist.toNamingStrategy(value, strategy);
    }

    @Override
    public String convertToExportFieldName(final String originFieldName) {
        return renamedFields.getOrDefault(originFieldName, convertByNamingStrategy(originFieldName));
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
        return new ExportAnnotationScanner().scan(exportClass).entrySet()
                .stream()
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
        final Map<String, Field> fields = new HashMap<>();

        originFields.entrySet().stream().filter(f -> !renamedFields.containsKey(f.getKey())).forEach(f -> fields.put(convertByNamingStrategy(f.getKey()), f.getValue()));

        for(final Map.Entry<String, String> renamed : renamedFields.entrySet()) {
            final Field field = fields.get(renamed.getKey());

            if(field != null) {
                fields.remove(renamed.getKey());
                fields.put(renamed.getValue(), field);
            }
        }

        return fields;
    }
}
