package com.motaharinia.msutility.custom.customjson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.motaharinia.msutility.tools.calendar.CalendarTools;
import com.motaharinia.msutility.custom.customfield.CustomDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * @author eng.motahari@gmail.com<br>
 *     این کلاس برای تبدیل کلاس تاریخ-زمان میلادی به رشته جیسون تاریخ-زمان جلالی برای ارسال به سمت کلاینت میباشد
 */
@Slf4j
public class JsonSerializerCustomDateTime extends JsonSerializer<CustomDateTime> {

    @Override
    public void serialize(CustomDateTime customDateTime, JsonGenerator jg, SerializerProvider sp)  {
        try {
            //برای حفظ ترتیب درج بجای هشمپ از لینکدهشمپ استفاده میکنیم
            LinkedHashMap<String, String> output = new LinkedHashMap<>();
            if(CustomDateTime.isEmpty(customDateTime)){
                output.put("year", "");
                output.put("month", "");
                output.put("day", "");
                output.put("hour", "");
                output.put("minute", "");
                output.put("second", "");
            }else{
                Locale currentLocale = LocaleContextHolder.getLocale();
                if (currentLocale.getLanguage().equals("fa")) {
                    Date date= CalendarTools.getDateFromCustomDateTime(customDateTime);
                    customDateTime = CalendarTools.gregorianToJalaliCustomDateTime(date);
                }
                output.put("year", customDateTime.getYear().toString());
                output.put("month", CalendarTools.fixOneDigit(customDateTime.getMonth().toString()));
                output.put("day", CalendarTools.fixOneDigit(customDateTime.getDay().toString()));
                output.put("hour", CalendarTools.fixOneDigit(customDateTime.getHour().toString()));
                output.put("minute", CalendarTools.fixOneDigit(customDateTime.getMinute().toString()));
                output.put("second", CalendarTools.fixOneDigit(customDateTime.getSecond().toString()));
            }
            jg.writeObject(output);
        } catch (Exception exception) {
            log.error("UTILITY_EXCEPTION.JsonSerializerCustomDateTime.serialize() exception:",exception);
        }
    }
}
