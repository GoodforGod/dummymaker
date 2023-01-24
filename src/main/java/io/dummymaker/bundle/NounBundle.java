package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with noun words string values
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class NounBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "time", "year", "people", "way", "day", "man", "thing", "woman", "life", "child", "world", "school", "state",
            "family", "student", "group", "country", "problem", "hand", "part", "place", "case", "week", "company", "system",
            "program", "question", "work", "government", "number", "night", "point", "home", "water", "room", "mother", "area",
            "money", "story", "fact", "month", "lot", "right", "study", "book", "eye", "job", "word", "business", "issue", "side",
            "kind", "head", "house", "service", "friend", "father", "power", "hour", "game", "line", "end", "member", "law",
            "car", "city", "community", "name", "president", "team", "minute", "idea", "kid", "body", "information", "back",
            "parent", "face", "others", "level", "office", "door", "health", "person", "art", "war", "history", "party", "result",
            "change", "morning", "reason", "research", "girl", "guy", "moment", "air", "teacher", "force", "education");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "время", "год", "люди", "путь", "день", "мужчина", "вещь", "женщина", "жизнь", "ребенок", "мир", "школа",
            "государство", "семья", "ученик", "группа", "страна", "проблема", "рука", "часть", "место", "случай", "неделя",
            "компания", "система", "программа", "вопрос", "правительство", "число", "ночь", "точка", "дом", "вода",
            "комната", "мать", "площадь", "деньги", "история", "факт", "месяц", "много", "правильно", "учеба", "книга", "глаз",
            "работа", "слово", "бизнес", "выпуск", "сторона", "вид", "вид", "голова", "дом", "дом", "служба", "услуга", "работа",
            "слово", "бизнес", "выпуск", "сторона", "вид", "голова", "дом", "дом", "служба", "друг", "отец", "власть", "час",
            "игра", "линия", "конец", "член", "закон", "автомобиль", "город", "сообщество", "имя", "президент", "команда",
            "минута", "идея", "малыш", "тело", "информация", "назад", "родитель", "лицо", "другие", "уровень", "офис", "дверь",
            "здоровье", "человек", "искусство", "война", "история", "вечеринка", "результат", "изменение", "утро", "разум",
            "исследование", "девочка", "парень", "момент", "воздух", "учитель", "сила", "образование");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
