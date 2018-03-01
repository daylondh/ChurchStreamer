import org.junit.Test;
import org.squareroots.churchstuff.calendar.EasterCalculator;

public class CalendarTests {

    @Test
    public void TestEasterCalculation()
    {
        // should be april fools day 2018
        EasterCalculator ec2018 = new EasterCalculator(2018);
        assert (ec2018.getEasterDay() == 1);
        assert (ec2018.getEasterMonth() == 4);
    }
}
