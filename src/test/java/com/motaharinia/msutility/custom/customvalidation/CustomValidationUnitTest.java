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
            CustomValidationTestModel model = new CustomValidationTestModel();
            model.setCompanyPhoneNo("22555");
            model.setDateRange1(new CustomDate(new Date()));
            model.setDateRange2(new CustomDate(new Date()));
            model.setDateRange3(new CustomDate(new Date()));
            model.setDateRange4(new CustomDate(new Date()));
            model.setDecimalCount1(12.582);
            model.setDecimalCount2(12.582);
            model.setDoubleRange(12.582);
            model.setEmail("eng.motahari@gmail.com");
            model.setIntegerRange(12);
            model.setLatinCharacter("this is a latin character string");
            model.setListLength1(Arrays.asList("item1", "item2", "item3"));
            model.setListLength2(Arrays.asList("item1", "item2", "item3"));
            model.setListNoDuplicate(Arrays.asList("item1", "item2", "item3"));
            model.setListNoDuplicateByFields(Arrays.asList(new ListNoDuplicateByFieldsModel("f11", "f12"), new ListNoDuplicateByFieldsModel("f21", "f22"), new ListNoDuplicateByFieldsModel("f31", "f32")));
            model.setMobile("09124376251");
            model.setNationalCode("0557093007");
            model.setOrganizationEconomicCode("12345678901234");
            model.setOrganizationNationalCode("12345678901");
            model.setOrganizationRegistrationNo("2315");
            model.setPassword1("123456");
            model.setPassword2("123");
            model.setPassword3("123$Abcd");
            model.setPassword4("123$Abcd~");
            model.setPersianCharacters("مصطفی مطهری نیا");
            model.setPersonPhone("88888888");
            model.setPostalCode("1234567890");
            model.setPrice(new BigDecimal(15000));
            model.setRequired1("Mostafa Motaharinia");
            model.setRequired2(15000);
            model.setRequired3(true);
            model.setRequired4(Arrays.asList("item1", "item2"));
            model.setRequired5(sampleMap);
            model.setStringLength1("abc");
            model.setStringLength2("abc");
            model.setStringPattern("4012888888881881");
            model.setUsername1("09124376251");
            model.setUsername2("eng.motahari@gmail.com");
            Set<ConstraintViolation<CustomValidationTestModel>> violations = validator.validate(model);
            violations.forEach(item -> System.out.println(item.toString()));
            assertThat(violations).isEmpty();
        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


}
