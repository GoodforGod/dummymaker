package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.impl.NicknamesPresetBundle;
import io.dummymaker.generator.simple.IGenerator;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates nicknames as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 05.06.2017
 */
public class NickGenerator implements IGenerator<String> {

    private final NicknamesPresetBundle bundle = new NicknamesPresetBundle();

    @Override
    public String generate() {
        boolean revert = current().nextBoolean();
        boolean tuple = current().nextBoolean();

        final String first = bundle.getRandom();
        final String second = bundle.getRandom();

        return (!tuple)
                ? first
                : (revert) ? second + first
                            : first + second;
    }
}
