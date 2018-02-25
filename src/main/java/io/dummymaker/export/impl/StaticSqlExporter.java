package io.dummymaker.export.impl;

import io.dummymaker.export.Format;
import io.dummymaker.export.container.IClassContainer;
import io.dummymaker.export.container.impl.ExportContainer;
import io.dummymaker.export.container.impl.FieldContainer;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.impl.DefaultStrategy;
import io.dummymaker.writer.IWriter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.dummymaker.util.BasicDateUtils.*;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class StaticSqlExporter extends BasicStaticExporter {

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private final Integer INSERT_QUERY_LIMIT = 999;

    /**
     * Java & Sql Type Representation
     * <p>
     * Map is used to convert Java Field Data Type to Sql Data Type
     * <p>
     * You can add your specific values here by using constructor with Map'String, String'
     */
    private Map<Class, String> dataTypes;

    public StaticSqlExporter() {
        super(null, Format.SQL, new DefaultStrategy());
        this.dataTypes = buildDefaultDataTypeMap();
    }

    public StaticSqlExporter(Map<Class, String> dataTypes) {
        super(null, Format.SQL, new DefaultStrategy());
        final Map<Class, String> dataTypesTemp = buildDefaultDataTypeMap();
        if (dataTypes == null || dataTypes.isEmpty()) {
            this.dataTypes = dataTypesTemp;
        } else {
            dataTypes.entrySet().addAll(dataTypesTemp.entrySet());
            this.dataTypes = dataTypes;
        }
    }

    public StaticSqlExporter withPath(String path) {
        setPath(path);
        return this;
    }

    public StaticSqlExporter withStrategy(IStrategy strategy) {
        setStrategy(strategy);
        return this;
    }

    private HashMap<Class, String> buildDefaultDataTypeMap() {
        return new HashMap<Class, String>() {{
            put(Long.class, "BIGINT");
            put(Double.class, "DOUBLE PRECISION");
            put(String.class, "VARCHAR");
            put(Integer.class, "INT");
            put(LocalDateTime.class, "TIMESTAMP");
            put(Timestamp.class, "TIMESTAMP");
            put(LocalDate.class, "TIMESTAMP");
            put(LocalTime.class, "TIMESTAMP");
            put(Date.class, "TIMESTAMP");
        }};
    }

    /**
     * Convert Java Field Type to Sql Data Type
     *
     * @param exportFieldName final field name
     * @return sql data type
     */
    private String javaToSqlDataType(final String exportFieldName, IClassContainer container) {
        return dataTypes.getOrDefault(container.getField(exportFieldName).getType(), "VARCHAR");
    }

    /**
     * Create String of Create Table Query
     */
    private String sqlTableCreate(IClassContainer container) {
        String primaryKeyField = "";
        final StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(container.exportClassName().toLowerCase()).append("(\n");

        for(Map.Entry<String, FieldContainer> entry : container.getFieldContainers().entrySet()) {
            final String exportFieldName = entry.getValue().getExportName();
            builder.append("\t").append(sqlCreateInsertNameType(exportFieldName, container));

            if (exportFieldName.equalsIgnoreCase("id"))
                primaryKeyField = exportFieldName;

            builder.append(",").append("\n");
        }

        // Write primary key constraint
        builder.append("\t")
                .append("PRIMARY KEY (");

        if (primaryKeyField.isEmpty())
            builder.append(container.getFieldContainers().values().iterator().next().getExportName());
        else
            builder.append(primaryKeyField);

        builder.append(")")
                .append("\n")
                .append(");\n");

        return builder.toString();
    }

    /**
     * Creates String of Create Table Insert Field
     *
     * @param finalFieldName final field name
     * @return sql create table (name - type)
     */
    private String sqlCreateInsertNameType(final String finalFieldName, IClassContainer container) {
        return finalFieldName + "\t" + javaToSqlDataType(finalFieldName, container);
    }

    private String wrapWithComma(final String value) {
        return "'" + value + "'";
    }

    /**
     * Build insert query part with values
     */
    private <T> String sqlInsertIntoQuery(final T t, IClassContainer container) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);
        final StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(container.exportClassName().toLowerCase()).append(" (");

        final String names = exportContainers.stream()
                .map(ExportContainer::getExportName)
                .collect(Collectors.joining(", "));

        return builder.append(names)
                .append(") ")
                .append("VALUES ")
                .toString();
    }

    /**
     * Creates insert query field name
     */
    private <T> String format(final T t, final IClassContainer container) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);
        if(exportContainers.isEmpty())
            return "";

        final StringBuilder builder = new StringBuilder("(");
        final String resultValues = exportContainers.stream()
                .map(c -> convertFieldValue(container.getField(c.getExportName()), c))
                .collect(Collectors.joining(", "));

        return builder.append(resultValues)
                .append(")")
                .toString();
    }

    /**
     * Check data types for field class compatibility with Timestamp class
     */
    private boolean isTypeTimestampConvertible(Field field) {
        return dataTypes.entrySet().stream()
                .anyMatch(e -> e.getValue().equals("TIMESTAMP") && e.getKey().equals(field.getType()));
    }

    /**
     * Convert container value to Sql specific value type
     */
    private String convertFieldValue(Field field, ExportContainer container) {
        if (field.getType().equals(String.class))
            return wrapWithComma(container.getExportValue());
        else if (isTypeTimestampConvertible(field))
            return wrapWithComma(String.valueOf(convertFieldValueToTimestamp(field, container)));
        else
            return container.getExportValue();

    }

    /**
     * Convert container export value to timestamp value type
     */
    private Timestamp convertFieldValueToTimestamp(Field field, ExportContainer exportContainer) {
        if (field.getType().equals(LocalDateTime.class)) {
            return convertToTimestamp(parseDateTime(exportContainer.getExportValue()));
        } else if (field.getType().equals(LocalDate.class)) {
            return convertToTimestamp(parseDate(exportContainer.getExportValue()));
        } else if (field.getType().equals(LocalTime.class)) {
            return convertToTimestamp(parseTime(exportContainer.getExportValue()));
        } else if (field.getType().equals(Date.class)) {
            return convertToTimestamp(parseSimpleDate(exportContainer.getExportValue()));
        } else if (field.getType().equals(Timestamp.class)) {
            return Timestamp.valueOf(exportContainer.getExportValue());
        }
        return null;
    }

    @Override
    public <T> boolean export(final T t) {
        if (isExportEntityInvalid(t))
            return false;

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        return writer != null
                && writer.write(sqlTableCreate(container))
                && writer.write(sqlInsertIntoQuery(t, container))
                && writer.write(format(t, container) + ";")
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

        Integer i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = list.iterator();

        // Create Table Query
        writer.write(sqlTableCreate(container));

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                writer.write(sqlInsertIntoQuery(t, container));

            i--;

            final StringBuilder valueToWrite = new StringBuilder(format(t, container));

            if (iterator.hasNext() && i != 0)
                valueToWrite.append(",");
            else if (i == 0 || !iterator.hasNext())
                valueToWrite.append(";");

            writer.write(valueToWrite.toString());

            // End insert Query if no elements left or need to organize next batch
            if (i == 0) {
                if (iterator.hasNext()) {
                    writer.write("\n");
                    i = INSERT_QUERY_LIMIT;
                } else break;
            }
        }
        return writer.flush();
    }

    @Override
    public <T> String exportAsString(final T t) {
        if (isExportEntityInvalid(t))
            return "";

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        return sqlTableCreate(container) + "\n"
                + sqlInsertIntoQuery(t, container) + "\n"
                + format(t, container) + ";";
    }

    @Override
    public <T> String exportAsString(final List<T> list) {
        if (isExportEntityInvalid(list))
            return "";

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return "";

        final StringBuilder result = new StringBuilder();
        Integer i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = list.iterator();

        // Create Table Query
        result.append(sqlTableCreate(container)).append("\n");

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                result.append(sqlInsertIntoQuery(t, container)).append("\n");

            i--;

            final StringBuilder valueToWrite = new StringBuilder(format(t, container));

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
