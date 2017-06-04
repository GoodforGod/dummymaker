package io.model.dummymaker.populate;

import io.model.dummymaker.annotation.prime.PrimeGenAnnotation;
import io.model.dummymaker.scan.PopulateAnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 30.05.2017
 */
public class GenPopulateFactory<T> implements IPopulateFactory<T> {

    private final Logger logger = Logger.getLogger(GenPopulateFactory.class.getName());

    private final PopulateAnnotationScanner populateScanner = new PopulateAnnotationScanner();

    @Override
    public T populate(T t) {
        Map<Field, Set<Annotation>> classAnnotatedFields = populateScanner.scan(t.getClass());

        for(Map.Entry<Field, Set<Annotation>> annotatedField : classAnnotatedFields.entrySet()) {
            Object objValue = null;
            try {
                annotatedField.getKey().setAccessible(true);

                objValue = ((PrimeGenAnnotation) annotatedField.getValue().iterator().next()).value().newInstance().generate();

                annotatedField.getKey().set(t, annotatedField.getKey().getType().cast(objValue));
            }
            catch (IllegalAccessException e) {
                logger.warning(e.getMessage());
            }
            catch (ClassCastException e) {
                logger.info("CAN NOT CAST FIELD TYPE.. TRYING TO CONVERT TO STRING TYPE.");

                try {
                    if(annotatedField.getKey().getType().isAssignableFrom(String.class))
                        annotatedField.getKey().set(t, String.valueOf(objValue));
                }
                catch (Exception ex) {
                    logger.warning("FIELD TYPE AND GENERATE TYPE ARE NOT COMPATIBLE AND CAN NOT BE CONVERTED TO STRING.");
                }
            }
            catch (Exception e) {
                logger.warning(e.getMessage());
            }
            finally {
                annotatedField.getKey().setAccessible(false);
            }
        }

        return t;
    }

    @Override
    public List<T> populate(List<T> t) {
        return t.stream().map(this::populate).collect(Collectors.toList());
    }
}
