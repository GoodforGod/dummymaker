package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Product names bundle
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.7.2020
 */
public final class ProductBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Minerals", "Refined petroleum", "Automobiles", "Machinery and mechanical appliances", "Organic chemicals",
            "Pharmaceutical products", "Iron and steel", "Textiles", "Knit apparel and accessories", "Electrical machinery",
            "Cereal", "Seafood", "Iron and Steel articles", "Cotton", "Plastics", "Ships and marine equipment", "Clothing",
            "Aluminium", "Meat", "Miscellaneous Chemical products", "Refined petroleum", "Jewellery", "Pharmaceuticals", "Rice",
            "Cars", "Vegetable Saps", "Raw Cotton", "Broadcasting Equipment", "Iron ore", "Non-Retail Pure Cotton Yarn",
            "Car parts", "Frozen Beef", "Nitrogen Heterocyclic Compounds", "Non-Knit Women Suits", "Diamonds",
            "Cyclic Hydrocarbons", "Refined Copper", "Raw sugar", "Soybean meal", "House Linens");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Минералы", "Нефтепродукты", "Автомобили", "Машины и механические приборы", "Органические химикаты",
            "Фармацевтическая продукция", "Железо и сталь", "Текстиль", "Трикотажная одежда и аксессуары", "Электрические машины",
            "Зерновые", "Морепродукты", "Изделия из железа и стали", "Хлопок", "Пластмассы", "Корабли и морское оборудование",
            "Одежда", "Алюминий", "Мясо", "Разные химические продукты", "Рафинированная нефть", "Ювелирные изделия",
            "Фармацевтика", "Рис", "Автомобили", "Овощные соки", "Хлопок-сырец", "Вещательное оборудование", "Железная руда",
            "Нерозничная чистая хлопковая пряжа", "Автомобильные запчасти", "Замороженная говядина",
            "Азотные гетероциклические соединения", "Нетрикотажные женские костюмы", "Бриллианты", "Циклические углеводороды",
            "Рафинированная медь", "Сахар-сырец", "Соевый шрот", "Постельное белье");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
