package io.dummymaker.export;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.dummymaker.util.NameStrategist.NamingStrategy;

/**
 * Export objects as SQL insert query
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends BaseExporter<T> {

    /**
     * Java & Sql Type Representation
     *
     * Map is used to convert Java Field Data Type to Sql Data Type
     */
    private final Map<String, String> dataTypeMap;

    private HashMap<String, String> buildDefaultDataTypeMap() {
        return new HashMap<String, String>() {{
            put(Long.class.getName(), "BIGINT");
            put(Double.class.getName(), "DOUBLE PRECISION");
            put(String.class.getName(), "VARCHAR");
            put(Integer.class.getName(), "INT");
            put(LocalDateTime.class.getName(), "TIMESTAMP");
        }};
    }

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private final Integer INSERT_QUERY_LIMIT = 995;

    public SqlExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path) {
        this(primeClass, path, NamingStrategy.DEFAULT);
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path,
                       final NamingStrategy strategy) {
        super(primeClass, path, ExportFormat.SQL, strategy);
        this.dataTypeMap = buildDefaultDataTypeMap();
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path,
                       final NamingStrategy strategy,
                       final Map<String, String> dataTypeMap) {
        super(primeClass, path, ExportFormat.SQL, strategy);
        this.dataTypeMap = buildDefaultDataTypeMap();

        if(dataTypeMap != null && !dataTypeMap.isEmpty())
            this.dataTypeMap.putAll(dataTypeMap);
    }

    /**
     * convert Java Field Type to Sql Data Type
     */
    private String javaToSqlDataType(final String fieldName) {
        return dataTypeMap.getOrDefault(classContainer.finalFields().get(fieldName).getType().getName(), "VARCHAR");
    }

    /**
     * Create String of Create Table Query
     */
    private String sqlTableCreate() {
        final StringBuilder builder = new StringBuilder();
        final Iterator<Map.Entry<String, Field>> iterator = classContainer.finalFields().entrySet().iterator();

        builder.append("CREATE TABLE IF NOT EXISTS ").append(classContainer.finalClassName().toLowerCase()).append("(\n");

        while (iterator.hasNext()) {
            final Map.Entry<String, Field> field = iterator.next();
            builder.append("\t").append(sqlCreateInsertNameType(field.getKey()));

            if(field.getKey().equals("id"))
                builder.append(" PRIMARY KEY");

            if(iterator.hasNext())
                builder.append(",");

            builder.append("\n");
        }

        builder.append(");\n");

        return builder.toString();
    }

    /**
     * Creates String of Create Table Insert Field
     */
    private String sqlCreateInsertNameType(final String field) {
        return field + "\t" + javaToSqlDataType(field);
    }

    private String wrapWithComma(final String value) {
        return "'" + value + "'";
    }

    /**
     * Insert query
     */
    private String sqlInsertIntoQuery(final T t) {
        final Iterator<Map.Entry<String, String>> iterator = extractExportValues(t).entrySet().iterator();
        final StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO ").append(classContainer.finalClassName().toLowerCase()).append(" (");

        while (iterator.hasNext()) {
            builder.append(iterator.next().getKey());

            if(iterator.hasNext())
                builder.append(", ");
        }

        builder.append(") ").append("VALUES ");

        return builder.toString();
    }

    /**
     * Creates insert query field name
     */
    private StringBuilder sqlValuesInsert(final T t) {
        final StringBuilder builder = new StringBuilder();

        final Iterator<Map.Entry<String, String>> iterator = extractExportValues(t).entrySet().iterator();

        if(iterator.hasNext()) {
            builder.append("(");

            while (iterator.hasNext()) {
                Map.Entry<String, String> field = iterator.next();

                if(classContainer.finalFields().get(field.getKey()).getType().equals(String.class))
                    builder.append(wrapWithComma(field.getValue()));
                else if(classContainer.finalFields().get(field.getKey()).getType().equals(LocalDateTime.class))
                    builder.append(wrapWithComma(Timestamp.valueOf(LocalDateTime.parse(field.getValue())).toString()));
                else
                    builder.append(field.getValue());

                if (iterator.hasNext())
                    builder.append(", ");
            }

            builder.append(")");
        }

        return builder;
    }

    @Override
    public boolean export(final T t) {
        init();
        return isExportStateValid(t)
                && writeLine(sqlTableCreate())
                && writeLine(sqlInsertIntoQuery(t))
                && writeLine(sqlValuesInsert(t) + ";")
                && flush();
    }

    @Override
    public boolean export(final List<T> list) {
        if (!isExportStateValid(list))
            return false;

        init();

        Integer i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = list.iterator();

        // Create Table Query
        writeLine(sqlTableCreate());

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                writeLine(sqlInsertIntoQuery(t));

            i--;

            final StringBuilder valueToWrite = sqlValuesInsert(t);

            if (iterator.hasNext() && i != 0)
                valueToWrite.append(",");
            else if (i == 0 || !iterator.hasNext())
                valueToWrite.append(";");

            writeLine(valueToWrite.toString());

            // End insert Query if no elements left or need to organize next batch
            if (i == 0) {
                if (iterator.hasNext()) {
                    writeLine("\n");
                    i = INSERT_QUERY_LIMIT;
                } else break;
            }
        }
        return flush();
    }

    @Override
    public String exportAsString(final T t) {
        return (isExportStateValid(t))
                ? sqlTableCreate() + "\n" + sqlInsertIntoQuery(t) + "\n" + sqlValuesInsert(t) + ";"
                : "";
    }

    @Override
    public String exportAsString(final List<T> list) {
        if(!isExportStateValid(list))
            return "";

        final StringBuilder result = new StringBuilder();
        Integer i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = list.iterator();

        // Create Table Query
        result.append(sqlTableCreate()).append("\n");

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                result.append(sqlInsertIntoQuery(t)).append("\n");

            i--;

            final StringBuilder valueToWrite = sqlValuesInsert(t);

            if (iterator.hasNext() && i != 0)
                valueToWrite.append(",");
            else if (i == 0 || !iterator.hasNext())
                valueToWrite.append(";");

            result.append(valueToWrite.toString()).append("\n");

            // End insert Query if no elements left or need to organize next batch
            if (i == 0) {
                if (iterator.hasNext()) {
                    result.append("\n\n");
                    i = INSERT_QUERY_LIMIT;
                } else break;
            }
        }

        return result.toString();
    }
}
