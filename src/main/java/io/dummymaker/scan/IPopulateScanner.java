package io.dummymaker.scan;

import io.dummymaker.container.GenContainer;
import io.dummymaker.scan.impl.PopulateScanner;
import io.dummymaker.scan.impl.PopulateSimpleScanner;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Scanners used by populate factory primarily
 *
 * @author GoodforGod
 * @see PopulateScanner
 * @see PopulateSimpleScanner
 * @see GenContainer
 * @since 10.03.2018
 */
public interface IPopulateScanner extends IMapScanner<Field, GenContainer, Class> {

    @Override
    Map<Field, GenContainer> scan(Class target);
}
