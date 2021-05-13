package com.motaharinia.msutility.custom.customvalidation;

import com.motaharinia.msutility.custom.customfield.CustomDate;
import com.motaharinia.msutility.custom.customvalidation.sample.*;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author https://github.com/motaharinia<br>
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
            dto.setCompanyPhoneNo("22555");
            dto.setDateRange1(new CustomDate(new Date()));
            dto.setDateRange2(new CustomDate(new Date()));
            dto.setDateRange3(new CustomDate(new Date()));
            dto.setDateRange4(new CustomDate(new Date()));
            dto.setDecimalCount1(12.582);
            dto.setDecimalCount2(12.582);
            dto.setDoubleRange(12.582);
            dto.setEmail("eng.motahari@gmail.com");
            dto.setIntegerRange(12);
            dto.setLatinCharacter("this is a latin character string");
            dto.setListLength1(Arrays.asList("item1", "item2", "item3"));
            dto.setListLength2(Arrays.asList("item1", "item2", "item3"));
            dto.setListNoDuplicate(Arrays.asList("item1", "item2", "item3"));
            dto.setListNoDuplicateByFields(Arrays.asList(new ListNoDuplicateByFieldsDto("f11", "f12"), new ListNoDuplicateByFieldsDto("f21", "f22"), new ListNoDuplicateByFieldsDto("f31", "f32")));
            dto.setMobile("09124376251");
            dto.setNationalCode("0557093007");
            dto.setOrganizationEconomicCode("12345678901234");
            dto.setOrganizationNationalCode("12345678901");
            dto.setOrganizationRegistrationNo("2315");
            dto.setPassword1("123456");
            dto.setPassword2("123");
            dto.setPassword3("asd123ASD!@#");
            dto.setPassword4("123$Abcd~");
            dto.setPersianCharacters("مصطفی مطهری نیا");
            dto.setPersonPhone("88888888");
            dto.setPostalCode("1234567890");
            dto.setPrice(new BigDecimal(15000));
            dto.setRequired1("Mostafa Motaharinia");
            dto.setRequired2(15000);
            dto.setRequired3(true);
            dto.setRequired4(Arrays.asList("item1", "item2"));
            dto.setRequired5(sampleMap);
            dto.setStringLength1("abc");
            dto.setStringLength2("abc");
            dto.setStringPattern("4012888888881881");
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
