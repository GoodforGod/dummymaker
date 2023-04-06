package io.goodforgod.dummymaker.generator;

import io.goodforgod.dummymaker.GenParameterBuilder;
import io.goodforgod.dummymaker.GenType;
import io.goodforgod.dummymaker.cases.NamingCase;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 08.03.2023
 */
public interface GenParameters {

    /**
     * @return localisation expected from generated value
     */
    @NotNull
    Localisation localisation();

    /**
     * @return naming case strategy expected from generated value
     */
    @NotNull
    NamingCase namingCase();

    /**
     * @return field type
     */
    @NotNull
    GenType parameterType();

    /**
     * @return that can be used for complex field generation
     */
    @NotNull
    GenParameterBuilder genericBuilder();
}
