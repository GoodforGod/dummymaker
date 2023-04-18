package io.goodforgod.dummymaker.export;

import io.goodforgod.dummymaker.cases.NamingCase;
import io.goodforgod.dummymaker.cases.NamingCases;
import io.goodforgod.dummymaker.error.GenExportException;
import io.goodforgod.dummymaker.util.CollectionUtils;
import io.goodforgod.dummymaker.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 24.7.2020
 */
public final class SqlExporter extends AbstractExporter {

    private static final Pattern ID_PATTERN = Pattern.compile("id|[gu]?uid");
    private static final Pattern ID_SUFFIX_PATTERN = Pattern.compile("(id$)|([gu]?uid$)");

    /**
     * Insert values limit per single insert query (due to 1000 row insert limit in SQL)
     */
    private final int batchSize;

    /**
     * Java & Sql Type Representation Map is used to convert Java Field Data Type to Sql Data Type You
     * can add your specific values here by using constructor with Map[String, String]
     */
    private final Map<Class<?>, String> dataTypes;

    private SqlExporter(Set<String> fieldsInclude,
                        Set<String> fieldsExclude,
                        NamingCase fieldNamingCase,
                        @NotNull Function<String, Writer> writerFunction,
                        int batchSize,
                        Map<Class<?>, String> dataTypes) {
        super(fieldsInclude, fieldsExclude, fieldNamingCase, writerFunction);
        this.batchSize = batchSize;
        this.dataTypes = dataTypes;
    }

    public static final class Builder {

        private final Set<String> fieldsInclude = new HashSet<>();
        private final Set<String> fieldsExclude = new HashSet<>();
        private NamingCase fieldNamingCase = NamingCases.SNAKE_LOWER_CASE;
        private Function<String, Writer> writerFunction = fileName -> new SimpleFileWriter(false, fileName);
        private int batchSize = 999;
        private final Map<Class<?>, String> dataTypes = buildDefaultDataTypeMap();

        private Builder() {}

        /**
         * @param fieldNamingCase apply to SQL column name
         * @return self
         */
        @NotNull
        public Builder withCase(@NotNull NamingCase fieldNamingCase) {
            this.fieldNamingCase = fieldNamingCase;
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
        public Builder includeFields(@NotNull String... fields) {
            return includeFields(Arrays.asList(fields));
        }

        @NotNull
        public Builder includeFields(@NotNull Collection<String> fields) {
            if (!fieldsExclude.isEmpty()) {
                throw new IllegalStateException("Can't Include Fields when Exclude Fields is present!");
            }

            this.fieldsInclude.addAll(fields);
            return this;
        }

        @NotNull
        public Builder excludeFields(@NotNull String... fields) {
            return excludeFields(Arrays.asList(fields));
        }

        @NotNull
        public Builder excludeFields(@NotNull Collection<String> fields) {
            if (!fieldsInclude.isEmpty()) {
                throw new IllegalStateException("Can't Exclude Fields when Include Fields is present!");
            }

            this.fieldsExclude.addAll(fields);
            return this;
        }

        @NotNull
        public SqlExporter build() {
            return new SqlExporter(fieldsInclude, fieldsExclude, fieldNamingCase, writerFunction, batchSize, dataTypes);
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
            typeMap.put(CharSequence.class, "VARCHAR");
            typeMap.put(UUID.class, "UUID");
            typeMap.put(Object.class, "VARCHAR");
            typeMap.put(Duration.class, "BIGINT");
            typeMap.put(Time.class, "TIME");
            typeMap.put(LocalTime.class, "TIME");
            typeMap.put(LocalDate.class, "DATE");
            typeMap.put(Date.class, "TIMESTAMP");
            typeMap.put(java.util.Date.class, "TIMESTAMP");
            typeMap.put(Timestamp.class, "TIMESTAMP");
            typeMap.put(LocalDateTime.class, "TIMESTAMP");
            typeMap.put(OffsetTime.class, "TIME WITH TIME ZONE");
            typeMap.put(OffsetDateTime.class, "TIMESTAMP WITH TIME ZONE");
            typeMap.put(ZonedDateTime.class, "TIMESTAMP WITH TIME ZONE");
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

    private String wrap(String value) {
        return "'" + value + "'";
    }

    private <T> String getCollectionName(Class<T> type) {
        return fieldNamingCase.apply(type.getSimpleName()).toString();
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
        final String field = container.getName();
        final Class<?> fieldType = extractType(container.getType(), container.getField());
        switch (container.getType()) {
            case DATE:
                final String dateType = translateJavaTypeToSqlType(fieldType);
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
    private <T> String buildInsertQuery(Class<T> type, Collection<ExportField> containers) {
        final String collectionName = getCollectionName(type);
        final StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(collectionName)
                .append("(");

        final String names = containers.stream()
                .map(ExportField::getName)
                .collect(Collectors.joining(", "));

        return builder.append(names)
                .append(") ")
                .append("VALUES\n")
                .toString();
    }

    private String getPrimaryField(Collection<ExportField> containers) {
        return containers.stream()
                .map(ExportField::getName)
                .filter(name -> ID_PATTERN.matcher(name).matches())
                .findFirst()
                .orElseGet(() -> containers.stream()
                        .map(ExportField::getName)
                        .filter(name -> ID_SUFFIX_PATTERN.matcher(name).matches())
                        .findFirst()
                        .orElseGet(() -> containers.stream()
                                .filter(e -> e.getType().equals(ExportField.Type.NUMBER))
                                .map(ExportField::getName)
                                .findFirst()
                                .orElseGet(() -> containers.iterator().next().getName())));
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
    protected String convertDate(Object date, DateExportField formatterPattern) {
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
                || c.getType() == ExportField.Type.ARRAY
                || c.getType() == ExportField.Type.ARRAY_2D
                || c.getType() == ExportField.Type.COLLECTION;
    }

    @Override
    protected String convertNull() {
        return "NULL";
    }

    @Override
    protected @NotNull <T> String head(Class<T> type, Collection<ExportField> containers, boolean isCollection) {
        final String collectionName = getCollectionName(type);
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
                .append(primaryKeyField)
                .append(")\n);\n\n")
                .toString();
    }

    @Override
    protected @NotNull <T> String prefix(Class<T> type, Collection<ExportField> containers) {
        return buildInsertQuery(type, containers);
    }

    @Override
    protected @NotNull <T> String suffix(Class<T> type, Collection<ExportField> containers) {
        return ";";
    }

    @Override
    protected @NotNull <T> String map(T value, Collection<ExportField> containers) {
        final String resultValues = containers.stream()
                .map(c -> getValue(value, c))
                .collect(Collectors.joining(", "));

        return "(" + resultValues + ")";
    }

    @Override
    public void exportAsFile(@NotNull Collection<?> collection) {
        if (collection.isEmpty()) {
            return;
        }

        final Object firstValue = collection.iterator().next();
        try (final Writer writer = getWriter(firstValue.getClass().getSimpleName())) {
            exportAsStringInternal(collection, writer::write);
        } catch (Exception e) {
            throw new GenExportException("Error occurred while exporting due to: ", e);
        }
    }

    @Override
    public <T> void streamToFile(@NotNull Stream<T> stream, Class<T> type) {
        try (final Writer writer = getWriter(type.getSimpleName())) {
            streamToStringInternal(stream, type, writer::write);
        } catch (Exception e) {
            throw new GenExportException("Error occurred while exporting due to: ", e);
        }
    }

    @Override
    public @NotNull String exportAsString(@NotNull Collection<?> collection) {
        final StringBuilder builder = new StringBuilder();
        exportAsStringInternal(collection, builder::append);
        return builder.toString();
    }

    @Override
    public @NotNull <T> String streamToString(@NotNull Stream<T> stream, Class<T> type) {
        final StringBuilder builder = new StringBuilder();
        streamToStringInternal(stream, type, builder::append);
        return builder.toString();
    }

    private void exportAsStringInternal(@NotNull Collection<?> collection, Consumer<String> stringConsumer) {
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }

        final Object firstValue = collection.iterator().next();
        final Class<?> type = firstValue.getClass();
        final List<ExportField> containers = scan(type).collect(Collectors.toList());
        if (containers.isEmpty()) {
            return;
        }

        // Create Table Query
        stringConsumer.accept(head(type, containers, true));
        final Iterator<?> iterator = collection.iterator();
        int i = batchSize;

        while (iterator.hasNext()) {
            final Object next = iterator.next();
            if (i == batchSize) {
                stringConsumer.accept(buildInsertQuery(type, containers));
            }

            stringConsumer.accept(map(next, containers));

            i = i - 1;

            // End insert Query if no elements left or need to organize next batch
            final String suffix = (i <= 0 || !iterator.hasNext())
                    ? ";\n\n"
                    : ",\n";
            stringConsumer.accept(suffix);

            if (i <= 0) {
                i = batchSize;
            }
        }
    }

    private <T> void streamToStringInternal(@NotNull Stream<T> stream, Class<T> type, Consumer<String> stringConsumer) {
        final Collection<ExportField> containers = scan(type).collect(Collectors.toList());
        if (containers.isEmpty()) {
            return;
        }

        final AtomicInteger counter = new AtomicInteger(batchSize);
        final AtomicReference<T> firstValueRef = new AtomicReference<>();

        stream.filter(Objects::nonNull).forEach(value -> {
            synchronized (firstValueRef) {
                final String valueAsString = map(value, containers);
                if (StringUtils.isNotBlank(valueAsString)) {
                    int currentCounter = counter.getAndDecrement();
                    if (currentCounter <= 0) {
                        counter.set(batchSize - 1);
                        currentCounter = batchSize;
                    }

                    final boolean isFirst = (firstValueRef.get() == null);
                    if (isFirst) {
                        final String head = head(type, containers, true);
                        stringConsumer.accept(head);
                        firstValueRef.set(value);
                    } else if (currentCounter == batchSize) {
                        stringConsumer.accept(";\n\n");
                    } else {
                        stringConsumer.accept(",\n");
                    }

                    if (currentCounter == batchSize) {
                        stringConsumer.accept(buildInsertQuery(type, containers));
                    }

                    stringConsumer.accept(valueAsString);
                }
            }
        });

        if (firstValueRef.get() != null) {
            stringConsumer.accept(";");
        }
    }
}
