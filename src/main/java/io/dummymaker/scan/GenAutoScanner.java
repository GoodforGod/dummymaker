package io.dummymaker.scan;

import io.dummymaker.model.GenContainer;
import java.lang.reflect.Field;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Scanners used by populate factory primarily
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenScanner
 * @since 18.08.2019
 */
public interface GenAutoScanner extends GenScanner {

    /**
     * Same as default scan but also scan for suitable gen auto generators
     *
     * @param target        to scan
     * @param isDefaultAuto mark target as GenAuto as default even if it is not annotated
     * @return map of fields and its gen containers
     * @see io.dummymaker.annotation.special.GenAuto
     */
    @NotNull
    Map<Field, GenContainer> scan(Class target, boolean isDefaultAuto);
}
