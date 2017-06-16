package io.dummymaker.annotation.special;

import java.lang.annotation.*;

/**
 * Force object field to export, despite it been annotated with generate annotations
 *
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenForceExport {
    boolean value() default true;
}
