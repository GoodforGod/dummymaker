package io.dummymaker.export;

import io.dummymaker.annotation.special.GenForceExport;
import io.dummymaker.annotation.special.GenIgnoreExport;
import io.dummymaker.scan.ExportAnnotationScanner;
import io.dummymaker.writer.BufferedFileWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Prime (Parent) exporter for all others, provides core methods
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
abstract class OriginExporter<T> extends BufferedFileWriter implements IExporter<T> {

    /**
     * Class type to export
     */
    final Class<T> exportClass;

    private final Logger logger = Logger.getLogger(OriginExporter.class.getName());

    private final Predicate<Annotation> forcePredicate = (a) -> a.annotationType().equals(GenForceExport.class);

    private final Predicate<Annotation> ignorePredicate = (a) -> a.annotationType().equals(GenIgnoreExport.class);

    /**
     * Field name as a key, and field values as a values
     */
    final Map<String, Field> exportFields = new HashMap<>();

    OriginExporter(Class<T> exportClass, String path, ExportFormat type) {
        super(exportClass.getSimpleName(), path, type.getValue());
        this.exportClass = exportClass;

        new ExportAnnotationScanner().scan(exportClass).forEach((key, value) -> exportFields.put(key.getName(), key));
    }

    /**
     * Convert string values to empty if needed
     * @param value string to convert
     * @return return origin value or empty converted one
     */
    protected String convertToEmptyValue(String value) {
        return (value != null && !value.trim().isEmpty())
                ? value
                : "null";
    }

    /**
     * Generates class export field map
     * @param t class to export
     * @return map of field name as a key and fields string values as a values
     */
    Map<String, String> getExportValues(T t) {
        final Map<String, String> exports = new HashMap<>();

        for(Map.Entry<String, Field> field : exportFields.entrySet()) {
            try {
                Field fieldToExport = t.getClass().getDeclaredField(field.getKey());

                if(fieldToExport != null) {
                    fieldToExport.setAccessible(true);
                    exports.put(fieldToExport.getName(), String.valueOf(fieldToExport.get(t)));
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

        return exports;
    }

    @Override
    public abstract void export(T t);

    @Override
    public abstract void export(List<T> t);
}
