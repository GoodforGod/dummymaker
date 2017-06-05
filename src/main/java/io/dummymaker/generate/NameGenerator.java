package io.dummymaker.generate;

import io.dummymaker.bundle.FemaleNamePresetBundle;
import io.dummymaker.bundle.MaleNamePresetBundle;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates names (male & female) as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class NameGenerator extends StringGenerator {

    @Override
    public String generate() {
        int gender = current().nextInt(100);

        return (gender > 50)
                ? new MaleNamePresetBundle().getRandom()
                : new FemaleNamePresetBundle().getRandom();
    }
}
