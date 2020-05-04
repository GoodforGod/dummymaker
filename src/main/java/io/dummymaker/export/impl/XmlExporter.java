package io.dummymaker.export.impl;

import io.dummymaker.export.Cases;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.export.ClassContainer;
import io.dummymaker.model.export.ExportContainer;
import io.dummymaker.writer.IWriter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Export objects is XML format
 *
 * @author GoodforGod
 * @see Format
 * @since 25.02.2018
 */
public class XmlExporter extends BasicExporter {

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
        this(null);
    }

    public XmlExporter(GenRules rules) {
        super(Format.XML, Cases.DEFAULT.value(), rules);
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     * @return exporter
     */
    public XmlExporter withPath(String path) {
        setPath(path);
        return this;
    }

    /**
     * Build exporter with naming strategy
     *
     * @param nameCase naming strategy for exporter
     * @return exporter
     * @see ICase
     * @see Cases
     */
    public XmlExporter withCase(ICase nameCase) {
        setCase(nameCase);
        return this;
    }

    /**
     * @param fullName full name for XML list tag
     * @return exporter
     * @see #exportClassFullName
     */
    public XmlExporter withFullname(String fullName) {
        this.exportClassFullName = fullName;
        return this;
    }

    /**
     * @param ending custom ending for XML list tag
     * @return exporter
     * @see #exportClassEnding
     */
    public XmlExporter withEnding(String ending) {
        this.exportClassEnding = ending;
        return this;
    }

    private String wrapOpenXmlTag(String value) {
        return "<" + value + ">";
    }

    private String wrapCloseXmlTag(String value) {
        return "</" + value + ">";
    }

    /**
     * Format single T value to JSON format
     *
     * @param mode represent Single JSON object or List of objects
     */
    private <T> String format(T t, ClassContainer container, Mode mode) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);

        final String tabObject = (mode == Mode.SINGLE) ? "" : "\t";

        final StringBuilder builder = new StringBuilder()
                .append(tabObject)
                .append(wrapOpenXmlTag(container.getExportClassName()))
                .append("\n");

        final String resultValues = exportContainers.stream()
                .map(c -> buildXmlValue(mode, c))
                .collect(Collectors.joining("\n"));

        builder.append(resultValues)
                .append("\n");

        return builder.append(tabObject)
                .append(wrapCloseXmlTag(container.getExportClassName()))
                .toString();
    }

    private String buildXmlValue(Mode mode, ExportContainer container) {
        return buildXmlSimpleValue(mode, container);
    }

    private String buildXmlSimpleValue(Mode mode, ExportContainer container) {
        final String tabField = (mode == Mode.SINGLE) ? "\t" : "\t\t";

        return tabField + wrapOpenXmlTag(container.getExportName())
                + container.getExportValue()
                + wrapCloseXmlTag(container.getExportName());
    }

    /**
     * Build XML list tag determined by fullname status and ending class phrase
     *
     * @return XML list tag
     * @see #exportClassEnding
     * @see #exportClassFullName
     */
    private <T> String buildClassListTag(T t) {
        return (exportClassFullName == null)
                ? t.getClass().getSimpleName() + exportClassEnding
                : exportClassFullName;
    }

    @Override
    public <T> boolean export(T t) {
        if (isExportEntityInvalid(t))
            return false;

        final ClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        return (writer != null)
                && writer.write(format(t, container, Mode.SINGLE))
                && writer.flush();
    }

    @Override
    public <T> boolean export(List<T> list) {
        if (isExportEntityInvalid(list))
            return false;

        if (isExportEntitySingleList(list))
            return export(list.get(0));

        final ClassContainer container = buildClassContainer(list);
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
    public <T> @NotNull String exportAsString(T t) {
        if (isExportEntityInvalid(t))
            return "";

        final ClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        return format(t, container, Mode.SINGLE);
    }

    @Override
    public <T> @NotNull String exportAsString(List<T> list) {
        if (isExportEntityInvalid(list))
            return "";

        if (isExportEntitySingleList(list))
            return exportAsString(list.get(0));

        final ClassContainer container = buildClassContainer(list);
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

    /**
     * Single mode for single T value export List for multiple T values
     */
    private enum Mode {
        SINGLE,
        LIST
    }
}
