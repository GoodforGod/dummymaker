package io.dummymaker.export.impl;

import io.dummymaker.export.Cases;
import io.dummymaker.export.Format;
import io.dummymaker.export.ICase;
import io.dummymaker.model.GenRules;
import io.dummymaker.model.export.ClassContainer;
import io.dummymaker.model.export.DatetimeFieldContainer;
import io.dummymaker.model.export.ExportContainer;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.writer.IWriter;
import org.jetbrains.annotations.NotNull;

import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Export objects as SQL insert query.
 *
 * @author GoodforGod
 * @see Format
 * @since 25.02.2018
 */
@Named("sql")
@Singleton
public class SqlExporter extends BasicExporter {

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in
     * SQL)
     */
    private static final Integer INSERT_QUERY_LIMIT = 999;

    /**
     * Java & Sql Type Representation Map is used to convert Java Field Data Type to
     * Sql Data Type You can add your specific values here by using constructor with
     * Map'String, String'
     */
    private final Map<Class, String> dataTypes = buildDefaultDataTypeMap();

    public SqlExporter() {
        this(null);
    }

    public SqlExporter(GenRules rules) {
        super(Format.SQL, Cases.DEFAULT.value(), rules);
    }

    /**
     * @param dataTypes map with user custom types for 'dataTypeMap'
     * @return exporter
     */
    public SqlExporter withTypes(Map<Class, String> dataTypes) {
        if (CollectionUtils.isNotEmpty(dataTypes))
            this.dataTypes.putAll(dataTypes);

        return this;
    }

    /**
     * Build exporter with path value
     *
     * @param path path for export file
     * @return exporter
     */
    public SqlExporter withPath(String path) {
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
    public SqlExporter withCase(ICase nameCase) {
        setCase(nameCase);
        return this;
    }

    /**
     * Build default data types
     *
     * @see #dataTypes
     */
    private Map<Class, String> buildDefaultDataTypeMap() {
        final Map<Class, String> typeMap = new HashMap<>(25);
        typeMap.put(boolean.class, "BOOLEAN");
        typeMap.put(Boolean.class, "BOOLEAN");
        typeMap.put(byte.class, "BYTE");
        typeMap.put(Byte.class, "BYTE");
        typeMap.put(short.class, "SMALLINT");
        typeMap.put(Short.class, "SMALLINT");
        typeMap.put(int.class, "INT");
        typeMap.put(Integer.class, "INT");
        typeMap.put(long.class, "BIGINT");
        typeMap.put(Long.class, "BIGINT");
        typeMap.put(float.class, "DOUBLE PRECISION");
        typeMap.put(Float.class, "DOUBLE PRECISION");
        typeMap.put(double.class, "DOUBLE PRECISION");
        typeMap.put(Double.class, "DOUBLE PRECISION");
        typeMap.put(char.class, "CHAR");
        typeMap.put(Character.class, "CHAR");
        typeMap.put(String.class, "VARCHAR");
        typeMap.put(Object.class, "VARCHAR");
        typeMap.put(Time.class, "TIME");
        typeMap.put(LocalTime.class, "TIME");
        typeMap.put(LocalDate.class, "DATE");
        typeMap.put(Date.class, "DATETIME");
        typeMap.put(java.util.Date.class, "DATETIME");
        typeMap.put(Timestamp.class, "TIMESTAMP");
        typeMap.put(LocalDateTime.class, "TIMESTAMP");
        return typeMap;
    }

    private String wrapWithComma(String value) {
        return "'" + value + "'";
    }

    /**
     * Convert Java Field Type to Sql Data Type
     *
     * @param exportFieldType final field name
     * @return sql data type
     */
    private String translateJavaTypeToSqlType(Class<?> exportFieldType) {
        return dataTypes.getOrDefault(exportFieldType, "VARCHAR");
    }

    /**
     * Create String of Create Table Query
     */
    private String buildCreateTableQuery(ClassContainer container, String primaryKeyField) {
        final StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(container.getExportClassName().toLowerCase())
                .append("(\n");

        final String resultValues = container.getFormatSupported(Format.SQL).values().stream()
                .map(fieldContainer -> "\t" + buildInsertNameTypeQuery(fieldContainer.getExportName(), container))
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
    private String buildInsertNameTypeQuery(String finalFieldName, ClassContainer container) {
        final Class<?> exportFieldType = container.getField(finalFieldName).getType();
        final FieldContainer fieldContainer = container.getContainer(finalFieldName);
        switch (fieldContainer.getType()) {
            case DATETIME:
                final String dateType = (fieldContainer instanceof DatetimeFieldContainer
                        && ((DatetimeFieldContainer) fieldContainer).isUnixTime())
                                ? "BIGINT"
                                : translateJavaTypeToSqlType(exportFieldType);

                if (String.class.equals(container.getField(finalFieldName).getType()))
                    return finalFieldName + "\t" + "VARCHAR";
                return finalFieldName + "\t" + dateType;
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
    private <T> String buildInsertQuery(T t, ClassContainer container) {
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
    private <T> String format(T t, ClassContainer container) {
        final List<ExportContainer> exportContainers = extractExportContainers(t, container);

        final String resultValues = exportContainers.stream()
                .map(c -> convertFieldValue(container.getField(c.getExportName()), c))
                .collect(Collectors.joining(", "));

        return "(" + resultValues + ")";
    }

    /**
     * Search for primary key entity candidate field and retrieve its export field
     * name
     * <p>
     * Primary key is field that (in that order): - Have ID or UID name (case
     * ignored) - Is marked with enumerate gen annotation - Randomly selected
     */
    private String buildPrimaryKey(ClassContainer container) {
        final Map<Field, FieldContainer> containerMap = container.getFormatSupported(Format.SQL);

        for (Map.Entry<Field, FieldContainer> entry : containerMap.entrySet()) {
            if (entry.getValue().isSequential()
                    || entry.getKey().getName().equalsIgnoreCase("id")
                    || entry.getKey().getName().equalsIgnoreCase("uid")) {
                return entry.getValue().getExportName();
            }
        }

        return containerMap.entrySet().iterator().next().getValue().getExportName();
    }

    /**
     * Check data types for field class compatibility with Timestamp class
     */
    private boolean isTypeTimestampConvertible(Field field) {
        final String sqlType = translateJavaTypeToSqlType(field.getType());
        return ("TIMESTAMP".equals(sqlType)
                || "DATETIME".equals(sqlType)
                || "DATE".equals(sqlType)
                || "TIME".equals(sqlType));
    }

    /**
     * Convert container value to Sql specific value type
     */
    private String convertFieldValue(Field field, ExportContainer container) {
        if (container.getType() == FieldContainer.Type.ARRAY
                || container.getType() == FieldContainer.Type.ARRAY_2D
                || container.getType() == FieldContainer.Type.COLLECTION) {

            final Class<?> componentType = extractType(container.getType(), field);
            final String sqlType = translateJavaTypeToSqlType(componentType);
            final String result = (sqlType.equals("VARCHAR") || sqlType.equals("CHAR"))
                    ? container.getExportValue().replace("[", "{\"").replace("]", "\"}").replace(",", "\",\"").replace(" ", "")
                    : container.getExportValue().replace("[", "{").replace("]", "}");
            return wrapWithComma(result);
        } else if (isTypeTimestampConvertible(field)) {
            return wrapWithComma(container.getExportValue());
        } else if (String.class.equals(field.getType()) || "VARCHAR".equals(translateJavaTypeToSqlType(field.getType()))) {
            return wrapWithComma(container.getExportValue());
        }

        return container.getExportValue();
    }

    private Class<?> extractType(FieldContainer.Type type, Field field) {
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

    @Override
    public <T> boolean export(T t) {
        if (isExportEntityInvalid(t))
            return false;

        final ClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return false;

        final IWriter writer = getWriter(container);
        final String primaryKey = buildPrimaryKey(container);
        return writer != null
                && writer.write(buildCreateTableQuery(container, primaryKey) + "\n")
                && writer.write(buildInsertQuery(t, container))
                && writer.write(format(t, container) + ";")
                && writer.flush();
    }

    @Override
    public <T> boolean export(Collection<T> collection) {
        if (isExportEntityInvalid(collection))
            return false;

        if (isExportEntitySingleList(collection))
            return export(collection.iterator().next());

        final ClassContainer container = buildClassContainer(collection);
        if (!container.isExportable())
            return false;

        final IWriter writer = getWriter(container);
        if (writer == null)
            return false;

        int i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = collection.iterator();
        final String primaryKey = buildPrimaryKey(container);

        // Create Table Query
        if (!writer.write(buildCreateTableQuery(container, primaryKey)))
            return false;

        final StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            final T t = iterator.next();
            if (i == INSERT_QUERY_LIMIT) {
                builder.append("\n").append(buildInsertQuery(t, container));
            }

            builder.append(format(t, container));

            // End insert Query if no elements left or need to organize next batch
            final boolean hasNext = iterator.hasNext();
            if (i <= 0 || !hasNext) {
                builder.append(";\n");
            } else {
                builder.append(",\n");
            }

            i = nextInsertValue(i);
        }

        return writer.write(builder.toString()) && writer.flush();
    }

    @Override
    public <T> @NotNull String convert(T t) {
        if (isExportEntityInvalid(t))
            return "";

        final ClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        final String primaryKey = buildPrimaryKey(container);
        return buildCreateTableQuery(container, primaryKey) + "\n"
                + buildInsertQuery(t, container)
                + format(t, container) + ";";
    }

    @Override
    public <T> @NotNull String convert(Collection<T> collection) {
        if (isExportEntityInvalid(collection))
            return "";

        if (isExportEntitySingleList(collection))
            return convert(collection.iterator().next());

        final ClassContainer container = buildClassContainer(collection);
        if (!container.isExportable())
            return "";

        // Create Table Query
        final String primaryKey = buildPrimaryKey(container);
        final StringBuilder builder = new StringBuilder(buildCreateTableQuery(container, primaryKey));
        final Iterator<T> iterator = collection.iterator();
        int i = INSERT_QUERY_LIMIT;

        while (iterator.hasNext()) {
            final T t = iterator.next();
            if (i == INSERT_QUERY_LIMIT) {
                builder.append("\n").append(buildInsertQuery(t, container));
            }

            builder.append(format(t, container));

            // End insert Query if no elements left or need to organize next batch
            if (i <= 0 || !iterator.hasNext()) {
                builder.append(";\n");
            } else {
                builder.append(",\n");
            }

            i = nextInsertValue(i);
        }

        return builder.toString();
    }

    private int nextInsertValue(int current) {
        return (current <= 0)
                ? INSERT_QUERY_LIMIT
                : current - 1;
    }
}
