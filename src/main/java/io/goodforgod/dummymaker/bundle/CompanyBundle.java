package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with company names string values
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako
 * @since 07.06.2017
 */
public final class CompanyBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Walmart", "Amazon", "Apple", "CVS Health", "UnitedHealth Group", "Exxon Mobil", "Berkshire Hathaway", "Alphabet",
            "McKesson Corporation", "AmerisourceBergen", "Microsoft", "Costco", "Cigna", "AT&T", "Cardinal Health",
            "Chevron Corporation", "The Home Depot", "Walgreens Boots Alliance", "Marathon Petroleum", "Elevance Health",
            "Kroger", "Ford Motor Company", "Verizon Communications", "JPMorgan Chase", "General Motors", "Centene",
            "Meta Platforms", "Comcast", "Phillips 66", "Valero Energy", "Dell Technologies", "Target Corporation", "Fannie Mae",
            "United Parcel Service", "Lowe", "Bank of America", "Johnson & Johnson", "Archer Daniels Midland", "FedEx", "Humana",
            "Wells Fargo", "State Farm", "Pfizer", "Citigroup", "PepsiCo", "Intel", "Procter & Gamble", "General Electric", "IBM",
            "MetLife", "Prudential Financial", "Albertsons", "The Walt Disney Company", "Energy Transfer Partners",
            "Lockheed Martin", "Freddie Mac", "Goldman Sachs", "Raytheon Technologies", "HP", "Boeing", "Morgan Stanley",
            "HCA Healthcare", "AbbVie", "Dow Chemical Company", "Tesla", "Allstate", "AIG", "Best Buy", "Charter Communications",
            "Sysco", "Merck & Co", "New York Life Insurance Company", "Caterpillar", "Cisco", "TJX", "Publix", "ConocoPhillips",
            "Liberty Mutual Group", "Progressive Corporation", "Nationwide Mutual Insurance Company", "Tyson Foods",
            "Bristol-Myers Squibb", "Nike", "John Deere", "American Express", "Abbott Laboratories", "StoneX Group",
            "Plains All American Pipeline", "Enterprise Products", "TIAA", "Oracle Corporation", "Thermo Fisher Scientific",
            "Coca-Cola Company", "General Dynamics", "CHS", "USAA", "Northwestern Mutual", "Nucor", "Exelon");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "EN+ Group", "Mail.ru Group", "Merlion", "Polymetal International", "S7 Group", "X5 Retail Group", "АК Алроса",
            "АЛРОСА", "АФК Система", "АвтоВАЗ", "Автодор", "Альфа-банк", "Арктикгаз", "Аэрофлот", "Банк ФК Открытие", "Башнефть",
            "ВТБ", "ВЭБ.РФ", "Вертолеты России", "ВымпелКом", "ГАЗ", "ГК Мегаполис", "ГК СНС", "ГК ТНС энерго", "ГК Ташир",
            "Газпром", "Газпром нефть", "Газпромбанк", "Газпромнефть", "Группа М.Видео-Эльдорадо", "Группа Мэйджор",
            "Группа Рольф", "Группа Содружество", "Группа УГМК", "Группа компаний Мегаполис", "ПИК", "ДНС Групп", "Дикси групп",
            "ЕВРАЗ", "ЕвроСибЭнерго", "Еврохим", "Зарубежнефть", "Интер РАО", "Иркутская нефтяная компания", "КамАЗ", "Катрен",
            "Компания Газ-Альянс", "Корпорация Тактическое ракетное вооружение", "Красное и Белое", "ЛУКОЙЛ", "Лента", "Лукойл",
            "ММК", "МТС", "Магнит", "Мегафон", "Металлоинвест", "Мечел", "Мобильные телесистемы", "Мостотрест", "НЛМК", "НОВАТЭК",
            "Национальная компьютерная корпорация", "Независимая нефтегазовая компания", "Нижнекамскнефтехим", "Новатэк",
            "Норильский никель", "Норникель", "Объединенная авиастроительная корпорация",
            "Объединенная двигателестроительная корпорация", "Объединенная судостроительная корпорация",
            "Полюс", "Почта России", "Протек", "РЖД", "Росатом", "Роснефть", "Россельхозбанк", "Россети",
            "Российская Электроника", "Ростелеком", "Ростех", "РусГидро", "Русал", "Русснефть", "Русэнергосбыт", "САФМАР",
            "СОГАЗ", "СУЭК", "Сахалин Энерджи", "Сбербанк", "Северсталь", "Сибирский антрацит", "Сибур", "Славнефть",
            "Стройгазмонтаж", "Стройтранснефтегаз", "Сургутнефтегаз", "Т Плюс", "ТАИФ-НК", "ТМК", "Татнефть", "Тинькофф",
            "Томскнефть", "Трансмашхолдинг", "Транснефть", "Увраз", "Уралкалий", "ФК Пульс", "ФортеИнвест", "ФосАрго", "ЧТПЗ",
            "Ямал СПГ", "Яндекс");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
