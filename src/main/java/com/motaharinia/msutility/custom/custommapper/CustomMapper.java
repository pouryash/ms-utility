package com.motaharinia.msutility.custom.custommapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس مپر(مبدل پیش فرض انتیتی به مدل و مدل به انتیتی)
 */
public interface CustomMapper {


    /**
     * متد مبدل شیی لحظه به میلی ثانیه زمان
     *
     * @param instant شیی لحظه
     * @return خروجی: میلی ثانیه
     */
    default Long mapFromInstant(Instant instant) {
        return instant == null ? null : instant.toEpochMilli();
    }

    /**
     * متد مبدل میلی ثانیه زمان به شیی لحظه
     *
     * @param instantLong میلی ثانیه زمان
     * @return خروجی: شیی لحظه
     */
    default Instant mapToInstant(Long instantLong) {
        return instantLong == null ? null : Instant.ofEpochMilli(instantLong);
    }

    /**
     * متد مبدل شیی تاریخ به میلی ثانیه زمان
     *
     * @param localDate شیی تاریخ
     * @return خروجی: میلی ثانیه
     */
    default Long mapFromLocalDate(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * متد مبدل میلی ثانیه زمان به شیی تاریخ
     *
     * @param epochLong میلی ثانیه زمان
     * @return خروجی: شیی تاریخ
     */
    default LocalDate mapToLocalDate(Long epochLong) {
        //because of java8 compatibility:
//        return instantLong == null ? null : LocalDate.ofInstant(Instant.ofEpochMilli(instantLong), ZoneId.systemDefault());
        return epochLong == null ? null : Instant.ofEpochMilli(epochLong).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * متد مبدل شیی تاریخ-زمان به میلی ثانیه زمان
     *
     * @param localDateTime شیی تاریخ-زمان
     * @return خروجی: میلی ثانیه
     */
    default Long mapFromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * متد مبدل میلی ثانیه زمان به شیی تاریخ-زمان
     *
     * @param epochLong میلی ثانیه زمان
     * @return خروجی: شیی تاریخ-زمان
     */
    default LocalDateTime mapToLocalDateTime(Long epochLong) {
        return epochLong == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(epochLong), ZoneId.systemDefault());
    }


    /**
     * متد مبدل شیی Date به میلی ثانیه زمان
     *
     * @param date شیی Date
     * @return خروجی: میلی ثانیه
     */
    default Long mapFromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
     * متد مبدل میلی ثانیه زمان به شیی Date
     *
     * @param epochLong میلی ثانیه زمان
     * @return خروجی: شیی Date
     */
    default Date mapToDate(Long epochLong) {
        return epochLong == null ? null : new Date(epochLong);
    }
}
