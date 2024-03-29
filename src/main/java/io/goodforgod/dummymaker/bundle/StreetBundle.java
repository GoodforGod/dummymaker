package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class StreetBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Coniston Avenue", "St Mary's Road", "Burton Road", "Middle Street", "Moor Road", "York Road", "Nelson Close",
            "The Crescent", "Bramble Close", "Talbot Street", "Brunswick Street", "Warwick Road", "Oak Street", "Lilac Close",
            "Richmond Avenue", "Bridge Close", "Cumberland Road", "Holly Grove", "Scott Street", "Mount Road", "Foxglove Close",
            "Cedar Avenue", "Vicarage Gardens", "Smithy Lane", "Hampton Road", "St Peter's Close", "Wharf Road", "Woodland Close",
            "Glebe Street", "Nursery Lane", "Brookside Close", "Curlew Close", "Willow Court", "Bishops Close",
            "Coronation Street", "West Street", "Cross Road", "Albion Street", "The Grange", "King Street", "Dark Lane",
            "Stanley Close", "Byron Avenue", "Southgate", "Salisbury Road", "Duke Street", "Russell Street", "Dove Close",
            "St Michael's Close", "Gipsy Lane", "Coronation Road", "Station Approach", "Derwent Drive", "Green Street",
            "Links Road", "Oak Tree Close", "The Moorings", "Glebe Road", "Canterbury Close", "Church Way", "Larch Close",
            "Manor Street", "Sherwood Avenue", "Hazel Avenue", "Kenilworth Road", "Almond Close", "Post Office Lane",
            "Coronation Avenue", "Cobden Street", "Whitehall Road", "Meadowside", "Greenway", "Manse Road", "Carr Lane",
            "Abbey Road", "Ash Close", "Coach Road", "Cedar Road", "Milton Road", "Gainsborough Road", "Brook Lane",
            "Melbourne Road", "Wordsworth Avenue", "Linnet Close", "Nightingale Road", "The Mount", "Hazel Grove", "Grove Lane",
            "Poplar Avenue", "Trafalgar Road", "Malvern Close", "Sydney Road", "The Avenue", "Little Lane", "St George's Road",
            "Rydal Close", "Grange Road", "Stoney Lane", "Westfield Road", "Chester Street");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "1-й Верхний переулок", "1-й Конной Лахты 2-й проезд", "1-й Муринский проспект", "1-й Озерковский переулок",
            "1-й Предпортовый проезд", "1-й проезд, Шушары", "1-й Рабфаковский переулок", "1-й Рыбацкий проезд",
            "1-я Алексеевская улица", "1-я аллея, Торики", "1-я Березовая аллея", "1-я Жерновская улица",
            "1-я Заводская улица, Старо-Паново", "1-я Конная Лахта улица", "1-я Красноармейская улица",
            "1-я линия Васильевского острова", "1-я линия, Володарский", "1-я линия, Ново-Ковалево", "1-я линия, Торики",
            "1-я Никитинская улица", "1-я Полевая улица", "1-я Поперечная улица", "1-я Советская улица",
            "1-я Тупиковая улица, Ново-Ковалево", "1-я Утиная улица", "1-я Шоссейная улица, Старо-Паново",
            "10-я Красноармейская улица", "10-я линия Васильевского острова", "10-я Советская улица",
            "11-я Красноармейская улица", "11-я линия Васильевского острова", "12-я Красноармейская улица",
            "12-я линия Васильевского острова", "13-я Красноармейская улица", "13-я линия Васильевского острова",
            "14-я линия Васильевского острова", "15-я линия Васильевского острова", "16-я линия Васильевского острова",
            "17-я линия Васильевского острова", "18-я линия Васильевского острова", "19-я линия Васильевского острова",
            "2-й Верхний переулок", "2-й Луч улица", "2-й Муринский проспект", "2-й Озерковский переулок",
            "2-й Предпортовый проезд", "2-й проезд, Горелово", "2-й Рабфаковский переулок", "2-й Рыбацкий проезд",
            "2-я Алексеевская улица", "2-я аллея, Лесное", "2-я Березовая аллея", "2-я Жерновская улица",
            "2-я Заводская улица, Старо-Паново", "2-я Комсомольская улица", "2-я Конная Лахта улица", "2-я Красноармейская улица",
            "2-я линия Васильевского острова", "2-я линия, Володарский", "2-я линия, Ново-Ковалево", "2-я линия, Торики",
            "2-я Никитинская улица", "2-я Полевая улица", "2-я Поперечная улица", "2-я Семеновская улица", "2-я Советская улица",
            "2-я Тупиковая улица, Ново-Ковалево", "2-я Утиная улица", "2-я Шоссейная улица, Старо-Паново",
            "20-я линия Васильевского острова", "21-я линия Васильевского острова", "22-я линия Васильевского острова",
            "23-я линия Васильевского острова", "24-я линия Васильевского острова", "25-я линия Васильевского острова",
            "26-я линия Васильевского острова", "27-я линия Васильевского острова", "28-я линия Васильевского острова",
            "29-я линия, Васильевского острова", "3-й Верхний переулок", "3-й Озерковский переулок", "3-й Предпортовый проезд",
            "3-й проезд, Шушары", "3-й Рабфаковский переулок", "3-й Рыбацкий проезд", "3-я Жерновская улица",
            "3-я Конная Лахта улица", "3-я Красноармейская улица", "3-я линия 1-й половины улица", "3-я линия 2-й половины улица",
            "3-я линия Васильевского острова", "3-я линия, Володарский, Володарский", "3-я линия, Ново-Ковалево",
            "3-я линия, Торики", "3-я Поперечная улица", "3-я Советская улица", "300-летия Санкт-Петербурга парк",
            "4-й Верхний переулок", "4-й Предпортовый проезд", "4-й проезд, Горелово", "4-й Рыбацкий проезд",
            "4-я Жерновская улица", "4-я Красноармейская улица", "4-я линия Васильевского острова", "4-я линия, Володарский",
            "4-я линия, Ново-Ковалево", "4-я Поперечная улица", "4-я Советская улица", "5-й Верхний переулок",
            "5-й Предпортовый проезд", "5-й проезд, Горелово", "5-й Рыбацкий проезд", "5-я Жерновская улица",
            "5-я Красноармейская улица", "5-я линия Васильевского острова", "5-я линия, Володарский", "5-я линия, Ново-Ковалево",
            "5-я Поперечная улица", "5-я Советская улица", "6-й Верхний переулок", "6-й Предпортовый проезд",
            "6-й Рыбацкий проезд", "6-я аллея, Климовец", "6-я Жерновская улица", "6-я Красноармейская улица",
            "6-я линия Васильевского острова", "6-я линия, Володарский", "6-я линия, Электросила", "6-я Поперечная улица",
            "6-я Советская улица", "7-й Верхний переулок", "7-й Предпортовый проезд", "7-я Красноармейская улица",
            "7-я линия Васильевского острова", "7-я Поперечная улица", "7-я Советская улица", "8-й Верхний переулок",
            "8-й Предпортовый проезд", "8-й Рыбацкий проезд", "8-я Красноармейская улица", "8-я линия Васильевского острова",
            "8-я Поперечная улица", "8-я Советская улица", "9-й Верхний переулок", "9-я Красноармейская улица",
            "9-я линия Васильевского острова", "9-я Советская улица", "Абросимова улица", "Авангардная улица",
            "Авиаконструкторов проспект", "Авиационная улица", "Авиационная улица, Горелово", "Аврова улица",
            "Австрийская площадь", "Автобусная улица", "Автобусный переулок", "Автовская улица", "Автогенная улица",
            "Автомобильная улица", "Агатов переулок", "Адмирала Коновалова улица", "Адмирала Лазарева набережная",
            "Адмирала Трибуца улица", "Адмирала Черокова улица", "Адмиралтейская набережная", "Адмиралтейский проезд",
            "Адмиралтейский проспект", "Адмиралтейского канала набережная", "Адмиральский проезд", "Азовская улица",
            "Азовский переулок", "Академика Байкова улица", "Академика Глушко алеея", "Академика Иоффе площадь",
            "Академика Климова площадь", "Академика Константинова улица", "Академика Крылова улица", "Академика Лебедева улица",
            "Академика Лихачева алеея", "Академика Лихачева площадь");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
