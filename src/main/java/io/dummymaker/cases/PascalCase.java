package io.dummymaker.cases;

/**
 * First letter is upper case, letter after separator upper case, other letters are lower case is:
 * Bobby - Bobby, tonNy - Tonny
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
final class PascalCase extends AbstractSeparatorCase {

    PascalCase() {}

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
        return String.valueOf(Character.toLowerCase(letter));
    }
}
