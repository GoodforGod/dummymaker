package io.dummymaker.factory;

import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.factory.impl.GenOldFactory;
import io.dummymaker.model.Dummy;
import io.dummymaker.model.DummyCollectionWrong;
import io.dummymaker.model.DummyNoFillFields;
import io.dummymaker.model.DummyTime;
import io.dummymaker.model.deprecated.*;
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

    private final IProduceFactory factory = new GenOldFactory();

    @Test
    public void produceLessThanZeroAmount() {
        final List<Dummy> dummies = factory.produce(Dummy.class, -20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorErrorList() {
        final List<DummyNoFillFieldsTests> dummies = factory.produce(DummyNoFillFieldsTests.class, 20);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void noZeroConstructorError() {
        final DummyNoFillFieldsTests dummy = factory.produce(DummyNoFillFieldsTests.class);
        assertNull(dummy);
    }

    @Test
    public void arrayDummyToJson() {
        DummyArray dummyArray = new GenOldFactory().produce(DummyArray.class);
        String s = new JsonExporter().exportAsString(dummyArray);
        Pattern patternSingleArray = Pattern.compile("\"floatsff\":\\[-?[0-9]+\\.[0-9]+");
        Pattern patternTwoArray = Pattern.compile("\"bytes2\":\\[\\[[\\-0-9.]");
        Pattern patternList = Pattern.compile("\"floatList\":\\[[\\-0-9.]");
        Pattern patternMap= Pattern.compile("\"mapa\":\\{\"[a-zA-Z0-9_]+\":\"[a-zA-Z0-9_]+\"");
        assertTrue(patternSingleArray.matcher(s).find());
        assertTrue(patternTwoArray.matcher(s).find());
        assertTrue(patternList.matcher(s).find());
        assertTrue(patternMap.matcher(s).find());
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
        final DummyNoFillFields dummy = factory.produce(DummyNoFillFields.class);

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
        assertNotNull(embeddedMax.getEmbeddedMax());
    }

    @Test
    public void produceAutoDummy() {
        final DummyAuto dummyAuto = factory.produce(DummyAuto.class);
        assertNotNull(dummyAuto);

        assertNotNull(dummyAuto.getaLong());
        assertNotEquals(0, dummyAuto.getAnInt());

        assertNotNull(dummyAuto.getList());
        assertNotNull(dummyAuto.getMap());
        assertFalse(dummyAuto.getList().isEmpty());
        assertFalse(dummyAuto.getMap().isEmpty());
        assertNotEquals(0, dummyAuto.getList().size());
        assertNotEquals(0, dummyAuto.getMap().size());

        assertNotNull(dummyAuto.getDummyAuto());

        final DummyAuto innerDummy = dummyAuto.getDummyAuto();
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

        assertTrue(dummy.getDateTimeObject().toString(), localDateTimePattern.matcher(dummy.getDateTimeObject().toString()).matches());
        assertTrue(dummy.getDateTimeString(), localDateTimePattern.matcher(dummy.getDateTimeString()).matches());
    }

    @Test
    public void produceHalfAutoGenerateDummy() {
        final DummyAutoHalf dummyAuto = factory.produce(DummyAutoHalf.class);

        assertNotNull(dummyAuto);

        assertNotNull(dummyAuto.getName());
        assertFalse(dummyAuto.getName().isEmpty());
        assertTrue(dummyAuto.getName().matches("[a-zA-Z]+"));
        assertEquals(0, dummyAuto.getAnInt());

        assertNotNull(dummyAuto.getList());
        assertNotNull(dummyAuto.getMap());
        assertFalse(dummyAuto.getList().isEmpty());
        assertFalse(dummyAuto.getMap().isEmpty());
        assertNotEquals(0, dummyAuto.getList().size());
        assertEquals(6, dummyAuto.getMap().size());

        assertNotNull(dummyAuto.getDummyAuto());

        final DummyAuto innerDummy = dummyAuto.getDummyAuto();
        assertNotNull(innerDummy.getaLong());
        assertNotEquals(0, innerDummy.getAnInt());

        assertNotNull(innerDummy.getList());
        assertNotNull(innerDummy.getMap());
        assertFalse(innerDummy.getList().isEmpty());
        assertFalse(innerDummy.getMap().isEmpty());
        assertNotEquals(0, innerDummy.getList().size());
        assertNotEquals(0, innerDummy.getMap().size());
    }

    @Test
    public void produceDummyArray() {
        DummyArray dummyArray = factory.produce(DummyArray.class);

        assertNotNull(dummyArray);

        assertNotEquals(0, dummyArray.getBytes().length);
        assertNotEquals(0, dummyArray.getBytes2().length);
        assertNotEquals(0, dummyArray.getShorts().length);
        assertNotEquals(0, dummyArray.getShorts2().length);
        assertNotEquals(0, dummyArray.getInts().length);
        assertNotEquals(0, dummyArray.getInts2().length);
        assertNotEquals(0, dummyArray.getLongs().length);
        assertNotEquals(0, dummyArray.getLongs2().length);
        assertNotEquals(0, dummyArray.getDoubles().length);
        assertNotEquals(0, dummyArray.getDoubles2().length);

        assertNotNull(dummyArray.getaFloatObj());
        assertNotNull(dummyArray.getaByteObj());
        assertNotNull(dummyArray.getaShortObj());
        assertNotNull(dummyArray.getaDoubleObj());
        assertNotNull(dummyArray.getaLongObj());
    }
}
