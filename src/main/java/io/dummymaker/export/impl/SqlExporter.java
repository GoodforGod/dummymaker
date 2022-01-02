package io.dummymaker.export.impl;

import io.dummymaker.model.export.DateFieldContainer;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.writer.IWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.7.2020
 */
public class SqlExporter extends BaseExporter {

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private static final Integer INSERT_QUERY_LIMIT = 999;

    /**
     * Java & Sql Type Representation Map is used to convert Java Field Data Type to Sql Data Type You
     * can add your specific values here by using constructor with Map'String, String'
     */
    private final Map<Class<?>, String> dataTypes = buildDefaultDataTypeMap();

    public SqlExporter() {
        super();
    }

    public SqlExporter(@NotNull Function<String, IWriter> writerFunction) {
        super(writerFunction);
    }

    @Override
    protected @NotNull String getExtension() {
        return "sql";
    }

    /**
     * Build default data types
     *
     * @see #dataTypes
     */
    private Map<Class<?>, String> buildDefaultDataTypeMap() {
        final Map<Class<?>, String> typeMap = new HashMap<>(35);
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
        typeMap.put(UUID.class, "UUID");
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

    /**
     * @param dataTypes map with user custom types for 'dataTypeMap'
     * @return exporter
     */
    public SqlExporter withTypes(Map<Class<?>, String> dataTypes) {
        if (CollectionUtils.isNotEmpty(dataTypes))
            this.dataTypes.putAll(dataTypes);

        return this;
    }

    private String wrap(String value) {
        return "'" + value + "'";
    }

    private <T> String getCollectionName(T t) {
        return naming.format(t.getClass().getSimpleName()).toLowerCase();
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
     * Creates String of Create Table Insert Query
     *
     * @return sql create table (name - type)
     */
    private String translateContainerToSqlType(FieldContainer container) {
        final String field = container.getExportName(naming);
        final Class<?> fieldType = extractType(container.getType(), container.getField());
        switch (container.getType()) {
            case DATE:
                final String dateType = (container instanceof DateFieldContainer && ((DateFieldContainer) container).isUnixTime())
                        ? "BIGINT"
                        : translateJavaTypeToSqlType(fieldType);

                return field + "\t" + dateType;
            case ARRAY:
            case COLLECTION:
                return field + "\t" + translateJavaTypeToSqlType(fieldType) + "[]";
            case ARRAY_2D:
                return field + "\t" + translateJavaTypeToSqlType(fieldType) + "[][]";
            default:
                return field + "\t" + translateJavaTypeToSqlType(fieldType);
        }
    }

    /**
     * Build insert query part with values
     */
    private <T> String buildInsertQuery(T t, Collection<FieldContainer> containers) {
        final String collectionName = getCollectionName(t);
        final StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(collectionName)
                .append(" (");

        final String names = containers.stream()
                .map(c -> c.getExportName(naming))
                .collect(Collectors.joining(", "));

        return builder.append(names)
                .append(") ")
                .append("VALUES\n")
                .toString();
    }

    private String getPrimaryField(Collection<FieldContainer> containers) {
        final Pattern pattern = Pattern.compile("id|[gu]?uid");
        if (containers.isEmpty())
            return "";

        return containers.stream()
                .filter(FieldContainer::isSequential)
                .map(FieldContainer::getExportName)
                .findFirst()
                .orElseGet(() -> containers.stream()
                        .filter(c -> pattern.matcher(c.getExportName()).matches())
                        .map(FieldContainer::getExportName)
                        .findFirst()
                        .orElseGet(() -> containers.iterator().next().getExportName()));
    }

    @Override
    protected String convertArray(Object array) {
        final Class<?> type = array.getClass().getComponentType();
        final String sqlType = translateJavaTypeToSqlType(type);

        final String value = super.convertArray(array);
        final String result = (sqlType.equals("VARCHAR") || sqlType.equals("CHAR"))
                ? value.replace("[", "{\"").replace("]", "\"}").replace(",", "\",\"").replace(" ", "")
                : value.replace("[", "{").replace("]", "}");

        return wrap(result);
    }

    @Override
    protected String convertCollection(Collection<?> collection) {
        return convertArray(collection.toArray());
    }

    @Override
    protected String convertDate(Object date, String formatterPattern) {
        return wrap(super.convertDate(date, formatterPattern));
    }

    @Override
    protected String convertString(String s) {
        return wrap(super.convertString(s));
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
                return field.getType();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected Predicate<FieldContainer> filter() {
        return c -> c.getType() == FieldContainer.Type.STRING
                || c.getType() == FieldContainer.Type.BOOLEAN
                || c.getType() == FieldContainer.Type.NUMBER
                || c.getType() == FieldContainer.Type.DATE
                || c.getType() == FieldContainer.Type.SEQUENTIAL
                || c.getType() == FieldContainer.Type.ARRAY
                || c.getType() == FieldContainer.Type.ARRAY_2D
                || c.getType() == FieldContainer.Type.COLLECTION;
    }

    @Override
    protected String convertNull() {
        return "NULL";
    }

    @Override
    protected @NotNull <T> String head(T t, Collection<FieldContainer> containers, boolean isCollection) {
        final String collectionName = getCollectionName(t);
        final StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(collectionName)
                .append("(\n");

        final String resultValues = containers.stream()
                .map(c -> "\t" + translateContainerToSqlType(c))
                .collect(Collectors.joining(",\n"));

        builder.append(resultValues);

        final String primaryKeyField = getPrimaryField(containers);

        // Write primary key constraint
        return builder.append(",\n")
                .append("\tPRIMARY KEY (")
                .append(naming.format(primaryKeyField))
                .append(")\n);\n")
                .toString();
    }

    @Override
    protected @NotNull <T> String prefix(T t, Collection<FieldContainer> containers) {
        return buildInsertQuery(t, containers);
    }

    @Override
    protected @NotNull <T> String suffix(T t, Collection<FieldContainer> containers) {
        return ";";
    }

    @Override
    protected @NotNull <T> String map(T t, Collection<FieldContainer> containers) {
        final String resultValues = containers.stream()
                .map(c -> getValue(t, c))
                .collect(Collectors.joining(", "));

        return "(" + resultValues + ")";
    }

    @Override
    public <T> boolean export(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return false;

        final T t = collection.iterator().next();
        final List<FieldContainer> containers = scan(t.getClass()).collect(Collectors.toList());
        if (containers.isEmpty())
            return false;

        final IWriter writer = getWriter(t.getClass().getSimpleName());

        // Create Table Query
        if (!writer.write(head(t, containers, true)))
            return false;

        int i = INSERT_QUERY_LIMIT;
        StringBuilder builder = new StringBuilder();

        final Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final T next = iterator.next();
            if (i == INSERT_QUERY_LIMIT)
                builder.append(buildInsertQuery(next, containers));

            builder.append(map(next, containers));

            // End insert Query if no elements left or need to organize next batch
            final boolean hasNext = iterator.hasNext();
            if (i <= 0 || !hasNext) {
                builder.append(";\n");
                if (!writer.write(builder.toString()))
                    return false;

                builder = new StringBuilder();
            } else {
                builder.append(",\n");
            }

            i = nextInsertValue(i);
        }

        return writer.write(builder.toString());
    }

    @Override
    public @NotNull <T> String convert(@NotNull Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return "";

        final T t = collection.iterator().next();
        final List<FieldContainer> containers = scan(t.getClass()).collect(Collectors.toList());
        if (containers.isEmpty())
            return "";

        // Create Table Query
        final StringBuilder builder = new StringBuilder(head(t, containers, true));
        final Iterator<T> iterator = collection.iterator();
        int i = INSERT_QUERY_LIMIT;

        while (iterator.hasNext()) {
            final T next = iterator.next();
            if (i == INSERT_QUERY_LIMIT)
                builder.append(buildInsertQuery(next, containers));

            builder.append(map(next, containers));

            // End insert Query if no elements left or need to organize next batch
            final String suffix = (i <= 0 || !iterator.hasNext())
                    ? ";\n"
                    : ",\n";
            builder.append(suffix);

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
