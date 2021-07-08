package com.motaharinia.msutility.tools.calendar;

import com.motaharinia.msutility.custom.customexception.utility.UtilityException;
import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customfield.CustomDateTime;
import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
 * Description: <br>
 * کلاس تست CalendarTools
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalendarToolsUnitTest {
    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void beforeEach() {
        Locale.setDefault(Locale.US);
    }

    /**
     * این متد بعد از هر تست این کلاس اجرا میشود
     */
    @AfterEach
    void afterEach() {
        Locale.setDefault(Locale.US);
    }

    //--------------------------------------------------متدهای کمکی--------------------------------------------------
    @Order(1)
    @Test
    void fixDateSlashTest() {
        try {
            assertThat(CalendarTools.fixDateSlash("1399/5/3", "/")).isEqualTo("1399/05/03");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(2)
    @Test
    void fixOneDigitTest() {
        try {
            assertThat(CalendarTools.fixOneDigit("5")).isEqualTo("05");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای دریافت تاریخ و زمان فعلی--------------------------------------------------

    @Order(3)
    @Test
    void getCurrentGregorianDateStringTest() {
        try {
            assertThat(CalendarTools.getCurrentGregorianDateString("/")).isNotNull();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(4)
    @Test
    void getCurrentGregorianDateTimeStringTest() {
        try {
            assertThat(CalendarTools.getCurrentGregorianDateTimeString("/")).isNotNull();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(5)
    @Test
    void getCurrentJalaliDateStringTest() {
        try {
            assertThat(CalendarTools.getCurrentJalaliDateString("/")).isNotNull();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(6)
    @Test
    void getCurrentJalaliDateTimeStringTest() {
        try {
            assertThat(CalendarTools.getCurrentJalaliDateTimeString("/")).isNotNull();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای تبدیل تاریخ جلالی به میلادی--------------------------------------------------
    @Order(7)
    @Test
    void jalaliToGregorianDateTest() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.jalaliToGregorianDate("1399/12/30", "/")).isEqualTo(cal.getTime());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(8)
    @Test
    void jalaliToGregorianDateWithTimeTest() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.jalaliToGregorianDate("1399/12/30 00:00:00", "/")).isEqualTo(cal.getTime());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Order(9)
    @Test
    void jalaliToGregorianStringTest() {
        try {
            assertThat(CalendarTools.jalaliToGregorianString("1399/12/30", "/", "-")).isEqualTo("2021-03-20");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(10)
    @Test
    void jalaliToGregorianStringWithTimeTest() {
        try {
            assertThat(CalendarTools.jalaliToGregorianString("1399/12/30 00:00:00", "/", "-")).isEqualTo("2021-03-20 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(11)
    @Test
    void jalaliToGregorianInstantTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.jalaliToGregorianInstant("1399/12/30", "/", ZoneId.systemDefault())).isEqualTo(zonedDateTime.toInstant());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(12)
    @Test
    void jalaliToGregorianInstantWithTimeTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.jalaliToGregorianInstant("1399/12/30 00:00:00", "/", ZoneId.systemDefault())).isEqualTo(zonedDateTime.toInstant());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(13)
    @Test
    void jalaliToGregorianLocalDateTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.jalaliToGregorianLocalDate("1399/12/30", "/", ZoneId.systemDefault())).isEqualTo(zonedDateTime.toLocalDate());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Order(14)
    @Test
    void jalaliToGregorianLocalDateWithTimeTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.jalaliToGregorianLocalDate("1399/12/30 00:00:00", "/", ZoneId.systemDefault())).isEqualTo(zonedDateTime.toLocalDate());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(15)
    @Test
    void jalaliToGregorianLocalDateTimeTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.jalaliToGregorianLocalDateTime("1399/12/30", "/", ZoneId.systemDefault())).isEqualTo(zonedDateTime.toLocalDateTime());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای تبدیل تاریخ میلادی به جلالی--------------------------------------------------

    @Order(16)
    @Test
    void gregorianToJalaliStringTest() {
        try {
            assertThat(CalendarTools.gregorianToJalaliString("2021/03/20", "/", "-")).isEqualTo("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(17)
    @Test
    void gregorianToJalaliStringWithTimeTest() {
        try {
            assertThat(CalendarTools.gregorianToJalaliString("2021/03/20 00:00:00", "/", "-")).isEqualTo("1399-12-30 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(18)
    @Test
    void gregorianToJalaliDateTest() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.gregorianToJalaliString(cal.getTime(),"-",false)).isEqualTo("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(19)
    @Test
    void gregorianToJalaliDateWithTimeTest() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.gregorianToJalaliString(cal.getTime(),"-",true)).isEqualTo("1399-12-30 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(20)
    @Test
    void gregorianToJalaliInstantTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliString(zonedDateTime.toInstant(),"-",false).toString()).hasToString("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(21)
    @Test
    void gregorianToJalaliInstantWithTimeTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliString(zonedDateTime.toInstant(),"-",true).toString()).hasToString("1399-12-30 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(22)
    @Test
    void gregorianToJalaliLocalDateTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliString(zonedDateTime.toLocalDate(),"-",false).toString()).hasToString("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(23)
    @Test
    void gregorianToJalaliLocalDateWithTimeTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliString(zonedDateTime.toLocalDate(),"-",true).toString()).hasToString("1399-12-30 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(24)
    @Test
    void gregorianToJalaliLocalDateTimeTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliString(zonedDateTime.toLocalDateTime(),"-",false).toString()).hasToString("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(25)
    @Test
    void gregorianToJalaliLocalDateTimeWithTimeTest() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliString(zonedDateTime.toLocalDateTime(),"-",true).toString()).hasToString("1399-12-30 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(26)
    @Test
    void gregorianToJalaliCustomDate() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.gregorianToJalaliCustomDate(cal.getTime()).toString()).hasToString("CustomDate{1399-12-30}");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Order(27)
    @Test
    void gregorianToJalaliCustomDateTime() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.gregorianToJalaliCustomDateTime(cal.getTime()).toString()).hasToString("CustomDateTime{1399-12-30 00:00:00}");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای اصلاح کننده متناسب با زبان لوکال و تفاوت زمانی بین دو تاریخ--------------------------------------------------

    @Order(28)
    @Test
    void fixToLocaleDateTest() {
        try {
            assertThat(CalendarTools.fixToLocaleDate("2021/03/20", "-", new Locale("fa", "IR"))).isEqualTo("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(29)
    @Test
    void fixToLocaleDateTimeTest() throws ParseException, UtilityException {
        assertThat(CalendarTools.fixToLocaleDateTime("2021/03/20 00:00:00", "-", new Locale("fa", "IR"))).isEqualTo("1399-12-30 00:00:00");
    }

    @Order(30)
    @Test
    void getTwoDateDifferenceSecondTest() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(simpleDateFormat.parse("2021-03-20 00:00:10"));
            assertThat(CalendarTools.getTwoDateDifference(cal1.getTime(), cal2.getTime(), DateTimeUnitEnum.SECOND)).isEqualTo(10);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(31)
    @Test
    void getTwoDateDifferenceMinuteTest() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(simpleDateFormat.parse("2021-03-20 00:10:00"));
            assertThat(CalendarTools.getTwoDateDifference(cal1.getTime(), cal2.getTime(), DateTimeUnitEnum.MINUTE)).isEqualTo(10);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(32)
    @Test
    void getTwoDateDifferenceHourTest() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(simpleDateFormat.parse("2021-03-20 10:00:00"));
            assertThat(CalendarTools.getTwoDateDifference(cal1.getTime(), cal2.getTime(), DateTimeUnitEnum.HOUR)).isEqualTo(10);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(33)
    @Test
    void getTwoDateDifferenceDayTest() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(simpleDateFormat.parse("2021-03-19 00:00:00"));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.getTwoDateDifference(cal1.getTime(), cal2.getTime(), DateTimeUnitEnum.DAY)).isEqualTo(1);
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای تبدیلی CustomDate و CustomDateTime به Date--------------------------------------------------

    @Order(34)
    @Test
    void getDateFromCustomDateTest() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar cal = Calendar.getInstance();
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            CustomDate customDate = new CustomDate(cal.getTime());
            assertThat(CalendarTools.getDateFromCustomDate(customDate)).isEqualTo(cal.getTime());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(35)
    @Test
    void getDateFromCustomDateTimeTest() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar cal = Calendar.getInstance();
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            CustomDateTime customDateTime = new CustomDateTime(cal.getTime());
            assertThat(CalendarTools.getDateFromCustomDateTime(customDateTime)).isEqualTo(cal.getTime());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای بررسی کننده تاریخهای جلالی و میلادی--------------------------------------------------

    @Order(36)
    @Test
    void checkJalaliDateValidityTest() {
        try {
            assertThat(CalendarTools.checkJalaliDateValidity(1399, 8, 31)).isFalse();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(37)
    @Test
    void checkGregorianDateValidityTest() {
        try {
            assertThat(CalendarTools.checkGregorianDateValidity(2021, 18, 20)).isFalse();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(38)
    @Test
    void isTodayTest() {
        try {
            assertThat(CalendarTools.isToday(new Date())).isTrue();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(39)
    @Test
    void fixJalaliStringTest() {
        try {
            assertThat(CalendarTools.fixJalaliString("1361-06-25","/")).isEqualTo("1361/06/25");
            assertThat(CalendarTools.fixJalaliString("1361_06_25","/")).isEqualTo("1361/06/25");
            assertThat(CalendarTools.fixJalaliString("13610625","/")).isEqualTo("1361/06/25");
            assertThat(CalendarTools.fixJalaliString("610625","/")).isEqualTo("1361/06/25");
            assertThat(CalendarTools.fixJalaliString("000625","/")).isEqualTo("1400/06/25");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }
}
