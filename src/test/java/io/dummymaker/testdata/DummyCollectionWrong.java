package io.dummymaker.testdata;

import io.dummymaker.annotation.parameterized.GenList;
import io.dummymaker.annotation.parameterized.GenMap;
import io.dummymaker.annotation.parameterized.GenSet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 07.03.2018
 */
public class DummyCollectionWrong {

    public enum FieldNames {

        LIST("list"),
        SET("set"),
        MAP("map");

        private final String name;

        FieldNames(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @GenList
    private LocalTime list;

    @GenSet
    private LocalDate set;

    @GenMap
    private LocalDateTime map;

    public LocalTime getList() {
        return list;
    }

    public LocalDate getSet() {
        return set;
    }

    public LocalDateTime getMap() {
        return map;
    }
}
