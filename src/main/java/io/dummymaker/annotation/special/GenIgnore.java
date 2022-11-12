package io.dummymaker.annotation.special;

import io.dummymaker.export.Exporter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ignores object production
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @since 01.03.2019
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenIgnore {

}
