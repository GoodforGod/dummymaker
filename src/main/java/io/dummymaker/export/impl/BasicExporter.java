package io.dummymaker.export.impl;

import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.ClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.util.BasicCollectionUtils;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.BufferedFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static io.dummymaker.util.BasicStringUtils.isBlank;

/**
 * Basic abstract exporter implementation
 *
 * @see IExporter
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
abstract class BasicExporter implements IExporter {

    private final Logger logger = Logger.getLogger(BasicExporter.class.getName());

    private String path;
    private Format format;
    private IStrategy strategy;

    /**
     * @param strategy naming strategy
     * @param path     path where to export (NULL IF HOME DIR)
     * @param format   export format
     *
     * @see IStrategy
     * @see Format
     */
    BasicExporter(final String path,
                  final Format format,
                  final IStrategy strategy) {
        setPath(path);
        this.format = format;
        this.strategy = strategy;
    }

    void setPath(final String path) {
        this.path = (isBlank(path))
                ? null
                : path;
    }

    void setStrategy(final IStrategy strategy) {
        if(strategy != null)
            this.strategy = strategy;
    }

    /**
     * Build class container with export entity parameters
     */
    <T> IClassContainer buildClassContainer(final T t) {
        return new ClassContainer(t, strategy);
    }

    /**
     * Build class container with export entity parameters
     */
    <T> IClassContainer buildClassContainer(final List<T> list) {
        return (BasicCollectionUtils.isNotEmpty(list))
                ? buildClassContainer(list.get(0))
                : null;
    }

    /**
     * Build buffered writer for export
     *
     * @see #export(Object)
     * @see #export(List)
     */
    IWriter buildWriter(final IClassContainer classContainer) {
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
    <T> List<ExportContainer> extractExportContainers(final T t,
                                                      final IClassContainer classContainer) {

        final List<ExportContainer> exports = new ArrayList<>();
        classContainer.getContainers().forEach((key, value) -> {
            try {
                value.getField().setAccessible(true);

                final Object exportField = value.getField().get(t);
                final String exportFieldName = value.getExportName();
                final String exportFieldValue = (exportField.getClass().equals(Date.class))
                        ? String.valueOf(((Date) exportField).getTime())
                        : String.valueOf(exportField);

                exports.add(new ExportContainer(exportFieldName, exportFieldValue));

                value.getField().setAccessible(false);
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
        return (t == null);
    }

    /**
     * Validate export arguments
     *
     * @param t class to validate
     * @return validation result
     */
    <T> boolean isExportEntityInvalid(final List<T> t) {
        return (BasicCollectionUtils.isEmpty(t) || isExportEntityInvalid(t.get(0)));
    }

    /**
     * Validate export arguments
     *
     * @param t class to validate
     * @return validation result
     */
    <T> boolean isExportEntitySingleList(final List<T> t) {
        return (t.size() == 1);
    }

    public abstract <T> boolean export(final T t);

    public abstract <T> boolean export(final List<T> t);

    public abstract <T> String exportAsString(final T t);

    public abstract <T> String exportAsString(final List<T> list);
}
