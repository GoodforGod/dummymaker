package io.dummymaker.export.impl;

import io.dummymaker.container.IClassContainer;
import io.dummymaker.container.impl.ClassContainer;
import io.dummymaker.container.impl.ExportContainer;
import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.util.BasicCollectionUtils;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.BufferedFileWriter;

import java.io.IOException;
import java.util.*;
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
        if (caseUsed != null)
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
            return new BufferedFileWriter(classContainer.getExportClassName(), path, format.getExtension());
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

        // Using only SIMPLE values containers
        classContainer.getFormatSupported(format).forEach((k, v) -> {
            try {
                k.setAccessible(true);

                final String exportFieldName = v.getExportName();
                final Object exportFieldValue = k.get(t);

                exports.add(buildContainer(exportFieldName, exportFieldValue, v.getType()));

                k.setAccessible(false);
            } catch (Exception ex) {
                logger.warning(ex.getMessage());
            }
        });

        return exports;
    }

    private ExportContainer buildContainer(final String exportFieldName,
                                           final Object exportFieldValue,
                                           final FieldContainer.Type type) {
        if (exportFieldValue == null) {
            return ExportContainer.asValue(exportFieldName, "");
        }

        //TODO Check this in SQL exporter
        if (exportFieldValue.getClass().equals(Date.class))
            return ExportContainer.asValue(exportFieldName, String.valueOf(((Date) exportFieldValue).getTime()));

        if (this.format == Format.JSON) {
            if (FieldContainer.Type.ARRAY.equals(type) && !(exportFieldValue).getClass().getComponentType().isPrimitive()) {
                return ExportContainer.asArray(exportFieldName,
                        Arrays.toString((Object[]) exportFieldValue));
            } else if (FieldContainer.Type.ARRAY_2D.equals(type)) {
                return ExportContainer.asArray2D(exportFieldName,
                        Arrays.deepToString((Object[]) exportFieldValue));
            } else if (FieldContainer.Type.COLLECTION.equals(type)) {
                return ExportContainer.asList(exportFieldName, exportFieldValue.toString());
            } else if (FieldContainer.Type.MAP.equals(type)) {
                return ExportContainer.asMap(exportFieldName, convertAsMap(exportFieldValue));
            }
        }

        return ExportContainer.asValue(exportFieldName, String.valueOf(exportFieldValue));
    }

    private String convertAsMap(Object exportMap) {
        final StringBuilder builder = new StringBuilder("{");
        ((Map) exportMap).forEach((k, v) -> builder.append("\"").append(k).append("\":\"").append(v).append("\","));
        int length = builder.length();
        return builder.toString().substring(0, length - 1) + "}";
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
