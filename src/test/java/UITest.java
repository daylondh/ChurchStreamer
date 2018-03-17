import org.junit.Test;
import org.squareroots.churchstuff.SUI.UserInterface;
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
        UserInterface ui = new UserInterface(lc);
        ui.createAndShowGui();
    }
}
