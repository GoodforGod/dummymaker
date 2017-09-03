package io.dummymaker.export;

import io.dummymaker.export.container.ExportContainer;

import java.util.Iterator;
import java.util.List;

import static io.dummymaker.util.NameStrategist.NamingStrategy;
import static io.dummymaker.util.NameStrategist.NamingStrategy.DEFAULT;

/**
 * Export objects is XML format
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class XmlExporter<T> extends BaseExporter<T> {

    private enum Mode {
        SINGLE,
        LIST
    }

    private final String exportClassListName;

    public XmlExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public XmlExporter(final Class<T> primeClass, final String path) {
        this(primeClass, path, DEFAULT, null);
    }

    public XmlExporter(final Class<T> primeClass, final String path, final NamingStrategy namingStrategy) {
        this(primeClass, path, namingStrategy, null);
    }

    /**
     * @param primeClass export class
     * @param path path where to export, 'null' for project HOME path
     * @param strategy naming strategy
     * @param exportClassListName class top name wrapper for XML file, or default [YourClassName]List
     */
    public XmlExporter(final Class<T> primeClass, final String path, final NamingStrategy strategy, final String exportClassListName) {
        super(primeClass, path, ExportFormat.XML, strategy);
        this.exportClassListName = (exportClassListName == null || exportClassListName.trim().isEmpty())
                ? classContainer.finalClassName() + "List"
                : exportClassListName;
    }

    private String wrapOpenXmlTag(final String value) {
        return "<" + value + ">";
    }

    private String wrapCloseXmlTag(final String value) {
        return "</" + value + ">";
    }

    private String objectToXml(final T t, final Mode mode) {
        final Iterator<ExportContainer> iterator = extractExportValues(t).iterator();

        final StringBuilder builder = new StringBuilder("");

        final String tabObject = (mode == Mode.SINGLE)
                ? ""
                : "\t";

        final String tabField = (mode == Mode.SINGLE)
                ? "\t"
                : "\t\t";

        if(iterator.hasNext()) {
            builder.append(tabObject).append(wrapOpenXmlTag(classContainer.finalClassName()));

            while (iterator.hasNext()) {
                final ExportContainer container = iterator.next();
                builder.append("\n").append(tabField)
                                    .append(wrapOpenXmlTag(container.getFieldName()))
                                    .append(container.getFieldValue())
                                    .append(wrapCloseXmlTag(container.getFieldName()));
            }
            builder.append("\n").append(tabObject).append(wrapCloseXmlTag(classContainer.finalClassName()));
        }

        return builder.toString();
    }

    @Override
    public boolean export(final T t) {
        init();
        return isExportStateValid(t) && writeLine(objectToXml(t, Mode.SINGLE)) && flush();
    }

    @Override
    public boolean export(final List<T> list) {
        if(!isExportStateValid(list))
            return false;

        init();

        writeLine(wrapOpenXmlTag(exportClassListName));

        for (final T t : list)
            writeLine(objectToXml(t, Mode.LIST));

        writeLine(wrapCloseXmlTag(exportClassListName));

        return flush();
    }

    @Override
    public String exportAsString(final T t) {
        return (!isExportStateValid(t))
                ? ""
                : objectToXml(t, Mode.SINGLE);
    }

    @Override
    public String exportAsString(final List<T> list) {
        if(!isExportStateValid(list))
            return "";

        final StringBuilder result = new StringBuilder();
        result.append(wrapOpenXmlTag(exportClassListName)).append("\n");

        for (final T t : list)
            result.append(objectToXml(t, Mode.LIST)).append("\n");

        result.append(wrapCloseXmlTag(exportClassListName));

        return result.toString();
    }
}
