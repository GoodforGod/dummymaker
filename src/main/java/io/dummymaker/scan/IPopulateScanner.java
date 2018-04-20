package io.dummymaker.scan;

import io.dummymaker.container.impl.PopulateContainer;
import io.dummymaker.scan.impl.PopulateEmbeddedFreeScanner;
import io.dummymaker.scan.impl.PopulateScanner;

import java.lang.reflect.Field;

/**
 * Scanners used by populate factory primarily
 *
 * @see PopulateScanner
 * @see PopulateEmbeddedFreeScanner
 *
 * @see PopulateContainer
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public interface IPopulateScanner extends IScanner<Field, PopulateContainer> {

}
