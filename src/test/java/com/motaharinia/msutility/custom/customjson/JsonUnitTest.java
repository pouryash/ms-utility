package com.motaharinia.msutility.custom.customjson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.motaharinia.msutility.custom.customjson.sample.EtcItemGender;
import com.motaharinia.msutility.tools.calendar.CalendarTools;
import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customjson.sample.JsonDto;
import com.motaharinia.msutility.custom.customjson.sample.MembershipRequestFrontDtoUpdate;
import com.motaharinia.msutility.custom.customdto.ClientResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس تست SearchDataDto
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonUnitTest {

    private CustomObjectMapper mapper =null;

    /**
     * این متد مقادیر پیش فرض قبل از هر تست این کلاس تست را مقداردهی اولیه میکند
     */
    @BeforeEach
    void initUseCase() {
        Locale.setDefault(new Locale("fa", "IR"));
        mapper= new CustomObjectMapper(getMessageSource());
        //این قطعه تنظیمات در پروژه های دیگر زمان بالا آمدن context از فایلهای properties میتواند خوانده  و تنظیم شود
        mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,true);
    }

    /**
     * این متد بعد از هر تست این کلاس اجرا میشود
     */
    @AfterEach
    void finalizeEach() {
        Locale.setDefault(Locale.US);
    }


    private ReloadableResourceBundleMessageSource getMessageSource(){
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:lang/businessexception","classpath:lang/customvalidation","classpath:lang/usermessage","classpath:lang/comboitem");
        messageSource.setCacheSeconds(5);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Order(1)
    @Test
    void serializeDeserializeTest() {
        try {
            //تاریخ میلادی موجود در دیتابیس
            Date dbDate = new Date();

            //تاریخ میلادی موجود در دیتابیس به روش Instant
            Instant dateOfBirth = Instant.now();

            //مدل جاوا حاوی تاریخ میلادی برای ارسال به کلاینت
            JsonDto jsonDto = new JsonDto(new CustomDate(dbDate), Stream.of(EtcItemGender.values()).map(EtcItemGender::getValue).collect(Collectors.toList()),dateOfBirth.toEpochMilli());
            ClientResponseDto<JsonDto> dto = new ClientResponseDto<>(jsonDto,"USER_MESSAGE.FORM_SUBMIT_SUCCESS");

            //سریالایز مدل جاوا در کنترلر در زمان ارسال به کلاینت
            String jsonString= this.mapper.writeValueAsString(dto);

            //تستهای سریالایز
            //تست عدم وجود خطا
            assertThat(jsonString).isNotNull();
            //تست تبدیل تاریخ میلادی به جلالی جهت ارسال به کلاینت
            //برای اینکه بتوانیم سال شمسی را در رشته جیسون پیدا کنیم این شیی را میسازیم
            CustomDate jalaliCustomDate= CalendarTools.gregorianToJalaliDate(dbDate);
            assertThat(jsonString).contains(jalaliCustomDate.getYear().toString());

            //تست  رشته ترجمه "ای تی سی آیتم" به زبان فارسی
            assertThat(jsonString).contains("زن");

            //دیسریالایز رشته جیسون دریافتی از کلاینت به مدل در کنترلر
            dto = this.mapper.readValue(jsonString, new TypeReference<ClientResponseDto<JsonDto>>() {
            });

            //تست های دیسریالایز
            //تست عدم وجود خطا
            assertThat(dto).isNotNull();
            //تست تبدیل تاریخ جلالی دریافتی از کلاینت به میلادی
            assertThat(dto.getData().getCustomDate()).isNotNull();
            Calendar dbDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"));
            dbDateCalendar.setTime(dbDate);
            assertThat(dto.getData().getCustomDate().getYear()).isEqualTo(dbDateCalendar.get(Calendar.YEAR));
            assertThat(dto.getData().getCustomDate().getMonth()).isEqualTo(dbDateCalendar.get(Calendar.MONTH)+1);
            assertThat(dto.getData().getCustomDate().getDay()).isEqualTo(dbDateCalendar.get(Calendar.DAY_OF_MONTH));
            assertThat(Date.from(Instant.ofEpochMilli(dto.getData().getDateOfBirth()))).isEqualTo(Date.from(dateOfBirth));

        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Order(2)
    @Test
    void serializeEmptyDtoTest() {
        try {
            MembershipRequestFrontDtoUpdate membershipRequestFrontDtoUpdate = new MembershipRequestFrontDtoUpdate();
            String jsonString= this.mapper.writeValueAsString(membershipRequestFrontDtoUpdate);
            assertThat(jsonString).isNotNull();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

}
