package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 06.03.2019
 */
public final class SurnameBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson",
            "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez",
            "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green",
            "Adams", "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell",
            "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell",
            "Murphy", "Bailey", "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray",
            "Ramirez", "James", "Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross", "Henderson",
            "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler", "Simmons",
            "Foster", "Gonzales", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Иванов", "Петров", "Смирнов", "Кузнецов", "Васильев", "Попов", "Волков", "Андреев", "Сергеев", "Новиков", "Соколов",
            "Михайлов", "Алексеев", "Павлов", "Романов", "Морозов", "Макаров", "Николаев", "Егоров", "Степанов", "Орлов",
            "Козлов", "Никитин", "Захаров", "Александров", "Зайцев", "Фролов", "Белов", "Максимов", "Яковлев", "Григорьев",
            "Антонов", "Шевченко", "Лебедев", "Сидоров", "Борисов", "Кузьмин", "Медведев", "Дмитриев", "Федоров", "Семенов",
            "Миронов", "Жуков", "Матвеев", "Мельников", "Коваленко", "Тарасов", "Бондаренко", "Ильин", "Поляков", "Кравченко",
            "Сергеевич", "Сорокин", "Данилов", "Власов", "Богданов", "Фёдоров", "Семёнов", "Котов", "Чернов", "Денисов",
            "Колесников", "Карпов", "Алиев", "Абрамов", "Титов", "Баранов", "Давыдов", "Осипов", "Гусев", "Фомин", "Назаров",
            "Белый", "Тимофеев", "Филиппов", "Тихонов", "Ткаченко", "Куликов", "Гончаров", "Марков", "Беляев", "Исаев", "Калинин",
            "Бойко", "Гаврилов", "Федотов", "Мельник", "Ефимов", "Коновалов", "Афанасьев", "Филатов", "Казаков", "Комаров",
            "Щербаков", "Наумов", "Виноградов", "Савельев", "Быков", "Ковалев", "Соловьев");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
