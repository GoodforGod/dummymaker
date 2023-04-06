package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class CategoryBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Appliances", "Apps", "Games", "Arts", "Crafts", "Sewing", "Automotive Parts", "Baby", "Beauty", "Personal Care",
            "Books", "CDs", "Vinyl", "Cell Phones", "Accessories", "Clothing", "Shoes", "Jewelry", "Collectibles", "Fine Art",
            "Computers", "Electronics", "Garden", "Outdoor", "Grocery", "Gourmet Food", "Handmade", "Health", "Household",
            "Baby Care", "Kitchen", "Industrial", "Scientific", "Kindle", "Luggage", "Travel Gear", "Movies", "TV",
            "Musical Instruments", "Office Products", "Pet Supplies", "Sports", "Outdoors", "Tools", "Home Improvement", "Toys",
            "Video Games");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Техника", "Программы", "Игры", "Искусство", "Ремесла", "Шитье", "Автозапчасти", "Малыш", "Красота", "Личная гигиена",
            "Книги", "CD", "Винил", "Мобильные телефоны", "Аксессуары", "Одежда", "Обувь", "Ювелирные изделия",
            "Коллекционирование", "Изобразительное искусство", "Компьютеры", "Электроника", "Сад", "На природе", "Бакалея",
            "Деликатесы", "Ручная работа", "Здоровье", "Домашнее хозяйство", "Уход за ребенком", "Кухня", "Промышленный",
            "Научный", "Разжечь", "Багаж", "Туристическое снаряжение", "Кино", "Телевидение", "Музыкальные инструменты",
            "Офисные товары", "Товары для животных", "Спорт", "На открытом воздухе", "Инструменты", "Благоустройство дома",
            " Игрушки", "Видеоигры");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
