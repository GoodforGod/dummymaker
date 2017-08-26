package io.dummymaker.export;

import io.dummymaker.scan.ExportAnnotationScanner;
import io.dummymaker.scan.RenameAnnotationScanner;
import io.dummymaker.writer.BufferedFileWriter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Prime (Parent) exporter for all others, provides core methods
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
abstract class OriginExporter<T> extends BufferedFileWriter implements IExporter<T> {

    /**
     * Available export types, used by writer
     *
     * @see io.dummymaker.writer.BufferedFileWriter
     */
    enum ExportFormat {
        CSV(".csv"),
        JSON(".json"),
        XML(".xml"),
        SQL(".sql");

        ExportFormat(String value) {
            this.value = value;
        }

        private final String value;

        public String getValue() {
            return value;
        }
    }

    /**
     * Class type to export
     */
    private final Class<T> exportClass;

    final String exportClassName;

    private final Logger logger = Logger.getLogger(OriginExporter.class.getName());

    /**
     * Field origin name as a key, and field values as a values
     */
    private final Map<String, Field> exportOriginFields;

    /**
     * Fields to export with origin names, Key is renamed (or origin) field name, Value is field
     */
    final Map<String, Field> exportRenamedFields;

    /**
     * Renamed fields to export, Key is renamed (or origin) field name, Value is field
     */
    private final Map<String, String> renamedFields = new TreeMap<>();

    OriginExporter(final Class<T> exportClass,
                   final String path,
                   final ExportFormat type) {
        super(exportClass.getSimpleName(), path, type.getValue());
        this.exportClass = exportClass;

        // Fill export origin fields
        this.exportOriginFields = fillExportOriginFields();

        final Map<String, String> renamedMap = new RenameAnnotationScanner().scan(exportClass);

        // Fill renamed fields
        renamedMap.entrySet().stream()
                .filter(ren -> this.exportOriginFields.containsKey(ren.getKey()))
                .forEach(ren -> this.renamedFields.put(ren.getKey(), ren.getValue()));

        this.exportRenamedFields = fillExportRenamedFields();

        // Setup class name, renamed or origin
        this.exportClassName = (renamedMap.containsKey(null))
                ? renamedMap.get(null)
                : exportClass.getSimpleName();
    }

    /**
     * Map where key is field name and field as value
     *
     * @return map with field name, field values
     */
    private Map<String, Field> fillExportOriginFields() {
        return new ExportAnnotationScanner().scan(exportClass)
                .entrySet().stream().collect(HashMap<String, Field>::new,
                            (m, c) -> m.put(c.getKey().getName(), c.getKey()),
                            (m, u) -> {
                        });
    }

    /**
     * Convert origin field names to renamed ones
     *
     * @return exported renamed field map
     * Where Key is renamed or origin (is not renamed) field name, and Value is field
     */
    private Map<String, Field> fillExportRenamedFields() {
        final Map<String, Field> returnFields = new HashMap<>(exportOriginFields);

        for(final Map.Entry<String, String> renamed : renamedFields.entrySet()) {
            final Field field = returnFields.get(renamed.getKey());

            if(field != null) {
                returnFields.remove(renamed.getKey());
                returnFields.put(renamed.getValue(), field);
            }
        }

        return returnFields;
    }

    /**
     * Generates class export field map
     * @param t class to export
     * @return map of field name as a key and fields string values as a values
     */
    Map<String, String> getExportValues(final T t) {
        final Map<String, String> exports = new HashMap<>();

        for(Map.Entry<String, Field> field : exportOriginFields.entrySet()) {
            try {
                final Field fieldToExport = t.getClass().getDeclaredField(field.getKey());

                if(fieldToExport != null) {
                    fieldToExport.setAccessible(true);

                    final String renamedValue = (renamedFields.containsKey(fieldToExport.getName()))
                            ? renamedFields.get(fieldToExport.getName())
                            : fieldToExport.getName();

                    exports.put(renamedValue, String.valueOf(fieldToExport.get(t)));
                    fieldToExport.setAccessible(false);
                }
            }
            catch (IllegalAccessException e) {
                logger.info(e.getMessage());
            }
            catch (NoSuchFieldException e) {
                logger.warning(e.getMessage());
            }
        }

        return new HashMap<>(exports);
    }

    /**
     * Validate export arguments and export fields of class
     * @param t class to validate
     * @return validation result
     */
    boolean isExportStateValid(final T t) {
        return !exportOriginFields.isEmpty() && t != null;
    }

    /**
     * Validate export arguments and export fields of class
     * @param t class to validate
     * @return validation result
     */
    boolean isExportStateValid(final List<T> t) {
        return !exportOriginFields.isEmpty() && t != null && !t.isEmpty();
    }

    @Override
    public abstract boolean export(final T t);

    @Override
    public abstract boolean export(final List<T> t);

    @Override
    public abstract String exportAsString(final T t);

    @Override
    public abstract String exportAsString(final List<T> list);
}
