package org.squareroots.churchstuff.Bulletins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CommandLineHandler {

    public static void getURL(String date, OnBulletinRecieved obr) {
        try
        {
            Process p=Runtime.getRuntime().exec("curl https://us-central1-churchstreamer-1547585613263.cloudfunctions.net/getURL?date=" + date);
            p.waitFor();
            BufferedReader reader=new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );
            String line;
            while((line = reader.readLine()) != null)
            {
                if(line.contains("404 : No file at that location.")) {
                    obr.doOnFailure();
                }
                else if (line.contains("https://firebasestorage.googleapis.com")) {
                    obr.doOnSuccess(line);
                }
                System.out.println("URL: " + line);
            }

        }
        catch(IOException | InterruptedException e1) {e1.printStackTrace();}

    }

    public static void getURL(OnBulletinRecieved obr) {
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
        try
        {
            Process p=Runtime.getRuntime().exec("curl https://us-central1-churchstreamer-1547585613263.cloudfunctions.net/getURL?date=" + formattedDate);
            p.waitFor();
            BufferedReader reader=new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );
            String line;
            while((line = reader.readLine()) != null)
            {
                if(line.contains("404 : No file at that location.")) {
                    obr.doOnFailure();
                }
                else if (line.contains("https://firebasestorage.googleapis.com")) {
                    obr.doOnSuccess(line);
                }
                System.out.println("URL: " + line);
            }

        }
        catch(IOException | InterruptedException e1) {e1.printStackTrace();}

    }
}
