package io.dummymaker.scan;

import io.dummymaker.factory.impl.MainGenFactory;
import io.dummymaker.model.GenContainer;
import java.lang.reflect.Field;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Scanners used by populate factory primarily
 *
 * @author Anton Kurako (GoodforGod)
 * @see MainGenFactory
 * @see GenContainer
 * @since 10.03.2018
 */
public interface GenScanner extends MapScanner<Field, GenContainer, Class> {

    /**
     * Scan for annotated target fields
     *
     * @param target to scan
     * @return map of fields and its gen containers
     */
    @NotNull
    Map<Field, GenContainer> scan(Class target);
}
