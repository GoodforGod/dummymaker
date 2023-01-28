package io.dummymaker.export;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.cases.Case;
import io.dummymaker.error.GenExportException;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.StringUtils;
import java.lang.reflect.Field;
import java.sql.Time;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/**
 * Core exporter class with mapping and converting functionality
 *
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
abstract class AbstractExporter implements Exporter {

    private static final String DEFAULT_EMPTY_VALUE = "";

    protected final ExportScanner scanner = new ExportScanner();

    protected final Case fieldCase;
    protected final Function<String, Writer> writerFunction;

    AbstractExporter(Case fieldCase, Function<String, Writer> writerFunction) {
        this.fieldCase = fieldCase;
        this.writerFunction = writerFunction;
    }

    protected abstract @NotNull String getExtension();

    protected abstract <T> @NotNull String map(T t, Collection<ExportField> containers);

    protected Predicate<ExportField> filter() {
        return c -> true;
    }

    protected Stream<ExportField> scan(Class<?> type) {
        return scan(type, filter());
    }

    protected Stream<ExportField> scan(Class<?> type, Predicate<ExportField> filter) {
        return scanner.scan(type).stream().filter(filter);
    }

    protected <T> @NotNull String getValue(T t, ExportField container) {
        if (t == null)
            return DEFAULT_EMPTY_VALUE;

        try {
            final Field field = container.getField();
            field.setAccessible(true);
            final Object value = field.get(t);
            if (value == null) {
                return convertNull();
            }

            switch (container.getType()) {
                case BOOLEAN:
                    return convertBoolean((Boolean) value);
                case NUMBER:
                case SEQUENTIAL:
                    return convertNumber(value);
                case STRING:
                    return convertString(String.valueOf(value));
                case DATE:
                    return ((TimeExportField) container).isUnixTime()
                            ? convertDateUnix(value)
                            : convertDate(value, ((TimeExportField) container).getFormatter());
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
            throw new GenExportException(ex);
        }
    }

    protected String convertNull() {
        return DEFAULT_EMPTY_VALUE;
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

    protected String convertDate(Object date, String formatterPattern) {
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
        } else if (date instanceof OffsetTime) {
            return ((OffsetTime) date).format(formatter);
        } else if (date instanceof OffsetDateTime) {
            return ((OffsetDateTime) date).format(formatter);
        } else if (date instanceof ZonedDateTime) {
            return ((ZonedDateTime) date).format(formatter);
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
        } else if (date instanceof OffsetTime) {
            return String.valueOf(LocalDateTime.of(LocalDate.of(1970, 1, 1),
                    ((OffsetTime) date).toLocalTime()).toEpochSecond(ZoneOffset.UTC));
        } else if (date instanceof OffsetDateTime) {
            return String.valueOf(((OffsetDateTime) date).toEpochSecond());
        } else if (date instanceof ZonedDateTime) {
            return String.valueOf(((ZonedDateTime) date).toEpochSecond());
        } else {
            return String.valueOf(date);
        }
    }

    protected DateTimeFormatter getDateFormatter(Object date, String formatter) {
        if (date instanceof Time || date instanceof LocalTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof LocalDate) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_DATE
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof Date || date instanceof LocalDateTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_LOCAL_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof OffsetTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_OFFSET_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof OffsetDateTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else if (date instanceof ZonedDateTime) {
            return GenTime.DEFAULT_FORMAT.equals(formatter)
                    ? DateTimeFormatter.ISO_ZONED_DATE_TIME
                    : DateTimeFormatter.ofPattern(formatter);
        } else {
            return DateTimeFormatter.ofPattern(formatter);
        }
    }

    protected String convertArray(Object array) {
        final Class<?> type = array.getClass().getComponentType();
        if (type == byte.class)
            return Arrays.toString(((byte[]) array));
        else if (type == short.class)
            return Arrays.toString(((short[]) array));
        else if (type == int.class)
            return Arrays.toString(((int[]) array));
        else if (type == long.class)
            return Arrays.toString(((long[]) array));
        else if (type == float.class)
            return Arrays.toString(((float[]) array));
        else if (type == double.class)
            return Arrays.toString(((double[]) array));
        else if (type == boolean.class)
            return Arrays.toString(((boolean[]) array));
        else if (type == char.class)
            return Arrays.toString(((char[]) array));

        return Arrays.stream(((Object[]) array))
                .map(v -> convertString(String.valueOf(v)))
                .collect(Collectors.joining(",", "[", "]"));
    }

    protected String convertArray2D(Object array) {
        return Arrays.deepToString((Object[]) array);
    }

    protected String convertCollection(Collection<?> collection) {
        return collection.stream()
                .map(v -> convertString(String.valueOf(v)))
                .collect(Collectors.joining(",", "[", "]"));
    }

    protected String convertMap(Map<?, ?> map) {
        return map.entrySet().stream()
                .map(e -> {
                    final String key = convertString(String.valueOf(e.getKey()));
                    final String value = convertString(String.valueOf(e.getValue()));
                    return key + ":" + value;
                })
                .collect(Collectors.joining(",", "{", "}"));
    }

    protected String convertComplex(Object object) {
        return DEFAULT_EMPTY_VALUE;
    }

    protected <T> @NotNull String prefix(T t, Collection<ExportField> containers) {
        return DEFAULT_EMPTY_VALUE;
    }

    protected <T> @NotNull String suffix(T t, Collection<ExportField> containers) {
        return DEFAULT_EMPTY_VALUE;
    }

    protected <T> @NotNull String separator(T t, Collection<ExportField> containers) {
        return DEFAULT_EMPTY_VALUE;
    }

    /**
     * @param t            one of exported objects as example
     * @param containers   scanned from object
     * @param isCollection true if collection is exported or false otherwise
     * @param <T>          type of exported object
     * @return head for data
     */
    protected <T> @NotNull String head(T t, Collection<ExportField> containers, boolean isCollection) {
        return DEFAULT_EMPTY_VALUE;
    }

    /**
     * @param t            one of exported objects as example
     * @param containers   scanned from object
     * @param isCollection true if collection is exported or false otherwise
     * @param <T>          type of exported object
     * @return tail for data
     */
    protected <T> @NotNull String tail(T t, Collection<ExportField> containers, boolean isCollection) {
        return DEFAULT_EMPTY_VALUE;
    }

    protected @NotNull Writer getWriter(String filename) {
        return writerFunction.apply(filename + "." + getExtension());
    }

    @Override
    public <T> void exportAsFile(T value) {
        if (value == null) {
            return;
        }

        final Collection<ExportField> containers = scan(value.getClass()).collect(Collectors.toList());
        if (containers.isEmpty()) {
            return;
        }

        try (final Writer writer = getWriter(value.getClass().getSimpleName())) {
            final String head = head(value, containers, false);
            final String data = prefix(value, containers) + map(value, containers) + suffix(value, containers);
            final String tail = tail(value, containers, false);
            writer.write(head + data + tail);
        } catch (Exception e) {
            throw new GenExportException(e);
        }
    }

    @Override
    public <T> void exportAsFile(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }

        final T t = collection.iterator().next();
        final Collection<ExportField> containers = scan(t.getClass()).collect(Collectors.toList());
        if (containers.isEmpty()) {
            return;
        }

        try (final Writer writer = getWriter(t.getClass().getSimpleName())) {
            final String head = head(t, containers, true);
            writer.write(head);

            final Iterator<T> iterator = collection.iterator();
            while (iterator.hasNext()) {
                final T value = iterator.next();
                if (value != null) {
                    final String valueAsString = map(value, containers);
                    if (StringUtils.isNotBlank(valueAsString)) {
                        final String result = prefix(valueAsString, containers) + valueAsString
                                + suffix(valueAsString, containers);
                        if (iterator.hasNext()) {
                            final String separator = separator(value, containers);
                            writer.write(result + separator);
                        } else {
                            writer.write(result);
                        }
                    }
                }
            }

            final String tail = tail(t, containers, true);
            writer.write(tail);
        } catch (Exception e) {
            throw new GenExportException(e);
        }
    }

    @Override
    public <T> @NotNull String exportAsString(T value) {
        if (value == null) {
            return DEFAULT_EMPTY_VALUE;
        }

        final Collection<ExportField> containers = scan(value.getClass()).collect(Collectors.toList());
        if (containers.isEmpty()) {
            return DEFAULT_EMPTY_VALUE;
        }

        final String head = head(value, containers, false);
        final String data = prefix(value, containers) + map(value, containers) + suffix(value, containers);
        final String tail = tail(value, containers, false);
        return head + data + tail;
    }

    @Override
    public <T> @NotNull String exportAsString(@NotNull Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return DEFAULT_EMPTY_VALUE;
        }

        final T t = collection.iterator().next();
        final Collection<ExportField> containers = scan(t.getClass()).collect(Collectors.toList());
        if (containers.isEmpty()) {
            return DEFAULT_EMPTY_VALUE;
        }

        final String head = head(t, containers, true);
        final String data = convertData(collection, containers);
        final String tail = tail(t, containers, true);
        return head + data + tail;
    }

    protected <T> String convertData(Collection<T> collection, Collection<ExportField> containers) {
        final T first = collection.iterator().next();
        return collection.stream()
                .filter(Objects::nonNull)
                .map(v -> {
                    final String value = map(v, containers);
                    return StringUtils.isEmpty(value)
                            ? DEFAULT_EMPTY_VALUE
                            : prefix(v, containers) + value + suffix(v, containers);
                })
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(separator(first, containers)));
    }
}
