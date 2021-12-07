package com.motaharinia.msutility.custom.customvalidation.postalcode;

import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی کد پستی <br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {
    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(postalCode)) {
            return true;
        }
        return postalCode.matches(CustomValidationRegularExceptionEnum.POSTAL_CODE.getValue());
    }
}
