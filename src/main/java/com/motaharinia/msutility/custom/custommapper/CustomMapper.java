package com.motaharinia.msutility.custom.custommapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
     * @param instantLong میلی ثانیه زمان
     * @return خروجی: شیی تاریخ
     */
    default LocalDate mapToLocalDate(Long instantLong) {
        //because of java8 compatibility:
//        return instantLong == null ? null : LocalDate.ofInstant(Instant.ofEpochMilli(instantLong), ZoneId.systemDefault());
        return instantLong == null ? null : Instant.ofEpochMilli(instantLong).atZone(ZoneId.systemDefault()).toLocalDate();
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
     * @param instantLong میلی ثانیه زمان
     * @return خروجی: شیی تاریخ-زمان
     */
    default LocalDateTime mapToLocalDateTime(Long instantLong) {
        return instantLong == null ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(instantLong), ZoneId.systemDefault());
    }
}
