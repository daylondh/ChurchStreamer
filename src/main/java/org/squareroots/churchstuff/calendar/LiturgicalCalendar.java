package org.squareroots.churchstuff.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LiturgicalCalendar {
    private EasterCalculator _easterCalculator;
    private FixedDates _fixedDates;
    private String[] _entries;
    private int year;

    public LiturgicalCalendar(int year)
    {
        this.year = year;
        _entries = new String[IsLeapYear()? 366:365];
        BuildYear();
    }

    private boolean IsLeapYear() {
        return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }

    public String LookupByDate(int month, int dayinmonth)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(year, month, dayinmonth);
        int dayOfYear = gc.get(GregorianCalendar.DAY_OF_YEAR);
        return _entries[dayOfYear];
    }

    private void BuildYear() {
        _easterCalculator = new EasterCalculator(year);
        BuildEpiphany();
        BuildLent();
        BuildEaster(); // week and season...
        BuildPentecost();
        BuildAdvent();

    }

    private void BuildAdvent() {
    }

    private void BuildPentecost() {

    }

    private void BuildEaster() {
    }

    private void BuildLent() {
    }

    private void BuildEpiphany() {
        // epiphany is 1/6, and we already have it through fixed dates.
        // we need to get

    }
}
