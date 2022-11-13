package io.dummymaker.annotation;

import io.dummymaker.export.Exporter;

import java.lang.annotation.*;

/**
 * Ignores object production
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @since 01.03.2019
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenIgnore {

}
