package io.dummymaker.cases;

/**
 * First letter is lower case, other letters are as is: Bobby - bobby, TonNy - tonNy
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
final class CamelWordCase extends AbstractSeparatorCase {

    CamelWordCase() {}

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
        return String.valueOf(letter);
    }
}
