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
 * این کلاس برای تبدیل رشته به رشته جیسون برای ارسال به سمت کلاینت میباشد<br>
 * این کلاس در صورتی که رشته جیسون داده شده از نوع پیامهای کاربری ترجمه شدنی نباشد خودش را خروجی میدهد<br>
 * در غیر این صورت آن را ترجمه مینماید
 */
public class JsonSerializerString extends JsonSerializer<String> {

    private final MessageSource messageSource;

    public JsonSerializerString(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void serialize(String objString, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
        if ((!ObjectUtils.isEmpty(messageSource)) && objString.startsWith("USER_MESSAGE.")) {
            objString = StringTools.translateCustomMessage(messageSource, objString);
        }
        jsonGen.writeString(objString);
    }
}
