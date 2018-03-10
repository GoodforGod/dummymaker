package io.dummymaker.bundle;

import io.dummymaker.bundle.impl.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.runners.Parameterized.Parameters;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
@RunWith(Parameterized.class)
public class BundleImplTest {

    private BasicBundle bundle;

    public BundleImplTest(BasicBundle bundle) {
        this.bundle = bundle;
    }

    @Parameters(name = "{index}: Bundle - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new CityPresetBundle() },
                { new CompanyPresetBundle() },
                { new CountryPresetBundle() },
                { new DomainExtensionPresetBundle() },
                { new EmailServicesPresetBundle() },
                { new FemaleNamePresetBundle() },
                { new MaleNamePresetBundle() },
                { new NicknamesPresetBundle() },
                { new NounPresetBundle() },
                { new PhrasePresetBundle() },
                { new TagPresetBundle() }
        });
    }

    @Test
    public void bundlePresentSizeCheck() {
        assertEquals(bundle.getAll().size(), bundle.size());
    }

    @Test
    public void bundlePresentIndexGet() {
        Integer index = current().nextInt(0, bundle.size() - 1);
        assertEquals(bundle.getAll().get(index), bundle.get(index));
    }

    @Test
    public void testIndexOutOfBound() {
        Integer integer = -1;
        assertNotNull(bundle.get(integer));
    }

    @Test
    public void bundlePresentRandomGet() {
        assertNotNull(bundle.getRandom());
    }
}
