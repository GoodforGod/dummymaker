package io.dummymaker.export.impl;

import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.writer.IWriter;

import java.util.Iterator;
import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class StaticXmlExporter extends BasicStaticExporter {

    private enum Mode {
        SINGLE,
        LIST
    }

    private final String exportClassListName;

    private String wrapOpenXmlTag(final String value) {
        return "<" + value + ">";
    }

    private String wrapCloseXmlTag(final String value) {
        return "</" + value + ">";
    }

    private <T> String format(final T t, final IClassContainer container, final Mode mode) {
        final Iterator<ExportContainer> iterator = extractExportContainers(t, container).iterator();

        final StringBuilder builder = new StringBuilder("");

        final String tabObject = (mode == Mode.SINGLE)
                ? ""
                : "\t";

        final String tabField = (mode == Mode.SINGLE)
                ? "\t"
                : "\t\t";

        if(iterator.hasNext()) {
            builder.append(tabObject).append(wrapOpenXmlTag(container.exportClassName()));

            while (iterator.hasNext()) {
                final ExportContainer exportContainer = iterator.next();
                builder.append("\n").append(tabField)
                        .append(wrapOpenXmlTag(exportContainer.getExportName()))
                        .append(exportContainer.getExportValue())
                        .append(wrapCloseXmlTag(exportContainer.getExportName()));
            }
            builder.append("\n").append(tabObject).append(wrapCloseXmlTag(container.exportClassName()));
        }

        return builder.toString();
    }

    @Override
    public <T> boolean export(final T t) {
        if (isExportEntityInvalid(t))
            return false;

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        return writer.write(format(t, container, Mode.SINGLE))
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

        writer.write(wrapOpenXmlTag(exportClassListName));

        for (final T t : list)
            writer.write(format(t, container, Mode.LIST));

        writer.write(wrapCloseXmlTag(exportClassListName));

        return writer.flush();
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

        final StringBuilder result = new StringBuilder();
        result.append(wrapOpenXmlTag(exportClassListName)).append("\n");

        for (final T t : list)
            result.append(format(t, container, Mode.LIST)).append("\n");

        result.append(wrapCloseXmlTag(exportClassListName));

        return result.toString();
    }
}
