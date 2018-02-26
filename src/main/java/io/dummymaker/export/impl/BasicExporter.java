package io.dummymaker.export.impl;

import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.BasicStaticClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.writer.BufferedFileWriter;
import io.dummymaker.writer.IWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Basic abstract exporter implementation
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public abstract class BasicExporter implements IExporter {

    private final Logger logger = Logger.getLogger(BasicExporter.class.getName());

    private String path;
    private Format format;
    private IStrategy strategy;

    /**
     * @param strategy naming strategy
     * @param path     path where to export (NULL IF HOME DIR)
     * @param format   export format
     * @see IStrategy
     * @see Format
     */
    BasicExporter(final String path,
                  final Format format,
                  final IStrategy strategy) {

        if (strategy == null)
            logger.warning("NamingStrategy in nullable.. Using default strategy.");

        this.path = path;
        this.format = format;
        this.strategy = (strategy == null)
                ? PresetStrategies.DEFAULT.getStrategy()
                : strategy;
    }

    void setPath(String path) {
        this.path = (path == null || path.trim().isEmpty())
                ? null
                : path;
    }

    void setStrategy(IStrategy strategy) {
        this.strategy = (strategy == null)
                ? this.strategy
                : strategy;
    }

    /**
     * Build class container with export entity parameters
     */
    <T> IClassContainer buildClassContainer(T t) {
        return new BasicStaticClassContainer(t, strategy);
    }

    /**
     * Build class container with export entity parameters
     */
    <T> IClassContainer buildClassContainer(List<T> list) {
        return (list != null && !list.isEmpty())
                ? buildClassContainer(list.get(0))
                : null;
    }

    /**
     * Build buffered writer for export
     *
     * @see #export(Object)
     * @see #export(List)
     */
    IWriter buildWriter(IClassContainer classContainer) {
        try {
            return new BufferedFileWriter(classContainer.exportClassName(), path, format.getExtension());
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    /**
     * Generates class export field-value map
     *
     * @param t class to export
     * @return export containers
     * @see ExportContainer
     */
    <T> List<ExportContainer> extractExportContainers(final T t, final IClassContainer classContainer) {

        final List<ExportContainer> exports = new ArrayList<>();
        classContainer.getContainers().forEach((key, value) -> {
            try {
                final Field field = t.getClass().getDeclaredField(key);

                if (field != null) {
                    field.setAccessible(true);

                    final String exportFieldName = value.getExportName();
                    exports.add(new ExportContainer(exportFieldName, String.valueOf(field.get(t))));

                    field.setAccessible(false);
                }
            } catch (IllegalAccessException e) {
                logger.info(e.getMessage());
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        });

        return exports;
    }

    /**
     * Validate export argument
     *
     * @param t class to validate
     * @return validation result
     */
    <T> boolean isExportEntityInvalid(final T t) {
        return t == null;
    }

    /**
     * Validate export arguments
     *
     * @param t class to validate
     * @return validation result
     */
    <T> boolean isExportEntityInvalid(final List<T> t) {
        return t == null || t.isEmpty() || isExportEntityInvalid(t.get(0));
    }

    public abstract <T> boolean export(final T t);

    public abstract <T> boolean export(final List<T> t);

    public abstract <T> String exportAsString(final T t);

    public abstract <T> String exportAsString(final List<T> list);
}
