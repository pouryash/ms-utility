package com.motaharinia.msutility.custom.customjson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.motaharinia.msutility.tools.calendar.CalendarTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author eng.motahari@gmail.com<br>
 * این کلاس برای تبدیل کلاس Date میلادی به رشته جیسون تاریخ-زمان جلالی برای ارسال به سمت کلاینت میباشد
 */
@Slf4j
public class JsonSerializerDate extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGen, SerializerProvider sp) {
        if (!ObjectUtils.isEmpty(date)) {
            try {
                jsonGen.writeString(CalendarTools.fixToLocaleDate(date, "/", LocaleContextHolder.getLocale()));
            } catch (Exception exception) {
                log.error("UTILITY_EXCEPTION.JsonSerializerDate.serialize() exception:", exception);
            }
        }
    }

}
