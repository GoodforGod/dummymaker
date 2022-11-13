package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.GenCustom;
import io.dummymaker.generator.simple.time.DateGenerator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This date is exported in long milliseconds format So date is the milliseconds since January 1,
 * 1970, 00:00:00 GMT to 1/1/3000
 *
 * @author Anton Kurako (GoodforGod)
 * @see DateGenerator
 * @see java.util.Date
 * @since 21.02.2018
 */
@GenCustom(DateGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDate {

}
