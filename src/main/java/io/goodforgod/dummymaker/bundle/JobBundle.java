package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class JobBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Academic librarian", "Accountant", "Accounting technician", "Actuary", "Adult nurse",
            "Advertising account executive", "Advertising account planner", "Advertising copywriter", "Advice worker", "Advocate",
            "Aeronautical engineer", "Agricultural consultant", "Agricultural manager", "Aid worker", "Air traffic controller",
            "Airline cabin crew", "Amenity horticulturist", "Analytical chemist", "Animal nutritionist", "Animator",
            "Archaeologist", "Architect", "Architectural technologist", "Archivist", "Armed forces officer", "Aromatherapist",
            "Art therapist", "Arts administrator", "Auditor", "Automotive engineer", "Barrister", "Barrister clerk",
            "Bilingual secretary", "Biomedical engineer", "Biomedical scientist", "Biotechnologist", "Brand manager",
            "Broadcasting presenter", "Building control officer", "Building services engineer", "Building surveyor",
            "Business analyst", "Camera operator", "Careers adviser", "Careers consultant", "Cartographer", "Catering manager",
            "Charities administrator", "Charities fundraiser", "Chemical engineer", "Child psychotherapist", "Children nurse",
            "Chiropractor", "Civil engineer", "Civil Service administrator", "Clinical biochemist", "Clinical cytogeneticist",
            "Clinical microbiologist", "Clinical molecular geneticist", "Clinical research associate",
            "Clinical scientist - tissue typing", "Clothing and textile technologist", "Colour technologist",
            "Commercial horticulturist", "Commercial surveyor", "Residential surveyor", "Rural surveyor", "Commissioning editor",
            "Commissioning engineer", "Commodity broker", "Communications engineer", "Community arts worker",
            "Community education officer", "Community worker", "Company secretary", "Computer sales support",
            "Computer scientist", "Conference organiser", "Consultant", "Consumer rights adviser",
            "Control and instrumentation engineer", "Corporate banker", "Corporate treasurer", "Counsellor", "Court reporter",
            "Credit analyst", "Crown Prosecution Service lawyer", "Crystallographer", "Curator", "Customs officer",
            "Cyber security specialist", "Dance movement psychotherapist", "Data analyst", "Data scientist",
            "Data visualisation analyst", "Database administrator", "Debt adviser", "Finance adviser", "Dental hygienist",
            "Dentist", "Design engineer", "Dietitian", "Diplomatic service", "Doctor", "Dramatherapist", "Economist",
            "Editorial assistant", "Education administrator", "Electrical engineer", "Electronics engineer",
            "Employment advice worker", "Energy conservation officer", "Engineering geologist", "Environmental education officer",
            "Environmental health officer", "Environmental manager", "Environmental scientist", "Equal opportunities officer",
            "Equality and diversity officer", "Ergonomist", "Estate agent", "European Commission administrators",
            "Exhibition display designer", "Exhibition organiser", "Exploration geologist", "Facilities manager",
            "Field trials officer", "Financial manager", "Firefighter", "Fisheries officer", "Fitness centre manager",
            "Food scientist", "Food technologist", "Forensic scientist", "Geneticist", "Geographical information systems manager",
            "Geomatics surveyor", "Government lawyer", "Government research officer", "Graphic designer",
            "Health and safety adviser", "Health and safety inspector", "Health promotion specialist", "Health service manager",
            "Health visitor", "Herbalist", "Heritage manager", "Higher education administrator", "Higher education advice worker",
            "Homeless worker", "Horticultural consultant", "Hotel manager", "Housing adviser", "Human resources officer",
            "Hydrologist", "Illustrator", "Immigration officer", "Immunologist", "Product designer", "Information scientist",
            "Information systems manager", "Information technology trainers", "Software trainers", "Insurance broker",
            "Insurance claims inspector", "Insurance risk surveyor", "Insurance underwriter", "Interpreter", "Investment analyst",
            "Investment banker", "Investment fund manager", "IT consultant", "IT support analyst", "Journalist",
            "Laboratory technician", "Land-based engineer", "Landscape architect", "Learning disability nurse", "Learning mentor",
            "Lecturer", "Legal executive", "Leisure centre manager", "Licensed conveyancer", "Local government administrator",
            "Local government lawyer", "Logistics manager", "Distribution manager", "Magazine features editor",
            "Magazine journalist", "Maintenance engineer", "Management accountant", "Manufacturing engineer",
            "Manufacturing machine operator", "Manufacturing toolmaker", "Marine scientist", "Market research analyst",
            "Market research executive", "Marketing assistant", "Marketing executive", "Marketing manager", "Materials engineer",
            "Materials specialist", "Mechanical engineer", "Media analyst", "Media buyer", "Media planner", "Medical physicist",
            "Medical representative", "Mental health nurse", "Metallurgist", "Meteorologist", "Microbiologist", "Midwife",
            "Mining engineer", "Mobile developer", "Multimedia programmer", "Multimedia specialists", "Museum education officer",
            "Museum exhibition officer", "Gallery exhibition officer", "Music therapist", "Nanoscientist",
            "Nature conservation officer", "Naval architect", "Network administrator", "Nurse", "Nutritional therapist",
            "Nutritionist", "Occupational therapist", "Oceanographer", "Office manager", "Operational researcher", "Orthoptist",
            "Outdoor pursuits manager", "Packaging technologist", "Paramedic", "Patent attorney", "Patent examiner",
            "Pension scheme manager", "Personal assistant", "Petroleum engineer", "Pharmacist", "Pharmacologist",
            "Pharmacovigilance officer", "Photographer", "Physiotherapist", "Picture researcher",
            "Planning and development surveyor", "Planning technician", "Plant breeder", "Police officer",
            "Political party agent", "Political party research officer", "Practice nurse", "Press photographer",
            "Press sub-editor", "Prison officer", "Private music teacher", "Probation officer", "Product development scientist",
            "Production manager", "Programme researcher", "Project manager", "Psychologist", "Psychotherapist",
            "Public affairs consultant", "Public house manager", "Public librarian", "Public relations officer", "QA analyst",
            "Quality assurance manager", "Quantity surveyor", "Records manager", "Recruitment consultant", "Recycling officer",
            "Regulatory affairs officer", "Research chemist", "Research scientist", "Restaurant manager", "Retail banker",
            "Retail buyer", "Retail manager", "Retail merchandiser", "Retail pharmacist", "Sales executive",
            "Scene of crime officer", "Secretary", "Seismic interpreter", "Site engineer", "Site manager", "Social researcher",
            "Social worker", "Software developer", "Soil scientist", "Solicitor", "Speech and language therapist", "Sports coach",
            "Sports development officer", "Sports therapist", "Statistician", "Stockbroker", "Structural engineer",
            "Systems analyst", "Systems developer", "Tax inspector", "Teacher early years", "Teacher primary",
            "Teacher secondary", "Teaching assistant", "Technical author", "Technical sales engineer", "TEFL teacher",
            "TESL teacher", "Television production assistant", "Test automation developer", "Tour guide", "Tour operator",
            "Tour representative", "Holiday representative", "Tourism officer", "Tourist information manager",
            "Town and country planner", "Toxicologist", "Trade union research officer", "Trader", "Trading standards officer",
            "Training and development officer", "Translator", "Transportation planner", "Travel agent", "TV designer",
            "Film designer", "Theatre designer", "UX designer", "Validation engineer", "Veterinary nurse", "Veterinary surgeon",
            "Video game designer", "Video game developer", "Volunteer work organiser", "Warehouse manager",
            "Waste disposal officer", "Water conservation officer", "Water engineer", "Web designer", "Web developer",
            "Welfare rights adviser", "Writer", "Youth worker");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Академический библиотекарь", "Бухгалтер", "Бухгалтерский техник", "Актуарий", "Взрослая медсестра",
            "Менеджер по рекламному учету", "Планировщик рекламных аккаунтов", "Рекламный копирайтер", "Консультант", "Адвокат",
            "Авиационный инженер", "Сельскохозяйственный консультант", "Менеджер по сельскому хозяйству",
            "Работник по оказанию помощи", "Авиадиспетчер", "Бортпроводники авиакомпании", "Садовод-любитель",
            "Аналитический химик", "Диетолог животных", "Аниматор", "Археолог", "Архитектор", "Архитектурный технолог",
            " Архивариус", "Офицер вооруженных сил", "Ароматерапевт", "Арт-терапевт", "Арт-администратор", "Аудитор",
            "Автомобильный инженер", "Барристер", "Барристерский клерк", "Двуязычный секретарь", "Биомедицинский инженер",
            "Биомедицинский ученый", "Биомедицинский ученый", "Биотехнолог", "Бренд-менеджер", "Телеведущий",
            "Сотрудник строительного контроля", "Инженер строительных услуг", "Строительный геодезист", "Бизнес-аналитик",
            "Оператор камеры", "Консультант по карьере", "Консультант по карьере", "Картограф", "Менеджер общественного питания",
            "Благотворительные организации администратор", "Благотворительный фандрайзер", "Инженер-химик",
            "Детский психотерапевт", "Детская медсестра", "Мануальный терапевт", "Инженер-строитель",
            "Администратор государственной службы", "Клинический биохимик", "Клинический цитогенетик", "Клинический микробиолог",
            "Клинический молекулярный генетик", "Клинический научный сотрудник", "Клинический ученый - типирование тканей",
            "Технолог одежды и текстиля", "Цветной технолог", "Коммерческий садовод", "Коммерческий геодезист",
            "Жилищный инспектор", "Сельский геодезист", "Сельский  сюрвейер", "Редактор по заказу", "Инженер-заказчик",
            "Товарный брокер", "Инженер связи", "Общественный деятель искусств", "Сотрудник по общественному образованию",
            "Общественный работник", "Секретарь компании", "Поддержка компьютерных продаж", "Компьютерный ученый",
            "Организатор конференции", "Консультант", "Консультант по правам потребителей",
            "Инженер по контролю и контрольно-измерительным приборам", "Корпоративный банкир", "Корпоративный казначей",
            "Советник", "Судебный репортер", "Кредитный аналитик", "Юрист Королевской прокуратуры", "Кристаллограф", "Куратор",
            "Таможенник", "Специалист по кибербезопасности", "Психотерапевт танцевального движения", "Аналитик данных",
            "Data scientist", "Аналитик визуализации данных", "Администратор базы данных", "Консультант по долгам",
            "Финансовый консультант", "Стоматологический гигиенист", "Стоматолог", "Инженер-конструктор", "Диетолог",
            "Дипломатическая служба", "Врач", "Драматург", "Экономист", "Помощник редактора", "Администратор образования",
            "Инженер-электрик", "Инженер-электронщик", "Инженер-электронщик", "Специалист по трудоустройству", "Энергетика",
            "Сотрудник по охране окружающей среды", "Инженер-геолог", "Сотрудник по экологическому образованию",
            "Сотрудник по гигиене окружающей среды", "Менеджер по окружающей среде", "Ученый-эколог",
            "Сотрудник по равным возможностям", "Сотрудник по вопросам равенства и разнообразия", "Эргономист",
            "Агент по недвижимости", "Администраторы Европейской комиссии", "Проектировщик выставок", "Организатор выставки",
            "Геолог-разведчик", "Менеджер по объектам", "Сотрудник по полевым испытаниям", "Финансовый менеджер", "Пожарный",
            "Сотрудник по рыболовству", "Фитнес менеджер центра", "Ученый-пищевик", "Пищевой технолог", "Судебный эксперт",
            "Генетик", "Менеджер географических информационных систем", "Геодезист", "Государственный юрист",
            "Государственный научный сотрудник", "Графический дизайнер", "Советник по охране труда и технике безопасности",
            "Инспектор по охране труда и технике безопасности", "Специалист по укреплению здоровья",
            "Менеджер по здравоохранению", "Посетитель здравоохранения", "Травник", "Менеджер наследия",
            "Администратор высшего образования", "Консультант по высшему образованию", "Бездомный работник", "Садоводство",
            "Консультант", "Менеджер отеля", "Консультант по жилищным вопросам", "Сотрудник по персоналу", "Гидролог",
            "Иллюстратор", "Сотрудник иммиграционной службы", "Иммунолог", "Разработчик продукта", "Специалист по информатике",
            "Менеджер информационных систем", "Тренеры по информационным технологиям", "Тренеры по программному обеспечению",
            "Страховой брокер", "Инспектор по страховым претензиям", "Инспектор по страховым рискам", "Страховой андеррайтер",
            "Переводчик", "Инвестиционный аналитик", "Инвестиционный банкир", "Менеджер инвестиционного фонда", "ИТ-консультант",
            "ИТ-поддержка", "аналитик", "Журналист", "Лаборант", "Наземный инженер", "Ландшафтный архитектор",
            "Медсестра по обучению с ограниченными возможностями", "Наставник по обучению", "Лектор", "Юрист",
            "Менеджер досугового центра", "Лицензированный перевозчик", "Администратор местного самоуправления",
            "Юрист местного самоуправления", "Менеджер по логистике", "Менеджер по дистрибуции", "Редактор журнальных материалов",
            "Журнальный журналист", "Инженер по техническому обслуживанию", "Бухгалтер по управлению", "Инженер-технолог",
            "Оператор производственной машины", "Производственная инструментальная компания", "Морской ученый",
            "Аналитик по исследованию рынка", "Руководитель маркетинговых исследований", "Ассистент по маркетингу",
            "Менеджер по маркетингу", "Менеджер по маркетингу", "Инженер-материаловед", "Специалист по материалам",
            "Инженер-механик", "Медиааналитик", "Медиа-покупатель", "Медиапланер", "Медицинский физик",
            "Медицинский представитель", "Медсестра по психическому здоровью", "Металлург", "Метеоролог", "Микробиолог",
            "Акушерка", "Горный инженер", "Мобильный разработчик", "Мультимедийный программист", "Мультимедийный программист",
            "Мультимедийный специалист", "Сотрудник музейного образования", "Сотрудник музейной выставки",
            "Выставочный сотрудник галереи", "Музыкальный терапевт", "Наноученый", "Сотрудник по охране природы",
            "Морской архитектор", "Сетевой администратор", "Медсестра", "Диетолог", "Диетолог", "Трудотерапевт", "Океанограф",
            "Офис-менеджер", "Оперативный исследователь", "Ортоптист", "Менеджер по занятиям на открытом воздухе",
            "Технолог упаковки", "Парамедик", "Патентный поверенный", "Патентный эксперт", "Менеджер пенсионной схемы",
            "Личный помощник", "Инженер-нефтяник", "Фармацевт", "Фармаколог", "Сотрудник фармаконадзора", "Фотограф",
            "Физиотерапевт", "Фотоискатель", "Инспектор по планированию и развитию", "Техник по планированию",
            "Селекционер растений", "Полицейский", "Агент политической партии", "Научный сотрудник политической партии",
            "Медсестра практики", "Пресс-фотограф", "Подредактор прессы", "Тюремный офицер", "Частный учитель музыки",
            "Сотрудник по пробации", "Ученый по разработке продукта", "Менеджер по производству", "Программный исследователь",
            "Менеджер проектов", "Психолог", "Психотерапевт", "Консультант по связям с общественностью",
            "Менеджер публичного дома", "Публичный библиотекарь", "Сотрудник по связям с общественностью", "Аналитик QA",
            "Менеджер по обеспечению качества", "Количественный инспектор", "Менеджер по записям",
            "Консультант по подбору персонала", "Сотрудник по утилизации", "Сотрудник по нормативным вопросам",
            "Химик-исследователь", "Научный сотрудник", "Менеджер ресторана", "Розничный банкир", "Розничный покупатель",
            "Розничный менеджер", "Розничный мерчендайзер", "Розничный фармацевт", "Менеджер по продажам",
            "Сотрудник по расследованию преступлений", "Секретарь", "Сейсмический переводчик", "Инженер сайта", "Менеджер сайта",
            "Социальный исследователь", "Социальный работник", "Разработчик программного обеспечения", "Почвовед", "Солиситор",
            "Логопед", "Спортивный тренер", "Специалист по развитию спорта", "Спортивный терапевт", "Статистик",
            "Биржевой маклер", "Инженер-строитель", "Системный аналитик", "Системный разработчик", "Налоговый инспектор",
            "Учитель ранних лет", "Учитель начальных классов", "Учитель средний", "Помощник преподавателя", "Технический автор",
            "Технический инженер по продажам", "Преподаватель TEFL", "Преподаватель TESL",
            "Помощник по телевизионному производству", "Разработчик автоматизации тестирования", "Гид", "Туроператор",
            "Представитель тура", "Праздничный представитель", "Сотрудник по туризму", "Менеджер по туристической информации",
            "Планировщик города и страны", "Токсиколог", "Научный сотрудник профсоюза", "Трейдер",
            "Сотрудник по торговым стандартам", "Сотрудник по обучению и развитию", "Переводчик", "Транспортный планировщик",
            "Турагент", "Телевизионный дизайнер", "Кинодизайнер", "Театральный дизайнер", "UX-дизайнер", "Инженер-валидатор",
            "Ветеринарная медсестра", "Ветеринарный хирург", "Дизайнер видеоигр", "Разработчик видеоигр",
            "Организатор волонтерской работы", "Менеджер склада", "Сотрудник по утилизации отходов",
            "Сотрудник по водосбережению", "Инженер по водным ресурсам", "Веб-дизайнер", "Веб-разработчик",
            "Консультант по социальным правам", "Писатель", "Молодежный работник");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}