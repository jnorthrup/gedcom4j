package org.gedcom4j.model;

import org.gedcom4j.model.IndividualEventType;
import junit.framework.Assert;
import junit.framework.Test;

/**
 * Test for the individual event type
 * 
 * @author frizbog1
 * 
 */
public class IndividualEventTypeTest {

    /**
     * Test for {@link IndividualEventType#getFromTag(String)}
     */
    @Test
    public void testGetFromTag() {
        Assert.assertSame(IndividualEventType.PROBATE,
                IndividualEventType.getFromTag("PROB"));
        Assert.assertNull(IndividualEventType.getFromTag(""));
        Assert.assertNull(IndividualEventType.getFromTag(null));
    }

    /**
     * Test for {@link IndividualEventType#isValidTag(String)}
     */
    @Test
    public void testIsValidTag() {
        Assert.assertTrue(IndividualEventType.isValidTag("BAPM"));
        Assert.assertFalse("Baptism is BAPM, not BAPT like you might expect",
                IndividualEventType.isValidTag("BAPT"));
    }
}
