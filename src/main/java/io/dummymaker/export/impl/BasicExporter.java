package io.dummymaker.export;

import io.dummymaker.export.container.BasicClassContainer;
import io.dummymaker.export.container.ExportContainer;
import io.dummymaker.export.container.FieldContainer;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.util.NameStrategist;
import io.dummymaker.writer.BufferedFileWriter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Prime (Parent) exporter for all others, provides core methods
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public abstract class BaseExporter<T> extends BufferedFileWriter implements IExporter<T> {

    private final Logger logger = Logger.getLogger(BaseExporter.class.getName());

    /**
     * Available export types, used by writer
     *
     * @see io.dummymaker.writer.BufferedFileWriter
     */
    public enum ExportFormat {
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

    final IClassContainer classContainer;

    /**
     * @param exportClass class to export
     * @param path path where to export (NULL IF HOME DIR)
     * @param format export format
     */
    BaseExporter(final Class<T> exportClass,
                 final String path,
                 final ExportFormat format,
                 final NameStrategist.NamingStrategy strategy) {
        super(exportClass.getSimpleName(), path, format.getValue());

        this.classContainer = new BasicClassContainer(exportClass, strategy);
    }

    /**
     * Generates class export field-value map
     * @param t class to export
     * @return map of field name as a 'key' and fields string value as a 'values'
     */
    List<ExportContainer> extractExportValues(final T t) {
        final List<ExportContainer> exports = new ArrayList<>();

        for(Map.Entry<String, FieldContainer> fieldEntry : classContainer.fieldContainerMap().entrySet()) {
            try {
                final Field field = t.getClass().getDeclaredField(fieldEntry.getKey());

                if(field != null) {
                    field.setAccessible(true);

                    final String exportFieldName = classContainer.getExportFieldName(field.getName());
                    exports.add(new ExportContainer(exportFieldName, String.valueOf(field.get(t))));

                    field.setAccessible(false);
                }
            }
            catch (IllegalAccessException e) {
                logger.info(e.getMessage());
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }

        return exports;
    }

    /**
     * Validate export arguments and export fields of class
     * @param t class to validate
     * @return validation result
     */
    boolean isExportStateValid(final T t) {
        return !classContainer.fieldContainerMap().isEmpty() && t != null;
    }

    /**
     * Validate export arguments and export fields of class
     * @param t class to validate
     * @return validation result
     */
    boolean isExportStateValid(final List<T> t) {
        return !classContainer.fieldContainerMap().isEmpty() && t != null && !t.isEmpty();
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
