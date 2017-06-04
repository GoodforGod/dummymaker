package io.dummymaker.annotation.prime;

import java.lang.annotation.*;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 03.06.2017
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenIgnoreExport {
    boolean value() default true;
}
