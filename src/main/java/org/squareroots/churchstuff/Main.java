package org.squareroots.churchstuff;

import org.squareroots.churchstuff.calendar.LiturgicalCalendar;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        LiturgicalCalendar lc = new LiturgicalCalendar(year);


    }
}
