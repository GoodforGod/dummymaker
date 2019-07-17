package io.dummymaker.scan;

import io.dummymaker.container.impl.GenContainer;
import io.dummymaker.scan.impl.PopulateScanner;
import io.dummymaker.scan.impl.PopulateSimpleScanner;

import java.lang.reflect.Field;

/**
 * Scanners used by populate factory primarily
 *
 * @see PopulateScanner
 * @see PopulateSimpleScanner
 *
 * @see GenContainer
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public interface IPopulateScanner extends IScanner<Field, GenContainer> {

}
