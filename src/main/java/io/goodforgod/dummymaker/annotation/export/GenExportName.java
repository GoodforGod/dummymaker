package io.goodforgod.dummymaker.annotation.export;

import io.goodforgod.dummymaker.export.Exporter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow to rename fields and class names when export To rename class you should annotate its
 * constructor
 *
 * @author Anton Kurako (GoodforGod)
 * @since 06.07.2017
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenExportName {

    /**
     * @return field name to use when {@link Exporter} is used
     */
    String value();
}
