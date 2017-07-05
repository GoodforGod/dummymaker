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
abstract class OriginExporter<T> extends BufferedFileWriter implements IExporter<T> {

    /**
     * Class type to export
     */
    final Class<T> exportClass;

    private final Logger logger = Logger.getLogger(OriginExporter.class.getName());

    /**
     * Field name as a key, and field values as a values
     */
    final Map<String, Field> exportFields = new HashMap<>();

    OriginExporter(final Class<T> exportClass, final String path, final ExportFormat type) {
        super(exportClass.getSimpleName(), path, type.getValue());
        this.exportClass = exportClass;

        new ExportAnnotationScanner().scan(exportClass).forEach((key, value) -> exportFields.put(key.getName(), key));
    }

    /**
     * Generates class export field map
     * @param t class to export
     * @return map of field name as a key and fields string values as a values
     */
    Map<String, String> getExportValues(final T t) {
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
    public abstract boolean export(final T t);

    @Override
    public abstract boolean export(final List<T> t);

    @Override
    public abstract String exportAsString(final T t);

    @Override
    public abstract String exportAsString(final List<T> list);
}
