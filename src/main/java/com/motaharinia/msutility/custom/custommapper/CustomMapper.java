package com.motaharinia.msutility.custom.custommapper;

import com.motaharinia.msutility.tools.calendar.CalendarTools;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author eng.motahari@gmail.com<br>
 * Default mapper useful for all mappers. it converts basic transformations
 */
public interface CustomMapper {


    //--------------------Instant-Long--------------------

    /**
     * Instant to Long(Epoch-milli)
     *
     * @param instant Instant value
     * @return Long(Epoch - milli) value
     */
    default Long mapInstantToLong(Instant instant) {
        return instant == null ? null : instant.toEpochMilli();
    }

    /**
     * Long(Epoch-milli) value to Instant
     *
     * @param epochMilliLong Long(Epoch-milli) value
     * @return Instant value
     */
    default Instant mapLongToInstant(Long epochMilliLong) {
        return epochMilliLong == null ? null : Instant.ofEpochMilli(epochMilliLong);
    }

    //--------------------LocalDate-Long--------------------

    /**
     * LocalDate to Long(Epoch-milli)
     *
     * @param localDate LocalDate value
     * @return Long(Epoch - milli) value
     */
    default Long mapLocalDateToLong(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Long(Epoch-milli) value to LocalDate
     *
     * @param epochMilliLong Long(Epoch-milli) value
     * @return LocalDate value
     */
    default LocalDate mapLongToLocalDate(Long epochMilliLong) {
        //because of java8 compatibility:
//        return instantLong == null ? null : LocalDate.ofInstant(Instant.ofEpochMilli(instantLong), ZoneId.systemDefault());
        return epochMilliLong == null ? null : Instant.ofEpochMilli(epochMilliLong).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //--------------------LocalDateTime-Long--------------------

    /**
     * LocalDateTime to Long(Epoch-milli)
     *
     * @param localDateTime LocalDateTime value
     * @return Long(Epoch - milli) value
     */
    default Long mapLocalDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Long(Epoch-milli) value to LocalDateTime
     *
     * @param epochMilliLong Long(Epoch-milli) value
     * @return LocalDateTime value
     */
    default LocalDateTime mapLongToLocalDateTime(Long epochMilliLong) {
        return epochMilliLong == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilliLong), ZoneId.systemDefault());
    }

    //--------------------Date-Long--------------------

    /**
     * Date to Long(Epoch-milli)
     *
     * @param date Date value
     * @return Long(Epoch - milli) value
     */
    default Long mapDateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
     * Long(Epoch-milli) value to Date
     *
     * @param epochMilliLong Long(Epoch-milli) value
     * @return Date value
     */
    default Date mapLongToDate(Long epochMilliLong) {
        return epochMilliLong == null ? null : new Date(epochMilliLong);
    }


    //--------------------Instant-String--------------------

    /**
     * Instant to String(JalaliDate)
     *
     * @param instant Instant value
     * @return String(JalaliDate) value
     */
    default String mapInstantToJalaliString(Instant instant) {
        return instant == null ? null : CalendarTools.gregorianToJalaliString(instant, "/", true);
    }

    /**
     * String(JalaliDate) to Instant
     *
     * @param jalaliString String(JalaliDate) value
     * @return Instant value
     */
    default Instant mapJalaliStringToInstant(String jalaliString) {
        jalaliString = CalendarTools.fixJalaliString(jalaliString, "/");
        return jalaliString == null ? null : CalendarTools.jalaliToGregorianInstant(jalaliString, "/", ZoneId.systemDefault());
    }

    //--------------------LocalDate-String--------------------

    /**
     * LocalDate to String(JalaliDate)
     *
     * @param localDate LocalDate value
     * @return String(JalaliDate) value
     */
    default String mapLocalDateToJalaliString(LocalDate localDate) {
        return localDate == null ? null : CalendarTools.gregorianToJalaliString(localDate, "/", false);
    }

    /**
     * String(JalaliDate) to LocalDate
     *
     * @param jalaliString String(JalaliDate) value
     * @return LocalDate value
     */
    default LocalDate mapJalaliStringToLocalDate(String jalaliString) {
        jalaliString = CalendarTools.fixJalaliString(jalaliString, "/");
        return jalaliString == null ? null : CalendarTools.jalaliToGregorianLocalDate(jalaliString, "/", ZoneId.systemDefault());
    }

    //--------------------LocalDateTime-String--------------------

    /**
     * LocalDateTime to String(JalaliDate)
     *
     * @param localDateTime LocalDateTime value
     * @return String(JalaliDate) value
     */
    default String mapLocalDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime == null ? null : CalendarTools.gregorianToJalaliString(localDateTime, "/", true);
    }

    /**
     * String(JalaliDate) to LocalDateTime
     *
     * @param jalaliString String(JalaliDate) value
     * @return LocalDateTime value
     */
    default LocalDateTime mapStringToLocalDateTime(String jalaliString) {
        jalaliString = CalendarTools.fixJalaliString(jalaliString, "/");
        return jalaliString == null ? null : CalendarTools.jalaliToGregorianLocalDateTime(jalaliString, "/", ZoneId.systemDefault());
    }

    //--------------------Date-String--------------------

    /**
     * Date to String(JalaliDate)
     *
     * @param date Date value
     * @return String(JalaliDate) value
     */
    default String mapDateToJalaliString(Date date) {
        return date == null ? null : CalendarTools.gregorianToJalaliString(date, "/", true);
    }

    /**
     * String(JalaliDate) to Date
     *
     * @param jalaliString String(JalaliDate) value
     * @return Date value
     */
    default Date mapJalaliStringToDate(String jalaliString) {
        jalaliString = CalendarTools.fixJalaliString(jalaliString, "/");
        return jalaliString == null ? null : CalendarTools.jalaliToGregorianDate(jalaliString, "/");
    }

}
