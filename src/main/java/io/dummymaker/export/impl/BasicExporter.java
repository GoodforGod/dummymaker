package io.dummymaker.export.impl;

import io.dummymaker.container.IClassContainer;
import io.dummymaker.container.impl.ClassContainer;
import io.dummymaker.container.impl.ExportContainer;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.util.BasicCollectionUtils;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.BufferedFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    private ICase caseUsed;

    /**
     * @param caseUsed naming strategy
     * @param path     path where to export (NULL IF HOME DIR)
     * @param format   export format
     *
     * @see ICase
     * @see Format
     */
    BasicExporter(final String path,
                  final Format format,
                  final ICase caseUsed) {
        setPath(path);
        this.format = format;
        this.caseUsed = caseUsed;
    }

    void setPath(final String path) {
        this.path = (isBlank(path))
                ? null
                : path;
    }

    void setCase(final ICase caseUsed) {
        if(caseUsed != null)
            this.caseUsed = caseUsed;
    }

    /**
     * Build class container with export entity parameters
     */
    <T> IClassContainer buildClassContainer(final T t) {
        return new ClassContainer(t, caseUsed);
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

                final String exportFieldName = value.getExportName();
                final Object exportFieldValue = value.getField().get(t);

                exports.add(buildContainer(exportFieldName, exportFieldValue));

                value.getField().setAccessible(false);
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        });

        return exports;
    }

    ExportContainer buildContainer(final String exportFieldName,
                                   final Object exportFieldValue) {
        if(exportFieldValue == null)
           return ExportContainer.buildValue(exportFieldName, "");

        if (exportFieldValue.getClass().equals(Date.class))
            return ExportContainer.buildValue(exportFieldName, String.valueOf(((Date) exportFieldValue).getTime()));

        if(this.format == Format.JSON) {
            if (exportFieldValue.getClass().isAssignableFrom(Collection.class)) {
                final ExportContainer container = ExportContainer.buildCollection(exportFieldName);
            }

            final IClassContainer classContainer = buildClassContainer(exportFieldValue.getClass());
            if(classContainer.isExportable()) {
                final List<ExportContainer> embeddedContainers = extractExportContainers(exportFieldValue, classContainer);
            }
        }


        return ExportContainer.buildValue(exportFieldName, String.valueOf(exportFieldValue));
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
