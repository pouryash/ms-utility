package com.motaharinia.msutility.custom.customvalidation.mobile;

import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author https://github.com/motaharinia<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی تلفن همراه <br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(mobile)) {
            return true;
        }
        return mobile.matches(CustomValidationRegularExceptionEnum.MOBILE.getValue());
    }
}
