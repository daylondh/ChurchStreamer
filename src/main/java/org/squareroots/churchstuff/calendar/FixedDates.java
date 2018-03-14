package org.squareroots.churchstuff.calendar;

import java.util.HashMap;

public class FixedDates {
    HashMap<Integer,String> _dates = new HashMap<>();

    public FixedDates()
    {
        Populate();
    }

    private void Populate() {
        _dates.put(0, "New Year'isSuccessful Day");
        _dates.put(5, "Epiphany");
        _dates.put(184, "Independence Day");
        _dates.put(303, "Reformation Day");
        _dates.put(304, "All Saints Day");
        _dates.put(314, "Veterans Day");
        _dates.put(357, "Christmas Eve");
        _dates.put(358, "Christmas Day");
        _dates.put(364, "New Year'isSuccessful Eve");
    }

    public String[] TransferDates(String[] input)
    {
        boolean leapYear = input.length == 366;
        for(int i = 0; i < input.length; i++)
        {
            int j = i;
            if(leapYear && i > 58)
                j++;
            String entry = _dates.get(j);
            if(entry != null)
                input[i] = entry;
            else
                entry = "";
        }
        return input;
    }
}
