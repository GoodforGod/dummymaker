package io.goodforgod.dummymaker.annotation;

import io.goodforgod.dummymaker.generator.AnnotationGeneratorFactory;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.lang.annotation.*;

/**
 * Instantiates {@link ParameterizedGenerator} from annotation with parameters
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.12.2022
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface GenCustomFactory {

    /**
     * @return generator factory class to be called to generate values on factory (generator factory is
     *             expected to be initialized with Zero Argument Constructor)
     */
    Class<? extends AnnotationGeneratorFactory<?>> value();
}
