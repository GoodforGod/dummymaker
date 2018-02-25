package io.dummymaker.export.impl;

import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.container.impl.FieldContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.PresetStrategies;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Export objects as SQL insert query
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends BasicExporter<T> {

    /**
     * Java & Sql Type Representation
     *
     * Map is used to convert Java Field Data Type to Sql Data Type
     *
     * You can add your specific values here by using constructor with Map'String, String'
     */
    private final Map<Class, String> dataTypeMap;

    private HashMap<Class, String> buildDefaultDataTypeMap() {
        return new HashMap<Class, String>() {{
            put(Long.class, "BIGINT");
            put(Double.class, "DOUBLE PRECISION");
            put(String.class, "VARCHAR");
            put(Integer.class, "INT");
            put(LocalDateTime.class, "TIMESTAMP");
        }};
    }

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private final Integer INSERT_QUERY_LIMIT = 995;

    public SqlExporter(final Class<T> primeClass) throws Exception {
        this(primeClass, null);
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path) throws Exception {
        this(primeClass, path, PresetStrategies.DEFAULT.getStrategy());
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path,
                       final IStrategy strategy) throws Exception {
        super(primeClass, path, ExportFormat.SQL, strategy);
        this.dataTypeMap = buildDefaultDataTypeMap();
    }

    /**
     * @param primeClass export class
     * @param path path where to export, 'null' for project HOME path
     * @param strategy naming strategy
     * @param dataTypeMap map with user custom types for 'dataTypeMap'
     *
     * @see PresetStrategies
     */
    public SqlExporter(final Class<T> primeClass,
                       final String path,
                       final IStrategy strategy,
                       final Map<Class, String> dataTypeMap) throws Exception {
        super(primeClass, path, ExportFormat.SQL, strategy);
        this.dataTypeMap = buildDefaultDataTypeMap();

        if(dataTypeMap != null && !dataTypeMap.isEmpty())
            this.dataTypeMap.putAll(dataTypeMap);
    }

    /**
     * Convert Java Field Type to Sql Data Type
     * @param finalFieldName final field name
     * @return sql data type
     */
    private String javaToSqlDataType(final String finalFieldName) {
        return dataTypeMap.getOrDefault(classContainer.getField(finalFieldName).getType(), "VARCHAR");
    }

    /**
     * Create String of Create Table Query
     */
    private String sqlTableCreate() {
        final StringBuilder builder = new StringBuilder();
        final Iterator<Map.Entry<String, FieldContainer>> iterator = classContainer.getFieldContainers().entrySet().iterator();

        String primaryKeyField = "";

        builder.append("CREATE TABLE IF NOT EXISTS ").append(classContainer.exportClassName().toLowerCase()).append("(\n");

        while (iterator.hasNext()) {
            final String finaFieldName = iterator.next().getValue().getExportName();
            builder.append("\t").append(sqlCreateInsertNameType(finaFieldName));

            if (finaFieldName.equalsIgnoreCase("id"))
                primaryKeyField = finaFieldName;

            builder.append(",");

            builder.append("\n");
        }

        // Write primary key constraint
        builder.append("\t").append("PRIMARY KEY (");

        if(primaryKeyField.isEmpty())
            builder.append(classContainer.getFieldContainers().values().iterator().next().getExportName());
        else
            builder.append(primaryKeyField);

        builder.append(")");

        builder.append("\n").append(");\n");

        return builder.toString();
    }

    /**
     * Creates String of Create Table Insert Field
     * @param finalFieldName final field name
     * @return sql create table (name - type)
     */
    private String sqlCreateInsertNameType(final String finalFieldName) {
        return finalFieldName + "\t" + javaToSqlDataType(finalFieldName);
    }

    private String wrapWithComma(final String value) {
        return "'" + value + "'";
    }

    /**
     * Insert query
     */
    private String sqlInsertIntoQuery(final T t) {
        final Iterator<ExportContainer> iterator = extractExportValues(t).iterator();
        final StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO ").append(classContainer.exportClassName().toLowerCase()).append(" (");

        while (iterator.hasNext()) {
            builder.append(iterator.next().getExportName());

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
        final Iterator<ExportContainer> iterator = extractExportValues(t).iterator();
        final StringBuilder builder = new StringBuilder();

        if(iterator.hasNext()) {
            builder.append("(");

            while (iterator.hasNext()) {
                final ExportContainer container = iterator.next();

                final Field fieldType = classContainer.getField(container.getExportName());

                if(fieldType.getType().equals(String.class))
                    builder.append(wrapWithComma(container.getExportValue()));
                else if(fieldType.getType().equals(LocalDateTime.class))
                    builder.append(wrapWithComma(Timestamp.valueOf(LocalDateTime.parse(container.getExportValue())).toString()));
                else
                    builder.append(container.getExportValue());

                if (iterator.hasNext())
                    builder.append(", ");
            }

            builder.append(")");
        }

        return builder;
    }

    @Override
    public boolean export(final T t) {
        return isExportStateValid(t)
                && write(sqlTableCreate())
                && write(sqlInsertIntoQuery(t))
                && write(sqlValuesInsert(t) + ";")
                && flush();
    }

    @Override
    public boolean export(final List<T> list) {
        if (!isExportStateValid(list))
            return false;

        Integer i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = list.iterator();

        // Create Table Query
        write(sqlTableCreate());

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                write(sqlInsertIntoQuery(t));

            i--;

            final StringBuilder valueToWrite = sqlValuesInsert(t);

            if (iterator.hasNext() && i != 0)
                valueToWrite.append(",");
            else if (i == 0 || !iterator.hasNext())
                valueToWrite.append(";");

            write(valueToWrite.toString());

            // End insert Query if no elements left or need to organize next batch
            if (i == 0) {
                if (iterator.hasNext()) {
                    write("\n");
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
