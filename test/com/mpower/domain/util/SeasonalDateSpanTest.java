package com.mpower.domain.util;

import org.junit.Test;
import static org.junit.Assert.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 */
public class SeasonalDateSpanTest {

    private final static DateFormat FORMAT = new SimpleDateFormat("MM/dd/yyyy");


    @Test
    public void testSameYear() throws Exception {

        //FEB 1st 2009
        Date start = FORMAT.parse("02/01/2009");
        // JUN 1st 2009
        Date end = FORMAT.parse("06/01/2009");

        SeasonalDateSpan ds = new SeasonalDateSpan(start,end);

        Date testDate = FORMAT.parse("03/01/2009");
        assertTrue(ds.contains(testDate));

        testDate = FORMAT.parse("03/01/2010");
        assertTrue(ds.contains(testDate));

        testDate = FORMAT.parse("03/01/2008");
        assertTrue(ds.contains(testDate));

        testDate = FORMAT.parse("01/15/2009");
        assertFalse( ds.contains(testDate));

        testDate = FORMAT.parse("01/15/2008");
        assertFalse( ds.contains(testDate));

        testDate = FORMAT.parse("01/15/2010");
        assertFalse( ds.contains(testDate));

        testDate = FORMAT.parse("06/15/2009");
        assertFalse( ds.contains(testDate));

        testDate = FORMAT.parse("06/15/2008");
        assertFalse( ds.contains(testDate));

        testDate = FORMAT.parse("06/15/2010");
        assertFalse( ds.contains(testDate));

    }

    @Test
    public void testRolledYear() throws Exception {

        //NOV 1st 2009
        Date start = FORMAT.parse("11/01/2009");
        // FEB 1st 2010
        Date end = FORMAT.parse("02/01/2010");

        SeasonalDateSpan ds = new SeasonalDateSpan(start,end);

        Date testDate = FORMAT.parse("11/15/2009");
        assertTrue(ds.contains(testDate));

        testDate = FORMAT.parse("01/15/2010");
        assertTrue(ds.contains(testDate));

        testDate = FORMAT.parse("01/15/2008");
        assertTrue(ds.contains(testDate));

        testDate = FORMAT.parse("01/15/2009");
        assertTrue( ds.contains(testDate));

        testDate = FORMAT.parse("06/15/2009");
        assertFalse( ds.contains(testDate));

        testDate = FORMAT.parse("10/30/2009");
        assertFalse( ds.contains(testDate));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDates() throws Exception {

        //NOV 1st 2009
        Date start = FORMAT.parse("11/01/2009");
        // FEB 1st 2010
        Date end = FORMAT.parse("10/01/2009");

        SeasonalDateSpan ds = new SeasonalDateSpan(start,end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullEndDate() throws Exception {

        //NOV 1st 2009
        Date start = FORMAT.parse("11/01/2009");
        SeasonalDateSpan ds = new SeasonalDateSpan(start,null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullStartDate() throws Exception {

        //NOV 1st 2009
        Date end = FORMAT.parse("11/01/2009");
        SeasonalDateSpan ds = new SeasonalDateSpan(null, end);

    }

}
