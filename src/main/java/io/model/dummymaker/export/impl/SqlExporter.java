package io.model.dummymaker.export.impl;

import io.model.dummymaker.export.ExportType;
import io.model.dummymaker.export.OriginExporter;

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

    private enum DataType {
        LONG    ("BIGINT",  Long.class.getSimpleName()),
        DOUBLE  ("DOUBLE",  Double.class.getSimpleName()),
        STRING  ("VARCHAR", String.class.getSimpleName()),
        INTEGER ("INT",     Integer.class.getSimpleName()),
        LOCAL_DATE_TIME("TIMESTAMP", LocalDateTime.class.getSimpleName());

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

    private String toSqlDataType(String typeName) {
        if(typeName.equals(DataType.DOUBLE.java))
            return DataType.DOUBLE.sql;

        if(typeName.equals(DataType.INTEGER.java))
            return DataType.INTEGER.sql;

        if(typeName.equals(DataType.LOCAL_DATE_TIME.java))
            return DataType.LOCAL_DATE_TIME.sql;

        if(typeName.equals(DataType.LONG.java))
            return DataType.LONG.sql;

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

    /**
     * Insert query
     */
    private String sqlInsertIntoQuery(T t) {
        Iterator<Map.Entry<String, String>> iterator = getExportValues(t).entrySet().iterator();
        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO").append(" '").append(primeClass.getSimpleName()).append("' (");

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
    private String sqlValuesInsert(Map.Entry<String, String> field) {
        return "'" + field.getKey() + "'";
    }

    @Override
    public void export(T t) {
        try {
            writeLine(sqlTableCreate(t));
            writeLine(sqlInsertIntoQuery(t));
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
