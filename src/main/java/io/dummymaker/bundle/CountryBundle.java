package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with country names string values
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class CountryBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia",
            "Austria", "Azerbaijan", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan",
            "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burma", "Burundi",
            "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Republic", "Chad", "Chile", "China", "Colombia",
            "Comoros", "Costa Rica", "Cote dIvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic",
            "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador",
            "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Federated States of Micronesia",
            "Fiji", "Finland", "France", "Gabon", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea",
            "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
            "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
            "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia",
            "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius",
            "Mexico", "Moldova", "Monaco", "Mongolia", "Morocco", "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands",
            "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Panama",
            "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Republic of the Congo",
            "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa",
            "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia and Montenegro", "Seychelles",
            "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",
            "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan",
            "Tanzania", "Thailand", "The Bahamas", "The Gambia", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
            "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay",
            "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Афганистан", "Албания", "Алжир", "Андорра", "Ангола", "Антигуа и Барбуда", "Аргентина", "Армения", "Австралия",
            "Австрия", "Азербайджан", "Бахрейн", "Бангладеш", "Барбадос", "Беларусь", "Бельгия", "Белиз", "Бенин", "Бутан",
            "Боливия", "Босния и Герцеговина", "Ботсвана", "Бразилия", "Бруней", "Болгария", "Буркина-Фасо", "Бирма", "Бурунди",
            "Камбоджа", "Камерун", "Канада", "Кабо-Верде", "Центральноафриканская Республика", "Чад", "Чили", "Китай", "Колумбия",
            "Коморские Острова", "Коста-Рика", "Коте dIvoire", "Хорватия", "Куба", "Кипр", "Чешская Республика",
            "Демократическая Республика Конго", "Дания", "Джибути", "Доминика", "Доминиканская Республика", "Восточный Тимор",
            "Эквадор", "Египет", "Сальвадор", "Экваториальная Гвинея", "Эритрея", "Эстония", "Эстония", "Эфиопия",
            "Федеративные Штаты Микронезии", "Фиджи", "Финляндия", "Франция", "Габон", "Грузия", "Германия", "Гана", "Греция",
            "Гренада", "Гватемала", "Гвинея", "Гвинея-Бисау", "Гайана", "Гаити", "Гондурас", "Венгрия", "Исландия", "Индия",
            "Индонезия", "Иран", "Ирак", "Ирландия", "Израиль", "Италия", "Ямайка", "Япония", "Иордания", "Казахстан", "Кения",
            "Кирибати", "Кувейт", "Кыргызстан", "Лаос", "Латвия", "Ливан", "Лесото", "Либерия", "Ливия", "Лихтенштейн", "Литва",
            "Люксембург", "Македония", "Мадагаскар", "Малави", "Малайзия", "Мальдивы", "Мали", "Мальта", "Маршалловы Острова",
            "Мавритания", "Маврикий", "Мексика", "Молдова", "Монако", "Монголия", "Марокко", "Мозамбик", "Намибия", "Науру",
            "Непал", "Нидерланды", "Новая Зеландия", "Никарагуа", "Нигер", "Нигерия", "Северная Корея", "Норвегия", "Оман",
            "Пакистан", "Палау", "Панама", "Папуа — Новая Гвинея", "Парагвай", "Перу", "Филиппины", "Польша", "Португалия",
            "Катар", "Республика Конго", "Румыния", "Россия", "Руанда", "Сент-Китс и Невис", "Сент-Люсия",
            "Сент-Винсент и Гренадины", "Самоа", "Сан-Марино", "Сан-Томе и Принсипи", "Саудовская Аравия", "Сенегал",
            "Сербия и Черногория", "Сейшельские Острова", "Сьерра-Леоне", "Сингапур", "Словакия", "Словения",
            "Соломоновы Острова", "Сомали", "Южная Африка", "Южная Корея", "Испания", "Шри-Ланка", "Судан", "Суринам",
            "Свазиленд", "Швеция", "Швейцария", "Сирия", "Тайвань", "Таджикистан", "Танзания", "Таиланд", "Багамские Острова",
            "Гамбия", "Того", "Тонга", "Тринидад и Тобаго", "Тунис", "Турция", "Туркменистан", "Тувалу", "Уганда", "Украина",
            "Объединенные Арабские Эмираты", "Соединенное Королевство", "Соединенные Штаты", "Уругвай", "Узбекистан", "Вануату",
            "Ватикан", "Венесуэла", "Вьетнам", "Йемен", "Замбия", "Зимбабве");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
