/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swampbits.chaudiere;

import java.time.Instant;


/**
 *
 * @author paul
 */
public class DateTime {

   private double timeIntervalSince1970 = 0.0;
   private int year = 0;
   private int month = 0;
   private int day = 0;
   private int hour = 0;
   private int minute = 0;
   private int second = 0;
   private int weekDay = -1;
   private boolean haveUnixTimeValue = false;
   private Instant instant;


   public static int numberLeadingZeros(String value) {
      int leadingZeros = 0;
      for (int i=0; i < value.length(); ++i) {
         if (value.charAt(i) != '0') {
            break;
         } else {
            ++leadingZeros;
         }
      }
   
      return leadingZeros;
   }

   /**
    *
    * @param dateValue
    * @return 
    */
   public static DateTime dateFromString(String dateValue) {
      DateTime date = null;
      /*
      date = new DateTime(1);
      int valueLength = dateValue.length();
      if (valueLength > 18) {
         const char* firstDash = ::strchr(dateValue, '-');
         if (firstDash > dateValue) {
            const char* secondDash = ::strchr(firstDash+1, '-');
            if (secondDash) {
               const char* space = ::strchr(secondDash+1, ' ');
               if (space) {
                  const char* firstColon = ::strchr(space+1, ':');
                  if (firstColon) {
                     const char* secondColon = ::strchr(firstColon+1, ':');
                     if (secondColon) {
                        long yearLen = firstDash - dateValue;
                        long monthLen = secondDash - firstDash - 1;
                        long dayLen = space - secondDash - 1;
                        long hourLen = firstColon - space - 1;
                        long minuteLen = secondColon - firstColon - 1;
                        int secondLen = ::strlen(secondColon+1);
                     
                        if ((yearLen == 4L) &&
                            (monthLen == 2L) &&
                            (dayLen == 2L) &&
                            (hourLen == 2L) &&
                            (minuteLen == 2L) &&
                            (secondLen == 2)) {
                           
                           char yearAsString[5];
                           char monthAsString[3];
                           char dayAsString[3];
                           char hourAsString[3];
                           char minuteAsString[3];
                           char secondAsString[3];
                        
                           ::memset(yearAsString, 0, 5);
                           ::memset(monthAsString, 0, 3);
                           ::memset(dayAsString, 0, 3);
                           ::memset(hourAsString, 0, 3);
                           ::memset(minuteAsString, 0, 3);
                           ::memset(secondAsString, 0, 3);
                        
                           ::strncpy(yearAsString, dateValue, 4);
                           ::strncpy(monthAsString, firstDash+1, 2);
                           ::strncpy(dayAsString, secondDash+1, 2);
                           ::strncpy(hourAsString, space+1, 2);
                           ::strncpy(minuteAsString, firstColon+1, 2);
                           ::strncpy(secondAsString, secondColon+1, 2);
                        
                           int leadingZeros1 = numberLeadingZeros(yearAsString, 4);
                           int leadingZeros2 = numberLeadingZeros(monthAsString, 2);
                           int leadingZeros3 = numberLeadingZeros(dayAsString, 2);
                           int leadingZeros4 = numberLeadingZeros(hourAsString, 2);
                           int leadingZeros5 = numberLeadingZeros(minuteAsString, 2);
                           int leadingZeros6 = numberLeadingZeros(secondAsString, 2);
                        
                           if ((leadingZeros1 < 4) &&
                               (leadingZeros2 < 2) &&
                               (leadingZeros3 < 2)) {
                              date.year = ::atoi(yearAsString+leadingZeros1);
                              date.month = ::atoi(monthAsString+leadingZeros2);
                              date.day = ::atoi(dayAsString+leadingZeros3);
                              date.hour = ::atoi(hourAsString+leadingZeros4);
                              date.minute = ::atoi(minuteAsString+leadingZeros5);
                              date.second = ::atoi(secondAsString+leadingZeros6);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      */

      return date;
   }
      
   /**
    *
    * @param date
    * @return
    */
   public static double unixTimeValue(DateTime date) {
      if (date.haveUnixTimeValue) {
         return date.timeIntervalSince1970;
      } else {
         if (date.year >= 1970) {
            // elements of struct tm (from man page)
            /*
            int tm_sec;     // seconds (0 - 60)
            int tm_min;     // minutes (0 - 59)
            int tm_hour;    // hours (0 - 23)
            int tm_mday;    // day of month (1 - 31)
            int tm_mon;     // month of year (0 - 11)
            int tm_year;    // year - 1900
            int tm_wday;    // day of week (Sunday = 0)
            int tm_yday;    // day of year (0 - 365)
            int tm_isdst;   // is summer time in effect?
            char *tm_zone;  // abbreviation of timezone name
            long tm_gmtoff; // offset from UTC in seconds
            */
            /*
            tm time;
            ::memset(&time, 0, sizeof(tm));
            time.tm_year = date.year - 1900;
            time.tm_mon = date.month - 1;
            time.tm_mday = date.day;
            time.tm_hour = date.hour;
            time.tm_min = date.minute;
            time.tm_sec = date.second;
            time.tm_isdst = -1;
            return ::mktime(&time);
            */
            return 0.0;
         } else {
            return 0.0;
         }
      }
   }
      
   /**
    *
    * @param date
    * @param unixTime
    * @return
    */
   public static boolean populateFromUnixTime(DateTime date, double unixTime) {
      //TODO:
      //DQ: use local time or gm time?
      /*
      const time_t epochTime = unixTime;
      struct tm timeComponents;
      ::memset(&timeComponents, 0, sizeof(timeComponents));
      
      if (::localtime_r(&epochTime, &timeComponents)) {
         date.year = timeComponents.tm_year + 1900;
         date.month = timeComponents.tm_mon + 1;
         date.day = timeComponents.tm_mday;
         date.hour = timeComponents.tm_hour;
         date.minute = timeComponents.tm_min;
         date.second = timeComponents.tm_sec;
         date.timeIntervalSince1970 = unixTime;
         date.haveUnixTimeValue = true;
         
         return true;
      } else {
         return false;
      }
      */
      return false;
   }

   /**
    * Retrieves current date and time as GMT in a newly created object
    * @return DateTime instance with GMT date time (caller must delete)
    */
   public static DateTime gmtDateTime() {
      //TODO:
      return null;
   }
      
   /**
    * Default constructor. Retrieves local (system) date and time.
    */
   public DateTime() {
      //TODO: populate with current system time
      instant = Instant.now();
   }
      
   /**
    * Constructs a DateTime instance without retrieving date/time
    * from system. This is useful when the date/time values are
    * already available from another source.
    * @param dummy ignored
    */
   public DateTime(int dummy) {
      // don't retrieve system time
   }
      
   /**
    *
    * @param year
    * @param month
    * @param day
    * @param hour
    * @param minute
    * @param second
    */
   public DateTime(int year,
                   int month,
                   int day,
                   int hour,
                   int minute,
                   int second) {
      this.timeIntervalSince1970 = 0.0;
      this.year = year;
      this.month = month;
      this.day = day;
      this.hour = hour;
      this.minute = minute;
      this.second = second;
      this.weekDay = -1;
      this.haveUnixTimeValue = false;
   }
   
   public DateTime(double timeIntervalSince1970) {
      this.timeIntervalSince1970 = timeIntervalSince1970;
      this.haveUnixTimeValue = true;
      populateFromUnixTime(this, timeIntervalSince1970);
   }
      
   /**
    * Constructs a DateTime instance by parsing a string that
    * has the unformatted date/time (yyyymmddhhmmss).
    * @param dateTime the unformatted date/time as a string
    */
   public DateTime(String dateTime) {
      
   }
      
   /**
    * Equality operator
    * @param compare the DateTime instance to test for equality
    * @return boolean indicating whether the 2 objects are equal
    */
   public boolean equals(DateTime compare) {
      //TODO:
      return false;
   }
      
   /**
    * Less-than operator
    * @param compare the DateTime instance to determine if current object is smaller than
    * @return boolean indicating if current object is smaller than argument object
    */
   public int compare(DateTime compare) {
      //TODO:
      return 0;
   }
      
   /**
    *
    * @param compare
    * @return
    */
   public double timeIntervalSinceDate(DateTime compare) {
      //TODO:
      return 0.0;
   }
      
   /**
    *
    * @return
    */
   public double timeIntervalSince1970() {
      //TODO:
      return 0.0;
   }

   /**
    * Retrieves the date/time as a formatted string (yyyy-mm-dd hh:mm:ss)
    * @return the date/time as a formatted string
    */
   public String formattedString() {
      //TODO:
      return "";
   }
      
   /**
    * Retrieves the date/time as an unformatted string (yyyymmddhhmmss)
    * @return the date/time as an unformatted string
    */
   public String unformattedString() {
      //TODO:
      return "";
   }
      
   /**
    * Sets the year value
    * @param year the new year value
    */
   public void setYear(int year) {
      this.year = year;
   }
      
   /**
    * Retrieves the year value
    * @return year value
    */
   public int getYear() {
      return this.year;
   }

   /**
    * Sets the month value
    * @param month the new month value
    */
   public void setMonth(int month) {
      this.month = month;
   }
      
   /**
    * Retrieves the month value
    * @return month value
    */
   public int getMonth() {
      return this.month;
   }

   /**
    * Sets the day value
    * @param day the new day value
    */
   public void setDay(int day) {
      this.day = day;
   }
      
   /**
    * Retrieves the day value
    * @return day value
    */
   public int getDay() {
      return this.day;
   }

   /**
    * Sets the hour value
    * @param hour the new hour value
    */
   public void setHour(int hour) {
      this.hour = hour;
   }
      
   /**
    * Retrieves the hour value
    * @return hour value
    */
   public int getHour() {
      return this.hour;
   }

   /**
    * Sets the minute value
    * @param minute the new minute value
    */
   public void setMinute(int minute) {
      this.minute = minute;
   }
      
   /**
    * Retrieves the minute value
    * @return minute value
    */
   public int getMinute() {
      return this.minute;
   }

   /**
    * Sets the second value
    * @param second the new second value
    */
   public void setSecond(int second) {
      this.second = second;
   }
      
   /**
    * Retrieves the second value
    * @return second value
    */
   public int getSecond() {
      return this.second;
   }

   /**
    * Sets the weekday value
    * @param weekDay the new weekday value
    */
   public void setWeekDay(int weekDay) {
      this.weekDay = weekDay;
   }
      
   /**
    * Retrieves the weekday value
    * @return weekday value
    */
   public int getWeekDay() {
      return this.weekDay;
   }
}
