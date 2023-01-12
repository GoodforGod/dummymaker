package io.dummymaker.scan;

import io.dummymaker.factory.old.MainGenFactory;
import io.dummymaker.model.GenContainer;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Scanners used by populate factory primarily
 *
 * @author Anton Kurako (GoodforGod)
 * @see MainGenFactory
 * @see GenContainer
 * @since 10.03.2018
 */
public interface GenScanner extends ListScanner<GenContainer, Class<?>> {

    /**
     * Scan for annotated target fields
     *
     * @param target to scan
     * @return map of fields and its gen containers
     */
    @NotNull
    List<GenContainer> scan(Class<?> target);
}
