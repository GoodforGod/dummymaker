package io.dummymaker.annotation.export;

import io.dummymaker.export.Exporter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Force object field to export, despite it been annotated with generate annotations
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @since 31.05.2017
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenExportForce {

}
