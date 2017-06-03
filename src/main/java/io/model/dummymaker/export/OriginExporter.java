package io.model.dummymaker.export;

import io.model.dummymaker.scan.ExportAnnotationScanner;
import io.model.dummymaker.writer.DiskWriter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public abstract class OriginExporter<T> extends DiskWriter<T> implements IExporter<T> {

    protected final Class<T> primeClass;

    protected final Logger logger = Logger.getLogger(OriginExporter.class.getName());

    protected final ExportAnnotationScanner exportScanner = new ExportAnnotationScanner();

    protected final List<Field> fieldsToExport = new ArrayList<>();

    public OriginExporter(Class<T> primeClass, ExportType type) {
        this(primeClass, null, type);
    }

    public OriginExporter(Class<T> primeClass, String path, ExportType type) {
        super(primeClass, path, type);
        this.primeClass = primeClass;
        exportScanner.scan(primeClass).entrySet().forEach(set -> fieldsToExport.add(set.getKey()));
    }

    protected Map<String, String> getExportValues(T t) {
        Map<String, String> exports = new HashMap<>();
        for(Field field : fieldsToExport) {
            try {
                Field exportField = t.getClass().getDeclaredField(field.getName());
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
