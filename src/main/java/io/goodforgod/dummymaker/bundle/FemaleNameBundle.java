package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * Contains Female Names As String
 *
 * @author Anton Kurako (GoodforGod)
 * @since 31.05.2017
 */
public final class FemaleNameBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret", "Dorothy", "Lisa",
            "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna", "Carol", "Ruth", "Sharon", "Michelle", "Laura", "Sarah",
            "Kimberly", "Deborah", "Jessica", "Shirley", "Cynthia", "Angela", "Melissa", "Brenda", "Amy", "Anna", "Rebecca",
            "Virginia", "Kathleen", "Pamela", "Debra", "Martha", "Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances",
            "Ann", "Joyce", "Diane", "Alice", "Julie", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn", "Jean",
            "Cheryl", "Mildred", "Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly", "Nicole", "Judy",
            "Christina", "Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn",
            "Kathryn", "Andrea", "Louise", "Sara", "Anne", "Jacqueline", "Wanda", "Bonnie", "Julia", "Ruby", "Lois", "Tina",
            "Phyllis", "Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin", "Peggy", "Crystal", "Gladys", "Rita",
            "Dawn", "Connie", "Florence", "Tracy", "Edna", "Tiffany", "Carmen", "Rosa", "Cindy", "Grace", "Wendy", "Victoria",
            "Edith", "Kim", "Sherry", "Sylvia", "Josephine", "Thelma", "Shannon", "Sheila", "Ethel", "Ellen", "Elaine",
            "Marjorie", "Carrie", "Charlotte", "Monica", "Esther", "Pauline", "Emma", "Juanita", "Anita", "Rhonda", "Hazel",
            "Amber", "Eva", "Debbie", "April", "Leslie", "Clara", "Lucille", "Jamie", "Joanne", "Eleanor", "Valerie", "Danielle",
            "Megan", "Alicia", "Suzanne", "Michele", "Gail", "Bertha", "Darlene", "Veronica", "Jill", "Erin", "Geraldine",
            "Lauren", "Cathy", "Joann", "Lorraine", "Lynn", "Sally", "Regina", "Erica", "Beatrice", "Dolores", "Bernice",
            "Audrey", "Yvonne", "Annette", "June", "Samantha", "Marion", "Dana", "Stacy", "Ana", "Alma", "Sue", "Elsie", "Beth",
            "Jeanne", "Vicki", "Carla", "Tara", "Rosemary", "Eileen", "Terri", "Gertrude", "Lucy", "Tonya", "Ella", "Stacey",
            "Wilma", "Gina", "Kristin", "Jessie", "Natalie", "Agnes", "Vera", "Willie", "Charlene", "Bessie", "Delores",
            "Melinda", "Pearl", "Arlene", "Maureen", "Colleen");

    private static final List<String> BUNDLE_RUSSIAN = Arrays.asList(
            "Аглая", "Агния", "Аграфена", "Агрипина", "Аделаида", "Аксинья", "Акулина", "Александра", "Алена", "Алина", "Алиса",
            "Алла", "Альбина", "Анастасия", "Анжела", "Анна", "Антонина", "Анфиса", "Аполлинария", "Арина", "Ася", "Болеслава",
            "Борислава", "Бронислава", "Валентина", "Валерия", "Варвара", "Василиса", "Вера", "Вероника", "Виктория", "Владилена",
            "Владлена", "Галина", "Глафира", "Груша", "Дарина", "Дарья", "Дуня", "Ева", "Евгения", "Евдокия", "Екатерина",
            "Елена", "Елизавета", "Ефросинья", "Жанна", "Заря", "Зинаида", "Злата", "Зоя", "Инна", "Ирина", "Искра", "Катерина",
            "Кира", "Клавдия", "Клара", "Ксения", "Лариса", "Лидия", "Лизавета", "Лилия", "Любава", "Любовь", "Людмила",
            "Магдалина", "Маргарита", "Марианна", "Марина", "Мария", "Марфа", "Марья", "Надежда", "Найда", "Наталья", "Неонила",
            "Ника", "Нина", "Нинель", "Нонна", "Оксана", "Ольга", "Отрада", "Павлина", "Пелагея", "Полина", "Прасковья", "Рада",
            "Раиса", "Римма", "Светлана", "Серафима", "Слава", "София", "Станислава", "Таисия", "Тамара", "Татьяна", "Ульяна",
            "Устинья", "Фаина", "Фекла", "Феодора", "Юлиана", "Юлия", "Юстина", "Ярина", "Яромира", "Ярослава");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_RUSSIAN;
    }
}
