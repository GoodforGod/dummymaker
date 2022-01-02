package io.dummymaker.annotation.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Force object field to export, despite it been annotated with generate annotations
 *
 * @author GoodforGod
 * @see io.dummymaker.export.IExporter
 * @since 31.05.2017
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenExportForce {

}
