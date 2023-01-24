package io.dummymaker.bundle;

import io.dummymaker.generator.Localisation;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class BundleImplTests extends Assertions {

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

    @MethodSource("data")
    @ParameterizedTest
    void bundlePresentRandomGet(Bundle bundle) {
        for (Localisation localisation : Localisation.values()) {
            assertNotNull(bundle.random(localisation));
        }
    }
}
