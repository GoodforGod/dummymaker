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
import java.time.ZoneOffset;
import java.util.*;

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
    private String javaToSqlDataType(final String exportFieldName) {
        return dataTypes.getOrDefault(classContainer.getField(exportFieldName).getType(), "VARCHAR");
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
        builder.append("\t")
                .append("PRIMARY KEY (");

        if (primaryKeyField.isEmpty())
            builder.append(classContainer.getFieldContainers().values().iterator().next().getFinalFieldName());
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
    private String sqlCreateInsertNameType(final String finalFieldName) {
        return finalFieldName + "\t" + javaToSqlDataType(finalFieldName);
    }

    private String wrapWithComma(final String value) {
        return "'" + value + "'";
    }

    /**
     * Insert query
     */
    private <T> String sqlInsertIntoQuery(final T t) {
        final Iterator<ExportContainer> iterator = extractExportValues(t).iterator();
        final StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO ").append(classContainer.exportClassName().toLowerCase()).append(" (");

        while (iterator.hasNext()) {
            builder.append(iterator.next().getExportName());

            if (iterator.hasNext())
                builder.append(", ");
        }

        builder.append(") ").append("VALUES ");

        return builder.toString();
    }

    /**
     * Creates insert query field name
     */
    private <T> StringBuilder format(final T t, final IClassContainer container) {
        final Iterator<ExportContainer> iterator = extractExportValues(t).iterator();
        final StringBuilder builder = new StringBuilder();

        if (iterator.hasNext()) {
            builder.append("(");

            while (iterator.hasNext()) {
                final ExportContainer exportContainer = iterator.next();

                final Field fieldType = container.getField(exportContainer.getExportName());

                if (fieldType.getType().equals(String.class))
                    builder.append(wrapWithComma(exportContainer.getExportValue()));
                else if (fieldType.getType().equals(LocalDateTime.class))
                    builder.append(wrapWithComma(Timestamp.valueOf(LocalDateTime.parse(exportContainer.getExportValue())).toString()));
                else
                    builder.append(exportContainer.getExportValue());

                if (iterator.hasNext())
                    builder.append(", ");
            }

            builder.append(")");
        }

        return builder;
    }

    private Timestamp convertToTimestamp(Field field, ExportContainer exportContainer) {
        if (field.getType().equals(LocalDateTime.class)) {
            return convertToTimestamp(parseDateTime(exportContainer.getExportValue()));
        } else if (field.getType().equals(LocalDate.class)) {
            convertToTimestamp(parseDate(exportContainer.getExportValue()));
        } else if (field.getType().equals(LocalTime.class)) {
            convertToTimestamp(parseTime(exportContainer.getExportValue()));
        } else if (field.getType().equals(Date.class)) {
            convertToTimestamp(parseDate(exportContainer.getExportValue()));
        } else if (field.getType().equals(Timestamp.class)) {
            Timestamp.valueOf(exportContainer.getExportValue());
        }
        return null;
    }

    private Timestamp convertToTimestamp(Date date) {
        return Timestamp.valueOf(LocalDateTime.ofEpochSecond(date.getTime(), 0, ZoneOffset.UTC));
    }

    private Timestamp convertToTimestamp(LocalDate localDate) {
        return Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(0, 0)));
    }

    private Timestamp convertToTimestamp(LocalTime localTime) {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.of(1970, 1, 1), localTime));
    }

    private Timestamp convertToTimestamp(LocalDateTime localDateTime) {
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
                && writer.write(sqlTableCreate())
                && writer.write(sqlInsertIntoQuery(t))
                && writer.write(format(t) + ";")
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
        writer.write(sqlTableCreate());

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                writer.write(sqlInsertIntoQuery(t));

            i--;

            final StringBuilder valueToWrite = format(t);

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
        return flush();
    }

    @Override
    public <T> String exportAsString(final T t) {
        if (isExportEntityInvalid(t))
            return "";

        final IClassContainer container = buildClassContainer(t);
        if (!container.isExportable())
            return "";

        return sqlTableCreate() + "\n"
                + sqlInsertIntoQuery(t) + "\n"
                + format(t) + ";";
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
        result.append(sqlTableCreate()).append("\n");

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                result.append(sqlInsertIntoQuery(t)).append("\n");

            i--;

            final StringBuilder valueToWrite = format(t);

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
