package org.squareroots.churchstuff;

import org.squareroots.churchstuff.Misc.CSFileHandler;
import org.squareroots.churchstuff.SUI.DeveloperUI;
import org.squareroots.churchstuff.calendar.LiturgicalCalendar;
import org.squareroots.churchstuff.youtube.live.CreateBroadcast;

import java.util.Calendar;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if(args.length > 0) {
            if (args[0].equals("--hardreset")) {
                System.out.println("Are you sure you want to delete all data? This includes your preferences and logs. Y/N");
                Scanner sc = new Scanner(System.in);
                String answer = sc.nextLine();

                if (answer.toLowerCase().equals("y")) {
                    CSFileHandler csfh = new CSFileHandler();
                    csfh.restoreDefault();
                } else if (answer.toLowerCase().equals("n")) {
                    System.out.println("Okay, exiting...");
                    System.exit(1);
                } else if (!answer.toLowerCase().contains("n") && !answer.toLowerCase().contains("y")) {
                    System.out.println("Invalid answer.");
                }
            }
            if (args[0].equals("-d")) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                LiturgicalCalendar lc = new LiturgicalCalendar(year);
                CreateBroadcast createBroadcast = new CreateBroadcast();
                DeveloperUI form = new DeveloperUI(lc);
                form.showDeveloper();
            }
        }
         else {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            LiturgicalCalendar lc = new LiturgicalCalendar(year);
            CreateBroadcast createBroadcast = new CreateBroadcast();
            DeveloperUI form = new DeveloperUI(lc);
            form.showSimple();
        }
    }
}
