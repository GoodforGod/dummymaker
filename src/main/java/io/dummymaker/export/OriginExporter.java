package io.dummymaker.export;

import io.dummymaker.scan.ExportAnnotationScanner;
import io.dummymaker.writer.BufferedFileWriter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Prime (Parent) exporter for all others, provides core methods
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
abstract class OriginExporter<T> extends BufferedFileWriter<T> implements IExporter<T> {

    /**
     * Class type to export
     */
    protected final Class<T> exportClass;

    protected final Logger logger = Logger.getLogger(OriginExporter.class.getName());

    protected final ExportAnnotationScanner exportScanner = new ExportAnnotationScanner();

    /**
     * Field name as a key, and field values as a values
     */
    protected final Map<String, Field> fieldsToExport = new HashMap<>();

    public OriginExporter(Class<T> exportClass, String path, ExportFormat type) {
        super(exportClass, path, type.getValue());
        this.exportClass = exportClass;

        exportScanner.scan(exportClass).entrySet().forEach(set -> fieldsToExport.put(set.getKey().getName(), set.getKey()));
    }

    /**
     * Convert string values to empty if needed
     * @param value string to convert
     * @return return origin value or empty converted one
     */
    protected String convertToEmptyValue(String value) {
        // Value to be exported if object value is Null or Empty

        String EMPTY_VALUE = "null";
        return (value != null && !value.trim().isEmpty())
                ? value
                : EMPTY_VALUE;
    }

    /**
     * Generates class export field map
     * @param t class to export
     * @return map of field name as a key and fields string values as a values
     */
    protected Map<String, String> getExportValues(T t) {
        Map<String, String> exports = new HashMap<>();
        for(Map.Entry<String, Field> field : fieldsToExport.entrySet()) {
            try {
                Field exportField = t.getClass().getDeclaredField(field.getKey());
                if(exportField != null) {
                    exportField.setAccessible(true);
                    exports.put(exportField.getName(), String.valueOf(exportField.get(t)));
                    exportField.setAccessible(false);
                }
            }
            catch (IllegalAccessException e) {
                logger.info(e.getMessage());
            }
            catch (NoSuchFieldException e) {
                logger.warning(e.getMessage());
            }
        }

        return exports;
    }

    @Override
    public abstract void export(T t);

    @Override
    public abstract void export(List<T> t);
}
