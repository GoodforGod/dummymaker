package io.dummymaker.generate;

import io.dummymaker.bundle.NicknamesPresetBundle;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates nicknames as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 05.06.2017
 */
public class NickGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        NicknamesPresetBundle bundle = new NicknamesPresetBundle();
        boolean revert = current().nextBoolean();
        boolean tuple = current().nextBoolean();

        String first = bundle.getRandom();
        String second = bundle.getRandom();

        return (!tuple)
                ? first
                : (revert) ? second + first
                            : first + second;
    }
}
