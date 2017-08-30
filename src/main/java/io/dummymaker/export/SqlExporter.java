package io.dummymaker.export;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Export objects as SQL insert query
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends BaseExporter<T> {

    /**
     * Java & Sql Type Representation
     */
    private enum DataType {
        LONG    ("BIGINT",  Long.class.getName()),
        DOUBLE  ("DOUBLE PRECISION",  Double.class.getName()),
        STRING  ("VARCHAR", String.class.getName()),
        INTEGER ("INT",     Integer.class.getName()),
        LOCAL_DATE_TIME("TIMESTAMP", LocalDateTime.class.getName());

        DataType(final String sql, final String java) {
            this.sql = sql;
            this.java = java;
        }

        private final String sql;
        private final String java;

        public String getSql() {
            return sql;
        }

        public String getJava() {
            return java;
        }
    }

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private final Integer INSERT_QUERY_LIMIT = 995;

    public SqlExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public SqlExporter(final Class<T> primeClass, final String path) {
        super(primeClass, path, ExportFormat.SQL);
    }

    /**
     * convert Java Field Type to Sql Data Type
     */
    private String javaToSqlFieldType(final String fieldName) {
        final String fieldType = classContainer.finalFields().get(fieldName).getType().getName();

        if(fieldType != null) {
            if (fieldType.equals(DataType.DOUBLE.getJava()))
                return DataType.DOUBLE.getSql();

            if (fieldType.equals(DataType.INTEGER.getJava()))
                return DataType.INTEGER.getSql();

            if (fieldType.equals(DataType.LOCAL_DATE_TIME.getJava()))
                return DataType.LOCAL_DATE_TIME.getSql();

            if (fieldType.equals(DataType.LONG.getJava()))
                return DataType.LONG.getSql();
        }

        return DataType.STRING.sql;
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
        return field + "\t" + javaToSqlFieldType(field);
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
        return (!isExportStateValid(t))
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
