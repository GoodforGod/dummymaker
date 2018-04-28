package io.dummymaker.annotation.special;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ignores object field during export
 *
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
 * @since 03.06.2017
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenIgnoreExport {

}
