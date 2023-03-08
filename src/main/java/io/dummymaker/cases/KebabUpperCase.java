package io.dummymaker.cases;

/**
 * Example: ( DummyList - DUMMY-LIST )
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
public final class KebabUpperCase extends AbstractSeparatorCase {

    KebabUpperCase() {}

    @Override
    protected String mapFirstLetter(char letter) {
        return String.valueOf(Character.toUpperCase(letter));
    }

    @Override
    protected String mapAfterSeparator(char letter) {
        return "-" + Character.toUpperCase(letter);
    }

    @Override
    protected String mapDefault(char previousLetter, char letter) {
        if (Character.isUpperCase(letter)) {
            if (Character.isUpperCase(previousLetter)) {
                return String.valueOf(Character.toUpperCase(letter));
            } else {
                return "-" + Character.toUpperCase(letter);
            }
        } else {
            return String.valueOf(Character.toUpperCase(letter));
        }
    }
}
