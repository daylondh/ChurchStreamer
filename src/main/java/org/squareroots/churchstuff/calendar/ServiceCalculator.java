package org.squareroots.churchstuff.calendar;

import java.util.Calendar;

public class ServiceCalculator {
    Calendar calendar;


    public String CalculateService()
    {
        calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMin = calendar.get(Calendar.MINUTE);
        if(currentHour < 9)
            return "Early Service";
        if(currentHour <= 10)
        {
            // either a late service or a single service
            if(currentHour < 10)
                return "Single Service";
            if(currentMin < 5)
                return "Single Service";
            return "Late Service";
        }
        return "Evening Service";
    }
}
