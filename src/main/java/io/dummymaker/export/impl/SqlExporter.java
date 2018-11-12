package io.dummymaker.export.impl;

import io.dummymaker.container.IClassContainer;
import io.dummymaker.container.impl.ExportContainer;
import io.dummymaker.container.impl.FieldContainer;
import io.dummymaker.export.Format;
import io.dummymaker.export.naming.Cases;
import io.dummymaker.export.naming.ICase;
import io.dummymaker.writer.IWriter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.dummymaker.util.BasicDateUtils.*;

/**
 * Export objects as SQL insert query.
 *
 * @author GoodforGod
 * @see Format
 * @since 25.02.2018
 */
public class SqlExporter extends BasicExporter {

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private static final Integer INSERT_QUERY_LIMIT = 999;

    /**
     * Java & Sql Type Representation
     * Map is used to convert Java Field Data Type to Sql Data Type
     * You can add your specific values here by using constructor with Map'String, String'
     */
    private Map<Class, String> dataTypes = buildDefaultDataTypeMap();

    public SqlExporter() {
        super(null, Format.SQL, Cases.DEFAULT.value());
    }

    /**
     * @param dataTypes map with user custom types for 'dataTypeMap'
     * @return exporter
     */
    public SqlExporter withTypes(final Map<Class, String> dataTypes) {
        if (dataTypes != null && !dataTypes.isEmpty())
            dataTypes.forEach((key, value) -> this.dataTypes.put(key, value));

        return this;
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     * @return exporter
     */
    public SqlExporter withPath(final String path) {
        setPath(path);
        return this;
    }

    /**
     * Build exporter with naming strategy
     *
     * @param nameCase naming strategy for exporter
     * @return exporter
     * @see ICase
     * @see Cases
     */
    public SqlExporter withCase(final ICase nameCase) {
        setCase(nameCase);
        return this;
    }

    /**
     * Build default data types
     *
     * @see #dataTypes
     */
    private HashMap<Class, String> buildDefaultDataTypeMap() {
        return new HashMap<Class, String>() {{
            put(boolean.class, "BOOLEAN");
            put(Boolean.class, "BOOLEAN");
            put(byte.class, "BYTE");
            put(Byte.class, "BYTE");
            put(short.class, "INT");
            put(Short.class, "INT");
            put(int.class, "INT");
            put(Integer.class, "INT");
            put(long.class, "BIGINT");
            put(Long.class, "BIGINT");
            put(float.class, "DOUBLE PRECISION");
            put(Float.class, "DOUBLE PRECISION");
            put(double.class, "DOUBLE PRECISION");
            put(Double.class, "DOUBLE PRECISION");
            put(char.class, "CHAR");
            put(Character.class, "CHAR");
            put(Date.class, "BIGINT");
            put(String.class, "VARCHAR");
            put(Object.class, "VARCHAR");
            put(Timestamp.class, "TIMESTAMP");
            put(LocalDate.class, "TIMESTAMP");
            put(LocalTime.class, "TIMESTAMP");
            put(LocalDateTime.class, "TIMESTAMP");
        }};
    }

    private String wrapWithComma(final String value) {
        return "'" + value + "'";
    }

    /**
     * Convert Java Field Type to Sql Data Type
     *
     * @param exportFieldType final field name
     * @return sql data type
     */
    private String translateJavaTypeToSqlType(final Class<?> exportFieldType) {
        return dataTypes.getOrDefault(exportFieldType, "VARCHAR");
    }

    /**
     * Create String of Create Table Query
     */
    private String buildCreateTableQuery(final IClassContainer container,
                                         final String primaryKeyField) {
        final StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(container.getExportClassName().toLowerCase())
                .append("(\n");

        final String resultValues = container.getFormatSupported(Format.SQL).entrySet().stream()
                .map(e -> "\t" + buildInsertNameTypeQuery(e.getValue().getExportName(), container))
                .collect(Collectors.joining(",\n"));

        builder.append(resultValues);

        // Write primary key constraint
        return builder.append(",\n")
                .append("\tPRIMARY KEY (")
                .append(primaryKeyField)
                .append(")\n);\n")
                .toString();
    }

    /**
     * Creates String of Create Table Insert Quert
     *
     * @param finalFieldName final field name
     * @return sql create table (name - type)
     */
    private String buildInsertNameTypeQuery(final String finalFieldName,
                                            final IClassContainer container) {
        final Class<?> exportFieldType = container.getField(finalFieldName).getType();
        switch (container.getContainer(finalFieldName).getType()) {
            case ARRAY:
            case COLLECTION:
                final Class<?> type = exportFieldType.getComponentType();
                return finalFieldName + "\t" + translateJavaTypeToSqlType(type) + "[]";
            case ARRAY_2D:
                final Class<?> type2D = exportFieldType.getComponentType().getComponentType();
                return finalFieldName + "\t" + translateJavaTypeToSqlType(type2D) + "[][]";
            default:
                return finalFieldName + "\t" + translateJavaTypeToSqlType(exportFieldType);
        }
    }

    /**
     * Build insert query part with values
     */
    private <T> String buildInsertQuery(final T t,
                                        final IClassContainer container) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);
        final StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(container.getExportClassName().toLowerCase())
                .append(" (");

        final String names = exportContainers.stream()
                .map(ExportContainer::getExportName)
                .collect(Collectors.joining(", "));

        return builder.append(names)
                .append(") ")
                .append("VALUES\n")
                .toString();
    }

    /**
     * Creates insert query field name
     */
    private <T> String format(final T t,
                              final IClassContainer container) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);

        final String resultValues = exportContainers.stream()
                .map(c -> convertFieldValue(container.getField(c.getExportName()), c))
                .collect(Collectors.joining(", "));

        return "(" + resultValues + ")";
    }

    /**
     * Search for primary key entity candidate field and retrieve its export field name
     * <p>
     * Primary key is field that (in that order):
     * - Have ID or UID name (case ignored)
     * - Is marked with enumerate gen annotation
     * - Randomly selected
     */
    private String buildPrimaryKey(final IClassContainer container) {
        final Map<Field, FieldContainer> containerMap = container.getFormatSupported(Format.SQL);

        for (Map.Entry<Field, FieldContainer> entry : containerMap.entrySet()) {
            if (entry.getValue().isEnumerable()) {
                return entry.getValue().getExportName();
            } else if (entry.getKey().getName().equalsIgnoreCase("id")
                    || entry.getKey().getName().equalsIgnoreCase("uid")) {
                return entry.getValue().getExportName();
            }
        }

        return containerMap.entrySet().iterator().next().getValue().getExportName();
    }

    /**
     * Check data types for field class compatibility with Timestamp class
     */
    private boolean isTypeTimestampConvertible(final Field field) {
        return dataTypes.entrySet().stream()
                .anyMatch(e -> e.getValue().equals("TIMESTAMP") && e.getKey().equals(field.getType()));
    }

    /**
     * Convert container value to Sql specific value type
     */
    private String convertFieldValue(final Field field,
                                     final ExportContainer container) {
        final boolean isArray2D = (container.getType() == FieldContainer.Type.ARRAY_2D);
        if (field.getType().equals(String.class)) {
            return wrapWithComma(container.getExportValue());
        } else if (isTypeTimestampConvertible(field)) {
            return wrapWithComma(String.valueOf(convertFieldValueToTimestamp(field, container)));
        } else if (container.getType() == FieldContainer.Type.ARRAY
                || isArray2D
                || container.getType() == FieldContainer.Type.COLLECTION) {

            final Class<?> componentType = extractType(container.getType(), field);
            final String sqlType = dataTypes.getOrDefault(componentType, "VARCHAR");
            final String result = (sqlType.equals("VARCHAR") || sqlType.equals("CHAR"))
                    ? container.getExportValue().replace("[", "{\"").replace("]", "\"}").replace(",", "\",\"").replace(" ", "")
                    : container.getExportValue().replace("[", "{").replace("]", "}");
            return wrapWithComma(result);
        }

        return container.getExportValue();
    }

    private Class<?> extractType(FieldContainer.Type type,
                                 Field field) {
        switch (type) {
            case ARRAY:
                return field.getType().getComponentType();
            case ARRAY_2D:
                return field.getType().getComponentType().getComponentType();
            case COLLECTION:
                return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            default:
                return String.class;
        }
    }

    /**
     * Convert container export value to timestamp value type
     */
    private Timestamp convertFieldValueToTimestamp(final Field field,
                                                   final ExportContainer exportContainer) {
        if (field.getType().equals(LocalDateTime.class)) {
            return convertToTimestamp(parseDateTime(exportContainer.getExportValue()));
        } else if (field.getType().equals(LocalDate.class)) {
            return convertToTimestamp(parseDate(exportContainer.getExportValue()));
        } else if (field.getType().equals(LocalTime.class)) {
            return convertToTimestamp(parseTime(exportContainer.getExportValue()));
        } else if (field.getType().equals(Date.class)) {
            return convertToTimestamp(parseSimpleDateLong(exportContainer.getExportValue()));
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
        final String primaryKey = buildPrimaryKey(container);
        return writer != null
                && writer.write(buildCreateTableQuery(container, primaryKey) + "\n")
                && writer.write(buildInsertQuery(t, container))
                && writer.write(format(t, container) + ";")
                && writer.flush();
    }

    @Override
    public <T> boolean export(final List<T> list) {
        if (isExportEntityInvalid(list))
            return false;

        if (isExportEntitySingleList(list))
            return export(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return false;

        final IWriter writer = buildWriter(container);
        if (writer == null)
            return false;

        int i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = list.iterator();
        final String primaryKey = buildPrimaryKey(container);

        // Create Table Query
        if (!writer.write(buildCreateTableQuery(container, primaryKey)))
            return false;

        while (iterator.hasNext()) {
            final T t = iterator.next();
            final StringBuilder builder = new StringBuilder();
            if (i == INSERT_QUERY_LIMIT) {
                builder.append("\n").append(buildInsertQuery(t, container));
            }

            builder.append(format(t, container));

            // End insert Query if no elements left or need to organize next batch
            final boolean hasNext = iterator.hasNext();
            if (i < 0 || !hasNext) {
                builder.append(";\n");
            } else {
                builder.append(",\n");
            }

            i = nextInsertValue(i);
            writer.write(builder.toString());
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

        final String primaryKey = buildPrimaryKey(container);
        return buildCreateTableQuery(container, primaryKey) + "\n"
                + buildInsertQuery(t, container)
                + format(t, container) + ";";
    }

    @Override
    public <T> String exportAsString(final List<T> list) {
        if (isExportEntityInvalid(list))
            return "";

        if (isExportEntitySingleList(list))
            return exportAsString(list.get(0));

        final IClassContainer container = buildClassContainer(list);
        if (!container.isExportable())
            return "";

        // Create Table Query
        final String primaryKey = buildPrimaryKey(container);
        final StringBuilder builder = new StringBuilder(buildCreateTableQuery(container, primaryKey));
        final Iterator<T> iterator = list.iterator();
        int i = INSERT_QUERY_LIMIT;

        while (iterator.hasNext()) {
            final T t = iterator.next();
            if (i == INSERT_QUERY_LIMIT) {
                builder.append("\n").append(buildInsertQuery(t, container));
            }

            builder.append(format(t, container));

            // End insert Query if no elements left or need to organize next batch
            if (i < 0 || !iterator.hasNext()) {
                builder.append(";\n");
            } else {
                builder.append(",\n");
            }

            i = nextInsertValue(i);
        }

        return builder.toString();
    }

    private int nextInsertValue(final int current) {
        return (current <= 0)
                ? INSERT_QUERY_LIMIT
                : current - 1;
    }
}
