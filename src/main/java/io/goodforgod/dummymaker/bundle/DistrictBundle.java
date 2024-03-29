package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with names of districts of cities
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class DistrictBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "North Glec", "Waterside Stag", "Whecloat Avenue", "Sceessalt Bazaar", "Midtown Siaggund", "Rurresk Street",
            "Schipalt Street", "Upper East Shrinnild", "Teonectark Center", "Swufioshot Park", "Wad Street", "Upper East Nub",
            "East Lichod", "West Vacciopt", "Upper West Zailan", "Downtown Meled", "Bayside Chuggok", "Lower Mowest",
            "Scimartruft Hill", "Shainnossuc Hill", "Tramp East", "South Bud", "Lower East Rurvin", "Lower North Trugwep",
            "Bayside Swacib", "Fiofos Row", "Upper East Schifus", "Yeanead Park", "Saffurluc District", "Nemmeggant",
            "Crald Yard", "Fap East", "Welgup North", "Fort Drurlop", "Little Stimoal", "Cummenk South", "Clottuc Plaza",
            "Upper East Piogek", "Xeeroaglalf Center", "Pissiglac Center", "Des Avenue", "Gork Side", "Bayside Leathot",
            "Throrwog West", "Troppit Corner", "Waterside Clipuk", "Clussand Valley", "Qaisseept Hill", "Wuppialnun District",
            "Cotuwlusp Hill", "Upper East Scot", "Upper North Reard", "Fort Muwab", "Whitlelf Yard", "Upper East Biacunk",
            "Lower East Trenneept", "Glatal Plaza", "Downtown Smoppind", "Snemabliop Wood", "Drussatnend Circle", "Mel Square",
            "Pliab South", "Upper West Dailzet", "Whorstin Grove", "Upper South Mallunk", "Liafift Vale", "South Prisec",
            "Ceppat Road", "Stehiroopt Vale", "Feliwnad");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Центральный административный округ",
            "Северный административный округ",
            "Северо-Восточный административный округ",
            "Восточный административный округ",
            "Юго-Восточный административный округ",
            "Южный административный округ",
            "Юго-Западный административный округ",
            "Западный административный округ",
            "Северо-Западный административный округ",
            "Зеленоградский административный округ",
            "Новомосковский административный округ",
            "Троицкий административный округ");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
