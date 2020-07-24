package io.dummymaker.export.impl;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.error.ExportException;
import io.dummymaker.export.Cases;
import io.dummymaker.export.ICase;
import io.dummymaker.export.IExporter;
import io.dummymaker.model.export.DateFieldContainer;
import io.dummymaker.model.export.FieldContainer;
import io.dummymaker.scan.IExportScanner;
import io.dummymaker.scan.impl.ExportScanner;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.StringUtils;
import io.dummymaker.writer.IWriter;
import io.dummymaker.writer.impl.FileWriter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Core exporter class with mapping and converting functionality
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public abstract class BaseExporter implements IExporter {

    protected final IExportScanner scanner = new ExportScanner();

    protected boolean cleanExportFile = true;
    protected ICase naming = Cases.DEFAULT.value();

    protected abstract @NotNull String getExtension();

    protected abstract <T> @NotNull String map(T t, Collection<FieldContainer> containers);

    /**
     * Build exporter with naming strategy
     *
     * @param naming naming strategy for exporter
     * @see Cases
     */
    public IExporter withCase(@NotNull ICase naming) {
        this.naming = naming;
        return this;
    }

    public @NotNull IExporter withAppend() {
        this.cleanExportFile = false;
        return this;
    }

    protected Predicate<FieldContainer> filter() {
        return c -> true;
    }

    protected Stream<FieldContainer> scan(Class<?> type) {
        return scan(type, filter());
    }

    protected Stream<FieldContainer> scan(Class<?> type, Predicate<FieldContainer> filter) {
        return scanner.scan(type).stream().filter(filter);
    }

    protected <T> @NotNull String getValue(T t, FieldContainer container) {
        if (t == null)
            return "";

        try {
            final Field field = container.getField();
            field.setAccessible(true);
            final Object value = field.get(t);
            if (value == null)
                return convertNull();

            switch (container.getType()) {
                case BOOLEAN:
                    return convertBoolean((Boolean) value);
                case NUMBER:
                case SEQUENTIAL:
                    return convertNumber(value);
                case STRING:
                    return convertString((String) value);
                case DATE:
                    return convertDate(value, (DateFieldContainer) container);
                case ARRAY:
                    return convertArray(value);
                case ARRAY_2D:
                    return convertArray2D(value);
                case MAP:
                    return convertMap((Map) value);
                case COLLECTION:
                    return convertCollection((Collection) value);
                case COMPLEX:
                default:
                    return convertComplex(value);
            }
        } catch (Exception ex) {
            throw new ExportException(ex);
        }
    }

    protected String convertNull() {
        return "";
    }

    protected String convertBoolean(Boolean bool) {
        return bool.toString();
    }

    protected String convertString(String s) {
        return s;
    }

    protected String convertNumber(Object number) {
        return String.valueOf(number);
    }

    protected String convertDate(Object date, DateFieldContainer container) {
        if (container.isUnixTime())
            return convertDateUnix(date);

        final String formatterPattern = container.getFormatter();
        final DateTimeFormatter formatter = getDateFormatter(date, formatterPattern);
        if (date instanceof Date) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(((Date) date).getTime()), TimeZone.getDefault().toZoneId())
                    .format(formatter);
        } else if (date instanceof LocalDate) {
            return ((LocalDate) date).format(formatter);
        } else if (date instanceof LocalTime) {
            return ((LocalTime) date).format(formatter);
        } else if (date instanceof LocalDateTime) {
            return ((LocalDateTime) date).format(formatter);
        } else {
            return String.valueOf(date);
        }
    }

    protected String convertDateUnix(Object date) {
        if (date instanceof Date) {
            return String.valueOf(((Date) date).getTime());
        } else if (date instanceof ChronoLocalDate) {
            return String.valueOf(((LocalDate) date).toEpochDay());
        } else if (date instanceof LocalTime) {
            return String.valueOf(LocalDateTime.of(LocalDate.of(1970, 1, 1),
                    ((LocalTime) date)).toEpochSecond(ZoneOffset.UTC));
        } else if (date instanceof LocalDateTime) {
            return String.valueOf(((LocalDateTime) date).toEpochSecond(ZoneOffset.UTC));
        } else {
            return String.valueOf(date);
        }
    }

    protected DateTimeFormatter getDateFormatter(Object date, String formatter) {
        if (date instanceof Time) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof Date) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof LocalDate) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_DATE
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof LocalTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof LocalDateTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else {
            return DateTimeFormatter.ofPattern(formatter);
        }
    }

    protected String convertArray(Object array) {
        final Class<?> type = array.getClass().getComponentType();
        if(type == byte.class)
            return Arrays.toString(((byte[]) array));
        else if(type == short.class)
            return Arrays.toString(((short[]) array));
        else if(type == int .class)
            return Arrays.toString(((int[]) array));
       else if(type == long.class)
            return Arrays.toString(((long[]) array));
        else if(type == float.class)
            return Arrays.toString(((float[]) array));
        else if(type == double.class)
            return Arrays.toString(((double[]) array));
        else if(type == boolean.class)
            return Arrays.toString(((boolean[]) array));
        else if(type == char.class)
            return Arrays.toString(((char[]) array));

        return Arrays.stream(((Object[]) array))
                .map(v -> v instanceof String ? convertString((String) v) : v.toString())
                .collect(Collectors.joining(",", "[", "]"));
    }

    protected String convertArray2D(Object array) {
        return Arrays.deepToString((Object[]) array);
    }

    protected String convertCollection(Collection<?> collection) {
        return collection.stream()
                .map(v -> v instanceof String ? convertString((String) v) : v.toString())
                .collect(Collectors.joining(",", "[", "]"));
    }

    protected String convertMap(Map<?, ?> map) {
        return map.entrySet().stream()
                .map(e -> {
                    final String key = e.getKey() instanceof String ? convertString((String) e.getKey()) : e.getKey().toString();
                    final String value = e.getValue() instanceof String ? convertString((String) e.getValue())
                            : e.getValue().toString();
                    return key + ":" + value;
                })
                .collect(Collectors.joining(",", "{", "}"));
    }

    protected String convertComplex(Object object) {
        return "";
    }

    protected <T> @NotNull String prefix(T t, Collection<FieldContainer> containers) {
        return "";
    }

    protected <T> @NotNull String suffix(T t, Collection<FieldContainer> containers) {
        return "";
    }

    protected <T> @NotNull String separator(T t, Collection<FieldContainer> containers) {
        return "";
    }

    protected <T> @NotNull String head(T t, Collection<FieldContainer> containers) {
        return "";
    }

    protected <T> @NotNull String tail(T t, Collection<FieldContainer> containers) {
        return "";
    }

    protected <T> @NotNull IWriter getWriter(String typeName) {
        return new FileWriter(typeName, "./", getExtension(), cleanExportFile);
    }

    @Override
    public <T> boolean export(T t) {
        if (t == null)
            return false;

        final Collection<FieldContainer> containers = scan(t.getClass()).collect(Collectors.toList());
        if(containers.isEmpty())
            return false;

        final IWriter writer = getWriter(t.getClass().getSimpleName());

        final String data = prefix(t, containers) + map(t, containers) + suffix(t, containers);
        return writer.append(head(t, containers))
                && writer.append(data)
                && writer.append(tail(t, containers));
    }

    @Override
    public <T> boolean export(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return false;

        final T t = collection.iterator().next();
        final Collection<FieldContainer> containers = scan(t.getClass()).collect(Collectors.toList());
        if(containers.isEmpty())
            return false;

        final IWriter writer = getWriter(t.getClass().getSimpleName());

        final String data = convertData(collection, containers);
        return writer.append(head(t, containers))
                && writer.append(data)
                && writer.append(tail(t, containers));
    }

    @Override
    public <T> @NotNull String convert(T t) {
        if (t == null)
            return "";

        final Collection<FieldContainer> containers = scan(t.getClass()).collect(Collectors.toList());
        if(containers.isEmpty())
            return "";

        final String data = prefix(t, containers) + map(t, containers) + suffix(t, containers);
        return head(t, containers) + data + tail(t, containers);
    }

    @Override
    public <T> @NotNull String convert(@NotNull Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection))
            return "";

        final T t = collection.iterator().next();
        final Collection<FieldContainer> containers = scan(t.getClass()).collect(Collectors.toList());
        if(containers.isEmpty())
            return "";

        final String data = convertData(collection, containers);
        return head(t, containers) + data + tail(t, containers);
    }

    private <T> String convertData(Collection<T> collection, Collection<FieldContainer> containers) {
        final T t = collection.iterator().next();
        return collection.stream()
                .filter(Objects::nonNull)
                .map(v -> {
                    final String value = map(v, containers);
                    return StringUtils.isEmpty(value) ? "" : prefix(v, containers) + value + suffix(v, containers);
                })
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(separator(t, containers)));
    }
}
