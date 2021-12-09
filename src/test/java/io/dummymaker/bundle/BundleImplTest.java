package io.dummymaker.bundle;


import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.runners.Parameterized.Parameters;

import io.dummymaker.bundle.impl.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
@RunWith(Parameterized.class)
public class BundleImplTest extends Assert {

    private BasicBundle bundle;

    public BundleImplTest(BasicBundle bundle) {
        this.bundle = bundle;
    }

    @Parameters(name = "{index}: Bundle - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new CityBundle() },
                { new FormatBundle() },
                { new JobBundle() },
                { new StreetBundle() },
                { new DistrictBundle() },
                { new MiddleNameBundle() },
                { new CompanyBundle() },
                { new CountryBundle() },
                { new DomainBundle() },
                { new EmailServicesBundle() },
                { new FemaleNameBundle() },
                { new MaleNameBundle() },
                { new LoginBundle() },
                { new NounBundle() },
                { new PhraseBundle() },
                { new SurnameBundle() },
                { new TagsBundle() }
        });
    }

    @Test
    public void bundlePresentSizeCheck() {
        assertEquals(bundle.all().length, bundle.size());
    }

    @Test
    public void bundlePresentIndexGet() {
        int index = current().nextInt(0, bundle.size() - 1);
        assertEquals(bundle.all()[index], bundle.get(index));
    }

    @Test
    public void testIndexOutOfBound() {
        int integer = -1;
        assertNotNull(bundle.get(integer));
    }

    @Test
    public void bundlePresentRandomGet() {
        assertNotNull(bundle.random());
    }
}
