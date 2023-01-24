package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with tag string values
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class TagsBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "#yummy", "#healthy", "#instacool", "#hot", "#iphoneonly", "#instapic", "#funny", "#Instagram", "#night", "#baby",
            "#ootd", "#makeup", "#cat", "#girls", "#cool", "#lol", "#party", "#foodporn", "#tflers", "#hair", "#photo", "#sunset",
            "#vsco", "#dog", "#lfl", "#pretty", "#f4f", "#travel", "#sky", "#music", "#swag", "#follow4follow", "#beach",
            "#beauty", "#sun", "#vscocam", "#bestoftheday", "#fitness", "#life", "#amazing", "#follow4follow", "#nofilter",
            "#style", "#instamood", "#nature", "#likeforlike", "#family", "#art", "#food", "#instalike", "#igers", "#repost",
            "#smile", "#tagforlikes", "#fun", "#girl", "#instadaily", "#friends", "#summer", "#picoftheday", "#selfie",
            "#fashion", "#followme", "#cute", "#beautiful", "#tbt", "#movies", "#theatre", "#movies", "#theatre", "#video",
            "#movie", "#film", "#films", "#videos", "#actor", "#actress", "#cinema", "#dvd", "#amc", "#instamovies", "#star",
            "#moviestar", "#hollywood", "#goodmovie", "#instagood", "#flick", "#instaflicks", "#videogames", "#games", "#gamer",
            "#gaming", "#instagaming", "#instagamer", "#playinggames", "#online", "#photooftheday", "#onlinegaming",
            "#videogameaddict", "#instagame", "#gamestagram", "#gamerguy", "#gamergirl", "#gamin", "#game", "#igaddict",
            "#winning", "#play", "#playing", "#books", "#book", "#read", "#TagsForLikes", "#reading", "#reader", "#page",
            "#pages", "#paper", "#kindle", "#nook", "#library", "#author", "#bestoftheday", "#bookworm", "#readinglist",
            "#imagine", "#plot", "#climax", "#story", "#literature", "#literate", "#stories", "#words", "#text");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "#архитектура", "#безфильтров", "#взаимныелайки", "#горы", "#девочкитакиедевочки", "#еда", "#женскаяодежда",
            "#здоровье", "#интерьер", "#йога", "#кроссовки", "#лайк", "#море", "#новый", "#осень", "#пп", "#ручнаяработа",
            "#смех", "#танцы", "#утро", "#фитнес", "#худеемвместе", "#цитаты", "#часы", "#шутки", "#щенки", "#эксклюзив",
            "#юбка");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
