package io.dummymaker.export;

import io.dummymaker.cases.Case;
import io.dummymaker.cases.Cases;
import io.dummymaker.util.CollectionUtils;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public final class SqlExporter extends AbstractExporter {

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private final int batchSize;

    /**
     * Java & Sql Type Representation Map is used to convert Java Field Data Type to Sql Data Type You
     * can add your specific values here by using constructor with Map'String, String'
     */
    private final Map<Class<?>, String> dataTypes;

    private SqlExporter(Case fieldCase,
                        @NotNull Function<String, Writer> writerFunction,
                        int batchSize,
                        Map<Class<?>, String> dataTypes) {
        super(fieldCase, writerFunction);
        this.batchSize = batchSize;
        this.dataTypes = dataTypes;
    }

    public static final class Builder {

        private boolean appendFile = false;
        private Case fieldCase = Cases.DEFAULT.value();
        private Function<String, Writer> writerFunction;
        private int batchSize = 999;
        private final Map<Class<?>, String> dataTypes = buildDefaultDataTypeMap();

        private Builder() {}

        @NotNull
        public Builder appendFile(boolean appendFile) {
            this.appendFile = appendFile;
            return this;
        }

        @NotNull
        public Builder withCase(@NotNull Case fieldCase) {
            this.fieldCase = fieldCase;
            return this;
        }

        @NotNull
        public Builder withWriter(@NotNull Function<String, Writer> writerFunction) {
            this.writerFunction = writerFunction;
            return this;
        }

        @NotNull
        public Builder withBatchSize(int batchSize) {
            if (batchSize > 999 || batchSize < 1) {
                throw new IllegalArgumentException("Batch Size can be from 1 to 999");
            }

            this.batchSize = batchSize;
            return this;
        }

        @NotNull
        public Builder withDataType(@NotNull Class<?> fieldDateType, @NotNull String sqlDataType) {
            this.dataTypes.put(fieldDateType, sqlDataType);
            return this;
        }

        @NotNull
        public Builder withDataTypes(@NotNull Map<Class<?>, String> dataTypes) {
            this.dataTypes.putAll(dataTypes);
            return this;
        }

        @NotNull
        public SqlExporter build() {
            final Function<String, Writer> writer = (writerFunction == null)
                    ? fileName -> new DefaultFileWriter(fileName, appendFile)
                    : writerFunction;

            return new SqlExporter(fieldCase, writer, batchSize, dataTypes);
        }

        private static Map<Class<?>, String> buildDefaultDataTypeMap() {
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
            typeMap.put(BigInteger.class, "BIGINT");
            typeMap.put(BigDecimal.class, "NUMERIC");
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
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull
    public static SqlExporter build() {
        return new Builder().build();
    }

    @Override
    protected @NotNull String getExtension() {
        return "sql";
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
        return fieldCase.apply(t.getClass().getSimpleName()).toLowerCase();
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
    private String translateContainerToSqlType(ExportField container) {
        final String field = container.getName(fieldCase);
        final Class<?> fieldType = extractType(container.getType(), container.getField());
        switch (container.getType()) {
            case DATE:
                final String dateType = (container instanceof TimeExportField && ((TimeExportField) container).isUnixTime())
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
    private <T> String buildInsertQuery(T t, Collection<ExportField> containers) {
        final String collectionName = getCollectionName(t);
        final StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(collectionName)
                .append(" (");

        final String names = containers.stream()
                .map(c -> c.getName(fieldCase))
                .collect(Collectors.joining(", "));

        return builder.append(names)
                .append(") ")
                .append("VALUES\n")
                .toString();
    }

    private String getPrimaryField(Collection<ExportField> containers) {
        final Pattern pattern = Pattern.compile("id|[gu]?uid");
        if (containers.isEmpty())
            return "";

        return containers.stream()
                .filter(ExportField::isSequential)
                .map(ExportField::getName)
                .findFirst()
                .orElseGet(() -> containers.stream()
                        .filter(c -> pattern.matcher(c.getName()).matches())
                        .map(ExportField::getName)
                        .findFirst()
                        .orElseGet(() -> containers.iterator().next().getName()));
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

    private Class<?> extractType(ExportField.Type type, Field field) {
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
    protected Predicate<ExportField> filter() {
        return c -> c.getType() == ExportField.Type.STRING
                || c.getType() == ExportField.Type.BOOLEAN
                || c.getType() == ExportField.Type.NUMBER
                || c.getType() == ExportField.Type.DATE
                || c.getType() == ExportField.Type.SEQUENTIAL
                || c.getType() == ExportField.Type.ARRAY
                || c.getType() == ExportField.Type.ARRAY_2D
                || c.getType() == ExportField.Type.COLLECTION;
    }

    @Override
    protected String convertNull() {
        return "NULL";
    }

    @Override
    protected @NotNull <T> String head(T t, Collection<ExportField> containers, boolean isCollection) {
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
                .append(fieldCase.apply(primaryKeyField))
                .append(")\n);\n")
                .toString();
    }

    @Override
    protected @NotNull <T> String prefix(T t, Collection<ExportField> containers) {
        return buildInsertQuery(t, containers);
    }

    @Override
    protected @NotNull <T> String suffix(T t, Collection<ExportField> containers) {
        return ";";
    }

    @Override
    protected @NotNull <T> String map(T t, Collection<ExportField> containers) {
        final String resultValues = containers.stream()
                .map(c -> getValue(t, c))
                .collect(Collectors.joining(", "));

        return "(" + resultValues + ")";
    }

    @Override
    public <T> boolean exportAsFile(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return false;

        final T t = collection.iterator().next();
        final List<ExportField> containers = scan(t.getClass()).collect(Collectors.toList());
        if (containers.isEmpty())
            return false;

        final Writer writer = getWriter(t.getClass().getSimpleName());

        // Create Table Query
        if (!writer.write(head(t, containers, true)))
            return false;

        int i = batchSize;
        StringBuilder builder = new StringBuilder();

        final Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final T next = iterator.next();
            if (i == batchSize)
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
    public @NotNull <T> String exportAsString(@NotNull Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return "";

        final T t = collection.iterator().next();
        final List<ExportField> containers = scan(t.getClass()).collect(Collectors.toList());
        if (containers.isEmpty())
            return "";

        // Create Table Query
        final StringBuilder builder = new StringBuilder(head(t, containers, true));
        final Iterator<T> iterator = collection.iterator();
        int i = batchSize;

        while (iterator.hasNext()) {
            final T next = iterator.next();
            if (i == batchSize)
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
                ? batchSize
                : current - 1;
    }
}
