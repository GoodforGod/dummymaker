package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class MiddleNameBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "James", "William", "John", "Arthur", "Charles", "Alexander", "George", "Henry", "David", "Edward", "Peter",
            "Anthony", "Robin", "Michael", "Richard", "Francis", "Phillip", "Thomas", "Angus", "Christopher", "Frederick",
            "Nicholas", "Robert");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Александрович", "Алексеевич", "Анатольевич", "Андреевич", "Антонович", "Аркадьевич", "Артемович", "Бедросович",
            "Богданович", "Борисович", "Валентинович", "Валерьевич", "Васильевич", "Викторович", "Витальевич", "Владимирович",
            "Владиславович", "Вольфович", "Вячеславович", "Геннадиевич", "Георгиевич", "Григорьевич", "Данилович", "Денисович",
            "Дмитриевич", "Евгеньевич", "Егорович", "Ефимович", "Иванович", "Иваныч", "Игнатьевич", "Игоревич", "Ильич",
            "Иосифович", "Исаакович", "Кириллович", "Константинович", "Леонидович", "Львович", "Максимович", "Матвеевич",
            "Михайлович", "Николаевич", "Олегович", "Павлович", "Палыч", "Петрович", "Платонович", "Робертович", "Романович",
            "Саныч", "Северинович", "Семенович", "Сергеевич", "Станиславович", "Степанович", "Тарасович", "Тимофеевич",
            "Федорович", "Феликсович", "Филиппович", "Эдуардович", "Юрьевич", "Яковлевич", "Ярославович");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
