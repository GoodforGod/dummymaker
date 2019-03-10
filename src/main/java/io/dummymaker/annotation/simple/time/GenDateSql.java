package io.dummymaker.annotation.simple.time;

import io.dummymaker.annotation.PrimeGen;
import io.dummymaker.generator.simple.impl.time.impl.DateSqlGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see DateSqlGenerator
 * @see java.sql.Date
 *
 * @author GoodforGod
 * @since 10.03.2019
 */
@PrimeGen(DateSqlGenerator.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenDateSql {

}
