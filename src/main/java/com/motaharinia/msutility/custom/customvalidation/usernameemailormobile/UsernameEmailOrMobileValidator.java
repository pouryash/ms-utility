package com.motaharinia.msutility.custom.customvalidation.usernameemailormobile;

import com.motaharinia.msutility.custom.customvalidation.CustomValidationRegularExceptionEnum;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author eng.motahari@gmail.com<br>
 * کلاس بررسی کننده انوتیشن اعتبارسنجی کلمه کاربری با پست الکترونیکی یا شماره تلفن همراه<br>
 * فقط برای فیلدهای از نوع String میتوان از این اعتبارسنجی استفاده کرد
 */
public class UsernameEmailOrMobileValidator implements ConstraintValidator<UsernameEmailOrMobile, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext cvc) {
        if (ObjectUtils.isEmpty(username)) {
            return true;
        }
        return username.toLowerCase().matches(CustomValidationRegularExceptionEnum.EMAIL.getValue()) || username.matches(CustomValidationRegularExceptionEnum.MOBILE.getValue());
    }
}
