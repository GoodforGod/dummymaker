package io.dummymaker.cases;

/**
 * First letter is lower case, letter after separator lower case, other letters are as is: Bobby -
 * bobby, TonNy - tonny
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
final class CamelCase extends AbstractSeparatorCase {

    CamelCase() {}

    @Override
    protected String mapFirstLetter(char letter) {
        return String.valueOf(Character.toLowerCase(letter));
    }

    @Override
    protected String mapAfterSeparator(char letter) {
        return String.valueOf(Character.toUpperCase(letter));
    }

    @Override
    protected String mapDefault(char previousLetter, char letter) {
        return String.valueOf(Character.toLowerCase(letter));
    }
}
