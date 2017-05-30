package io.generator.scan;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public interface IScanner {
    List<Annotation> scan(Class t);
}
