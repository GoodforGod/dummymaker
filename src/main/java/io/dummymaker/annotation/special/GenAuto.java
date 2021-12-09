package io.dummymaker.annotation.special;


import io.dummymaker.annotation.core.PrimeGen;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotations is used on classes And uses default suitable generators to fill class fields
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.IGenSupplier
 * @since 21.04.2018
 */
@PrimeGen
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface GenAuto {

    /**
     * @see GenEmbedded#MAX
     */
    int MAX = GenEmbedded.MAX;

    /**
     * @return desired embedded depth
     * @see GenEmbedded#depth()
     */
    int depth() default 1;
}
