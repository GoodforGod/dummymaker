package io.dummymaker.bundle;

import org.junit.Test;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by GoodforGod on 20.08.2017.
 */
public class BundleImplTest {

    @Test
    public void tagPresentSizeCheck() {
        IPresetBundle bundle = new TagPresetBundle();
        assertEquals(bundle.preset.size(), bundle.size());
    }

    @Test
    public void nickPresentIndexGet() {
        IPresetBundle bundle = new NicknamesPresetBundle();
        Integer index = current().nextInt(0, bundle.size() - 1);
        assertEquals(bundle.preset.get(index), bundle.get(index));
    }

    //<editor-fold desc="All presents random get access">

    @Test
    public void cityPresentRandom() {
        IPresetBundle bundle = new CityPresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void companyPresentRandom() {
        IPresetBundle bundle = new CompanyPresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void countryPresentRandom() {
        IPresetBundle bundle = new CountryPresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void domainPresentRandom() {
        IPresetBundle bundle = new DomainExtensionPresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void emailServicesPresentRandom() {
        IPresetBundle bundle = new EmailServicesPresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void femaleNamePresentRandom() {
        IPresetBundle bundle = new FemaleNamePresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void maleNamePresentRandom() {
        IPresetBundle bundle = new MaleNamePresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void nickPresentRandom() {
        IPresetBundle bundle = new NicknamesPresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void phrasePresentRandom() {
        IPresetBundle bundle = new PhrasePresetBundle();
        assertNotNull(bundle.getRandom());
    }

    @Test
    public void tagPresentRandom() {
        IPresetBundle bundle = new TagPresetBundle();
        assertNotNull(bundle.getRandom());
    }
    //</editor-fold>
}
