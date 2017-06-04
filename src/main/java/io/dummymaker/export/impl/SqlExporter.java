package io.dummymaker.export.impl;

import io.dummymaker.export.ExportType;
import io.dummymaker.export.OriginExporter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends OriginExporter<T> {

    /**
     * Java & Sql Type Representation
     */
    private enum DataType {
        LONG    ("BIGINT",  Long.class.getName()),
        DOUBLE  ("DOUBLE",  Double.class.getName()),
        STRING  ("VARCHAR", String.class.getName()),
        INTEGER ("INT",     Integer.class.getName()),
        LOCAL_DATE_TIME("TIMESTAMP", LocalDateTime.class.getName());

        DataType(String sql, String java) {
            this.sql = sql;
            this.java = java;
        }

        private String sql;
        private String java;

        public String getSql() {
            return sql;
        }

        public String getJava() {
            return java;
        }
    }

    public SqlExporter(Class<T> primeClass) {
        super(primeClass, ExportType.SQL);
    }

    public SqlExporter(Class<T> primeClass, String path) {
        super(primeClass, path, ExportType.SQL);
    }

    /**
     * convert Java Field Type to Sql Data Type
     */
    private String toSqlDataType(String fieldName) {
        String fieldType = fieldsToExport.get(fieldName).getType().getName();

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
    private String sqlTableCreate(T t) {
        Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ").append(t.getClass().getSimpleName()).append("(\n");

        while (iterator.hasNext()) {
            Map.Entry<String, String> field = iterator.next();

            builder.append("\t").append(sqlCreateInsert(field));

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
    private String sqlCreateInsert(Map.Entry<String, String> field) {
        return field.getKey() + "\t" + toSqlDataType(field.getKey());
    }

    private String wrapWithComma(String value) {
        return "'" + value + "'";
    }

    /**
     * Insert query
     */
    private String sqlInsertIntoQuery(T t) {
        Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();
        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO ").append(primeClass.getSimpleName()).append(" (");

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
    private StringBuilder sqlValuesInsert(T t) {
        StringBuilder builder = new StringBuilder();

        Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();

        if(iterator.hasNext()) {
            builder.append("(");

            while (iterator.hasNext()) {
                Map.Entry<String, String> field = iterator.next();

                if(fieldsToExport.get(field.getKey()).getType().equals(String.class))
                    builder.append(wrapWithComma(field.getValue()));
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
    public void export(T t) {
        try {
            writeLine(sqlTableCreate(t));
            writeLine(sqlInsertIntoQuery(t));
            writeLine(sqlValuesInsert(t) + ";");
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
        finally {
            try {
                flush();
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }
    }

    @Override
    public void export(List<T> list) {
        try {
            final int max = 950;
            int i = max;

            // Check for nonNull list
            if(list == null || list.isEmpty())
                return;

            Iterator<T> iterator = list.iterator();

            // Create Table Query
            writeLine(sqlTableCreate(list.get(0)));

            while (iterator.hasNext()) {
                T t = iterator.next();

                // Insert Values Query
                if(i == max)
                    writeLine(sqlInsertIntoQuery(t));

                i--;

                StringBuilder valueToWrite = sqlValuesInsert(t);

                if(iterator.hasNext() && i != 0)
                    valueToWrite.append(",");

                writeLine(valueToWrite.toString());

                // End insert Query if no elements left or need to organize next batch
                if(i == 0 || !iterator.hasNext()) {
                    writeLine(";");

                    if(!iterator.hasNext())
                        break;
                    else {
                        writeLine("\n");
                        i = max;
                    }
                }
            }
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
        finally {
            try {
                flush();
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }
    }
}
