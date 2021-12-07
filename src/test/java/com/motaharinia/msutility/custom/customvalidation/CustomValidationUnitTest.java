package com.motaharinia.msutility.custom.customvalidation;

import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customvalidation.sample.*;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.InstanceOfAssertFactories.INSTANT;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس تست CustomValidation
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomValidationUnitTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

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


    @Order(1)
    @Test
    void customValidationTest() {
        try {
            Map<Integer,String> sampleMap = new HashMap<>();
            sampleMap.put(1,"A");
            sampleMap.put(2,"B");
            sampleMap.put(3,"C");


            Locale.setDefault(new Locale("fa", "IR"));

            CustomValidationTestDto dto = new CustomValidationTestDto();
            //CompanyPhone:
            dto.setCompanyPhoneNo("22555");
            //CustomDateRange:
            dto.setCustomDateRange1(new CustomDate(new Date()));
            dto.setCustomDateRange2(new CustomDate(new Date()));
            dto.setCustomDateRange3(new CustomDate(new Date()));
            dto.setCustomDateRange4(new CustomDate(new Date()));
            //EpochMiliDateRange:
            dto.setEpochMiliDateRange1(Instant.now().toEpochMilli());
            dto.setEpochMiliDateRange2(Instant.now().toEpochMilli());
            dto.setEpochMiliDateRange3(Instant.now().toEpochMilli());
            dto.setEpochMiliDateRange4(Instant.now().toEpochMilli());
            //DecimalCount:
            dto.setDecimalCount1(12.582);
            dto.setDecimalCount2(12.582);
            //DoubleRange:
            dto.setDoubleRange(12.582);
            //Email:
            dto.setEmail("eng.motahari@gmail.com");
            //IntegerRange:
            dto.setIntegerRange(12);
            //LatinCharacters:
            dto.setLatinCharacter("this is a latin character string");
            //ListLength:
            dto.setListLength1(Arrays.asList("item1", "item2", "item3"));
            dto.setListLength2(Arrays.asList("item1", "item2", "item3"));
            //ListNoDuplicate:
            dto.setListNoDuplicate(Arrays.asList("item1", "item2", "item3"));
            //ListNoDuplicateByFields:
            dto.setListNoDuplicateByFields(Arrays.asList(new ListNoDuplicateByFieldsDto("f11", "f12"), new ListNoDuplicateByFieldsDto("f21", "f22"), new ListNoDuplicateByFieldsDto("f31", "f32")));
            //LongRange:
            dto.setLongRange1(12L);
            dto.setLongRange2(10000L);
            //Mobile:
            dto.setMobile("09124376251");
            //NationalCode:
            dto.setNationalCode("0557093007");
            //OrganizationEconomicCode:
            dto.setOrganizationEconomicCode("12345678901234");
            //OrganizationNationalCode:
            dto.setOrganizationNationalCode("12345678901");
            //OrganizationRegistrationNo:
            dto.setOrganizationRegistrationNo("2315");
            //Password:
            dto.setPassword1("123456");
            dto.setPassword2("123");
            dto.setPassword3("asd123ASD!@#");
            dto.setPassword4("123$Abcd~");
            //PersianCharacters:
            dto.setPersianCharacters("مصطفی مطهری نیا");
            //PersonPhone:
            dto.setPersonPhone("88888888");
            //PostalCode:
            dto.setPostalCode("1234567890");
            //Price:
            dto.setPrice(new BigDecimal(15000));
            //Required:
            dto.setRequired1("Mostafa Motaharinia");
            dto.setRequired2(15000);
            dto.setRequired3(true);
            dto.setRequired4(Arrays.asList("item1", "item2"));
            dto.setRequired5(sampleMap);
            //StringLength:
            dto.setStringLength1("abc");
            dto.setStringLength2("abc");
            //StringPattern (Visa Card. All Visa card numbers start with a 4. New cards have 16 digits. Old cards have 13):
            dto.setStringPattern("4012888888881881");
            //Url:
            dto.setUrl1("https://www.google.com");
            dto.setUrl2("http://www.google.com");
            dto.setUrl3("http://google.com/");
            dto.setUrl4("https://google.com/");
            dto.setUrl5("www.google.com");
            dto.setUrl6("google.com");
            dto.setUrl7("https://www.google.com.ua");
            dto.setUrl8("http://www.google.com.ua");
            dto.setUrl9("http://google.com.ua");
            dto.setUrl10("https://google.com.ua/");
            dto.setUrl11("www.google.com.ua");
            dto.setUrl12("google.com.ua");
            dto.setUrl13("https://mail.google.com");
            dto.setUrl14("http://mail.google.com");
            dto.setUrl15("mail.google.com");
            dto.setUrl16("http://mail.google.com:8080");
            dto.setUrl17("mail.google.com:8080");
            //UsernameEmailOrMobile:
            dto.setUsername1("09124376251");
            dto.setUsername2("eng.motahari@gmail.com");

            Set<ConstraintViolation<CustomValidationTestDto>> violations = validator.validate(dto);
            violations.forEach(item -> System.out.println(item.toString()));
            assertThat(violations).isEmpty();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


}
