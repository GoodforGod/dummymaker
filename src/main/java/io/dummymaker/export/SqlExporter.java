package io.dummymaker.export;

import io.dummymaker.export.container.ExportContainer;
import io.dummymaker.export.container.FieldContainer;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.dummymaker.util.NameStrategist.NamingStrategy;

/**
 * Export objects as SQL insert query
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class SqlExporter<T> extends BaseExporter<T> {

    /**
     * Java & Sql Type Representation
     *
     * Map is used to convert Java Field Data Type to Sql Data Type
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

    public SqlExporter(final Class<T> primeClass) {
        this(primeClass, null);
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path) {
        this(primeClass, path, NamingStrategy.DEFAULT);
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path,
                       final NamingStrategy strategy) {
        super(primeClass, path, ExportFormat.SQL, strategy);
        this.dataTypeMap = buildDefaultDataTypeMap();
    }

    public SqlExporter(final Class<T> primeClass,
                       final String path,
                       final NamingStrategy strategy,
                       final Map<Class, String> dataTypeMap) {
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
        return dataTypeMap.getOrDefault(classContainer.getFieldByFinalName(finalFieldName).getType(), "VARCHAR");
    }

    /**
     * Create String of Create Table Query
     */
    private String sqlTableCreate() {
        final StringBuilder builder = new StringBuilder();
        final Iterator<Map.Entry<String, FieldContainer>> iterator = classContainer.fieldContainerMap().entrySet().iterator();

        String primaryKeyField = "";

        builder.append("CREATE TABLE IF NOT EXISTS ").append(classContainer.finalClassName().toLowerCase()).append("(\n");

        while (iterator.hasNext()) {
            final String finaFieldName = iterator.next().getValue().getFinalFieldName();
            builder.append("\t").append(sqlCreateInsertNameType(finaFieldName));

            if (finaFieldName.equalsIgnoreCase("id"))
                primaryKeyField = finaFieldName;

            builder.append(",");

            builder.append("\n");
        }

        // Write primary key constraint
        builder.append("\t").append("PRIMARY KEY (");

        if(primaryKeyField.isEmpty())
            builder.append(classContainer.fieldContainerMap().values().iterator().next().getFinalFieldName());
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

        builder.append("INSERT INTO ").append(classContainer.finalClassName().toLowerCase()).append(" (");

        while (iterator.hasNext()) {
            builder.append(iterator.next().getFieldName());

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

                final Field fieldType = classContainer.getFieldByFinalName(container.getFieldName());

                if(fieldType.getType().equals(String.class))
                    builder.append(wrapWithComma(container.getFieldValue()));
                else if(fieldType.getType().equals(LocalDateTime.class))
                    builder.append(wrapWithComma(Timestamp.valueOf(LocalDateTime.parse(container.getFieldValue())).toString()));
                else
                    builder.append(container.getFieldValue());

                if (iterator.hasNext())
                    builder.append(", ");
            }

            builder.append(")");
        }

        return builder;
    }

    @Override
    public boolean export(final T t) {
        init();
        return isExportStateValid(t)
                && writeLine(sqlTableCreate())
                && writeLine(sqlInsertIntoQuery(t))
                && writeLine(sqlValuesInsert(t) + ";")
                && flush();
    }

    @Override
    public boolean export(final List<T> list) {
        if (!isExportStateValid(list))
            return false;

        init();

        Integer i = INSERT_QUERY_LIMIT;

        final Iterator<T> iterator = list.iterator();

        // Create Table Query
        writeLine(sqlTableCreate());

        while (iterator.hasNext()) {
            final T t = iterator.next();

            // Insert Values Query
            if (i.equals(INSERT_QUERY_LIMIT))
                writeLine(sqlInsertIntoQuery(t));

            i--;

            final StringBuilder valueToWrite = sqlValuesInsert(t);

            if (iterator.hasNext() && i != 0)
                valueToWrite.append(",");
            else if (i == 0 || !iterator.hasNext())
                valueToWrite.append(";");

            writeLine(valueToWrite.toString());

            // End insert Query if no elements left or need to organize next batch
            if (i == 0) {
                if (iterator.hasNext()) {
                    writeLine("\n");
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
