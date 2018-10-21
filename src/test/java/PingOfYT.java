import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

public class PingOfYT {
   @Test
   public void ping() {

        long currentTime = System.currentTimeMillis();
        boolean isPinged = false; // 2 seconds
        try {
            isPinged = InetAddress.getByName("www.youtube.com").isReachable(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentTime = System.currentTimeMillis() - currentTime;
        if(isPinged) {
            System.out.println("pinged successfully in "+ currentTime+ " millisecond");
        } else {
            System.out.println("PIng failed.");
        }
    }
}
