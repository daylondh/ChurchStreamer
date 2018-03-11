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
    private int _christmas = 358;
    private int _newYearDayOfWeek;
    private int year;

    public LiturgicalCalendar(int year)
    {
        // don't forget memorial day, labor day, Thanksgiving (Day and eve)
        this.year = year;
        _entries = new String[IsLeapYear()? 366:365];
        Calendar gc = new GregorianCalendar();
        gc.set(year, 0, 1);
        _newYearDayOfWeek = gc.get(Calendar.DAY_OF_WEEK)-1;
        if(IsLeapYear())
            _christmas++;
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
        // Feast of St. Andrew is November 30. We want to find the closest Sunday, and that's Advent 1. If it falls on a
        // Wednesday means the sunday before. The day is the 333rd day of the year (counting from 0) or 334th if a leap year
        int stAndrew = IsLeapYear() ? 334: 333;

        int totDays = stAndrew + _newYearDayOfWeek;
        int modulo = totDays % 7;
        int advent1 = -1;
        if(modulo < 5)
            advent1 = stAndrew - modulo;
        else
            advent1 = stAndrew + 7 - modulo;
        _entries[advent1 - 7] = "Last Sunday in Pentecost";
        _entries[advent1 - 6] = "Last Monday in Pentecost";
        _entries[advent1 - 5] = "Last Tuesday in Pentecost";
        _entries[advent1 - 4] = "Last Wednesday in Pentecost";
        _entries[advent1 - 3] = "Last Thursday in Pentecost";
        _entries[advent1 - 2] = "Last Friday in Pentecost";
        _entries[advent1 - 1] = "Last Saturday in Pentecost";
        _entries[advent1] = "First Sunday in Advent";
        _firstAdvent = advent1;
        for(int i = _firstAdvent; i < _christmas; i++)
        {
            if(_entries[i] == null || _entries[i] == "")
            {
                int weekSinceAdvent = (i-_firstAdvent) / 7 + 1;
                _entries[i] = Nth(weekSinceAdvent) + WeekDay(i) + " in Advent";
            }
        }
        for(int i = _christmas+1; i < (IsLeapYear()? 366:365); i++)
        {
            if(_entries[i] == null || _entries[i] == "")
            {
                int weekSinceChristmas = (i-_christmas) / 7 + 1;
                _entries[i] = Nth(weekSinceChristmas) + WeekDay(i) + " of Christmas";
            }
        }
    }

    private void BuildPentecost() {
        int pentecostEnd = _firstAdvent - 8;
        _entries[_pentecost + 1] = "Pentecost Monday";
        _entries[_pentecost + 2] = "Pentecost Tuesday";
        _entries[_pentecost + 3] = "Pentecost Wednesday";
        _entries[_pentecost + 4] = "Pentecost Thursday";
        _entries[_pentecost + 5] = "Pentecost Friday";
        _entries[_pentecost + 6] = "Pentecost Saturday";
        _entries[_pentecost + 7] = "Holy Trinity Sunday";
        for(int i = _pentecost+8; i < pentecostEnd; i++)
        {
            if(_entries[i] ==  null || _entries[i] == "") {
                int weekSincePentecost = (i-_pentecost) / 7;
                _entries[i] = Nth(weekSincePentecost) + WeekDay(i) + " after Pentecost";
            }
        }
        System.out.println("ha");
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
        for(int i = 0; i < _ashWednesday - 3; i++)
        {
            int weekInYear = i/7;
            if(_entries[i] ==  null || _entries[i] == "")
            {
                if(weekInYear == 0 && i < 6)
                    _entries[i] = "Second " + WeekDay(i) + " after Christmas";
                else if(i < 13 && i > 6 && WeekDay(i).equals("Sunday"))
                    _entries[i] = "Baptism of Our Lord";
                else
                {
                    // ephiphany
                    int weekSinceEpiphany = (i-5) / 7;
                    _entries[i] = Nth(weekSinceEpiphany) + WeekDay(i) + " after Ephiphany";
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
                _entries[i] = Nth(weekInLent) + WeekDay(i) + " of Lent";
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
                _entries[i] = Nth(weekafterEaster) + WeekDay(i) + " of Easter";
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

    private String WeekDay(int dayInYear) {
        int totDays = dayInYear + _newYearDayOfWeek;
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
