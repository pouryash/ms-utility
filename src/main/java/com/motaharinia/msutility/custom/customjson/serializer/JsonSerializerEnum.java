package com.motaharinia.msutility.custom.customjson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.motaharinia.msutility.tools.string.StringTools;
import org.springframework.context.MessageSource;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @author https://github.com/motaharinia<br>
 * این کلاس برای تبدیل مقدار enum به رشته جیسون برای ارسال به سمت کلاینت میباشد<br>
 * این کلاس در صورتی که messageSource ورودی نداشته باشد خود enum را خروجی میدهد<br>
 * در غیر این صورت اگر رشته مورد نظر از نوع فیلدهای با مقادیر ثابت باشد آن را ترجمه مینماید
 */
public class JsonSerializerEnum extends JsonSerializer<CustomEnum> {

    private final MessageSource messageSource;

    public JsonSerializerEnum(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void serialize(CustomEnum objEnum, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
        if (!ObjectUtils.isEmpty(messageSource)) {
            jsonGen.writeString(StringTools.translateCustomMessage(messageSource, objEnum.getValue()));
        } else {
            jsonGen.writeString(objEnum.toString());
        }
    }
}
