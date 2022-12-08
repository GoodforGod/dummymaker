package io.dummymaker.generator;

import io.dummymaker.factory.GenStorage;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Complex Generator used by ComplexGen annotation to populate fields When annotation have
 * attributes or value generates for multiple field types
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
public interface ComplexGenerator extends Generator<Object> {

    /**
     * Generates object for field marked by complex annotation
     *
     * @param annotation field was marked by
     * @param field      for which value is generated
     * @param storage    factory storage with generators and embedded entity production
     * @param depth      current depth of the generated field
     * @param parent     parent object class
     * @return generated object
     */
    @Nullable
    Object generate(@NotNull Class<?> parent,
                    @NotNull Field field,
                    @NotNull GenStorage storage,
                    @Nullable Annotation annotation,
                    int depth);
}
