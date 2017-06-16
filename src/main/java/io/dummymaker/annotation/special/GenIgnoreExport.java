package io.dummymaker.annotation.special;

import java.lang.annotation.*;

/**
 * Ignores object field during export
 *
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
 * @since 03.06.2017
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenIgnoreExport {
    boolean value() default true;
}
