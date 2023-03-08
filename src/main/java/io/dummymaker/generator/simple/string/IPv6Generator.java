package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2023
 */
public final class IPv6Generator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("ipv6", CASE_INSENSITIVE);
    private static final List<Character> CHARACTERS = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f');

    @Override
    public @NotNull String get() {
        return IntStream.range(0, 8)
                .mapToObj(i -> CollectionUtils.random(CHARACTERS)
                        + CollectionUtils.random(CHARACTERS)
                        + CollectionUtils.random(CHARACTERS)
                        + CollectionUtils.random(CHARACTERS) + "")
                .collect(Collectors.joining(":"));
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
