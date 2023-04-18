package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Contains male names as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 30.05.2017
 */
public final class MaleNameBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Thomas", "Christopher",
            "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven", "Edward", "Brian", "Ronald", "Anthony", "Kevin",
            "Jason", "Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey", "Frank", "Scott", "Eric", "Stephen", "Andrew",
            "Raymond", "Gregory", "Joshua", "Jerry", "Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl",
            "Arthur", "Ryan", "Roger", "Joe", "Juan", "Jack", "Albert", "Jonathan", "Justin", "Terry", "Gerald", "Keith",
            "Robert", "Samuel", "Willie", "Ralph", "Lawrence", "Nicholas", "Roy", "Benjamin", "Bruce", "Brandon", "Adam", "Harry",
            "Fred", "Wayne", "Billy", "Steve", "Louis", "Jeremy", "Aaron", "Randy", "Howard", "Eugene", "Carlos", "Russell",
            "Bobby", "Victor", "Martin", "Ernest", "Phillip", "Todd", "Jesse", "Craig", "Alan", "Shawn", "Clarence", "Sean",
            "Philip", "Chris", "Johnny", "Earl", "Jimmy", "Antonio", "Danny", "Bryan", "Tony", "Luis", "Mike", "Stanley",
            "Leonard", "Nathan", "Dale", "Manuel", "Rodney", "Curtis", "Norman", "Allen", "Marvin", "Vincent", "Glenn", "Jeffery",
            "Travis", "Jeff", "Chad", "Jacob", "Lee", "Melvin", "Alfred", "Kyle", "Francis", "Bradley", "Jesus", "Herbert",
            "Frederick", "Ray", "Joel", "Edwin", "Don", "Eddie", "Ricky", "Troy", "Randall", "Barry", "Alexander", "Bernard",
            "Mario", "Leroy", "Francisco", "Marcus", "Micheal", "Theodore", "Clifford", "Miguel", "Oscar", "Jay", "Jim", "Tom",
            "Calvin", "Alex", "Jon", "Ronnie", "Bill", "Lloyd", "Tommy", "Leon", "Derek", "Warren", "Darrell", "Jerome", "Floyd",
            "Leo", "Alvin", "Tim", "Wesley", "Gordon", "Dean", "Greg", "Jorge", "Dustin", "Pedro", "Derrick", "Dan", "Lewis",
            "Zachary", "Corey", "Herman", "Maurice", "Vernon", "Roberto", "Clyde", "Glen", "Hector", "Shane", "Ricardo", "Sam",
            "Rick", "Lester", "Brent", "Ramon", "Charlie", "Tyler", "Gilbert", "Gene", "Marc", "Reginald", "Ruben", "Brett",
            "Angel", "Nathaniel", "Jordon", "Danilo", "Claud", "Val", "Sherwood", "Raymon", "Rayford", "Cristobal", "Ambrose",
            "Titus", "Hyman", "Felton", "Ezequiel", "Erasmo", "Stanton", "Lonny", "Len", "Ike", "Milan", "Lino", "Jarod", "Herb",
            "Andreas", "Walton", "Rhett", "Palmer", "Jude", "Douglass", "Cordell", "Oswaldo", "Ellsworth", "Virgilio", "Toney",
            "Nathanael", "Del", "Britt", "Benedict", "Mose", "Hong", "Leigh");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Август", "Авдей", "Аверкий", "Аверьян", "Авксентий", "Автоном", "Агап", "Агафон", "Аггей", "Адам", "Адриан",
            "Азарий", "Александр", "Алексей", "Амвросий", "Амос", "Ананий", "Анатолий", "Андрей", "Андриан", "Андрон", "Андроник",
            "Аникей", "Аникита", "Анисим", "Антип", "Антон", "Аполлинарий", "Аполлон", "Арефий", "Аристарх", "Аркадий", "Арсений",
            "Артем", "Артемий", "Архип", "Аскольд", "Афанасий", "Афиноген", "Бажен", "Богдан", "Болеслав", "Борис", "Борислав",
            "Боян", "Бронислав", "Вадим", "Валентин", "Валерий", "Валерьян", "Варфоломей", "Василий", "Вацлав", "Венедикт",
            "Вениамин", "Викентий", "Виктор", "Викторин", "Виссарион", "Виталий", "Владимир", "Владислав", "Влас", "Всеволод",
            "Вячеслав", "Гавриил", "Гаврила", "Галактион", "Гедеон", "Геннадий", "Георгий", "Герасим", "Герман", "Глеб", "Гордей",
            "Григорий", "Гурий", "Давид", "Давыд", "Даниил", "Данила", "Дементий", "Демид", "Демьян", "Денис", "Дмитрий",
            "Дорофей", "Евгений", "Евграф", "Евдоким", "Евлампий", "Евсей", "Евстафий", "Евстигней", "Егор", "Елизар", "Елисей",
            "Емельян", "Епифан", "Еремей", "Ермил", "Ермолай", "Ерофей", "Ефим", "Ефрем", "Захар", "Зиновий", "Зосима", "Иван",
            "Игнатий", "Игорь", "Измаил", "Изот", "Изяслав", "Илларион", "Илья", "Иннокентий", "Иосиф", "Ипат", "Ипатий",
            "Ипполит", "Ираклий", "Исай", "Исидор", "Казимир", "Каллистрат", "Капитон", "Карл", "Карп", "Касьян", "Ким", "Кир",
            "Кирилл", "Клавдий", "Клементий", "Клим", "Климент", "Кондрат", "Кондратий", "Константин", "Корней", "Корнил",
            "Корнилий", "Кузьма", "Лавр", "Лаврентий", "Ладислав", "Лазарь", "Лев", "Леон", "Леонид", "Леонтий", "Лонгин", "Лука",
            "Лукьян", "Лучезар", "Любомир", "Макар", "Максим", "Максимильян", "Мариан", "Марк", "Мартын", "Матвей", "Мефодий",
            "Мечислав", "Милан", "Милен", "Мина", "Мирон", "Мирослав", "Митрофан", "Михаил", "Михей", "Модест", "Моисей", "Мокий",
            "Мстислав", "Назар", "Натан", "Наум", "Нестор", "Никандр", "Никанор", "Никита", "Никифор", "Никодим", "Николай",
            "Никон", "Нифонт", "Олег", "Олимпий", "Онисим", "Онуфрий", "Орест", "Осип", "Остап", "Павел", "Панкрат",
            "Пантелеймон", "Парамон", "Парфем", "Пахом", "Петр", "Пимен", "Платон", "Поликарп", "Порфирий", "Потап", "Прокл",
            "Прохор", "Радим", "Ратибор", "Ратмир", "Родион", "Роман", "Ростислав", "Рубен", "Руслан", "Рюрик", "Савва",
            "Савватий", "Савелий", "Самсон", "Самуил", "Светозар", "Святослав", "Севастьян", "Селиван", "Семен", "Серафим",
            "Сергей", "Сидор", "Сила", "Сильвестр", "Симон", "Сократ", "Соломон", "Софрон", "Спартак", "Спиридон", "Станимир",
            "Станислав", "Степан", "Стоян", "Тарас", "Твердислав", "Терентий", "Тимофей", "Тимур", "Тит", "Тихон", "Трифон",
            "Трофим", "Ульян", "Устин", "Фаддей", "Федор", "Федосий", "Федот", "Феликс", "Феоктист", "Феофан", "Ферапонт",
            "Филарет", "Филимон", "Филипп", "Флорентин", "Фока", "Фома", "Фортунат", "Фотий", "Фрол", "Харитон", "Харлампий",
            "Чеслав", "Эдуард", "Эмиль", "Эраст", "Эрнест", "Эрнст", "Юлиан", "Юлий", "Юрий", "Яков", "Якуб", "Ян", "Ярослав");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
