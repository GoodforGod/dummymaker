package io.dummymaker.data;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.generator.impl.time.impl.LocalDateTimeGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
public class DummyTest {

    @GenTime
    private LocalDateTime localDateTime;

    @GenList
    private List<String> strings;

    @GenList
    private List<Object> objects;

    @GenList
    private List<LocalDateTime > localDateTimes;

    @GenList(LocalDateTimeGenerator.class)
    private List<LocalDateTime > localDateTimeList;

    @GenTime
    private Timestamp timestamp;
}
