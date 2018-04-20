package io.dummymaker.export.impl;

import io.dummymaker.container.IClassContainer;
import io.dummymaker.container.impl.ExportContainer;
import io.dummymaker.export.Format;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.writer.IWriter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Export objects is XML format
 *
 * @see Format
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class XmlExporter extends BasicExporter {

    /**
     * Single mode for single T value export
     * List for multiple T values
     */
    private enum Mode {
        SINGLE,
        LIST
    }

    /**
     * Is used with className for XML list tag
     */
    private String exportClassEnding = "List";

    /**
     * Is used instead of class name + ending if set
     *
     * @see #exportClassEnding
     */
    private String exportClassFullName = null;

    public XmlExporter() {
        super(null, Format.XML, Cases.DEFAULT.value());
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     * @return exporter
     */
    public XmlExporter withPath(final String path) {
        setPath(path);
        return this;
    }

    /**
     * Build exporter with naming strategy
     *
     * @see ICase
     * @see Cases
     *
     * @param nameCase naming strategy for exporter
     * @return exporter
     */
    public XmlExporter withCase(final ICase nameCase) {
        setCase(nameCase);
        return this;
    }

    /**
     * @see #exportClassFullName
     * @param fullName full name for XML list tag
     * @return exporter
     */
    public XmlExporter withFullname(final String fullName) {
        this.exportClassFullName = fullName;
        return this;
    }

    /**
     * @see #exportClassEnding
     * @param ending custom ending for XML list tag
     * @return exporter
     */
    public XmlExporter withEnding(final String ending) {
        this.exportClassEnding = ending;
        return this;
    }

    private String wrapOpenXmlTag(final String value) {
        return "<" + value + ">";
    }

    private String wrapCloseXmlTag(final String value) {
        return "</" + value + ">";
    }

    /**
     * Format single T value to JSON format
     *
     * @param mode represent Single JSON object or List of objects
     */
    private <T> String format(final T t,
                              final IClassContainer container,
                              final Mode mode) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);

        final String tabObject = (mode == Mode.SINGLE) ? "" : "\t";

        final StringBuilder builder = new StringBuilder()
                .append(tabObject)
                .append(wrapOpenXmlTag(container.exportClassName()))
                .append("\n");

        final String resultValues = exportContainers.stream()
                .map(c -> buildXmlValue(mode, container.getField(c.getExportName()), c))
                .collect(Collectors.joining("\n"));

        builder.append(resultValues)
                .append("\n");

        return builder.append(tabObject)
                .append(wrapCloseXmlTag(container.exportClassName()))
                .toString();
    }

    private String buildXmlValue(final Mode mode,
                                 final Field field,
                                 final ExportContainer container) {
        return buildXmlSimpleValue(mode, container);
    }

    private String buildXmlSimpleValue(final Mode mode,
                                final ExportContainer container) {
        final String tabField = (mode == Mode.SINGLE) ? "\t" : "\t\t";

        return tabField + wrapOpenXmlTag(container.getExportName())
                + container.getExportValue()
                + wrapCloseXmlTag(container.getExportName());
    }

    /**
     * Build XML list tag determined by fullname status and ending class phrase
     *
     * @see #exportClassEnding
     * @see #exportClassFullName
     *
     * @return XML list tag
     */
    private <T> String buildClassListTag(final T t) {
        return (exportClassFullName != null)
                ? exportClassFullName
                : t.getClass().getSimpleName() + exportClassEnding;
    }

    @Override
    public <T> boolean export(final T t) {
        if (isExportEntityInvalid(t))
            return false;

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        return (writer != null)
                && writer.write(format(t, container, Mode.SINGLE))
                && writer.flush();
    }

    @Override
    public <T> boolean export(final List<T> list) {
        if (isExportEntityInvalid(list))
            return false;

        if(isExportEntitySingleList(list))
            return export(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        final String classListTag = buildClassListTag(list.get(0));
        if (!writer.write(wrapOpenXmlTag(classListTag) + "\n"))
            return false;

        final boolean writerHadErrors = list.stream()
                .anyMatch(t -> !writer.write(format(t, container, Mode.LIST) + "\n"));

        return !writerHadErrors
                && writer.write(wrapCloseXmlTag(classListTag))
                && writer.flush();
    }

    @Override
    public <T> String exportAsString(final T t) {
        if (isExportEntityInvalid(t))
            return "";

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        return format(t, container, Mode.SINGLE);
    }

    @Override
    public <T> String exportAsString(final List<T> list) {
        if (isExportEntityInvalid(list))
            return "";

        if(isExportEntitySingleList(list))
            return exportAsString(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return "";

        final String classListTag = buildClassListTag(list.get(0));

        final StringBuilder builder = new StringBuilder(wrapOpenXmlTag(classListTag))
                .append("\n");

        final String result = list.stream()
                .map(t -> format(t, container, Mode.LIST))
                .collect(Collectors.joining("\n"));

        return builder.append(result)
                .append("\n")
                .append(wrapCloseXmlTag(classListTag))
                .toString();
    }
}
