package com.motaharinia.msutility.custom.customjson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.motaharinia.msutility.tools.calendar.CalendarTools;
import com.motaharinia.msutility.custom.customfield.CustomDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;


/**
 * @author https://github.com/motaharinia<br>
 * این کلاس برای تبدیل کلاس تاریخ میلادی به رشته جیسون تاریخ جلالی برای ارسال به سمت کلاینت میباشد
 */
@Slf4j
public class JsonSerializerCustomDate extends JsonSerializer<CustomDate> {

    @Override
    public void serialize(CustomDate customDate, JsonGenerator jg, SerializerProvider sp) {
        try {
            //برای حفظ ترتیب درج بجای هشمپ از لینکدهشمپ استفاده میکنیم
            LinkedHashMap<String, String> output = new LinkedHashMap<>();
            if (CustomDate.isEmpty(customDate)) {
                output.put("year", "");
                output.put("month", "");
                output.put("day", "");
            } else {
                Locale currentLocale = LocaleContextHolder.getLocale();
                if (currentLocale.getLanguage().equals("fa")) {
                    Date date = CalendarTools.getDateFromCustomDate(customDate);
                    customDate = CalendarTools.gregorianToJalaliDate(date);
                }
                output.put("year", customDate.getYear().toString());
                output.put("month", CalendarTools.fixOneDigit(customDate.getMonth().toString()));
                output.put("day", CalendarTools.fixOneDigit(customDate.getDay().toString()));
            }
            jg.writeObject(output);
        } catch (Exception exception) {
            log.error("UTILITY_EXCEPTION.JsonSerializerCustomDate.serialize() exception:",exception);
        }
    }
}
