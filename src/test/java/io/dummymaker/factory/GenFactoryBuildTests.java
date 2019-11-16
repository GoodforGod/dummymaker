package io.dummymaker.factory;

import io.dummymaker.factory.impl.GenFactory;
import io.dummymaker.model.*;
import io.dummymaker.model.deprecated.DummyAuto;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 31.07.2017
 */
public class GenFactoryBuildTests {

    private final GenFactory factory = new GenFactory();

    @Test
    public void produceLessThanZeroAmount() {
        final List<Dummy> dummies = factory.build(Dummy.class, -20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorErrorList() {
        final List<DummyNoZeroConstructor> dummies = factory.build(DummyNoZeroConstructor.class, 20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorError() {
        final DummyNoZeroConstructor dummy = factory.build(DummyNoZeroConstructor.class);
        assertNull(dummy);
    }

    @Test
    public void produceListOfTwo() {
        final List<Dummy> dummies = factory.build(Dummy.class, 2);

        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());

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
    }

    @Test
    public void produceSingleDummy() {
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
    public void produceWithNoPopulateFields() {
        final DummyNoFillFields dummy = factory.build(DummyNoFillFields.class);

        assertNotNull(dummy);
        assertNull(dummy.getCity());
        assertNull(dummy.getName());
        assertNull(dummy.getNum());
        assertEquals("100", dummy.getGroup());
    }

    @Test
    public void produceWithCollectionFields() {
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
    public void produceAutoDummy() {
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
    public void produceWithWrongCollectionFields() {
        final DummyCollectionWrong dummy = factory.build(DummyCollectionWrong.class);

        assertNotNull(dummy);

        assertNull(dummy.getList());
        assertNull(dummy.getSet());
        assertNull(dummy.getMap());
    }

    @Test
    public void produceWithTimeFields() {
        final DummyTime dummy = factory.build(DummyTime.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getDateOld());
        assertNotNull(dummy.getDate());
        assertNotNull(dummy.getTime());
        assertNotNull(dummy.getDateTime());
        assertNotNull(dummy.getTimestamp());
        assertNotNull(dummy.getDateTimeString());
        assertNotNull(dummy.getDateTimeObject());

        final Pattern localDatePattern = DummyTime.Patterns.LOCAL_DATE.getPattern();
        final Pattern localTimePattern = DummyTime.Patterns.LOCAL_TIME.getPattern();
        final Pattern localDateTimePattern = DummyTime.Patterns.LOCAL_DATETIME.getPattern();
        final Pattern timestampPattern = DummyTime.Patterns.TIMESTAMP.getPattern();
        final Pattern datePattern = DummyTime.Patterns.DATE.getPattern();

        assertTrue(dummy.getDateOld().toString(), datePattern.matcher(dummy.getDateOld().toString()).matches());
        assertTrue(dummy.getDate().toString(), localDatePattern.matcher(dummy.getDate().toString()).matches());
        assertTrue(dummy.getTime().toString(), localTimePattern.matcher(dummy.getTime().toString()).matches());
        assertTrue(dummy.getDateTime().toString(), localDateTimePattern.matcher(dummy.getDateTime().toString()).matches());
        assertTrue(dummy.getTimestamp().toString(), timestampPattern.matcher(dummy.getTimestamp().toString()).matches());

        assertTrue(dummy.getDateTimeObject().toString(),
                localDateTimePattern.matcher(dummy.getDateTimeObject().toString()).matches());
        assertTrue(dummy.getDateTimeString(), localDateTimePattern.matcher(dummy.getDateTimeString()).matches());
    }
}
