package io.dummymaker.annotation.special;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ignores object production
 *
 * @see io.dummymaker.factory.IProduceFactory
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
 * @since 01.03.2019
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface GenIgnore {

}
