package io.dummymaker.export.container;

import io.dummymaker.scan.ExportAnnotationScanner;
import io.dummymaker.scan.RenameAnnotationScanner;
import io.dummymaker.util.INameStrategist;
import io.dummymaker.util.NameStrategist;
import io.dummymaker.util.NameStrategist.NamingStrategy;

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

    private final NamingStrategy strategy;

    private final INameStrategist strategist = new NameStrategist();

    /**
     * Field origin name as a 'key', fieldContainer as 'value'
     */
    private final Map<String, FieldContainer> fieldContainerMap;

    /**
     * Renamed fields, 'Key' is origin field name, 'Value' is new field name
     */
    private final Map<String, String> renamedFields;

    public BasicClassContainer(final Class exportClass,
                               final NamingStrategy strategy) {
        this.exportClass = exportClass;
        this.strategy = strategy;
        this.originClassName = exportClass.getSimpleName();

        this.renamedFields = new RenameAnnotationScanner().scan(exportClass);

        this.fieldContainerMap = fillExportFieldContainerMap();

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
    public String getExportFieldName(final String originFieldName) {
        return fieldContainerMap.get(originFieldName).getFinalFieldName();
    }

    public Field getFieldByFinalName(final String finalFieldName) {
        return fieldContainerMap.entrySet().stream()
                .filter(e -> e.getValue().getFinalFieldName().equals(finalFieldName))
                .findFirst()
                .orElseThrow(NullPointerException::new).getValue().getField();
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
    public Map<String, FieldContainer> fieldContainerMap() {
        return fieldContainerMap;
    }

    @Override
    public Map<String, String> renamedFields() {
        return renamedFields;
    }

    /**
     * @return final fields container map
     */
    private Map<String, FieldContainer> fillExportFieldContainerMap() {
        final Map<String, FieldContainer> fieldContainerMap = new HashMap<>();
        final Map<String, Field> originExportFields = fillExportOriginFields();

        for (Map.Entry<String, Field> fieldEntry : originExportFields.entrySet()) {
            final FieldContainer container = new FieldContainer(fieldEntry.getValue(),
                    (renamedFields.containsKey(fieldEntry.getKey()))
                            ? renamedFields.get(fieldEntry.getKey())
                            : convertByNamingStrategy(fieldEntry.getKey()));

            fieldContainerMap.put(fieldEntry.getKey(), container);
        }

        return new HashMap<>(fieldContainerMap);
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
                        (m, u) -> {
                        });
    }

    /**
     * Construct map with renamed fields names as keys if presented (or origin)
     *
     * @return map with renamed or origin field name as 'key', field as a 'value'
     */
    private Map<String, Field> fillExportFinalFields() {
        final Map<String, Field> fields = new HashMap<>();

        for (Map.Entry<String, Field> entry : fillExportOriginFields().entrySet()) {
            fields.put((renamedFields.containsKey(entry.getKey()))
                    ? renamedFields.get(entry.getKey())
                    : convertByNamingStrategy(entry.getKey()), entry.getValue());
        }

        return new HashMap<>(fields);
    }
}
