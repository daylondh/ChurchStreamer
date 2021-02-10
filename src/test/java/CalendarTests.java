import org.junit.Test;
import org.squareroots.churchstuff.calendar.EasterCalculator;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;

import java.util.GregorianCalendar;

public class CalendarTests {

    @Test
    public void TestEasterCalculation()
    {
        // should be april fools day 2018
        EasterCalculator ec2018 = new EasterCalculator(2021);
        assert (ec2018.getEasterDay() == 4);
        assert (ec2018.getEasterMonth() == 4);
    }

    @Test
    public void TestAshWednesdayCalculation()
    {
        int year = 2021;
        LiturgicalCalendar lc = new LiturgicalCalendar(year);
        int aw = lc.GetAshWednesday();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(year, 0, 0);
        gc.add(GregorianCalendar.DAY_OF_YEAR, aw);
        System.out.println("Ash wednesday is " + gc.get(GregorianCalendar.MONTH) +  " " + gc.get(GregorianCalendar.DAY_OF_MONTH) + ", on day " + aw + " of the year." );
    }

    @Test
    public void TestThanksgiving()
    {
        int year = 2019;
        LiturgicalCalendar lc = new LiturgicalCalendar(year);
        int thanksgiving = lc.GetThanksgiving();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(year, 0, 0);
        gc.add(GregorianCalendar.DAY_OF_YEAR, thanksgiving);
        System.out.println("Thanksgiving is " + gc.get(GregorianCalendar.MONTH) +  " " + gc.get(GregorianCalendar.DAY_OF_MONTH) + ", on day " + thanksgiving + " of the year." );

    }

    @Test
    public void TestEpiphany()
    {
        int year = 2021;
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(year, 1, 8);
        LiturgicalCalendar lc = new LiturgicalCalendar(year);
        String day = lc.LookupByDate(1,9);
    }
}
