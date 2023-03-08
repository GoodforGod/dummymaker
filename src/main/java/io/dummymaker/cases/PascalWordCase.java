package io.dummymaker.cases;

/**
 * First letter is upper case, other letters are as is: Bobby - Bobby, tonNy - TonNy
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
public final class PascalWordCase extends AbstractSeparatorCase {

    PascalWordCase() {}

    @Override
    protected String mapFirstLetter(char letter) {
        return String.valueOf(Character.toUpperCase(letter));
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
