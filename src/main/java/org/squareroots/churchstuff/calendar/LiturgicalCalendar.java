package org.squareroots.churchstuff.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LiturgicalCalendar {
    private EasterCalculator _easterCalculator;
    private FixedDates _fixedDates = new FixedDates();
    private String[] _entries;
    private int _ashWednesday;
    private int _pentecost;
    private int _firstAdvent;
    private int year;

    public LiturgicalCalendar(int year)
    {
        // don't forget memorial day, labor day, Thanksgiving (Day and eve)
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
        _entries = _fixedDates.TransferDates(_entries);
        _easterCalculator = new EasterCalculator(year);
        BuildToPentecost(); // week and season...
        BuildAdvent();
        BuildPentecost();
    }

    private void BuildAdvent() {
    }

    private void BuildPentecost() {
    }

    private void BuildToPentecost() {
        // works from 1/1 through ephiphany to easter
        int emo = _easterCalculator.getEasterMonth();
        int edomo = _easterCalculator.getEasterDay();
        Calendar gc = new GregorianCalendar();
        gc.set(year, emo - 1, edomo);
        int easterDayInYear = gc.get(Calendar.DAY_OF_YEAR)-1; // because we count from 0
        _entries[easterDayInYear] = "Easter";
        _ashWednesday = easterDayInYear - 46; // 40 days of lent, plus sundays
        _entries[_ashWednesday] = "Ash Wednesday";
        _entries[_ashWednesday - 3] = "Transfiguration";
        // ephiphany
        gc.set(year, 0, 1);
        int newYearDayOfWeek = gc.get(Calendar.DAY_OF_WEEK)-1; //between 0 and 6, Sun-Sat
        for(int i = 0; i < _ashWednesday - 3; i++)
        {
            int weekInYear = i/7;
            if(_entries[i] ==  null || _entries[i] == "")
            {
                if(weekInYear == 0 && i < 6)
                    _entries[i] = "Second " + WeekDay(i, newYearDayOfWeek) + " after Christmas";
                else if(i < 13 && i > 6 && WeekDay(i, newYearDayOfWeek).equals("Sunday"))
                    _entries[i] = "Baptism of Our Lord";
                else
                {
                    // ephiphany
                    int weekSinceEpiphany = (i-5) / 7;
                    _entries[i] = Nth(weekSinceEpiphany) + WeekDay(i, newYearDayOfWeek) + " after Ephiphany";
                }
            }
        }
        _entries[_ashWednesday - 2] = "Monday after Transfiguration";
        _entries[_ashWednesday - 1] = "Tuesday after Transfiguration";
        // lent
        for(int i = _ashWednesday - 3; i < easterDayInYear - 7; i++)
        {
            int weekInLent = (i - _ashWednesday+6) / 7;
            if(_entries[i] ==  null || _entries[i] == "") {
                _entries[i] = Nth(weekInLent) + WeekDay(i, newYearDayOfWeek) + " of Lent";
            }
        }
        _entries[easterDayInYear - 7] = "Passion Sunday";
        _entries[easterDayInYear - 6] = "Monday in Holy Week";
        _entries[easterDayInYear - 5] = "Tuesday in Holy Week";
        _entries[easterDayInYear - 4] = "Wednesday in Holy Week";
        _entries[easterDayInYear - 3] = "Maundy Thursday";
        _entries[easterDayInYear - 2] = "Good Friday";
        _entries[easterDayInYear - 1] = "Holy Saturday";
        _entries[easterDayInYear + 1] = "Easter Monday";
        _entries[easterDayInYear +2] = "Easter Tuesday";
        _entries[easterDayInYear +3] = "Easter Wednesday";
        _entries[easterDayInYear +4] = "Easter Thursday";
        _entries[easterDayInYear +5] = "Easter Friday";
        _entries[easterDayInYear +6] = "Easter Saturday";
        for(int i = easterDayInYear + 7; i < easterDayInYear + 48; i++)
        {
            int weekafterEaster = (i - easterDayInYear) / 7 + 1;
            if(_entries[i] ==  null || _entries[i] == "") {
                _entries[i] = Nth(weekafterEaster) + WeekDay(i, newYearDayOfWeek) + " of Easter";
            }
        }
        _pentecost = easterDayInYear + 49;
        _entries[_pentecost - 1] = "Pentecost Eve";
        _entries[_pentecost] = "Pentecost";
    }

    private String Nth(int sinceStart) {
        if(sinceStart == 0)
            return "";
        if(sinceStart == 1)
            return "First ";
        if(sinceStart == 2)
            return "Second ";
        if(sinceStart == 3)
            return "Third ";
        if(sinceStart == 4)
            return "Fourth ";
        if(sinceStart == 5)
            return "Fifth ";
        if(sinceStart == 6)
            return "Sixth ";
        if(sinceStart == 7)
            return "Seventh ";
        if(sinceStart == 8)
            return "Eighth ";
        if(sinceStart == 9)
            return "Ninth ";
        if(sinceStart == 10)
            return "Tenth ";
        if(sinceStart == 11)
            return "Eleventh ";
        if(sinceStart == 12)
            return "Twelfth ";
        if(sinceStart == 13)
            return "Thirteenth ";
        if(sinceStart == 14)
            return "Fourteenth ";
        if(sinceStart == 15)
            return "Fifteenth ";
        if(sinceStart == 16)
            return "Sixteenth ";
        if(sinceStart == 17)
            return "Seventeenth ";
        if(sinceStart == 18)
            return "Eighteenth ";
        if(sinceStart == 19)
            return "Nineteenth ";
        if(sinceStart == 20)
            return "Twentieth ";
        if(sinceStart == 21)
            return "Twenty-First ";
        if(sinceStart == 22)
            return "Twenty-Second ";
        if(sinceStart == 23)
            return "Twenty-Third ";
        if(sinceStart == 24)
            return "Twenty-Fourth ";
        if(sinceStart == 25)
            return "Twenty-Fifth ";
        if(sinceStart == 26)
            return "Twenty-Sixth ";
        if(sinceStart == 27)
            return "Twenty-Seventh ";
        if(sinceStart == 28)
            return "Twenty-Eighth ";
        return "Twenty-Ninth";
    }

    private String WeekDay(int dayInYear, int newYearDayOfWeek) {
        int totDays = dayInYear + newYearDayOfWeek;
        int modulo = totDays % 7;
        switch (modulo)
        {
            case 0:
                return "Sunday";
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                return "DUNNO";
        }
    }

    public int GetAshWednesday()
    {
        return _ashWednesday;
    }
}
