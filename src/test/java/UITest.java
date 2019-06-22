import org.junit.Test;
import org.squareroots.churchstuff.SUI.DeveloperUI;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;

/**
 * Created by alexh on 3/14/2018.
 */
public class UITest {

    @Test
    public void TestTheUI()
    {
        // pick a year
        int year = 2020;
        LiturgicalCalendar lc = new LiturgicalCalendar(year);
        DeveloperUI dui = new DeveloperUI(lc);
        dui.showSimple();
    }
}
