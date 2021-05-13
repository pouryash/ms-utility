package com.motaharinia.msutility.tools.calendar;

import com.motaharinia.msutility.custom.customexception.utility.UtilityException;
import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customfield.CustomDateTime;
import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
    void initUseCase() {
        Locale.setDefault(Locale.US);
    }

    /**
     * این متد بعد از هر تست این کلاس اجرا میشود
     */
    @AfterEach
    void finalizeEach() {
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
    void jalaliToGregorianDate2Test() {
        try {
            assertThat(CalendarTools.jalaliToGregorianDate("1399/12/30", "/", "-")).isEqualTo("2021-03-20");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(9)
    @Test
    void jalaliToGregorianDate3Test() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.jalaliToGregorianDate("1399/12/30", "/", ZoneId.systemDefault())).isEqualTo(zonedDateTime.toInstant());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای تبدیل تاریخ-زمان جلالی به میلادی--------------------------------------------------

    @Order(10)
    @Test
    void jalaliToGregorianDateTimeTest() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.jalaliToGregorianDateTime("1399/12/30 00:00:00", "/")).isEqualTo(cal.getTime());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(11)
    @Test
    void jalaliToGregorianDateTime2Test() {
        try {
            assertThat(CalendarTools.jalaliToGregorianDateTime("1399/12/30 00:00:00", "/", "-")).isEqualTo("2021-03-20 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(12)
    @Test
    void jalaliToGregorianDateTime3Test() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.jalaliToGregorianDateTime("1399/12/30 00:00:00", "/", ZoneId.systemDefault())).isEqualTo(zonedDateTime.toInstant());
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای تبدیل تاریخ میلادی به جلالی--------------------------------------------------

    @Order(13)
    @Test
    void gregorianToJalaliDateTest() {
        try {
            assertThat(CalendarTools.gregorianToJalaliDate("2021/03/20", "/", "-")).isEqualTo("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(14)
    @Test
    void gregorianToJalaliDate2Test() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.gregorianToJalaliDate(cal.getTime()).toString()).hasToString("CustomDate{1399-12-30}");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(15)
    @Test
    void gregorianToJalaliDate3Test() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliDate(zonedDateTime.toInstant(),"-").toString()).hasToString("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای تبدیل تاریخ-زمان میلادی به جلالی--------------------------------------------------
    @Order(16)
    @Test
    void gregorianToJalaliDateTimeTest() {
        try {
            assertThat(CalendarTools.gregorianToJalaliDateTime("2021/03/20 00:00:00", "/", "-")).isEqualTo("1399-12-30 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(17)
    @Test
    void gregorianToJalaliDateTime2Test() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            cal.setTime(simpleDateFormat.parse("2021-03-20 00:00:00"));
            assertThat(CalendarTools.gregorianToJalaliDateTime(cal.getTime()).toString()).hasToString("CustomDateTime{1399-12-30 00:00:00}");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(18)
    @Test
    void gregorianToJalaliDateTime3Test() {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.parse("2021-03-20T00:00:00.00"), ZoneId.systemDefault());
            assertThat(CalendarTools.gregorianToJalaliDateTime(zonedDateTime.toInstant(),"-").toString()).hasToString("1399-12-30 00:00:00");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    //--------------------------------------------------متدهای اصلاح کننده متناسب با زبان لوکال و تفاوت زمانی بین دو تاریخ--------------------------------------------------

    @Order(19)
    @Test
    void fixToLocaleDateTest() {
        try {
            assertThat(CalendarTools.fixToLocaleDate("2021/03/20", "-", new Locale("fa", "IR"))).isEqualTo("1399-12-30");
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(20)
    @Test
    void fixToLocaleDateTimeTest() throws ParseException, UtilityException {
        assertThat(CalendarTools.fixToLocaleDateTime("2021/03/20 00:00:00", "-", new Locale("fa", "IR"))).isEqualTo("1399-12-30 00:00:00");
    }

    @Order(21)
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

    @Order(22)
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

    @Order(23)
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

    @Order(24)
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

    @Order(25)
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

    @Order(26)
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

    @Order(27)
    @Test
    void checkJalaliDateValidityTest() {
        try {
            assertThat(CalendarTools.checkJalaliDateValidity(1399, 8, 31)).isFalse();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(28)
    @Test
    void checkGregorianDateValidityTest() {
        try {
            assertThat(CalendarTools.checkGregorianDateValidity(2021, 18, 20)).isFalse();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Order(29)
    @Test
    void isTodayTest() {
        try {
            assertThat(CalendarTools.isToday(new Date())).isTrue();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }
}
