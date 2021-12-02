package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CityBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.bundle.impl.StreetBundle;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Full address with street, city, district and house number
 *
 * @author GoodforGod
 * @see AddressGenerator
 * @since 16.07.2019
 */
public class AddressGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("addr(ess)?", CASE_INSENSITIVE);

    private final IBundle streetBundle = new StreetBundle();
    private final IBundle districtBundle = new DistrictBundle();
    private final IBundle cityBundle = new CityBundle();

    @Override
    public @NotNull String generate() {
        return cityBundle.random()
                + ", " + districtBundle.random()
                + ", " + streetBundle.random()
                + " " + CollectionUtils.random(20);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
