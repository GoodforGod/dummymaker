package io.model.dummymaker.export.impl;

import io.model.dummymaker.export.ExportType;
import io.model.dummymaker.export.OriginExporter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends OriginExporter<T> {

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

    private String toSqlDataType(String fieldName) {
        Optional<String> fieldType = fieldsToExport.stream().filter(field -> field.getName().equals(fieldName)).map(field -> field.getType().getName()).findAny();

        if(fieldType.isPresent()) {
            if (fieldType.get().equals(DataType.DOUBLE.getJava()))
                return DataType.DOUBLE.getSql();

            if (fieldType.get().equals(DataType.INTEGER.getJava()))
                return DataType.INTEGER.getSql();

            if (fieldType.get().equals(DataType.LOCAL_DATE_TIME.getJava()))
                return DataType.LOCAL_DATE_TIME.getSql();

            if (fieldType.get().equals(DataType.LONG.getJava()))
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

            builder.append("'").append(iterator.next().getKey()).append("'");

            if(iterator.hasNext())
                builder.append(", ");
        }

        builder.append(") ").append("VALUES ");

        return builder.toString();
    }

    /**
     * Creates insert query field name
     */
    private String sqlValuesInsert(T t) {
        StringBuilder builder = new StringBuilder();

        Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();

        if(iterator.hasNext()) {
            builder.append("(");

            while (iterator.hasNext()) {
                builder.append(wrapWithComma(iterator.next().getValue()));

                if (iterator.hasNext())
                    builder.append(", ");
            }

            builder.append(")");
        }

        return builder.toString();
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
    public void export(List<T> t) {
        try {
            writeLine(sqlTableCreate(t.get(0)));
            writeLine(sqlInsertIntoQuery(t.get(0)));
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
