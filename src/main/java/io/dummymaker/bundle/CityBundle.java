package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with city names string values
 *
 * @author Anton Kurako (GoodforGod) Anton Kurako
 * @since 07.06.2017
 */
public final class CityBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Moscow", "Saint-Petersburg", "Krasnoyarsk", "Kabul", "Tirana", "Algiers", "Andorra", "Luanda", "Buenos", "Yerevan",
            "Canberra", "Vienna", "Baku", "Nassau",
            "Manama", "Dhaka", "Bridgetown", "Minsk", "Brussels", "Belmopan", "Porto-Novo", "Thimphu", "Sucre", "Paz", "and",
            "Gaborone", "Brasilia", "Bandar", "Sofia", "Faso", "Bujumbura", "Verde", "Phnom", "Yaounde", "Ottawa", "African",
            "Djamena", "Santiago", "Beijing", "Bogota", "Moroni", "Rica", "Ivoire", "Zagreb", "Havana", "Nicosia", "Republic",
            "Copenhagen", "Djibouti", "Roseau", "Republic", "Quito", "Cairo", "Salvador", "Guinea", "Asmara", "Tallinn", "Addis",
            "Suva", "Helsinki", "Paris", "Libreville", "Banjul", "Tbilisi", "Berlin", "Accra", "Athens", "Saint", "Guatemala",
            "Conakry", "Bissau", "Georgetown", "Port-au-Prince", "Tegucigalpa", "Budapest", "Reykjavik", "New", "Jakarta",
            "Tehran", "Baghdad", "Dublin", "Jerusalem", "Rome", "Kingston", "Tokyo", "Amman", "Astana", "Nairobi", "Tarawa",
            "Pristina", "Kuwait", "Bishkek", "Vientiane", "Riga", "Beirut", "Maseru", "Monrovia", "Tripoli", "Vaduz", "Vilnius",
            "Luxembourg", "Antananarivo", "Lilongwe", "Kuala", "Male", "Bamako", "Valletta", "Islands", "Nouakchott", "Port",
            "Mexico", "Palikir", "Chisinau", "Monaco", "Ulaanbaatar", "Podgorica", "Rabat", "Maputo", "Windhoek", "Yaren",
            "Kathmandu", "Amsterdam", "Zealand", "Managua", "Niamey", "Abuja", "Oslo", "Muscat", "Islamabad", "Ngerulmud",
            "Jerusalem", "Panama", "New", "Asuncion", "Lima", "Manila", "Warsaw", "Lisbon", "Doha", "Bucharest",
            "Kigali", "Kitts", "Lucia", "Vincent", "Apia", "Marino", "Tome", "Arabia", "Dakar", "Belgrade", "Victoria", "Leone",
            "Singapore", "Bratislava", "Ljubljana", "Islands", "Mogadishu", "Madrid", "Lanka", "Khartoum", "Paramaribo",
            "Mbabane", "Stockholm", "Bern", "Damascus", "Taipei", "Dushanbe", "Dodoma", "Bangkok", "Dili", "Lome", "and", "Tunis",
            "Ankara", "Ashgabat", "Funafuti", "Kampala", "Kyiv", "Arab", "Kingdom", "Montevideo", "Tashkent", "Caracas", "Hanoi",
            "Sanaa", "Lusaka", "Harare");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Москва", "Санкт-Петербург", "Красноярск", "Кабул", "Тирана", "Алжир", "Андорра", "Луанда", "Ну", "Ереван",
            "Канберра", "Вена.Баку", "Нассау", "Манама", "Дакка", "Бриджтаун", "Минск", "Брюссель", "Бельмопан", "Порто-Ново",
            "Тхимпху", "Сахар", "Мира", "Габороне", "Бразилия", "Бандар", "София", "Фасо", "Бужумбура", "Грин", "Пном", "Яунде",
            "Оттава", "Африкан", "Джамена", "Джеймс", "Пекин", "Богота", "Морони", "Рика", "Берег Слоновой Кости", "Загреб",
            "Гавана", "Никосия", "Республика", "Копенгаген", "Джибути", "Розо", "Республика", "Кито", "Каир", "Сальвадор",
            "Гвинея", "Асмэра", "Таллинн", "Аддис", "Сува", "Хельсинки", "Париж", "Либревиль", "Банжул", "Тбилиси", "Берлин",
            "Аккра", "Афины", "Сент", "Гватемала", "Конакри", "Бисау", "Джорджтаун", "Порт-о-Пренс", "Тегусигальпа", "Будапешт",
            "Рейкьявик", "Нью", "Джакарта", "Тегеран", "Багдад", "Дублин", "Иерусалим", "Рим", "Кингстон", "Токио", "Амман",
            "Астана", "Найроби", "Тарава", "Приштина", "Кувейт", "Бишкек", "Вьентьян ", "Рига", "Бейрут", "Масеру", "Монровия",
            "Триполи", "Вадуц", "Вильнюс", "Люксембург", "Антананариву", "Лилонгве", "Куала", "Мале", "Бамако", "Валлетта",
            "Острова", "Нуакшот", "Порт", "Мексика", "Паликир", "Кишинев", "Монако", "Улан-Батор", "Подгорица", "Рабат", "Мапуту",
            "Виндхук", "Ярен", "Катманду", "Амстердам", "Зеландия", "Манагуа", "Ниамей", "Абуджа", "Осло", "Маскат", "Исламабад",
            "Нгерулмуд", "Иерусалим", "Панама", "Новый", "Успенский", "Лима", "Манила", "Варшава", "Лиссабон", "Доха", "Бухарест",
            "Москва", "Кигали", "Киттс", "Лючия", "Винсент", "Апиа", "Марин", "Томе", "Аравия", "Дакар", "Белград", "Виктория",
            "Лайон", "Сингапур", "Братислава", "Любляна", "Могадишо", "Мадрид", "Ланка", "Хартум", "Парамарибо", "Мбабане",
            "Стокгольм", "Берн", "Дамаск", "Тайбэй", "Душанбе", "Додома", "Бангкок", "Дили", "Ломе", "А", "Тунис", "Анкара",
            "Ашхабад", "Фунафути", "Кампала", "Киев", "Араб", "Королевство", "Монтевидео", "Ташкент", "Каракас", "Ханой");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
