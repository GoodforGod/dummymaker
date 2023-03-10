package io.dummymaker.annotation;

import java.lang.annotation.*;

/**
 * Ignores object generation for field
 *
 * @author Anton Kurako (GoodforGod)
 * @since 01.03.2019
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenIgnore {

}
