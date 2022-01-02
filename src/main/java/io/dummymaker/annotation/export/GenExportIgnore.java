package io.dummymaker.annotation.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ignores object field during export
 *
 * @author GoodforGod
 * @see io.dummymaker.export.IExporter
 * @since 03.06.2017
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenExportIgnore {

}
