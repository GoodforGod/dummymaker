package io.dummymaker.scan;

import io.dummymaker.annotation.simple.number.GenInt;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.annotation.simple.number.GenShort;
import io.dummymaker.beta.model.DummyCast;
import io.dummymaker.scan.impl.UniqueScanner;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Unique field annotation scanner tests
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class UniqueScannerTests extends Assert {

    @Test
    public void validAndUniqueScanned() {
        final UniqueScanner scanner = new UniqueScanner();
        final Map<Field, List<Annotation>> scanned = scanner.scan(DummyCast.class);

        assertNotNull(scanned);
        assertFalse(scanned.isEmpty());

        final Optional<Field> asByte = scanned.keySet().stream().filter(f -> f.getName().equals("asByte")).findFirst();
        final Optional<Field> asShort = scanned.keySet().stream().filter(f -> f.getName().equals("asShort")).findFirst();
        final Optional<Field> asInt = scanned.keySet().stream().filter(f -> f.getName().equals("asInt")).findFirst();
        final Optional<Field> asLong = scanned.keySet().stream().filter(f -> f.getName().equals("asLong")).findFirst();

        assertTrue(asByte.isPresent());
        assertTrue(asShort.isPresent());
        assertTrue(asInt.isPresent());
        assertTrue(asLong.isPresent());

        assertTrue(scanned.get(asByte.get()).stream().anyMatch(a -> a.annotationType().equals(GenShort.class)));
        assertTrue(scanned.get(asShort.get()).stream().anyMatch(a -> a.annotationType().equals(GenShort.class)));
        assertTrue(scanned.get(asInt.get()).stream().anyMatch(a -> a.annotationType().equals(GenInt.class)));
        assertTrue(scanned.get(asLong.get()).stream().anyMatch(a -> a.annotationType().equals(GenLong.class)));
    }
}
