
package com.motaharinia.msutility.custom.customvalidation.daterange;


import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import com.motaharinia.msutility.tools.calendar.CalendarTools;
import com.motaharinia.msutility.custom.customexception.utility.UtilityException;
import com.motaharinia.msutility.custom.customfield.CustomDate;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی محدوده تاریخ<br>
 * فقط برای فیلدهای از نوع CustomDate میتوان از این اعتبارسنجی استفاده کرد
 */
@Slf4j
public class DateRangeValidator implements ConstraintValidator<DateRange, CustomDate> {

    private static final String MESSAGE_FROM = "CUSTOM_VALIDATION.DATE_RANGE_FROM";
    private static final String MESSAGE_TO = "CUSTOM_VALIDATION.DATE_RANGE_TO";
    private static final String MESSAGE_FROM_TO = "CUSTOM_VALIDATION.DATE_RANGE_FROM_TO";

    private static final String DATE_UNLIMITED = "unlimited";
    private static final String DATE_TODAY = "today";

    private static final String UTILITY_EXCEPTION_DATE_RANGE_VALIDATOR = "UTILITY_EXCEPTION.DateRangeValidator.validateDateRange() exception:";

    private final DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String message;
    private String from;
    private String to;

    @Override
    public void initialize(DateRange dateRange) {
        from = dateRange.from();
        to = dateRange.to();
        message = dateRange.message();
    }

    @Override
    public boolean isValid(CustomDate customDate, ConstraintValidatorContext cvc) {
        if (CustomDate.isEmpty(customDate)) {
            return true;
        }

        if (!validateParameters(from)) {
            return false;
        }

        if (!validateParameters(to)) {
            return false;
        }

        boolean result;
        try {
            result = validateDateRange(customDate);
        } catch (UtilityException e) {
            result = false;
        }
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return result;
    }

    private static boolean validateParameters(String param) {
        if (param.equalsIgnoreCase(DATE_UNLIMITED) || param.equalsIgnoreCase(DATE_TODAY)) {
            return true;
        } else if (param.matches(CustomValidationRegularExceptionEnum.DATE.getValue())) {
            String[] paramParts = param.split("-");
            try {
                return CalendarTools.checkJalaliDateValidity(Integer.parseInt(paramParts[0]), Integer.parseInt(paramParts[1]), Integer.parseInt(paramParts[2]));
            } catch (UtilityException e) {
                return false;
            }
        } else {
            return false;
        }

    }

    private boolean validateDateRange(@NotNull CustomDate customDate) {
        boolean result;
        if (from.equalsIgnoreCase(DATE_UNLIMITED) && to.equalsIgnoreCase(DATE_UNLIMITED)) {
            //In this case, no need to validate date, every data is acceptable
            result = true;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, customDate.getYear());
            calendar.set(Calendar.MONTH, customDate.getMonth() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, customDate.getDay());
            Date userEnteredDate = calendar.getTime();

            if (from.equalsIgnoreCase(DATE_UNLIMITED) && !to.equalsIgnoreCase(DATE_UNLIMITED)) {
                result = validateDateRangeFromUnlimited(userEnteredDate, to);
            } else if (!from.equalsIgnoreCase(DATE_UNLIMITED) && to.equalsIgnoreCase(DATE_UNLIMITED)) {
                result = validateDateRangeToUnlimited(userEnteredDate, from);
            } else {
                result = validateDateRangeFromTo(userEnteredDate, from, to);
            }
        }
        return result;
    }

    private boolean validateDateRangeFromUnlimited(@NotNull Date userEnteredDate,@NotNull  String to) {
        boolean result = false;
        if (to.equalsIgnoreCase(DATE_TODAY)) {
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            c.add(Calendar.DATE, 1);
            result = userEnteredDate.before(c.getTime());
            if (!result) {
                message = MESSAGE_TO + "::" + to;
            }
        } else {
            //convert TO date from jalali to gregorian
            String gregorianToString = CalendarTools.jalaliToGregorianString(to, "-", "-");
            try {
                Date gregorianToDate = simpleDateFormat.parse(gregorianToString);
                result = userEnteredDate.before(gregorianToDate);
                if (!result) {
                    message = MESSAGE_TO + "::" + to;
                }
            } catch (ParseException exception) {
                log.error(UTILITY_EXCEPTION_DATE_RANGE_VALIDATOR, exception);
            }
        }
        return result;
    }

    private boolean validateDateRangeToUnlimited(@NotNull Date userEnteredDate,@NotNull  String from) {
        boolean result = false;
        if (from.equalsIgnoreCase(DATE_TODAY)) {
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            c.add(Calendar.DATE, -1);
            result = userEnteredDate.after(c.getTime());
            if (!result) {
                message = MESSAGE_FROM + "::" + from;
            }
        } else {
            //convert TO date from jalali to gregorian
            String gregorianFromString = CalendarTools.jalaliToGregorianString(from, "-", "-");
            try {
                Date gregorianFromDate = simpleDateFormat.parse(gregorianFromString);
                result = userEnteredDate.after(gregorianFromDate);
                if (!result) {
                    message = MESSAGE_FROM + "::" + from;
                }
            } catch (ParseException exception) {
                log.error(UTILITY_EXCEPTION_DATE_RANGE_VALIDATOR, exception);
            }
        }
        return result;
    }

    private boolean validateDateRangeFromTo(@NotNull Date userEnteredDate,@NotNull String from,@NotNull String to) {
        boolean result = false;
        Date fromDate;
        Date toDate;

        if (from.equalsIgnoreCase(DATE_TODAY)) {
            fromDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(fromDate);
            c.add(Calendar.DATE, -1);
            fromDate = c.getTime();
        } else {
            String gregorianFromString = CalendarTools.jalaliToGregorianString(from, "-", "-");
            try {
                fromDate = simpleDateFormat.parse(gregorianFromString);
            } catch (ParseException exception) {
                fromDate = null;
                log.error(UTILITY_EXCEPTION_DATE_RANGE_VALIDATOR, exception);
            }
        }

        if (to.equalsIgnoreCase(DATE_TODAY)) {
            toDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(toDate);
            c.add(Calendar.DATE, 1);
            toDate = c.getTime();
        } else {
            String gregorianToString = CalendarTools.jalaliToGregorianString(to, "-", "-");
            try {
                toDate = simpleDateFormat.parse(gregorianToString);
            } catch (ParseException exception) {
                toDate = null;
                log.error(UTILITY_EXCEPTION_DATE_RANGE_VALIDATOR, exception);
            }
        }

        if (fromDate != null && toDate != null) {
            if (fromDate.compareTo(toDate) > 0) {
                message += "[from>to]";
            } else {
                result = userEnteredDate.after(fromDate) && userEnteredDate.before(toDate);
                if (!result) {
                    message = MESSAGE_FROM_TO + "::" + from + "," + to;
                }
            }

        }
        return result;
    }
}
