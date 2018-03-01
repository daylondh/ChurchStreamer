package org.squareroots.churchstuff.calendar;

import java.util.HashMap;

public class FixedDates {
    HashMap<Integer,String> _dates;

    public FixedDates()
    {
        Populate();
    }

    private void Populate() {
        _dates.put(0101, "New Year's Day");
        _dates.put(0106, "Epiphany");
        _dates.put(0107, "Baptism of our Lord");
    }
}
