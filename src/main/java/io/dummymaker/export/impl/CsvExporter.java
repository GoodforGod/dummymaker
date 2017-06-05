package io.dummymaker.export.impl;

import io.dummymaker.export.ExportType;
import io.dummymaker.export.OriginExporter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class CsvExporter<T> extends OriginExporter<T> {

    private final char DEFAULT_SEPARATOR = ',';

    private char SEPARATOR = DEFAULT_SEPARATOR;

    //<editor-fold desc="Constructors">

    public CsvExporter(Class<T> primeClass) {
        this(primeClass, null, ' ');
    }

    public CsvExporter(Class<T> primeClass, char separator) {
        this(primeClass, null, separator);
    }

    public CsvExporter(Class<T> primeClass, String path) {
        this(primeClass, path, ' ');
    }

    public CsvExporter(Class<T> primeClass, String path, char separator) {
        super(primeClass, path, ExportType.CSV);
        SEPARATOR = (separator == ' ') ? DEFAULT_SEPARATOR : separator;
    }

    //</editor-fold>

    private String objectToCsv(T t) {
        StringBuilder builder = new StringBuilder("");

        Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, String> obj = iterator.next();
            builder.append(obj.getValue());

            if (iterator.hasNext())
                builder.append(SEPARATOR);
        }

        return builder.toString();
    }

    @Override
    public void export(T t) {
        try {
            writeLine(objectToCsv(t));
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        finally {
            try {
                flush();
            } catch (IOException e) {
                logger.warning(e.getMessage() + " | CAN NOT FLUSH FILE WRITER");
            }
        }
    }

    @Override
    public void export(List<T> t) {
        t.forEach(obj -> {
            try {
                writeLine(objectToCsv(obj));
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        });
        try {
            flush();
        } catch (IOException e) {
            logger.warning(e.getMessage() + " | CAN NOT FLUSH FILE WRITER");
        }
    }
}
