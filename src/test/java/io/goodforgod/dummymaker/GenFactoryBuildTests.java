package io.goodforgod.dummymaker;

import static org.junit.jupiter.api.Assertions.*;

import io.goodforgod.dummymaker.error.GenException;
import io.goodforgod.dummymaker.testdata.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 31.07.2017
 */
class GenFactoryBuildTests {

    @Test
    void produceLessThanZeroAmount() {
        try {
            final GenFactory factory = GenFactory.build();

            factory.build(Dummy.class, -20);

            fail("Should not happen");
        } catch (GenException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void produceListOfTwo() {
        final GenFactory factory = GenFactory.build();

        final List<Dummy> dummies = factory.build(Dummy.class, 2);

        assertNotNull(dummies);
        assertEquals(2, dummies.size());

        final Dummy dummy1 = dummies.get(0);
        assertNotNull(dummy1);
        assertNotNull(dummy1.getCity());
        assertNotNull(dummy1.getName());
        assertNotNull(dummy1.getNum());
        assertNotNull(dummy1.getGroup());

        final Dummy dummy2 = dummies.get(1);
        assertNotNull(dummy2);
        assertNotNull(dummy2.getCity());
        assertNotNull(dummy2.getName());
        assertNotNull(dummy2.getNum());
        assertNotNull(dummy2.getGroup());

        assertNotEquals(dummy1, dummy2);
    }

    @Test
    void produceStreamOfTwo() {
        final GenFactory factory = GenFactory.build();

        final List<Dummy> dummies = factory.stream(Dummy.class, 2).collect(Collectors.toList());

        assertNotNull(dummies);
        assertEquals(2, dummies.size());

        final Dummy dummy1 = dummies.get(0);
        assertNotNull(dummy1);
        assertNotNull(dummy1.getCity());
        assertNotNull(dummy1.getName());
        assertNotNull(dummy1.getNum());
        assertNotNull(dummy1.getGroup());

        final Dummy dummy2 = dummies.get(1);
        assertNotNull(dummy2);
        assertNotNull(dummy2.getCity());
        assertNotNull(dummy2.getName());
        assertNotNull(dummy2.getNum());
        assertNotNull(dummy2.getGroup());

        assertNotEquals(dummy1, dummy2);
    }

    @Test
    void produceSingleDummy() {
        final GenFactory factory = GenFactory.builder()
                .addRule(GenRule.ofGlobal().excludeFields("group"))
                .build();
        final Dummy dummy = factory.build(Dummy.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getCity());
        assertNotNull(dummy.getName());
        assertNotNull(dummy.getNum());
        assertNotNull(dummy.getGroup());

        assertTrue(dummy.getCity().matches("[a-zA-Z\\-]+"));
        assertTrue(dummy.getName().matches("[a-zA-Z]+"));
        assertEquals(0, (int) dummy.getNum());
        assertEquals("100", dummy.getGroup());
    }

    @Test
    void produceWithCollectionFields() {
        final GenFactory factory = GenFactory.build();

        final DummyCollection dummy = factory.build(DummyCollection.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getObjects());
        assertNotNull(dummy.getStrings());
        assertNotNull(dummy.getMap());

        assertNotNull(dummy.getObjectsFix());
        assertNotNull(dummy.getStringsFix());
        assertNotNull(dummy.getMapFix());

        assertFalse(dummy.getObjects().isEmpty());
        assertFalse(dummy.getStrings().isEmpty());
        assertFalse(dummy.getMap().isEmpty());

        assertFalse(dummy.getObjectsFix().isEmpty());
        assertFalse(dummy.getStringsFix().isEmpty());
        assertFalse(dummy.getMapFix().isEmpty());

        assertEquals(4, dummy.getObjectsFix().size());
        assertEquals(5, dummy.getStringsFix().size());
        assertEquals(3, dummy.getMapFix().size());
    }

    @Test
    void produceAutoDummy() {
        final GenFactory factory = GenFactory.build();

        final DummyAuto build = factory.build(DummyAuto.class);
        assertNotNull(build);

        assertNotNull(build.getaLong());
        assertNotEquals(0, build.getAnInt());

        assertNotNull(build.getList());
        assertNotNull(build.getMap());
        assertFalse(build.getList().isEmpty());
        assertFalse(build.getMap().isEmpty());
        assertNotEquals(0, build.getList().size());
        assertNotEquals(0, build.getMap().size());

        assertNotNull(build.getDummyAuto());

        final DummyAuto innerDummy = build.getDummyAuto();
        assertNotNull(innerDummy.getaLong());
        assertNotEquals(0, innerDummy.getAnInt());

        assertNotNull(innerDummy.getList());
        assertNotNull(innerDummy.getMap());
        assertFalse(innerDummy.getList().isEmpty());
        assertFalse(innerDummy.getMap().isEmpty());
        assertNotEquals(10, innerDummy.getList().size());
        assertNotEquals(10, innerDummy.getMap().size());
    }

    @Test
    void produceWithWrongCollectionFields() {
        final GenFactory factory = GenFactory.build();

        final DummyCollectionWrong dummy = factory.build(DummyCollectionWrong.class);

        assertNotNull(dummy);

        assertNull(dummy.getList());
        assertNull(dummy.getSet());
        assertNull(dummy.getMap());
    }

    @Test
    void produceWithTimeFields() {
        final GenFactory factory = GenFactory.build();
        final DummyTime d = factory.build(DummyTime.class);

        assertNotNull(d);
        assertNotNull(d.getDateOld());
        assertNotNull(d.getDate());
        assertNotNull(d.getTime());
        assertNotNull(d.getDateTime());
        assertNotNull(d.getTimestamp());
        assertNotNull(d.getDateTimeString());
        assertNotNull(d.getDateTimeObject());

        final Pattern localDatePattern = DummyTime.Patterns.LOCAL_DATE.getToStringPattern();
        final Pattern localTimePattern = DummyTime.Patterns.LOCAL_TIME.getToStringPattern();
        final Pattern localDateTimePattern = DummyTime.Patterns.LOCAL_DATETIME.getToStringPattern();
        final Pattern offsetDateTimePattern = DummyTime.Patterns.OFFSET_DATETIME.getToStringPattern();
        final Pattern timestampPattern = DummyTime.Patterns.TIMESTAMP.getToStringPattern();
        final Pattern datePattern = DummyTime.Patterns.DATE.getToStringPattern();

        assertTrue(datePattern.matcher(d.getDateOld().toString()).matches(), d.getDateOld().toString());
        assertTrue(localDatePattern.matcher(d.getDate().toString()).matches(), d.getDate().toString());
        assertTrue(localTimePattern.matcher(d.getTime().toString()).matches(), d.getTime().toString());
        assertTrue(localDateTimePattern.matcher(d.getDateTime().toString()).matches(), d.getDateTime().toString());
        assertTrue(timestampPattern.matcher(d.getTimestamp().toString()).matches(), d.getTimestamp().toString());

        assertTrue(offsetDateTimePattern.matcher(d.getDateTimeObject().toString()).matches(), d.getDateTimeObject().toString());
        assertTrue(offsetDateTimePattern.matcher(d.getDateTimeString()).matches(), d.getDateTimeString());
    }

    @Test
    void buildSingleKnown() {
        final GenFactory factory = GenFactory.build();

        final Integer v1 = factory.build(Integer.class);
        final String v2 = factory.build(String.class);
        final BigDecimal v3 = factory.build(BigDecimal.class);

        assertNotNull(v1);
        assertNotNull(v2);
        assertNotNull(v3);
    }

    @Test
    void buildListKnown() {
        final GenFactory factory = GenFactory.build();

        final List<Integer> v1 = factory.build(Integer.class, 2);
        final List<String> v2 = factory.build(String.class, 2);
        final List<BigDecimal> v3 = factory.build(BigDecimal.class, 2);

        assertNotNull(v1);
        assertEquals(2, v1.size());
        assertNotNull(v2);
        assertEquals(2, v2.size());
        assertNotNull(v3);
        assertEquals(2, v3.size());
    }
}
