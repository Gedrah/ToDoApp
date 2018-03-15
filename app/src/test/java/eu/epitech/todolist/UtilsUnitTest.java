package eu.epitech.todolist;

import eu.epitech.todolist.Utils.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {
    @Test
    public void TestCompareDate() throws Exception {
        String date = "13/02/2018";

        assertEquals(false, Utils.compareDate(date));
    }

    @Test
    public void TestCompareTime() throws Exception {
        String time = "13:02";

        assertEquals(true, Utils.compareTime(time));
    }

    @Test
    public void TestHoursValidity() throws Exception  {
        String time = "3:2";

        assertEquals("03:02", Utils.fixedHoursTime(time));
    }
}