package io.dummymaker.factory;

import io.dummymaker.data.*;
import io.dummymaker.factory.impl.GenProduceFactory;
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
public class ProduceFactoryTest {

    private final IProduceFactory factory = new GenProduceFactory();

    @Test
    public void produceLessThanZeroAmount() {
        final List<Dummy> dummies = factory.produce(Dummy.class, -20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorErrorList() {
        final List<DummyNoZeroConstructor> dummies = factory.produce(DummyNoZeroConstructor.class, 20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorError() {
        final DummyNoZeroConstructor dummy = factory.produce(DummyNoZeroConstructor.class);
        assertNull(dummy);
    }

    @Test
    public void produceListOfTwo() {
        final List<Dummy> dummies = factory.produce(Dummy.class, 2);

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
        final Dummy dummy = factory.produce(Dummy.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getCity());
        assertNotNull(dummy.getName());
        assertNotNull(dummy.getNum());
        assertNotNull(dummy.getGroup());

        assertTrue(dummy.getCity().matches("[a-zA-Z\\-]+"));
        assertTrue(dummy.getName().matches("[a-zA-Z]+"));
        assertEquals((int) dummy.getNum(), 0);
        assertEquals("100", dummy.getGroup());
    }

    @Test
    public void produceWithNoPopulateFields() {
        final DummyNoPopulateFields dummy = factory.produce(DummyNoPopulateFields.class);

        assertNotNull(dummy);
        assertNull(dummy.getCity());
        assertNull(dummy.getName());
        assertNull(dummy.getNum());
        assertEquals("100", dummy.getGroup());
    }

    @Test
    public void produceWithCollectionFields() {
        final DummyCollection dummy = factory.produce(DummyCollection.class);

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
    public void produceEmbeddedDummy() {
        final DummyEmbedded embedded = factory.produce(DummyEmbedded.class);
        validateEmbeddedDummy(embedded);
    }

    @Test
    public void produceEmbeddedDummyList() {
        final List<DummyEmbedded> embeddeds = factory.produce(DummyEmbedded.class, 2);
        for(DummyEmbedded embedded : embeddeds) {
            validateEmbeddedDummy(embedded);
        }
    }

    private void validateEmbeddedDummy(DummyEmbedded embedded) {
        assertNotNull(embedded);

        assertNotNull(embedded.getEmbedded());
        assertNotNull(embedded.getEmbeddedMax());
        assertNotNull(embedded.getEmbeddedDefault());

        assertNotEquals(0, embedded.getAnInt());
        assertNotEquals(0L, embedded.getaLong());
        assertNotEquals(0.0, embedded.getaDouble());

        // Check default
        assertNotNull(embedded.getEmbeddedDefault());
        assertNull(embedded.getEmbeddedDefault().getEmbeddedDefault());

        // Check embedded
        DummyEmbedded embeddedSimple = embedded;
        for(int i = 0; i < 3; i++) {
            assertNotNull(embeddedSimple);
            embeddedSimple = embeddedSimple.getEmbedded();
        }
        assertNull(embeddedSimple.getEmbedded());

        // Check max
        DummyEmbedded embeddedMax = embedded;
        for(int i = 0; i < 11; i++) {
            assertNotNull(embeddedMax);
            embeddedMax = embeddedMax.getEmbeddedMax();
        }
        assertNull(embeddedMax.getEmbeddedMax());
    }

    @Test
    public void produceWithWrongCollectionFields() {
        final DummyCollectionWrong dummy = factory.produce(DummyCollectionWrong.class);

        assertNotNull(dummy);

        assertNull(dummy.getList());
        assertNull(dummy.getSet());
        assertNull(dummy.getMap());
    }

    @Test
    public void produceWithTimeFields() {
        final DummyTime dummy = factory.produce(DummyTime.class);

        assertNotNull(dummy);
        assertNotNull(dummy.getDateOld());
        assertNotNull(dummy.getDate());
        assertNotNull(dummy.getTime());
        assertNotNull(dummy.getDateTime());
        assertNotNull(dummy.getTimestamp());
        assertNotNull(dummy.getDateTimeString());
        assertNotNull(dummy.getDateTimeObject());

        final Pattern datePattern = Pattern.compile("[A-Za-z]{3} [A-Za-z]{3} \\d{2} \\d{1,2}:\\d{1,2}:\\d{1,2} [A-Za-z]{3} \\d{4}");
        final Pattern localDatePattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
        final Pattern localTimePattern = Pattern.compile("\\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,10})?");
        final Pattern localDateTimePattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}[A-Z]\\d{1,2}:\\d{1,2}(:\\d{1,2}(\\.\\d+)?)?");
        final Pattern timestampPattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,10})?");

        assertTrue(dummy.getDateOld().toString(), datePattern.matcher(dummy.getDateOld().toString()).matches());
        assertTrue(dummy.getDate().toString(), localDatePattern.matcher(dummy.getDate().toString()).matches());
        assertTrue(dummy.getTime().toString(), localTimePattern.matcher(dummy.getTime().toString()).matches());
        assertTrue(dummy.getDateTime().toString(), localDateTimePattern.matcher(dummy.getDateTime().toString()).matches());
        assertTrue(dummy.getTimestamp().toString(), timestampPattern.matcher(dummy.getTimestamp().toString()).matches());

        assertTrue(dummy.getDateTimeObject().toString(), localDateTimePattern.matcher(dummy.getDateTimeObject().toString()).matches());
        assertTrue(dummy.getDateTimeString(), localDateTimePattern.matcher(dummy.getDateTimeString()).matches());
    }
}
