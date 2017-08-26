package io.dummymaker.bundle;

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
 * Created by GoodforGod on 20.08.2017.
 */
@RunWith(Parameterized.class)
public class BundleImplTest {

    private IPresetBundle bundle;

    public BundleImplTest(IPresetBundle bundle) {
        this.bundle = bundle;
    }

    @Parameters(name = "{index}: Bundle - ({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new TagPresetBundle() },
                { new CityPresetBundle() },
                { new CompanyPresetBundle() },
                { new CountryPresetBundle() },
                { new DomainExtensionPresetBundle() },
                { new EmailServicesPresetBundle() },
                { new FemaleNamePresetBundle() },
                { new MaleNamePresetBundle() },
                { new NicknamesPresetBundle() }
        });
    }

    @Test
    public void bundlePresentSizeCheck() {
        assertEquals(bundle.preset.size(), bundle.size());
    }

    @Test
    public void bundlePresentIndexGet() {
        Integer index = current().nextInt(0, bundle.size() - 1);
        assertEquals(bundle.preset.get(index), bundle.get(index));
    }

    @Test
    public void bundlePresentRandomGet() {
        assertNotNull(bundle.getRandom());
    }
}
