package io.dummymaker.cases;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NamingCasesTests extends Assertions {

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(NamingCases.DEFAULT, "I am Book   ROOK   ", "I am Book   ROOK   "),
                Arguments.of(NamingCases.LOWER_CASE, "I am Book   ROOK   ", "i am book   rook   "),
                Arguments.of(NamingCases.UPPER_CASE, "I am Book   ROOK   ", "I AM BOOK   ROOK   "),
                Arguments.of(NamingCases.PASCAL_CASE, "I am Book   ROOK   ", "IAmBookRook"),
                Arguments.of(NamingCases.PASCAL_WORD_CASE, "I am Book   ROOK   ", "IAmBookROOK"),
                Arguments.of(NamingCases.CAMEL_CASE, "I am Book   ROOK   ", "iAmBookRook"),
                Arguments.of(NamingCases.CAMEL_WORD_CASE, "I am Book   ROOK   ", "iAmBookROOK"),
                Arguments.of(NamingCases.SNAKE_LOWER_CASE, "I am Book   ROOK   ", "i_am_book_rook"),
                Arguments.of(NamingCases.SNAKE_UPPER_CASE, "I am Book   ROOK   ", "I_AM_BOOK_ROOK"),
                Arguments.of(NamingCases.KEBAB_LOWER_CASE, "I am Book   ROOK   ", "i-am-book-rook"),
                Arguments.of(NamingCases.KEBAB_UPPER_CASE, "I am Book   ROOK   ", "I-AM-BOOK-ROOK"),
                Arguments.of(NamingCases.DEFAULT, "MyTomEx", "MyTomEx"),
                Arguments.of(NamingCases.LOWER_CASE, "MyTomEx", "mytomex"),
                Arguments.of(NamingCases.UPPER_CASE, "MyTomEx", "MYTOMEX"),
                Arguments.of(NamingCases.KEBAB_LOWER_CASE, "MyTomEx", "my-tom-ex"),
                Arguments.of(NamingCases.KEBAB_UPPER_CASE, "MyTomEx", "MY-TOM-EX"),
                Arguments.of(NamingCases.SNAKE_LOWER_CASE, "MyTomEx", "my_tom_ex"),
                Arguments.of(NamingCases.SNAKE_UPPER_CASE, "MyTomEx", "MY_TOM_EX"),
                Arguments.of(NamingCases.CAMEL_WORD_CASE, "MyTomEx", "myTomEx"),
                Arguments.of(NamingCases.PASCAL_WORD_CASE, "myTomEx", "MyTomEx")

        );
    }

    @MethodSource("data")
    @ParameterizedTest
    void checkCase(NamingCase textNamingCase, String value, String expected) {
        assertEquals(expected, textNamingCase.apply(value));
    }
}
