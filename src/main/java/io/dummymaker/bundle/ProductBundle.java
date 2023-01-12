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

    private static final List<String> BUNDLE = Arrays.asList(
            "Minerals",
            "Refined petroleum",
            "Automobiles",
            "Machinery and mechanical appliances",
            "Organic chemicals",
            "Pharmaceutical products",
            "Iron and steel",
            "Textiles",
            "Knit apparel and accessories",
            "Electrical machinery",
            "Cereal",
            "Seafood",
            "Iron and Steel articles",
            "Cotton",
            "Plastics",
            "Ships and marine equipment",
            "Clothing",
            "Aluminium",
            "Meat",
            "Miscellaneous Chemical products",
            "Refined petroleum",
            "Jewellery",
            "Pharmaceuticals",
            "Rice",
            "Cars",
            "Vegetable Saps",
            "Raw Cotton",
            "Broadcasting Equipment",
            "Iron ore",
            "Non-Retail Pure Cotton Yarn",
            "Car parts",
            "Frozen Beef",
            "Nitrogen Heterocyclic Compounds",
            "Non-Knit Women Suits",
            "Diamonds",
            "Cyclic Hydrocarbons",
            "Refined Copper",
            "Raw sugar",
            "Soybean meal",
            "House Linens");

    public ProductBundle() {
        super(BUNDLE);
    }
}
