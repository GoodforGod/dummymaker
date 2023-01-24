package io.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Bundle with simple phrase string values
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class PhraseBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "A bird in the hand is worth two in the bush", "A bunch of fives", "A chain is only as strong as its weakest link",
            "A countenance more in sorrow than in anger", "A Daniel come to judgement", "A diamond in the rough",
            "A diamond is forever", "A different kettle of fish", "A dish fit for the gods", "A dog is a mans best friend",
            "A drop in the bucket", "A drop in the ocean", "A fate worse than death", "A feather in ones cap",
            "A fish rots from the head down", "A fish out of water", "A fly in the ointment",
            "A fool and his money are soon parted", "A fools paradise", "A foot in the door", "A foregone conclusion",
            "A friend in need is a friend indeed", "A golden key can open any door", "A good man is hard to find",
            "A hard man is good to find", "A hiding to nothing on", "A house divided against itself cannot stand",
            "A knight in shining armour", "A la carte", "A la mode", "A legend in ones own lifetime",
            "A leopard cannot change its spots", "A little bird told me", "A little knowledge is a dangerous thing",
            "A little of what you fancy does you good", "A load of cobblers", "A load of codswallop", "A man after my own heart",
            "A man who is his own lawyer has a fool for a client", "A ministering angel shall my sister be",
            "A miss is as good as a mile", "A nation of shopkeepers", "A nest of vipers", "A no-brainer", "A norange",
            "A picture is worth a thousand words", "A piece of the action", "A pig in a poke",
            "A place for everything and everything in its place", "A plague on both your houses", "A priori",
            "A red rag to a bull", "A riddle wrapped up in an enigma", "A rolling stone gathers no moss",
            "A rose by any other name would smell as sweet", "A rose is a rose is a rose", "A safe pair of hands", "A sea change",
            "A skeleton in the closet", "A sledgehammer to crack a nut", "A shot in the arm", "A sight for sore eyes",
            "A sorry sight", "A stitch in time saves nine", "A stones throw", "A thing of beauty is a joy forever",
            "A tinkers damn", "A turn up for the books", "A watched pot never boils", "A wolf in sheeps clothing",
            "A woman needs a man like a fish needs a bicycle", "A womans place is in the home", "A word in edgeways",
            "A word in your shell-like", "A1 at Lloyds", "Abandon all hope ye who enter here", "Abide with me", "About face",
            "About turn", "Above board", "Abracadabra", "Sence makes the heart grow fonder", "Absent without leave",
            "Absolute power corrupts absolutely", "Accidentally on purpose", "Accidents will happen", "According to Hoyle",
            "Ace in the hole", "Achilles heel", "Acid test", "Acronyms", "Across the board", "Act of God", "Act the giddy goat",
            "Action man", "Adams ale", "Aga saga", "Against the grain", "Age before beauty",
            "Age cannot wither her nor custom stale her infinite variety", "Agree to disagree", "Aid and abet", "Aide-mémoire",
            "Air kiss", "Air quotes", "Al fresco", "Alas poor Yorick! I knew him Horatio", "Alike as two peas in a pod",
            "Alive and kicking", "All agog", "All at sea", "All fingers and thumbs", "All in all", "All intents and purposes",
            "All of a sudden", "All present and correct", "All publicity is good publicity", "All singing all dancing",
            "All that glitters is not gold", "All the tea in China - Not for", "All things come to he who waits",
            "All things must pass", "All you can eat", "Alter ego", "Amber nectar", "An albatross around ones neck",
            "An apple a day keeps the doctor away", "An arm and a leg", "An axe to grind", "An Englishmans home is his castle",
            "An eye for an eye a tooth for a tooth", "An ill wind", "An offer he cant refuse", "An Oxford scholar",
            "Anchors aweigh", "Angry young man", "Ankle biter", "Annus horribilis", "Another think coming", "Apple of my eye",
            "Apple pie order", "April fool", "See ear candy", "Arms akimbo", "Ars longa vita brevis", "Arty-farty",
            "As soon as possible", "As X as Y", "As alike as two peas in a pod", "As bald as a coot",
            "As black as Newgates knocker", "As brown as a berry", "As busy as a bee", "As cold as any stone",
            "As cool as a cucumber", "As cute as a bugs ear", "As daft as a brush", "As dead as a dodo", "As dead as a doornail",
            "As different as chalk and cheese", "As easy as pie", "As fast as greased lightning", "As fine as frogs hair",
            "As fit as a butchers dog", "As fit as a fiddle", "As good as gold", "As good luck would have it", "As happy as...",
            "As happy as a clam", "As happy as a sandboy", "As happy as Larry", "As keen as mustard", "As mad as a hatter",
            "As mad as a March hare", "As nice as ninepence", "As old as Methuselah", "As old as the hills",
            "As pleased as Punch", "As pure as the driven snow", "As safe as houses", "As queer as a nine bob note",
            "As snug as a bug in a rug", "As straight as a die", "As the crow flies", "As white as snow",
            "Ashes to ashes dust to dust", "Ask a silly question and youll get a silly answer", "Ask not for whom the bell tolls",
            "Hasta la vista baby", "At loggerheads", "At one fell swoop", "At ones beck and call", "At sixes and sevens",
            "Augur well", "Auld lang syne", "Away with the fairies", "Baby blues", "Baby boomer", "Baby father", "Back of beyond",
            "Back-seat driver", "Back the field", "Back to basics", "Back to square one", "Back to the drawing board",
            "Backroom boy", "Bring home", "Bad books", "Bad egg", "Bad hair day", "Badger to death", "Bag and baggage",
            "Bakers dozen", "Balance of power", "Balance of trade", "Bald as a coot", "Bale out", "Ball and chain", "Bandy words",
            "Bane of your life", "Bang on about", "Baptism of fire", "Wouldnt touch with a", "Barking mad",
            "Barking up the wrong tree", "Basket case", "Bated breath", "Bats in the belfry", "Batten down the hatches",
            "Battle royal", "Be afraid be very afraid", "Be enthralled", "Be still my beating heart", "Beam ends on your",
            "Bean counter", "Beast with two backs", "Beat a hasty retreat", "Beat around the bush", "Beauty is only skin deep",
            "Beck and call", "Bed of roses", "Bee in your bonnet", "Beef and reef", "Beelzebub has a devil for a sideboard",
            "Been there done that", "Beer and skittles", "Make a bee-line for", "Bees knees", "Beetle-browed",
            "Before you can say Jack Robinson", "Beg the question", "Beggar belief", "Beggars cant be choosers",
            "Behind every great man theres a great woman", "Behind the eight ball", "Bell book and candle", "Bell the cat",
            "Belle of the ball", "Below the belt", "Below the salt", "Bells and whistles", "Belt and braces", "Belt up",
            "Best bib and tucker", "Best laid schemes of mice and men", "Bet your bottom dollar", "Better half",
            "Better late than never", "Better to have loved and lost than never to have loved at all",
            "Between a rock and a hard place", "Between the Devil and the deep blue sea", "Between two stools",
            "Between you me and the bed-post", "Beware of Greeks bearing gifts", "Beware the Ides of March", "Beyond belief",
            "Beyond our ken", "Beyond the pale", "Biblical phrases", "Big Apple");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "На дне каждого сердца есть осадок",
            "Многие люди подобны колбасам: чем их начинят, то и носят в себе",
            "Никогда не теряй из виду, что гораздо легче многих не удовлетворить, чем удовольствовать",
            "Что скажут о тебе другие, коли ты сам о себе ничего сказать не можешь",
            "Щелкни кобылу в нос – она махнет хвостом",
            "Смотри вдаль – увидишь даль; смотри в небо – увидишь небо; взглянув в маленькое зеркальце, увидишь только себя",
            "Если на клетке слона прочтешь надпись «буйвол», не верь глазам своим",
            "Всякая человеческая голова подобна желудку: одна переваривает входящую в оную пищу, а другая от нее засоряется",
            "Взирая на солнце, прищурь глаза свои, и ты смело разглядишь в нем пятна",
            "Не всякому офицеру мундир к лицу",
            "Купи прежде картину, а после рамку",
            "Отыщи всему начало, и ты многое поймешь",
            "В спертом воздухе при всем старании не отдышишься",
            "Кто жил и мыслил, тот не может В душе не презирать людей..",
            "Они сошлись: Волна и камень, Стихи и проза, лед и пламень",
            "Чем меньше женщину мы любим, Тем легче нравимся мы ей, И тем ее вернее губим Средь обольстительных сетей",
            "Быть можно дельным человеко",
            "И думать о красе ногтей",
            "К чему бесплодно спорить с веком",
            "Обычай деспот меж людей",
            "Но дружбы нет и той меж нами",
            "Все предрассудки истребя, Мы почитаем всех нулями, А единицами — себя",
            "Привычка свыше нам дана: Замена счастию она",
            "Под старость жизнь такая гадость..",
            "Я, конечно, презираю отечество мое с головы до ног — но мне досадно, если иностранец разделяет со мной это чувство",
            "Печальное нам смешно, смешное грустно, а вообще, по правде, мы ко всему довольно равнодушны, кроме самих себя",
            "Неужели зло так привлекательно?.",
            "Как страшно жизни сей оков",
            "Нам в одиночестве влачить",
            "Делить веселье все готовы",
            "Никто не хочет грусть делить",
            "Пустое сердце бьётся ровно",
            "И ненавидим мы, и любим мы случайно",
            "Ничем не жертвуя ни злобе, ни любви",
            "И царствует в душе какой-то холод тайный",
            "Когда огонь кипит в крови",
            "Была без радостей любовь",
            "Разлука будет без печали",
            "Всё это было бы смешно",
            "Когда бы не было так грустно",
            "Поверь мне — счастье только там",
            "Где любят нас, где верят нам",
            "Что страсти? — ведь рано иль поздно их сладкий неду",
            "Исчезнет при слове рассудка",
            "И жизнь, как посмотришь с холодным вниманьем вокруг",
            "Такая пустая и глупая шутка",
            "К добру и злу постыдно равнодушны, В начале поприща мы вянем без борьбы",
            "Перед опасностью позорно малодушны И перед властию — презренные рабы",
            "Служить бы рад, прислуживаться тошно",
            "Блажен, кто верует, тепло ему на свете",
            "Ах! Злые языки страшнее пистолета",
            "Минуй нас пуще всех печале",
            "И барский гнев, и барская любовь",
            "Есть люди, имеющие страстишку нагадить ближнему, иногда вовсе без всякой причины",
            "Часто сквозь видимый миру смех льются невидимые миру слёзы",
            "Счастье соткано из иллюзий, надежд, доверчивости к людям, уверенности в самом себе, потом из любви, дружбы",
            "Между мужчиной и женщиной нет и не может быть дружбы, что называемое дружбой между ними — есть не что иное, как или начало, или остатки любви, или наконец, самая любовь",
            "Есть три разряда эгоистов: эгоисты, которые сами живут и жить дают другим; эгоисты, которые сами живут и не дают жить другим; наконец, эгоисты, которые и сами не живут, и другим не дают",
            "Я бы отдал весь свой гений и все свои книги за то, чтобы где-нибудь была женщина, которую беспокоила бы мысль, опоздаю я или нет к обеду",
            "Счастье — как здоровье: когда его не замечаешь, значит, оно есть",
            "Смерть не ждет, и жизнь ждать не должна",
            "Общие принципы только в головах, а в жизни одни только частные случаи",
            "Пройдите мимо нас и простите нам наше счастье",
            "Красота спасет мир",
            "Трус тот, кто боится и бежит; а кто боится и не бежит, тот ещё не трус",
            "Я хочу хоть с одним человеком обо всём говорить как с собой",
            "Подлецы любят честных людей..",
            "Сострадание есть главный и, может быть, единственный закон бытия всего человечества",
            "Хорошее время не с неба падает, а мы его делаем",
            "Надо жить, надо любить, надо верить",
            "Ежели люди порочные связаны между собой и составляют силу, то людям честным надо сделать только то же самое",
            "Кто всё поймет, тот всё и простит",
            "Сражение выигрывает тот, кто твердо решил его выиграть",
            "И нет величия там, где нет простоты, добра и правды",
            "Мы не столько любим людей за то добро, которое они сделали нам, сколько за то добро, которое сделали им мы",
            "Все разнообразие, вся прелесть, вся красота жизни слагается из тени и света",
            "Если искать совершенства, то никогда не будешь доволен",
            "Правил у нас много, а правды нет",
            "Счастье не в том, чтобы делать всегда, что хочешь, а в том, чтобы всегда хотеть того, что делаешь",
            "Каждый мечтает изменить мир, но никто не ставит целью изменить самого себя",
            "Ничто так на соединяет людей, как улыбка",
            "Недаром, видно, кто-то сказал, что разлука для любви то же, что ветер для огня: маленькую любовь она тушит, а большую раздувает еще сильней",
            "Смутное влечение сердца никогда не ошибается в своих быстрых тайных предчувствиях",
            "Всё проходит, да не все забывается",
            "Как ни грустно в этом непонятном мире, он всё же прекрасен..",
            "Из нас как из дерева — или дубина, или икона",
            "Я не червонец, чтобы всем нравиться",
            "О счастье мы всегда лишь вспоминаем",
            "Вот этот сад осенний за сарае",
            "И чистый воздух, льющийся в окно",
            "Женщина прекрасная должна занимать вторую ступень; первая принадлежит женщине милой",
            "Попал в стаю, лай не лай, а хвостом виляй",
            "Голодная собака верует только в мясо..");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
