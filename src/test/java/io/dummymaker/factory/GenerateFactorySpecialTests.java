package io.dummymaker.factory;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.data.DummyCollection;
import io.dummymaker.factory.impl.BasicGenerateFactory;
import io.dummymaker.factory.impl.ListGenerateFactory;
import io.dummymaker.scan.IAnnotationScanner;
import io.dummymaker.scan.impl.BasicScanner;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 10.03.2018
 */
public class GenerateFactorySpecialTests extends Assert {

    @Test
    public void isSuitable() {
        BasicGenerateFactory generateFactory = new ListGenerateFactory();

        IAnnotationScanner scanner = new BasicScanner();

        List<Annotation> annotations = scanner.scan(DummyCollection.class).entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Annotation suitable =  generateFactory.findSuitable(annotations);

        assertTrue(generateFactory.isSuitable(suitable));
    }

    @Test
    public void tryToFindSuitable() {
        BasicGenerateFactory generateFactory = new ListGenerateFactory();

        IAnnotationScanner scanner = new BasicScanner();

        List<Annotation> annotations = scanner.scan(DummyCollection.class).entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Annotation suitable = generateFactory.findSuitable(annotations);

        assertNotNull(suitable);
        assertEquals(suitable.annotationType(), GenList.class);
    }

    @Test
    public void tryToFindSuitableNullAnnotation() {
        IGenerateFactory generateFactory = new ListGenerateFactory();

        Annotation suitable = generateFactory.findSuitable(new ArrayList<>());

        assertNull(suitable);
    }
}
