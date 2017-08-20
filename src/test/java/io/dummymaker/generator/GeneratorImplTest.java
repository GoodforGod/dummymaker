package io.dummymaker.generator;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Generators Tests
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class GeneratorImplTest {

    @Test
    public void bigDoubleGen() {
        IGenerator generator = new BigDoubleGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(Double.class));
        assertTrue(String.valueOf((double)generated).matches("-?[0-9]+.[0-9]+"));
    }

    @Test
    public void cityGen() {
        IGenerator generator = new CityGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("[a-zA-Z]+"));
    }

    @Test
    public void companyGen() {
        IGenerator generator = new CompanyGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches(".+(\\t.+)?"));
    }

    @Test
    public void countryGen() {
        IGenerator generator = new CountryGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("[a-zA-Z]+"));
    }

    @Test
    public void doubleGen() {
        IGenerator generator = new DoubleGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(Double.class));
        assertTrue(String.valueOf((double)generated).matches("1|0.[0-9]+."));
    }

    @Test
    public void emailGen() {
        IGenerator generator = new EmailGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("^[a-zA-Z0-9%_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]{1,6}$"));
    }

    @Test
    public void enumerateGen() {
        IGenerator generator = new EnumerateGenerator();

        Object generated = generator.generate();

        assertNull(generated);
    }

    @Test
    public void intGen() {
        IGenerator generator = new IntegerGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(Integer.class));
        assertTrue(String.valueOf((int)generated).matches("-?[0-9]+"));
    }

    @Test
    public void localDateTimeGen() {
        IGenerator generator = new LocalDateTimeGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(LocalDateTime.class));
    }

    @Test
    public void longGen() {
        IGenerator generator = new LongGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(Long.class));
        assertTrue(String.valueOf((long)generated).matches("-?[0-9]+"));
    }

    @Test
    public void nameGen() {
        IGenerator generator = new NameGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("[a-zA-Z]+"));
    }

    @Test
    public void nickGen() {
        IGenerator generator = new NickGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("[0-9a-zA-Z]+"));
    }

    @Test
    public void nullGen() {
        IGenerator generator = new NullGenerator();

        Object generated = generator.generate();

        assertNull(generated);
    }

    @Test
    public void passGen() {
        IGenerator generator = new PassGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("[0-9a-zA-Z]{6,}"));
    }

    @Test
    public void phoneGen() {
        IGenerator generator = new PhoneGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("[0-9]\\([0-9]{1,3}\\)[0-9]+"));
    }

    @Test
    public void phraseGen() {
        IGenerator generator = new PhraseGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches(".{1,}+(\\t.{1,}+)?"));
    }

    @Test
    public void stringGen() {
        IGenerator generator = new StringGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("[0-9a-zA-Z]+"));
    }

    @Test
    public void tagGen() {
        IGenerator generator = new TagGenerator();

        Object generated = generator.generate();

        assertNotNull(generated);
        assertTrue(generated.getClass().equals(String.class));
        assertTrue(String.valueOf(generated).matches("#[0-9a-zA-Z]+"));
    }

}
