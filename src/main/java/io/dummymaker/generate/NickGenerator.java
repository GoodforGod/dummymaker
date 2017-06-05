package io.dummymaker.generate;

import io.dummymaker.bundle.NicknamesPresentBundle;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Default Comment
 *
 * @author GoodforGod (Anton Kurako)
 * @since 05.06.2017
 */
public class NickGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        NicknamesPresentBundle bundle = new NicknamesPresentBundle();
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
