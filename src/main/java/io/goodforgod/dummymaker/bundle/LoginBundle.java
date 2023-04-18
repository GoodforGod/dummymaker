package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Contains nicknames as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 03.06.2017
 */
public final class LoginBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "3DWaffle", "Hightower", "PapaSmurf", "57Pixels", "HogButcher", "PepperLegs", "101", "Houston", "PinballWizard",
            "AccidentalGenius", "Hyper", "Pluto", "Alpha", "Jester", "Pogue", "AirportHobo", "Jigsaw", "Prometheus",
            "BeardedAngler", "JokersGrin", "PsychoThinker", "BeetleKing", "Judge", "Pusher", "Bitmap", "JunkyardDog", "RiffRaff",
            "Blister", "K9", "Roadblock", "Bowie", "Keystone", "Rooster", "Bowler", "Kickstart", "Sandbox", "Breadmaker",
            "KillSwitch", "Scrapper", "Broomspun", "Kingfisher", "Screwtape", "Buckshot", "Kitchen", "SexualChocolate", "Bugger",
            "Knuckles", "ShadowChaser", "Cabbie", "LadyKiller", "SherwoodGladiator", "CandyButcher", "LiquidScience", "Shooter",
            "CapitalF", "LittleCobra", "SidewalkEnforcer", "CaptainPeroxide", "LittleGeneral", "SkullCrusher", "CelticCharger",
            "LordNikon", "SkyBully", "CerealKiller", "LordPistachio", "SlowTrot", "ChicagoBlackout", "MadIrishman", "SnakeEyes",
            "ChocolateThunder", "MadJack", "SnowHound", "Chuckles", "MadRascal", "SofaKing", "Commando", "Manimal", "Speedwell",
            "CoolWhip", "Marbles", "SpiderFuji", "Cosmo", "MarriedMan", "SpringheelJack", "CrashOverride", "Marshmallow",
            "Squatch", "CrashTest", "Mental", "StackerofWheat", "CrazyEights", "MercuryReborn", "SugarMan", "CrissCross", "Midas",
            "SuicideJockey", "CrossThread", "MidnightRambler", "Swampmasher", "Cujo", "MidnightRider", "Swerve", "DancingMadman",
            "MindlessBobcat", "Tacklebox", "Dangle", "Mr44", "TakeAway", "DarkHorse", "MrFabulous", "TanStallion", "DayHawk",
            "MrGadget", "TheChinaWall", "DesertHaze", "MrLucky", "TheDude", "Digger", "MrPeppermint", "TheFlyingMouse",
            "DiscoThunder", "MrSpy", "TheHappyJock", "DiscoPotato", "MrThanksgiving", "TheHowlingSwede", "DrCocktail",
            "MrWholesome", "Thrasher", "Dredd", "MudPieMan", "Toe", "Dropkick", "MuleSkinner", "Toolmaker", "DropStone", "Murmur",
            "ToughNut", "DrugstoreCowboy", "Nacho", "Trip", "EasySweep", "NaturalMess", "Troubadour", "ElectricPlayer",
            "Necromancer", "TurnipKing", "Esquire", "NeophyteBeliever", "Twitch", "FastDraw", "Nessie", "VagabondWarrior",
            "Flakes", "NewCycle", "Voluntary", "Flint", "NicknameMaster", "Vortex", "Freak", "NightmareKing", "WaylayDave",
            "Glyph", "OldManWinter", "Wheels", "GraveDigger", "OldOrangeEyes", "WoodenMan", "Guillotine", "OldRegret", "WooWoo",
            "Gunhawk", "OnionKing", "YellowMenace", "HighKingdomWarrior", "Osprey", "ZeroCharisma", "HighlanderMonk", "Overrun",
            "ZestyDragon", "Zod");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "СЛАВЯНИН", "БезумныйАнгел", "Фантом", "хлебушек", "ОгненнаяДевочка", "Бородяга", "рYcСкAя_КрОвb", "Капитошка",
            "ВинниПух", "БэдБой", "Шашлы4ок", "РУССКИЙ", "ВоенаяКозявка", "Муха", "Сделан_в_СССР", "Лисёнок", "TheBesTолочь",
            "ЗлойТатарин", "Карбюратор", "СаньОК", "Тенса", "Шейх", "Джоник", "Зязик", "Иерон", "Е_с_Т_ь___К_т_О___Р_у_С_с_К_и_Е",
            "Ёж", "Марк", "хати", "Шапокляк", "Эмирон", "КаспийскийГруз", "Гогль-могль", "Серёга", "ТотКтоТебяОбманул", "Вольт",
            "НЕЙРОН", "Изумруд", "Серый", "Так_прохожий", "Татьяна", "ТвОя_МеЧта", "ЧАРОДЕЙ", "Шамли", "ШаШлЫк", "CCCР",
            "Pycckuu_BouH_u3_CCCP", "Забор", "Роскомнадзор", "Триада", "АпасныйЖорик", "Бессмертный", "мишутка", "Огурчик",
            "скрипач", "СОЛЕВОЙ", "НоВоГоДнЯя", "КаменныйЖук", "Купер", "ПиНгВиН", "тапочек", "чебуречик", "чебурашка", "Ядерный",
            "Волхв", "держиморда", "Кайф", "Позитив4ик", "Святой", "Сероглазая", "сыроед", "УльтроФиолет", "_ОВОЩЬ_", "Аквамарин",
            "Апокалипсис", "Батя Дома", "Ватага", "Корвус", "Лом", "Марлей", "ПлАтИ_НаЛоГи", "Ушастый_Нянь", "ФИКУС", "Чебурашка",
            "М-е-г-а-л-о-д-о-н", "винни_пух", "ВИРУС", "джанбул", "Диспетчер", "Иноятулло", "искушение", "Пластырь", "Руби",
            "СаКуРа", "Святоша", "Якуб", "CEKPET", "CHOPOBKA", "KOT_B_KOTKE", "TyMaH", "Агуша", "АлкоголикРусский",
            "АнгелХранитель", "АннаВай", "Борщ", "Бриллиант", "Вай", "Винишко", "гречка", "Доброеутро", "Дрифт", "Дырявыйносок",
            "Жажда", "Жук", "Имбирь", "ИньЯн", "Клевер", "КРАСОТА-ПО-РУССКИ", "лЕрОн", "Луна", "Мальчик_с_пистолетом", "МОНОЛИТ",
            "МояЖизнь", "МУРЛЫК", "Мята", "НЕвезучий", "Ожидание", "ПростоКласс", "РобинГуд", "РозыГрома", "РусскийАлкаш",
            "Ручки", "СаХаРоК", "СвятаяТРОИЦА", "СкрипОчка", "СОСИСКАвТЕСТЕ", "спонсорство", "Твой_зайка", "Темпест",
            "ТёплыйПассажир", "Удача", "Ушастик", "Художник", "Хулиганистан", "ЧайСРомашкой", "ЧерныйЛотус", "чертенок_из_рая",
            "чивапчичи", "Яга", "ЧЕСНОК", "4икапус", "9микрорайон", "BaжHaя_ПepcoHa", "OВOЩ", "АВАТАРКИ", "алейсес", "Антиуф",
            "Аревуар", "Баннихоп", "Бантики", "Беги_Задом", "БЕЗ_НИКА", "БеЗ_ПуЛи_Но_С_МоЗгАмИ", "Белый снег", "Бижутерия",
            "БЛОКЕРЫ", "Богатых", "Боза", "БОЛЬ", "БольшойПирог", "Бомба", "БРАТЯ", "Булава", "ванильный", "ВЕРТОЛЁТИКИ",
            "ВеСеЛкА", "Вечное лето", "Вечность", "ВЖУХ", "Винил", "Вьетнамский-кот", "Грибок", "ГубочкаНеБоб", "Далматинец",
            "Дельфин", "ДитяНочи", "ЖАТВА", "Животное", "жмых", "Зараженный", "ЗоЛотЦе_МоЕ", "Иду_По_ПрибораМ", "ИзИ_Б0Т", "Йога",
            "Кальян", "Камикадзе", "Каникулы", "Карго", "Квантумм", "Киселек", "КомпотИзЯблок", "кузя", "КУЛАК", "Лавина",
            "лазер", "ЛарекЕдинорога");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
