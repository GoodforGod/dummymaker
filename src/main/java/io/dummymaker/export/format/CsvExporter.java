package io.dummymaker.export.format;

import io.dummymaker.export.ExportType;
import io.dummymaker.export.IAbstractExporter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class CsvExporter<T> extends IAbstractExporter<T> {

    private final char DEFAULT_SEPARATOR = ';';

    public CsvExporter(Class<T> primeClass) throws IOException {
        super(primeClass, "~/", ExportType.JSON);
    }

    public CsvExporter(Class<T> primeClass, char separator) throws IOException {
        super(primeClass, "~/", ExportType.JSON);
    }

    public CsvExporter(Class<T> primeClass, String path) throws IOException {
        super(primeClass, path, ExportType.JSON);
    }

    public CsvExporter(Class<T> primeClass, String path, char separator) throws IOException {
        super(primeClass, path, ExportType.JSON);
    }

    private String followCVSformat(String value) {
        String result = value;
        if (result.contains("\""))
            result = result.replace("\"", "\"\"");
        return result;

    }

    private void writeLine(Writer w, List<String> values, char separators, char customQuote) {

        boolean first = true;

        //default customQuote is empty
        if (separators == ' ')
            separators = DEFAULT_SEPARATOR;

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first)
                sb.append(separators);

            if (customQuote == ' ')
                sb.append(followCVSformat(value));
            else
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);

            first = false;
        }

        sb.append("\n");

        try {
            w.append(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean export(T t) {

        return false;
    }

    @Override
    public boolean export(List<T> t) {
        t.forEach(this::export);

        return false;
    }
}
