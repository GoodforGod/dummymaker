package io.dummymaker.export.impl;

import io.dummymaker.export.Format;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;
import io.dummymaker.writer.IWriter;

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
public class StaticXmlExporter extends BasicStaticExporter {

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

    public StaticXmlExporter() {
        super(null, Format.XML, PresetStrategies.DEFAULT.getStrategy());
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     */
    public StaticXmlExporter withPath(String path) {
        setPath(path);
        return this;
    }

    /**
     * Build exporter with naming strategy
     *
     * @see IStrategy
     * @see PresetStrategies
     *
     * @param strategy naming strategy for exporter
     */
    public StaticXmlExporter withStrategy(IStrategy strategy) {
        setStrategy(strategy);
        return this;
    }

    /**
     * @see #exportClassFullName
     * @param fullName full name for XML list tag
     */
    public StaticXmlExporter withFullname(String fullName) {
        this.exportClassFullName = fullName;
        return this;
    }

    /**
     * @see #exportClassEnding
     * @param ending custom ending for XML list tag
     */
    public StaticXmlExporter withEnding(String ending) {
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
    private <T> String format(final T t, final IClassContainer container, final Mode mode) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);
        if (exportContainers.isEmpty())
            return "";

        final String tabObject = (mode == Mode.SINGLE) ? "" : "\t";
        final String tabField = (mode == Mode.SINGLE) ? "\t" : "\t\t";

        final StringBuilder builder = new StringBuilder()
                .append(tabObject)
                .append(wrapOpenXmlTag(container.exportClassName()))
                .append("\n");

        final String resultValues = exportContainers.stream()
                .map(c -> tabField + wrapOpenXmlTag(c.getExportName()) + c.getExportValue() + wrapCloseXmlTag(c.getExportName()))
                .collect(Collectors.joining("\n"));

        builder.append(resultValues)
                .append("\n");

        return builder.append(tabObject)
                .append(wrapCloseXmlTag(container.exportClassName()))
                .toString();
    }

    /**
     * Build XML list tag determined by fullname status and ending class phrase
     *
     * @see #exportClassEnding
     * @see #exportClassFullName
     *
     * @return XML list tag
     */
    private <T> String buildClassListTag(T t) {
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

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return "";

        final String classListTag = buildClassListTag(list.get(0));

        final StringBuilder builder = new StringBuilder(wrapOpenXmlTag(classListTag)).append("\n");
        final String result = list.stream()
                .map(t -> format(t, container, Mode.LIST))
                .collect(Collectors.joining());

        return builder.append(result)
                .append(wrapCloseXmlTag(classListTag))
                .toString();
    }
}
