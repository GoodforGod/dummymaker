package io.dummymaker.generator.simple.number;


import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;


/**
 * Generates random UTF-8 character in range of 33-254
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class CharGenerator implements IGenerator<Character> {

    @Override
    public @NotNull Character generate() {
        return ((char) CollectionUtils.random(33, 255));
    }
}
