package io.dummymaker.bundle;

import io.dummymaker.generator.Localisation;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author GoodforGod
 * @since 20.08.2017
 */
class BundleImplTests extends Assertions {

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(new CityBundle()),
                Arguments.of(new FormatBundle()),
                Arguments.of(new JobBundle()),
                Arguments.of(new StreetBundle()),
                Arguments.of(new DistrictBundle()),
                Arguments.of(new MiddleNameBundle()),
                Arguments.of(new CompanyBundle()),
                Arguments.of(new CountryBundle()),
                Arguments.of(new DomainBundle()),
                Arguments.of(new EmailServicesBundle()),
                Arguments.of(new FemaleNameBundle()),
                Arguments.of(new MaleNameBundle()),
                Arguments.of(new LoginBundle()),
                Arguments.of(new NounBundle()),
                Arguments.of(new PhraseBundle()),
                Arguments.of(new SurnameBundle()),
                Arguments.of(new TagsBundle()));
    }

    @MethodSource("data")
    @ParameterizedTest
    void bundlePresentRandomGet(Bundle bundle) {
        for (Localisation localisation : Localisation.values()) {
            assertNotNull(bundle.random(localisation));
        }
    }
}
