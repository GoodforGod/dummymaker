package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.time.DayOfWeekGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Kurako (GoodforGod)
 * @see DayOfWeekGenerator
 * @since 12.11.2022
 */
@GenCustom(DayOfWeekGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDayOfWeek {

}
