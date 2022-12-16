package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.time.TimeSqlGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see TimeSqlGenerator
 * @since 10.03.2019
 */
@GenCustom(TimeSqlGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenTimeSql {

}
