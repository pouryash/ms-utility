package com.motaharinia.msutility.custom.customjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customfield.CustomDateTime;
import com.motaharinia.msutility.custom.customjson.deserializer.JsonDeserializerString;
import com.motaharinia.msutility.custom.customjson.serializer.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.MessageSource;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author eng.motahari@gmail.com<br>
 * این کلاس مپر برای تبدیل کلاسهای مدل به رشته جیسون و برعکس استفاده میشود. تفاوت آن نسبت به مپر پیش فرض داشتن مسیج سورس برای ترجمه است
 */

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper(MessageSource messageSource) {
        //تنظیم سریالایزر نال
//        DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
//        sp.setNullValueSerializer(new JsonSerializerNullValue());
//        this.setSerializerProvider(sp);

        SimpleModule simpleModule = new SimpleModule();
        //تنظیم سرایالایزرها برای تبدیل خودکار اطلاعات از مدل جاوا به رشته جیسون کلاینت
        simpleModule.addSerializer(Date.class, new JsonSerializerDate());
        simpleModule.addSerializer(CustomDate.class, new JsonSerializerCustomDate());
        simpleModule.addSerializer(CustomDateTime.class, new JsonSerializerCustomDateTime());
        if (!ObjectUtils.isEmpty(messageSource)) {
            simpleModule.addSerializer(String.class, new JsonSerializerString(messageSource));
//            simpleModule.addSerializer(CustomEnum.class, new JsonSerializerEnum(messageSource));
        }


        //تنظیم دیسریالایزرها برای تبدیل خودکار اطلاعات از رشته جیسون کلاینت به مدل جاوا
        simpleModule.addDeserializer(String.class, new JsonDeserializerString());
        this.registerModule(simpleModule);
    }

    public CustomObjectMapper() {
        //تنظیم سریالایزر نال
//        DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
//        sp.setNullValueSerializer(new JsonSerializerNullValue());
//        this.setSerializerProvider(sp);

        SimpleModule simpleModule = new SimpleModule();
        //تنظیم سرایالایزرها برای تبدیل خودکار اطلاعات از مدل جاوا به رشته جیسون کلاینت
        simpleModule.addSerializer(Date.class, new JsonSerializerDate());
        simpleModule.addSerializer(CustomDate.class, new JsonSerializerCustomDate());
        simpleModule.addSerializer(CustomDateTime.class, new JsonSerializerCustomDateTime());

        //تنظیم دیسریالایزرها برای تبدیل خودکار اطلاعات از رشته جیسون کلاینت به مدل جاوا
        simpleModule.addDeserializer(String.class, new JsonDeserializerString());
        this.registerModule(simpleModule);
    }

    @Override
    public ObjectMapper copy() {
        return this.toBuilder().build();
    }

}
