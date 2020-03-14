import org.junit.Test;
import org.squareroots.churchstuff.Bulletins.CommandLineHandler;
import org.squareroots.churchstuff.Bulletins.OnBulletinRecieved;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CommandLineTests {
    @Test
    public void getURL() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        int day   = localDate.getDayOfMonth();


        String formattedDate = year + "-" + month + "-" + day;
        if (month < 10) {
             formattedDate = year + "-0" + month + "-" + day;
        }
        if (day < 10) {
            formattedDate = year + "-" + month + "-0" + day;
        }
        if (day < 10 && month < 10) {
            formattedDate = year + "-0" + month + "-0" + day;
        }


        System.out.println(formattedDate);
        CommandLineHandler.getURL(formattedDate, new OnBulletinRecieved() {
            @Override
            public void doOnSuccess(String url) {
                System.out.println(url);
            }

            @Override
            public void doOnFailure() {
                System.out.println("failed");
            }
        });
    }
}
